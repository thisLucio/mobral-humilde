<template>
  <v-main fluid>
    <v-container fluid class="text-center mt-10">
      <img :src="lang" />
    </v-container>
  </v-main>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  data() {
    return {
      lang: require(`@/assets/404${localStorage.getItem('lang')}.png`)
    }
  }
})
</script>