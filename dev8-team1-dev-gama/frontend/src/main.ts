import Vue from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import vuetify from './plugins/vuetify'
import http from './http'
import { translate, i18n } from '@/i18n'

Vue.config.productionTip = false
Vue.prototype.$http = http

Vue.filter('translate', function(value: string) {
  return translate(value)
})

new Vue({
  i18n,
  router,
  vuetify,
  render: h => h(App),
}).$mount('#app')
