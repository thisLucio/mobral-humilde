<template>
  <v-app>
    <v-app-bar app dark color="blue">
      <v-row justify-center>
        <v-col md="9">
          <v-toolbar-title>{{ 'front.8' | translate }}</v-toolbar-title>
        </v-col>
        <v-col md="2">
          <app-language-select></app-language-select>
        </v-col>
        <v-col md="1" class="text-center">
          <app-logout-button></app-logout-button>
        </v-col>
      </v-row>
    </v-app-bar>
    <router-view />
  </v-app>
</template>

<script lang="ts">
import Vue from 'vue'
import LanguageSelect from './views/LanguageSelect.vue'
import LogoutButton from './views/LogoutButton.vue'

export default Vue.extend({
  name: 'App',

  components: {
    'app-language-select': LanguageSelect,
    'app-logout-button': LogoutButton,
  },
})
</script>
