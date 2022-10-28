<template>
  <v-btn
  :disabled="isLogin"
  text
  @click="logout">{{ 'front.7' | translate }} </v-btn>
</template>

<script lang="ts">
export default {
  computed: {
    isLogin(): boolean {
      console.log(this.$router.currentRoute.name == 'Login');
      return this.$router.currentRoute.name == 'Login';
    },
  },
  methods: {
    logout() {
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      this.$router.push({ name: 'Login' })
    },
  },
}
</script>
