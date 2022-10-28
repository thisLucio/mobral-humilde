<template>
  <v-main>
    <v-container fluid fill-height>
      <v-card width="70%" class="ma-auto">
        <v-card-title class="justify-center font-italic font-weight-bold">{{
          'front.10' | translate
        }}</v-card-title>
        <v-card-text>
          <v-data-table
            :headers="headers"
            :items="users"
            :items-per-page="5"
            sort-by="id"
          >
            <template v-slot:[`item.photoUrl`]="{ item }">
              <v-avatar>
                <img
                  :src="(item.photoUrl != '' && item.photoUrl != null) ?
                    `/api/uploads/${item.photoUrl}` : require('@/assets/default-user-image.png')"
                />
              </v-avatar>
            </template>
            <template v-slot:[`item.admin`]="{ item }">
              <v-icon :color="item.admin ? 'green' : 'red'">
                {{ item.admin ? 'mdi-check' : 'mdi-close' }}
              </v-icon>
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
              <v-btn v-show="!item.admin"
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
         <v-btn fab small icon class="mx-6" color="blue" @click="user">
          <v-icon dark>mdi-account-circle</v-icon>
        </v-btn>
      </v-card>
    </v-container>
  </v-main>
</template>

<script lang="ts">
import Vue from 'vue'
import { translate } from '../i18n'
import { User } from '@/types/User'

export default Vue.extend({
  data() {
    return {
      headers: [
        { text: `${translate('front.9')}`, value: 'id', align: 'center' },
        {
          text: `${translate('front.18')}`,
          value: 'photoUrl',
          align: 'center',
        },
        { text: `${translate('front.2')}`, value: 'username' },
        { text: `${translate('front.10')}`, value: 'admin', align: 'center' },
        { text: `${translate('front.11')}`, value: 'actions', align: 'center' },
      ],
      users: [] as Array<User>,
    }
  },

  created() {
    this.$http.get('user').then(resp => (this.users = resp.data))
  },

  methods: {
    add() {
      this.$router.push({ name: 'UserRegister' })
    },

    edit(user: User) {
      this.$router.push({
        name: 'UserRegister',
        params: {
          id: user.id.toString(),
          admin: String(user.admin),
          username: user.username,
          email: user.email,
          photoUrl: user.photoUrl || '',
        },
      })
    },

    async remove(user: User) {
      const result = await this.$swal.fire({
        title: translate('message.2'),
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
        await this.$http.delete(`user/${user.id}`)
        const index = this.users.indexOf(user)
        this.users.splice(index, 1)
        this.$swal(`${translate('message.3')}`, '', 'success')
      } catch (err) {
        console.log(err)
        this.$swal(`${translate('message.4')}`, '', 'error')
      }
    },

    user() {
      this.$router.push({ name: 'User' })
    },
  },
})
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
