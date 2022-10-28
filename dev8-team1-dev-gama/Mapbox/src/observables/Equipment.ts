import { Equipment } from '@/types/equipment';
import { EquipmentMarker } from '@/types/equipmentMark';
import Vue from 'vue'

export const state = Vue.observable({
  equipment: [] as Array<EquipmentMarker>,
})

export const getters = {
  // getEquipment: (): any => JSON.parse(localStorage.getItem('user') ?? ''),
  getEquipment: (): any => state.equipment,
}

export const mutations = {
  // setUser: (user: any) => {
  //   state.user = user
  //   localStorage.setItem('user', JSON.stringify(state.user))
  // },
  pushEquipment: (equip: any) => {
    console.log("Estou a inserir um objecto", equip);
    let equipmentMarker: EquipmentMarker;
    equipmentMarker = equip as EquipmentMarker;
    state.equipment.push(equip);
  },
}