import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";
import UserChat from "@/views/Chat/UserChat.vue";
import EquipmentChat from "@/views/Chat/EquipmentChat.vue";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: "/",
    name: "Chat",
    component: UserChat,
  },
  {
    path: "/equipment",
    name: "EquipmentChat",
    component: EquipmentChat,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
