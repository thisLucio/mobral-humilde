import Vue from 'vue'
import { User } from '@/types/User';

type LoggedUser = Omit<User, 'password' | 'email' | 'photo'>;

export const state = Vue.observable({
  user: {
    username: '',
    photoUrl: '',
    id: 0,
    admin: false,
  } as LoggedUser,
})

export const getters = {
  getUser: (): LoggedUser => JSON.parse(localStorage.getItem('user') ?? ''),
}

export const mutations = {
  setUser: (user: LoggedUser) => {
    state.user = user
    localStorage.setItem('user', JSON.stringify(state.user))
  },
}
