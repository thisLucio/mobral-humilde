<template>
  <div id="app">
    {{ result }}
    <Map></Map>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { Message } from "webstomp-client";
import socket from "@/services/socket";
import { mutations } from "@/observables/Equipment";
import Map from "@/components/Map.vue";

@Component({
  components: {
    Map,
  },
})
export default class App extends Vue {

  created() {
    socket.connect({}, () => {
      socket.subscribe("/topic/equipment", this.handlerMessage);
    });
  }
  handlerMessage(message: Message): void {
    const json = JSON.parse(message.body);
    mutations.pushEquipment(json);
  }
}
</script>
<style></style>
