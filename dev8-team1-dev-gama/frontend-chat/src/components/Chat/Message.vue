<template>
  <div>
    <span>{{ message.content }}</span>
    <span class="message-timestamp">
      {{ timestamp }}
    </span>
  </div>
</template>

<script lang="ts">
import Vue from "vue";
import { Component, Prop } from "vue-property-decorator";
import { Message } from "@/types";

@Component
export default class ChatMessage extends Vue {
  @Prop() readonly message!: Message;

  get timestamp() {
    return this.message.timestamp.toLocaleTimeString().substr(0, 5);
  }
}
</script>

<style scoped>
.message-timestamp {
  justify-content: space-between;
  align-self: flex-end;
  font-size: 10px;
}
</style>
