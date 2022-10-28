<template>
  <v-main>
    <v-container fluid fill-height>
      <v-card width="30%" class="ma-auto">
        <v-col align="center">
          <v-card-title class="justify-center font-italic font-weight-bold">{{
            'front.1' | translate
          }}</v-card-title>

          <v-card-text>
            <v-col>
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
                  required
                  @click:append="show = !show"
                />
              </v-form>
            </v-col>
          </v-card-text>

          <v-card-actions>
            <v-row>
              <v-container align="center">
                <v-btn color="info" @click="login">{{
                  'front.3' | translate
                }}</v-btn>
              </v-container>
            </v-row>
          </v-card-actions>
        </v-col>
      </v-card>
    </v-container>
  </v-main>
</template>

<script lang="ts">
import Vue from 'vue'
import { translate } from '../i18n'
import { mutations } from '@/observables/User'
import { VForm } from '@/types/VForm';

export default Vue.extend({
  data() {
    return {
      user: {},
      valid: false,
      show: false,
      usernameRules: [(v: string) => !!v || `${translate('message.16')}`],
      passwordRules: [(v: string) => !!v || `${translate('message.17')}`],
    }
  },

  methods: {
    ...mutations,

    async login() {
      if (!this.form.validate()) {
        this.$swal.fire(`${translate('message.15')}`, '', 'warning')
        return
      }

      try {
        const resp = await this.$http.post('login', this.user)
        localStorage.setItem('token', resp.headers.authorization)
        this.setUser(resp.data)
        this.$router.push({ name: resp.data.admin ? 'Admin' : 'User' })
      } catch (err) {
        console.log(err)
        this.$swal.fire(`${translate('message.1')}`, '', 'error')
      }
    },
  },

  computed: {
    form() {
      return this.$refs.form as VForm
    },
  },
})
</script>
