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
                  v-model="equipment.description"
                  :rules="descriptionRules"
                  required
                />

                <v-text-field
                  v-bind:label="$t('front.26')"
                  prepend-icon="mdi-latitude"
                  v-model="equipment.latitude"
                  :rules="latitudeRules"
                  required
                />

                <v-text-field
                  v-bind:label="$t('front.27')"
                  prepend-icon="mdi-longitude"
                  v-model="equipment.longitude"
                  :rules="longitudeRules"
                  required
                />
                <v-spacer> </v-spacer>

                <v-row justify-center>
                  <v-col>
                    <v-text-field
                      v-bind:label="$t('front.28')"
                      prepend-icon="mdi-palette"
                      v-model="equipment.color"
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
                            v-model="equipment.color"
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
import Vue from 'vue'
import { translate } from '../i18n'
import { VForm } from '@/types/VForm'
import { Equipment } from '@/types/Equipment'

export default Vue.extend({
  data() {
    return {
      menu: '',
      equipment: {
        id: 0,
        description: '',
        latitude: 0,
        longitude: 0,
        color: '#FF0000',
      } as Equipment,
      valid: false,
      descriptionRules: [(v: string) => !!v || `${translate('message.20')}`],
      latitudeRules: [
        (v: string) => !!v || `${translate('message.21')}`,
        (v: string) =>
          /^[+-]?\d*\.\d+$|^[+-]?\d+(\.\d*)?$/.test(v) ||
          `${translate('message.23')}`,
        (v: number) => ( v && v <= 90 ) || `${translate('message.30')}`,
        (v: number) => ( v && v >= -90 ) || `${translate('message.31')}`
      ],
      longitudeRules: [
        (v: string) => !!v || `${translate('message.22')}`,
        (v: string) =>
          /^[+-]?\d*\.\d+$|^[+-]?\d+(\.\d*)?$/.test(v) ||
          `${translate('message.23')}`,
        (v: number) => ( v && v <= 180 ) || `${translate('message.32')}`,
        (v: number) => ( v && v >= -180 ) || `${translate('message.33')}`
      ],
      colorRules: [(v: string) => !!v || `${translate('message.24')}`],
    }
  },

  created() {
    if (this.$route.params.id) {
      const { id, color, description, latitude, longitude } = this.$route.params

      this.equipment = {
        id: Number(id),
        color,
        description,
        latitude: Number(latitude),
        longitude: Number(longitude),
      }
    }
  },

  methods: {
    async registerEquipment() {
      if (!this.form.validate()) {
        await this.$swal.fire(`${translate('message.25')}`, '', 'warning')
        return
      }

      const isEditing = !!this.equipment.id
      try {
        if (!isEditing) {
          await this.$http.post('equipment', this.equipment)
        } else {
          await this.$http.put('equipment', this.equipment)
        }

        await this.$swal.fire(
          `${translate(`${!isEditing ? 'message.11' : 'message.13'}`)}`,
          '',
          'success'
        )

        this.form.reset()
        this.equipment = {
          id: 0,
          description: '',
          latitude: 0,
          longitude: 0,
          color: '#FF0000',
        }

        if (isEditing) {
          this.back()
        }
      } catch (err) {
        console.log(err)
        await this.$swal.fire(
          `${translate(`${!isEditing ? 'message.12' : 'message.14'}`)}`,
          '',
          'error'
        )
      }
    },

    back() {
      this.$router.push({ name: 'Equipment' })
    },
  },

  computed: {
    form(): VForm {
      return this.$refs.form as VForm
    },
  },
})
</script>

<style scoped></style>
