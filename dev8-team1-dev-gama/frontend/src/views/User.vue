<template>
  <v-main>
    <v-container fluid fill-height>
      <v-card width="30%" class="ma-auto">
        <v-col align="center">
          <v-card-title>
            <v-col>
              <v-container>
                <v-avatar size="190">
                  <img :src="userPhotoUrl || require('@/assets/default-user-image.png')" class="mx-auto" />
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
              <v-container>
                <v-btn block color="info" @click="goToEquipment">{{
                  'front.21' | translate
                }}</v-btn>
              </v-container>

              <v-container>
                <v-dialog
                  v-model="dialog1"
                  fullscreen
                  hide-overlay
                  transition="dialog-bottom-transition"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn block color="info" v-bind="attrs" v-on="on">{{
                      'front.22' | translate
                    }}</v-btn>
                  </template>
                  <v-card class="chat-card fill-height">
                    <v-toolbar dark color="blue">
                      <v-btn icon dark @click="dialog1 = false">
                        <v-icon>mdi-close</v-icon>
                      </v-btn>
                      <v-toolbar-title>{{
                        'front.29' | translate
                      }}</v-toolbar-title>
                      <v-spacer></v-spacer>
                    </v-toolbar>
                    <iframe id="map-equipment" src="/map" title="Map"></iframe>
                  </v-card>
                </v-dialog>
              </v-container>

              <v-container>
                <v-dialog
                  v-model="dialog"
                  fullscreen
                  hide-overlay
                  transition="dialog-bottom-transition"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn block color="info" v-bind="attrs" v-on="on">{{
                      'front.23' | translate
                    }}</v-btn>
                  </template>
                  <v-card class="chat-card fill-height">
                    <v-toolbar dark color="blue">
                      <v-btn icon dark @click="dialog = false">
                        <v-icon>mdi-close</v-icon>
                      </v-btn>
                      <v-toolbar-title>{{
                        'front.30' | translate
                      }}</v-toolbar-title>
                      <v-spacer></v-spacer>
                    </v-toolbar>
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
                  </v-card>
                </v-dialog>
              </v-container>
            </v-col>
          </v-card-actions>
        </v-col>
      </v-card>
    </v-container>
  </v-main>
</template>

<script lang="ts">
import { getters } from '@/observables/User.ts'
export default {
  data() {
    return {
      tab: 'message',
      dialog: false,
      dialog1: false,
    }
  },

  methods: {
    goToEquipment() {
      this.$router.push({ name: 'Equipment' })
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
}
</script>

<style scoped>
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
