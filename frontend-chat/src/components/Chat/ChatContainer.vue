<template>
  <div class="chat-container">
    <div class="overlay" @click="hideContacts" v-if="isScreenSmall && isContactsVisible" />
    <div
      class="chat-contacts"
      :class="isScreenSmall && isContactsVisible && 'overlay-contacts'"
      v-show="!isScreenSmall || isContactsVisible"
      ref="contacts"
    >
      <div class="contacts-header">
        <slot name="user-info"></slot>
      </div>
      <contact-list @onContactClick="onContactClick" :contacts="contacts" />
    </div>
    <div class="chat-area">
      <div class="chat-header">
        <div class="contacts-toggle" v-show="isScreenSmall && hideContacts">
          <button @click="showContacts" class="menu-toggle">
            <v-icon>mdi-menu</v-icon>
          </button>
        </div>
        <slot name="chat-header" />
      </div>
      <message-list :messages="messages" />
      <div class="chat-input">
        <slot name="chat-input"></slot>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from "vue";
import MessageList from "@/components/Chat/MessageList.vue";
import ContactList from "@/components/Chat/ContactList.vue";
import { Component, Prop } from "vue-property-decorator";
import { Messages } from "@/types/messages";
import { Contact } from "@/types/contact";

@Component({
  components: {
    MessageList,
    ContactList,
  },
})
export default class ChatContainer extends Vue {
  @Prop() readonly messages?: Messages;
  @Prop() readonly contacts!: Array<Contact>;
  @Prop() readonly selectedContact?: Contact;

  isScreenSmall = false;
  isContactsVisible = false;
  showOverlay = false;
  message = "";

  created() {
    window.addEventListener("resize", this.onResize);
  }

  mounted() {
    this.onResize();
  }

  onResize() {
    this.isScreenSmall = window.innerWidth < 900;
  }

  showContacts() {
    this.isContactsVisible = true;
  }

  hideContacts() {
    this.isContactsVisible = false;
  }

  $refs!: {
    contacts: HTMLDivElement;
  };

  onContactClick(contact: Contact) {
    this.hideContacts();
    this.$emit("onContactClick", contact);
  }
}
</script>

<style scoped>
.chat-container {
  height: 100vh;
  width: 100%;
  display: flex;
  overflow: hidden;
  border: 1px solid #eee;
}

.chat-header {
  height: 80px;
  padding: 16px;
  background: #fff;
  display: flex;
  align-items: center;
}

.chat-area {
  flex: 1;
  display: flex;
  flex-direction: column;
  background: #eceff1;
}

.chat-input {
  padding: 8px;
  background: #fff;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.contacts-header {
  height: 80px;
  display: flex;
  align-items: center;
  border-bottom: 1px solid #eee;
}

.contacts-header > * {
  margin: 0px 8px;
}

.chat-contacts {
  flex: 0.3;
  display: flex;
  flex-direction: column;
  border-right: 1px solid #eee;
  background: #fff;
  z-index: 999;
}

.contacts-toggle {
  margin-right: 8px;
}

.overlay-contacts {
  position: absolute;
  top: 0;
  left: 0;
  height: 100vh;
  width: 100%;
  max-width: 300px;
}

.overlay {
  background: rgba(0, 0, 0, 0.6);
  height: 100vh;
  width: 100vw;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 99;
}
</style>
