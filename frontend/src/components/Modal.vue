<template>
  <v-container>
    <v-dialog
      v-model="dialogModal"
      fullscreen
      hide-overlay
      transition="dialog-bottom-transition"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn block color="info" v-bind="attrs" v-on="on">{{
          btn | translate
        }}</v-btn>
      </template>
      <v-card class="chat-card fill-height">
        <v-toolbar dark :color="($vuetify.theme.dark) ? 'primary darken-4' : 'primary'">
          <v-btn icon dark @click="dialogModal = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
          <v-toolbar-title>{{ title | translate }}</v-toolbar-title>
          <v-spacer></v-spacer>
        </v-toolbar>
        <slot></slot>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  props: {
    dialog: {
      required: true,
      type: Boolean,
    },
    btn: {
      required: true,
      type: String,
    },
    title: {
      required: true,
      type: String,
    },
  },

  data() {
    return {
      dialogModal: this.dialog,
    }
  },
})
</script>

<style>
.chat-card {
  display: grid;
  grid-template-rows: 64px 1fr;
}
</style>
