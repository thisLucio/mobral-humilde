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
                  type="password"
                  prepend-icon="mdi-lock"
                  v-model="user.password"
                  :rules="passwordRules"
                  required
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
import { mutations } from '@/observables/User.ts'

export default Vue.extend({
  data() {
    return {
      user: {},
      valid: false,
      usernameRules: [v => !!v || `${translate('message.16')}`],
      passwordRules: [v => !!v || `${translate('message.17')}`],
    }
  },

  methods: {
    ...mutations,

    login() {
      if(!this.$refs.form.validate()){
        alert(`${translate('message.15')}`)
        return
      }
      this.$http
        .post('login', this.user)
        .then(resp => {
          console.log(resp)
          localStorage.setItem('token', resp.headers.authorization)
          this.setUser(resp.data)
          if (resp.data.admin) {
            this.$router.push({ name: 'Admin' })
          } else {
            this.$router.push({ name: 'User' })
          }
        })
        .catch(err => {
          console.log(err)
          alert(`${translate('message.1')}`)
        })
    },
  },
})
</script>
