import vue from 'vue'
import http from '@/http'
import { AxiosInstance } from 'axios'

declare module 'vue/types/vue' {
    interface Vue {
      $http: AxiosInstance
    }
  }