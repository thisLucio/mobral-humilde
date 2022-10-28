<template>
  <v-btn
  :disabled="isLogin"
  text
  @click="logout">{{ 'front.7' | translate }} </v-btn>
</template>

<script lang="ts">
import Vue from 'vue';
import { logout } from '@/helpers/Logout'

export default Vue.extend({
  data(){
    return {
      logout: [logout],
    }
  },

  computed: {
    isLogin(): boolean {
      return this.$route.name == 'Login' || this.$route.name == 'Confirmation';
    },
  },
})
</script>
