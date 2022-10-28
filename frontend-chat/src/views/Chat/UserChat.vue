<template>
  <chat-container
    :messages="messages"
    :contacts="contacts"
    :selectedContact="selectedContact"
    @onContactClick="handleContactChanged"
  >
    <template v-slot:user-info>
      <div class="user-photo">
        <img
          :src="userPhotoUrl || require('@/assets/default-user-image.png')"
          alt="Imagem do usuário"
        />
      </div>
      <span>{{ sender.name }}</span>
    </template>

    <template v-slot:chat-header>
      <span>{{ selectedContact.name }}</span>
      <span style="font-style: italic" v-if="!selectedContact.id"
        >Selecione um contato para iniciar uma conversa</span
      >
    </template>

    <template v-slot:chat-input>
      <input
        ref="messageInput"
        @keydown.enter="sendMessage"
        type="text"
        v-model="message"
        placeholder="Digite sua mensagem..."
        :disabled="!selectedContact.id"
      />
      <button @click="sendMessage" :disabled="message.length === 0">
        Enviar
      </button>
    </template>
  </chat-container>
</template>

<script lang="ts">
import { Component } from "vue-property-decorator";
import ChatContainer from "@/components/Chat/ChatContainer.vue";
import { getUser } from "@/services/localStorage";
import SocketChat from "./SocketChat";

@Component({
  components: {
    ChatContainer,
  },
})
export default class UserChat extends SocketChat {
  async created() {
    const user = getUser();

    this.sender = {
      id: user.id,
      name: user.username,
      photo: user.photoUrl,
      entityType: "user",
      chatUsername: `user${user.id}`,
    };

    try {
      this.contacts = (await this.fetchEquipments()).map((equipment) => ({
        id: equipment.id,
        name: equipment.description,
        entityType: "equipment",
        chatUsername: `equipment${equipment.id}`,
      }));
    } catch (e) {
      if (process.env.NODE_ENV !== "production") {
        console.log(e);
      }
    }

    this.selectedContact = this.contacts[0];

    await this.fetchMessages();
  }

  get userPhotoUrl() {
    return this.sender.photo ? `/api/uploads/${this.sender.photo}` : null;
  }
}
</script>

<style scoped>
.user-photo {
  border-radius: 50%;
  width: 56px;
  height: 56px;
  overflow: hidden;
}

.user-photo img {
  width: 100%;
}

button {
  background: #056162;
  color: #fff;
  padding: 16px 24px;
  border: none;
  cursor: pointer;
  border-radius: 4px;
}

button:hover {
  opacity: 0.8;
}

button:disabled {
  cursor: default;
  opacity: 0.5;
}

input {
  background: #eceff1;
  padding: 16px 8px;
  width: 100%;
  margin: 0px 32px;
  border-radius: 4px;
}
</style>
