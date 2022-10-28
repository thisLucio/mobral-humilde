<template>
  <v-app>
    <v-app-bar
      app
      dark
      :color="$vuetify.theme.dark ? 'primary darken-4' : 'primary'"
    >
      <v-toolbar-title>{{ 'front.8' | translate }}</v-toolbar-title>
      <v-spacer></v-spacer>

      <v-btn icon @click="$vuetify.theme.dark = !$vuetify.theme.dark">
        <v-icon :color="$vuetify.theme.dark ? 'primary' : 'primary lighten-4'">
          {{ $vuetify.theme.dark ? 'mdi-weather-night' : 'mdi-weather-sunny' }}
        </v-icon>
      </v-btn>

      <app-language-select class="mx-4"></app-language-select>
      <app-logout-button></app-logout-button>
    </v-app-bar>
    <router-view />
  </v-app>
</template>

<script lang="ts">
import Vue from 'vue'
import LanguageSelect from './components/LanguageSelect.vue'
import LogoutButton from './components/LogoutButton.vue'

export default Vue.extend({
  name: 'App',

  components: {
    'app-language-select': LanguageSelect,
    'app-logout-button': LogoutButton,
  },

  data(){
    return {
      image: require("@/assets/jao.png")
    }
  },

  mounted() {
    const kode = [38, 38, 40, 40, 37, 39, 37, 39, 66, 65]
    const length = kode.length
    var pos = 0
    document.addEventListener(
      'keydown',
      (event) => {
        if (event.keyCode === kode[pos++]) {
          if (length === pos) {
            this.$swal.fire({html: '<img src='+this.image+'>'})
            pos = 0 // ability to start over
            return false
          }
        } else {
          pos = 0
        }
      },
      false
    )
  },
})
</script>

<style>
.swal2-title,
.swal2-actions {
  font-family: 'Roboto', sans-serif;
}
</style>
