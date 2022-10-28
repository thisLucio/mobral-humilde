<template>
  <v-main>
    <v-container fluid fill-height>
      <v-card width="40%" class="ma-auto" v-if="!users.id">
        <v-card-title class="justify-center font-italic font-weight-bold">{{
          'front.14' | translate
        }}</v-card-title>
        <v-card-text>
          <v-form v-model="valid" ref="form">
            <v-text-field
              v-bind:label="$t('front.2')"
              prepend-icon="mdi-account-circle"
              v-model="users.username"
              :rules="usernameRules"
              required
            />
            <v-text-field
              v-bind:label="$t('front.4')"
              type="password"
              prepend-icon="mdi-lock"
              v-model="users.password"
              :rules="passwordRules"
              required
            />
            <v-file-input
              v-bind:label="$t('front.18')"
              accept="image/*"
              ref="fileupload"
              clearable
              @change="onFileSelected"
            />
            <img class="photo" v-show="users.photo" :src="photoDisplay" />
            <v-text-field
              v-bind:label="$t('front.19')"
              type="email"
              prepend-icon="mdi-at"
              v-model="users.email"
              :rules="emailRules"
              required
            />
            <v-checkbox v-model="users.admin" label="Admin" />
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
              v-model="users.username"
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
              v-model="users.email"
              :rules="emailRules"
              required
            />
            <v-checkbox v-model="users.admin" label="Admin" />
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
import { translate } from '../i18n'
import { mutations, getters } from '@/observables/User.ts'

export default {
  data() {
    return {
      users: {},
      photoDisplay: '',
      valid: false,
      usernameRules: [v => !!v || `${translate('message.16')}`],
      passwordRules: [v => !!v || `${translate('message.17')}`],
      emailRules: [
        v => !!v || `${translate('message.18')}`,
        v => /.+@.+/.test(v) || `${translate('message.19')}`,
      ],
    }
  },

  created() {
    if (this.$route.params.user) {
      this.users = this.$route.params.user
    }
    if (this.users.photoUrl) {
      this.photoDisplay = '/api/uploads/' + this.users.photoUrl
    }
  },

  computed: {
    ...getters,
  },

  methods: {
    ...mutations,

    onFileSelected(files) {
      if (files) {
        this.users.photo = files
        this.photoDisplay = URL.createObjectURL(files)
      } else {
        this.photoDisplay = null
      }
    },

    registerUser() {
      if (!this.$refs.form.validate()) {
        alert(`${translate('message.25')}`)
        return
      }

      if (!this.users.admin) {
        this.users.admin = false
      }
      const formdata = new FormData()
      formdata.append('username', this.users.username)
      formdata.append('email', this.users.email)
      formdata.append('admin', this.users.admin)

      if (this.users.photo) {
        formdata.append('photo', this.users.photo)
      }

      if (!this.users.id) {
        formdata.append('password', this.users.password)
        this.$http
          .post('user', formdata)
          .then(() => {
            alert(`${translate('message.5')}`)
            this.$refs.form.reset()
            this.photoDisplay = null
          })
          .catch(err => {
            console.log(err)
            alert(`${translate('message.6')}`)
          })
      } else {
        formdata.append('id', this.users.id)
        this.$http
          .put('user', formdata)
          .then(resp => {
            if (this.users.id == this.getUser.id) {
              this.setUser(resp.data)
              let username = this.getUser.username
              if (username !== this.users.username) {
                alert(`${translate('message.26')}`)
                localStorage.removeItem('token')
                localStorage.removeItem('user')
                this.$router.push({ name: 'Login' })
                return
              }
            }
            alert(`${translate('message.7')}`)
            this.back()
          })
          .catch(err => {
            console.log(err)
            alert(`${translate('message.8')}`)
          })
      }
    },

    back() {
      this.$router.push({ name: 'Admin' })
    },
  },
}
</script>

<style scoped>
.photo {
  width: 100%;
  text-align: center;
}
</style>
