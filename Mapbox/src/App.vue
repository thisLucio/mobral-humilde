<template>
  <div id="app">
    <Map></Map>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Map from "../src/components/Map.vue";

@Component({
  components: {
    Map,
  },
})
export default class App extends Vue {}
</script>
