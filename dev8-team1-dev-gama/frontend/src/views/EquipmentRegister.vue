<template>
  <v-main>
    <v-container fluid fill-height>
      <v-card width="30%" class="ma-auto">
        <v-col>
          <v-card-title class="justify-center font-italic font-weight-bold">{{
            'front.21' | translate
          }}</v-card-title>

          <v-card-text>
            <v-col align="center">
              <v-form v-model="valid" ref="form">
                <v-text-field
                  v-bind:label="$t('front.24')"
                  prepend-icon="mdi-truck"
                  v-model="equipments.description"
                  :rules="descriptionRules"
                  required
                />

                <v-text-field
                  v-bind:label="$t('front.26')"
                  prepend-icon="mdi-latitude"
                  v-model="equipments.latitude"
                  :rules="latitudeRules"
                  required
                />

                <v-text-field
                  v-bind:label="$t('front.27')"
                  prepend-icon="mdi-longitude"
                  v-model="equipments.longitude"
                  :rules="longitudeRules"
                  required
                />
                <v-spacer> </v-spacer>

                <v-row justify-center>
                  <v-col>
                    <v-text-field
                      v-bind:label="$t('front.28')"
                      prepend-icon="mdi-palette"
                      v-model="equipments.color"
                      :rules="colorRules"
                      required
                    />
                  </v-col>

                  <v-col md="3">
                    <v-menu
                      v-model="menu"
                      top
                      nudge-bottom="105"
                      nudge-left="16"
                      :close-on-content-click="false"
                    >
                      <template v-slot:activator="{ on }">
                        <v-btn color="info" v-on="on">
                          <v-icon>mdi-eyedropper</v-icon>
                        </v-btn>
                        <v-container v-on="on"></v-container>
                      </template>

                      <v-card>
                        <v-card-text>
                          <v-color-picker
                            v-model="equipments.color"
                            mode="hexa"
                            hide-mode-switch
                            flat
                          >
                          </v-color-picker>
                        </v-card-text>
                      </v-card>
                    </v-menu>
                  </v-col>
                </v-row>
              </v-form>
            </v-col>
          </v-card-text>

          <v-card-actions>
            <v-row>
              <v-btn color="info" class="mb-3 mx-auto" @click="back">{{
                'front.15' | translate
              }}</v-btn>

              <v-btn
                color="info"
                class="mb-3 mx-auto"
                @click="registerEquipment"
                >{{ 'front.17' | translate }}</v-btn
              >
            </v-row>
          </v-card-actions>
        </v-col>
      </v-card>
    </v-container>
  </v-main>
</template>

<script lang="ts">
import { translate } from '../i18n'

export default {
  data() {
    return {
      menu: '',
      equipments: {
        color: '#FF0000',
      },
      valid: false,
      descriptionRules: [v => !!v || `${translate('message.20')}`],
      latitudeRules: [
        v => !!v || `${translate('message.21')}`,
        v => /^[+-]?\d*\.\d+$|^[+-]?\d+(\.\d*)?$/.test(v) || `${translate('message.23')}`
      ],
      longitudeRules: [
        v => !!v || `${translate('message.22')}`,
        v => /^[+-]?\d*\.\d+$|^[+-]?\d+(\.\d*)?$/.test(v) || `${translate('message.23')}`
      ],
      colorRules: [v => !!v || `${translate('message.24')}`],
    }
  },

  created() {
    if (this.$route.params.equipment) {
      this.equipments = this.$route.params.equipment
    }
  },

  methods: {
    registerEquipment() {
      if (!this.$refs.form.validate()) {
        alert(`${translate('message.25')}`)
        return
      }
      if (!this.equipments.id) {
        this.$http
          .post('equipment', this.equipments)
          .then(() => {
            alert(`${translate('message.11')}`)
            this.equipments = {}
            this.$refs.form.reset()
            this.equipments.color = '#FF0000'
          })
          .catch(err => {
            console.log(err)
            alert(`${translate('message.12')}`)
          })
      } else {
        this.$http
          .put('equipment', this.equipments)
          .then(() => {
            alert(`${translate('message.13')}`)
            this.equipments = {}
            this.$refs.form.reset()
            this.back()
          })
          .catch(err => {
            console.log(err)
            alert(`${translate('message.14')}`)
          })
      }
    },

    back() {
      this.$router.push({ name: 'Equipment' })
    },
  },
}
</script>

<style scoped></style>
