import vue from 'vue'
import http from '@/http'
import { AxiosInstance } from 'axios'
import VueSweetalert2 from 'vue-sweetalert2'

declare module 'vue/types/vue' {
    interface Vue {
      $http: AxiosInstance;
      $swal: VueSweetalert2;
    }
  }