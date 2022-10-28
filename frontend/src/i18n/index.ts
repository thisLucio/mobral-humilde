import Vue from 'vue'
import VueI18n from 'vue-i18n'
import en from './en.json'
import pt from './pt.json'
import es from './es.json'

Vue.use(VueI18n)

const i18n = new VueI18n({
  // locale: 'pt-BR', //if you need get the browser language use following "window.navigator.language"
  locale: localStorage.getItem('lang') || 'pt',
  fallbackLocale: 'pt',
  messages: { en, pt, es },
  silentTranslationWarn: true,
})

const translate = (key: string) => {
  if (!key) {
    return ''
  }
  return i18n.t(key)
}

export { i18n, translate } //export above method
