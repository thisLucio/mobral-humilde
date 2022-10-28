<template>
  <v-main>
    <v-container fluid fill-height>
      <v-card width="70%" class="ma-auto">
        <v-card-title class="justify-center font-italic font-weight-bold">{{
          'front.25' | translate
        }}</v-card-title>
        <v-card-text>
          <v-data-table
            :headers="headers"
            :items="equipments"
            :items-per-page="5"
            sort-by="id"
          >
            <template v-slot:[`item.color`]="{ item }">
              <v-banner single-line :color="item.color"/>
            </template>
            <template v-slot:[`item.actions`]="{ item }">
              <v-btn
                fab
                small
                icon
                class="mx-2"
                color="blue"
                @click="edit(item)"
              >
                <v-icon dark>mdi-circle-edit-outline</v-icon>
              </v-btn>
              <v-btn
                fab
                small
                icon
                class="mx-2"
                color="red"
                @click="remove(item)"
              >
                <v-icon dark>mdi-trash-can</v-icon>
              </v-btn>
            </template>
          </v-data-table>
        </v-card-text>
        <v-btn fab small icon class="mx-6" color="blue" @click="add">
          <v-icon dark>mdi-plus</v-icon>
        </v-btn>
        <v-btn fab small icon class="mx-6" color="blue" @click="back">
          {{ 'front.15' | translate }}
        </v-btn>
      </v-card>
    </v-container>
  </v-main>
</template>

<script lang="ts">
import Vue from 'vue'
import { translate } from '../i18n'
import { Equipment } from '@/types/Equipment'

export default Vue.extend({
  data() {
    return {
      equipments: [] as Array<Equipment>,
      headers: [
        { text: `${translate('front.9')}`, value: 'id', align: 'center' },
        { text: `${translate('front.24')}`, value: 'description' },
        { text: `${translate('front.28')}`, value: 'color', align: 'center' },
        { text: `${translate('front.11')}`, value: 'actions', align: 'center' },
      ],
    }
  },

  created() {
    this.$http.get('equipment').then(resp => (this.equipments = resp.data))
  },

  methods: {
    add() {
      this.$router.push({ name: 'EquipmentRegister' })
    },

    edit(equipment: Equipment) {
      this.$router.push({
        name: 'EquipmentRegister',
        params: {
          id: equipment.id.toString(),
          description: equipment.description,
          color: equipment.color,
          latitude: equipment.latitude.toString(),
          longitude: equipment.longitude.toString(),
        },
      })
    },

    async remove(equipment: Equipment) {
      const result = await this.$swal.fire({
        title: translate('message.9'),
        icon: 'warning',
        showDenyButton: true,
        showConfirmButton: true,
        confirmButtonText: 'OK',
        denyButtonText: 'Cancelar',
        reverseButtons: true,
      })

      if (result.isDenied || result.isDismissed) {
        return
      }

      try {
        await this.$http.delete(`equipment/${equipment.id}`)
        const index = this.equipments.indexOf(equipment)
        this.equipments.splice(index, 1)
        this.$swal.fire(`${translate('message.10')}`, '', 'success')
      } catch (err) {
        console.log(err)
        this.$swal.fire(`${translate('message.equipmentDeleteError')}`, '', 'error')
      }
    },

    back() {
      this.$router.push({ name: 'User' })
    },
  },
})
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
