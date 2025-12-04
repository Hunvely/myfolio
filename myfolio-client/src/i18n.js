import { createI18n } from 'vue-i18n'
import ko from './locales/ko.json'
import ja from './locales/ja.json'
import en from './locales/en.json'
import zh from './locales/zh.json'

const messages = { ko, ja, en, zh }

const i18n = createI18n({
  legacy: false,
  locale: 'ko',
  fallbackLocale: 'en',
  messages,
})

export default i18n
