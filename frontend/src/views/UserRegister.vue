<template>
  <v-main>
    <v-container fluid fill-height>
      <v-card width="40%" class="ma-auto" v-if="!user.id">
        <v-card-title class="justify-center font-italic font-weight-bold">{{
          'front.14' | translate
        }}</v-card-title>
        <v-card-text>
          <v-form v-model="valid" ref="form">
            <v-text-field
              v-bind:label="$t('front.2')"
              prepend-icon="mdi-account-circle"
              v-model="user.username"
              :rules="usernameRules"
              required
            />
            <v-text-field
              v-bind:label="$t('front.4')"
              :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
              :type="show ? 'text' : 'password'"
              prepend-icon="mdi-lock"
              v-model="user.password"
              :rules="passwordRules"
              error-count="6"
              required
              @click:append="show = !show"
            />
            <v-file-input
              v-bind:label="$t('front.18')"
              accept="image/*"
              ref="fileupload"
              clearable
              @change="onFileSelected"
            />
            <img class="photo" v-show="user.photo" :src="photoDisplay" />
            <v-text-field
              v-bind:label="$t('front.19')"
              type="email"
              prepend-icon="mdi-at"
              v-model="user.email"
              :rules="emailRules"
              required
            />
            <v-checkbox v-model="user.admin" label="Admin" />
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-btn color="info" class="mb-3 mx-auto" @click="back">{{
            'front.15' | translate
          }}</v-btn>

          <v-btn color="info" class="mb-3 mx-auto" @click="registerUser">{{
            'front.17' | translate
          }}</v-btn>
        </v-card-actions>
      </v-card>

      <v-card width="40%" class="ma-auto" v-else>
        <v-card-title class="justify-center font-italic font-weight-bold">{{
          'front.16' | translate
        }}</v-card-title>
        <v-card-text>
          <v-form v-model="valid" ref="form">
            <v-text-field
              v-bind:label="$t('front.2')"
              prepend-icon="mdi-account-circle"
              v-model="user.username"
              :rules="usernameRules"
              required
            />
            <v-file-input
              v-bind:label="$t('front.18')"
              accept="image/*"
              ref="fileupload"
              @change="onFileSelected"
              clearable
            />
            <img class="photo" v-show="photoDisplay" :src="photoDisplay" />
            <v-text-field
              v-bind:label="$t('front.19')"
              type="email"
              prepend-icon="mdi-at"
              v-model="user.email"
              :rules="emailRules"
              required
            />
            <v-checkbox v-model="user.admin" label="Admin" />
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-btn color="info" class="mb-3 mx-auto" @click="back">{{
            'front.15' | translate
          }}</v-btn>

          <v-btn color="info" class="mb-3 mx-auto" @click="registerUser">{{
            'front.17' | translate
          }}</v-btn>
        </v-card-actions>
      </v-card>
    </v-container>
  </v-main>
</template>

<script lang="ts">
import Vue from 'vue'
import { translate } from '../i18n'
import { mutations, getters } from '@/observables/User'
import { logout } from '@/helpers/Logout'
import { VForm } from '@/types/VForm'
import { User } from '@/types/User'

export default Vue.extend({
  data() {
    return {
      logout: [logout],
      user: {
        id: 0,
        email: '',
        username: '',
        password: '',
        admin: false,
        photo: undefined,
      } as User,
      photoDisplay: '' as string | null,
      valid: false,
      show: false,
      usernameRules: [(v: string) => !!v || `${translate('message.16')}`],
      passwordRules: [
        (v: string) => !!v || `${translate('message.17')}`,
        (v: string) =>
          /^(?=.*[a-z])/.test(v) || `${translate('message.passwordNotLower')}`,
        (v: string) =>
          /^(?=.*[A-Z])/.test(v) ||
          `${translate('message.passwordNotCapital')}`,
        (v: string) =>
          /^(?=.*[0-9])/.test(v) || `${translate('message.passwordNotDigit')}`,
        (v: string) =>
          /^(?=.*[@#$%^&+=])/.test(v) ||
          `${translate('message.passwordNotSimbol')}`,
        (v: string) =>
          /^(?=.{8,16}$)/.test(v) ||
          `${translate('message.passwordNotLength')}`,
      ],
      emailRules: [
        (v: string) => !!v || `${translate('message.18')}`,
        (v: string) =>
          /[a-z0-9]+(?:\.[a-z0-9]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/gi.test(
            v
          ) || `${translate('message.19')}`,
      ],
    }
  },

  created() {
    if (this.$route.params.id) {
      const { id, admin, username, email, photoUrl } = this.$route.params
      this.user = {
        id: Number(id),
        admin: admin === 'true',
        password: '',
        username,
        email,
        photoUrl,
      }
    }

    if (this.user.photoUrl) {
      this.photoDisplay = '/api/uploads/' + this.user.photoUrl
    }
  },

  computed: {
    ...getters,

    form(): VForm {
      return this.$refs.form as VForm
    },
  },

  methods: {
    ...mutations,

    onFileSelected(files: File) {
      console.log()

      if (files) {
        this.user.photo = files
        this.photoDisplay = URL.createObjectURL(files)
      } else {
        this.photoDisplay = null
      }
    },

    async registerUser() {
      if (!this.form.validate()) {
        await this.$swal.fire(`${translate('message.25')}`, '', 'warning')
        return
      }

      const formdata = new FormData()
      formdata.append('username', this.user.username)
      formdata.append('email', this.user.email)
      formdata.append('admin', String(this.user.admin))

      if (this.user.photo) {
        formdata.append('photo', this.user.photo)
      }

      const isEditing = !!this.user.id
      try {
        if (!isEditing) {
          formdata.append('password', this.user.password)
          await this.$http.post('user', formdata)

          await this.$swal(`${translate('message.5')}`, '', 'success')
          this.form.reset()
          this.user.admin = false
          this.photoDisplay = null
        } else {
          formdata.append('id', this.user.id.toString())
          const { data } = await this.$http.put<User>('user', formdata)

          if (this.user.id == this.getUser.id) {
            this.setUser(data)
            const { username } = this.getUser
            if (username !== this.user.username) {
              await this.$swal.fire(`${translate('message.26')}`, '', 'warning')
              logout()
              return
            }
          }

          await this.$swal(`${translate('message.7')}`, '', 'success')
          this.back()
        }
      } catch (err) {
        console.log(err)
        await this.$swal(
          `${translate(`${isEditing ? 'message.8' : 'message.6'}`)}`,
          '',
          'error'
        )
      }
    },

    back() {
      this.$router.push({ name: 'Admin' })
    },
  },
})
</script>

<style scoped>
.photo {
  width: 100%;
  text-align: center;
}
</style>
