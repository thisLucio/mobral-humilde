<template>
  <v-main>
    <v-container fluid fill-height>
      <v-card
        elevation="2"
        loading
        shaped
        width="50%"
        class="ma-auto text-center"
      >
        <v-card-text v-if="confirmation">
          <h1>{{ 'message.27' | translate }}</h1>
          <br>
          <img class="photo" :src="require('@/assets/gatojoinha.jpg')"/>
          <br>
          <router-link :to="{ name: 'Login' }">{{
            'message.28' | translate
          }}</router-link>
        </v-card-text>
        <v-card-text v-else>
          <h1>{{ 'message.29' | translate }}</h1>
          <br>
          <img class="photo" :src="require('@/assets/gatouniverse.png')"/>
          <br>
          <router-link :to="{ name: 'Login' }">{{
            'message.28' | translate
          }}</router-link>
        </v-card-text>
      </v-card>
    </v-container>
  </v-main>
</template>

<script lang="ts">
import Vue from 'vue'
import { translate } from '../i18n'

export default Vue.extend({
  data() {
    return {
      confirmation: true,
    }
  },

  created() {
    this.confirma()
  },

  methods: {
    async confirma() {
      const token = this.$route.query.token
      try {
        await this.$http.get(`confirmation?token=${token}`)
        this.confirmation = true
      } catch {
        this.confirmation = false
      }
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
