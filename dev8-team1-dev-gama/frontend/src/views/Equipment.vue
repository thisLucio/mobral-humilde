<template>
  <v-main>
    <v-container fluid fill-height>
      <v-card width="90%" class="ma-auto">
        <v-card-title class="justify-center font-italic font-weight-bold">{{
          'front.25' | translate
        }}</v-card-title>
        <v-card-text>
          <v-simple-table>
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-left">
                    {{ 'front.9' | translate }}
                  </th>
                  <th class="text-left">
                    {{ 'front.24' | translate }}
                  </th>
                  <th class="text-center">
                    {{ 'front.11' | translate }}
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="equipment in equipments" :key="equipment.id">
                  <td>{{ equipment.id }}</td>
                  <td>{{ equipment.description }}</td>
                  <td class="text-center">
                    <v-btn fab small icon class="mx-2" @click="edit(equipment)">
                      <v-icon dark>mdi-circle-edit-outline</v-icon>
                    </v-btn>
                    <v-btn
                      fab
                      small
                      icon
                      class="mx-2"
                      @click="remove(equipment)"
                    >
                      <v-icon dark>mdi-trash-can</v-icon>
                    </v-btn>
                  </td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-card-text>
        <v-btn fab small icon class="mx-6" @click="add">
          <v-icon dark>mdi-plus</v-icon>
        </v-btn>
        <v-btn fab small icon class="mx-6" @click="back">
          {{ 'front.15' | translate }}
        </v-btn>
      </v-card>
    </v-container>
  </v-main>
</template>

<script lang="ts">
import { translate } from '../i18n'

export default {
  data() {
    return {
      equipments: {},
    }
  },

  methods: {
    add() {
      this.$router.push({ name: 'EquipmentRegister' })
    },

    edit(equipment) {
      this.$router.push({ name: 'EquipmentRegister', params: { equipment } })
    },

    remove(equipment) {
      if (confirm(`${translate('message.9')}`)) {
        this.$http.delete('equipment/' + equipment.id).then(() => {
          let indice = this.equipments.indexOf(equipment)
          this.equipments.splice(indice, 1)
          alert(`${translate('message.10')}`)
        })
      }
    },

    back() {
      this.$router.push({ name: 'User' })
    },
  },

  created() {
    this.$http.get('equipment').then((resp) => (this.equipments = resp.data))
  },
}
</script>

<style scoped>
.card {
  border: 1px solid black;
  width: 50%;
  margin: 3% auto;
  text-align: center;
}

h4 {
  display: inline-block;
  margin: 1% 2%;
}

button {
  margin: 1% 2%;
  text-align: right;
}
</style>
