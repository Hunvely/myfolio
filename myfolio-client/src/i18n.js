import { createI18n } from 'vue-i18n'
import ko from './locales/ko.json'
import ja from './locales/ja.json'

const messages = { ko, ja }

const i18n = createI18n({
  locale: 'ko',
  fallbackLocale: 'ja',
  messages,
})

export default i18n
