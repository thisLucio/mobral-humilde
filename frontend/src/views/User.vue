<template>
  <v-main>
    <v-container fluid fill-height>
      <v-row justify="center">
        <v-col cols="12" sm="8" md="5" lg="4" xl="3" align="center">
          <v-card class="ma-auto">
            <v-card-title>
              <v-col>
                <v-container>
                  <v-avatar size="190">
                    <img
                      :src="
                        userPhotoUrl ||
                          require('@/assets/default-user-image.png')
                      "
                      class="mx-auto"
                    />
                  </v-avatar>
                </v-container>

                <v-spacer> </v-spacer>

                <v-container>
                  <p>{{ 'front.20' | translate }} {{ getUser.username }}</p>
                </v-container>
              </v-col>
            </v-card-title>

            <v-card-actions>
              <v-col>
                <v-container v-show="this.getUser.admin">
                  <v-btn block color="info" @click="goToAdmin">{{
                    'message.34' | translate
                  }}</v-btn>
                </v-container>

                <v-container>
                  <v-btn block color="info" @click="goToEquipment">{{
                    'front.21' | translate
                  }}</v-btn>
                </v-container>

                <my-modal
                  :dialog="dialog1"
                  :btn="'front.22'"
                  :title="'front.29'"
                >
                  <iframe id="map-equipment" src="/map" title="Map"></iframe>
                </my-modal>

                <my-modal
                  :dialog="dialog2"
                  :btn="'front.23'"
                  :title="'front.30'"
                >
                  <v-tabs
                    centered
                    grow
                    v-model="tab"
                    class="d-flex flex-column"
                  >
                    <v-tab href="#user">{{ 'front.2' | translate }} </v-tab>
                    <v-tab href="#equipment">{{
                      'front.25' | translate
                    }}</v-tab>
                    <v-tabs-items v-model="tab" class="fill-height">
                      <v-tab-item :key="1" value="user" class="fill-height">
                        <iframe
                          id="chat-user"
                          src="/chat"
                          title="User chat"
                        ></iframe>
                      </v-tab-item>
                      <v-tab-item
                        :key="2"
                        value="equipment"
                        class="fill-height"
                      >
                        <iframe
                          id="chat-equipment"
                          src="/chat/equipment"
                          title="Equipment chat"
                        ></iframe>
                      </v-tab-item>
                    </v-tabs-items>
                  </v-tabs>
                </my-modal>
              </v-col>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>

<script lang="ts">
import Vue from 'vue'
import { getters } from '@/observables/User'
import Modal from '@/components/Modal.vue'

export default Vue.extend({
  data() {
    return {
      tab: 'message',
      dialog2: false,
      dialog1: false,
    }
  },

  components: {
    'my-modal': Modal,
  },

  methods: {
    goToEquipment() {
      this.$router.push({ name: 'Equipment' })
    },

    goToAdmin() {
      this.$router.push({ name: 'Admin' })
    },
  },

  computed: {
    ...getters,
    userPhotoUrl() {
      return this.getUser.photoUrl
        ? `/api/uploads/${this.getUser.photoUrl}`
        : null
    },
  },
})
</script>

<style>
.chat-card {
  display: grid;
  grid-template-rows: 64px 1fr;
}

iframe {
  width: 100%;
  height: 100%;
  border: none;
  overflow: hidden;
}
</style>
