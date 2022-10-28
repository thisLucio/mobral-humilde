<template>
  <v-main>
    <v-container fluid fill-height>
      <v-card width="90%" class="ma-auto">
        <v-card-title class="justify-center font-italic font-weight-bold">{{
          'front.10' | translate
        }}</v-card-title>
        <v-card-text>
          <v-simple-table>
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-left">{{ 'front.9' | translate }}</th>
                  <th class="text-left">{{ 'front.2' | translate }}</th>
                  <th class="text-left">{{ 'front.10' | translate }}</th>
                  <th class="text-center">{{ 'front.11' | translate }}</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="user in users" :key="user.id">
                  <td>{{ user.id }}</td>
                  <td>{{ user.username }}</td>
                  <td>{{ user.admin }}</td>
                  <td class="text-center">
                    <v-btn fab small icon class="mx-2" @click="edit(user)">
                      <v-icon dark>mdi-circle-edit-outline</v-icon>
                    </v-btn>
                    <v-btn fab small icon class="mx-2" @click="remove(user)">
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
      </v-card>
    </v-container>
  </v-main>
</template>

<script lang="ts">
import { translate } from '../i18n'

export default {
  data() {
    return {
      users: {},
    }
  },

  created() {
    this.$http.get('user').then((resp) => (this.users = resp.data))
  },

  methods: {
    add() {
      this.$router.push({ name: 'UserRegister' })
    },

    edit(user) {
      this.$router.push({ name: 'UserRegister', params: { user } })
    },

    remove(user) {
      if (confirm(`${translate('message.2')}`)) {
        this.$http
          .delete('user/' + user.id)
          .then(() => {
            let indice = this.users.indexOf(user)
            this.users.splice(indice, 1)
            alert(`${translate('message.3')}`)
          })
          .catch((err) => {
            console.log(err)
            alert(`${translate('message.4')}`)
          })
      }
    },
  },
}
</script>

<style scoped>
h4 {
  display: inline-block;
  margin: 1% 2%;
}

.alinhar {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>
