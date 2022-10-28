<template>
  <v-app>
    <router-view />
  </v-app>
</template>

<style>
* {
  box-sizing: border-box;
  padding: 0px;
  margin: 0px;
}

.v-select.v-text-field > .v-input__control > .v-input__slot::before {
  border-style: none;
  display: none;
}
</style>
