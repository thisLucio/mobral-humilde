import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
import User from '../views/User.vue'
import Login from '../views/Login.vue'
import Admin from '../views/Admin.vue'
import Equipment from '../views/Equipment.vue'
import EquipmentRegister from '../views/EquipmentRegister.vue'
import UserRegister from '../views/UserRegister.vue'
import NotFound from '../views/NotFound.vue'
import http from '@/http'

Vue.use(VueRouter)

const routes: Array<RouteConfig> = [
  {
    path: '/',
    name: 'Login',
    component: Login,
    meta: {
      public: true,
      role: ['user']
    },
  },
  {
    path: '/user',
    name: 'User',
    component: User,
    meta: {
      public: false,
      role: ['user']
    },
  },
  {
    path: '/admin',
    name: 'Admin',
    component: Admin,
    meta: {
      public: false,
      role: ['admin']
    },
  },
  {
    path: '/equipment',
    name: 'Equipment',
    component: Equipment,
    meta: {
      public: false,
      role: ['user']
    },
  },
  {
    path: '/equipment-register',
    name: 'EquipmentRegister',
    component: EquipmentRegister,
    meta: {
      public: false,
      role: ['user']
    },
  },
  {
    path: '/user-register',
    name: 'UserRegister',
    component: UserRegister,
    meta: {
      public: false,
      role: ['admin']
    },
  },
  {
    path: '*',
    name: 'NotFound',
    component: NotFound,
    meta: {
      public: false,
      role: ['user']
    },
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
})

router.beforeEach((routeTo, routeFrom, next) => {
  const token = localStorage.getItem('token')
  if (!routeTo.meta?.public && !token) {
    return next({ path: '/' })
  }
  if (routeTo.meta?.role.includes('admin')) {
    http.get('/user')
      .then(() => {
        next()
      })
      .catch(() => {
        console.log(routeFrom.path)
        next({ path: '/user' })
      })
  } else {
    next()
  }
})

export default router
