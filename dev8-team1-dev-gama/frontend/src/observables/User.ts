import Vue from 'vue'

export const state = Vue.observable({
  user: {
    username: '',
    photoUrl: '',
    id: 0,
    admin: false,
  },
})

export const getters = {
  getUser: (): any => JSON.parse(localStorage.getItem('user') ?? ''),
}

export const mutations = {
  setUser: (user: any) => {
    state.user = user
    localStorage.setItem('user', JSON.stringify(state.user))
  },
}
