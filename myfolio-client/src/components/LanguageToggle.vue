<template>
  <div class="language-toggle">
    <select v-model="currentLocale" @change="changeLanguage" class="language-select">
      <option value="ko">🇰🇷 한국어</option>
      <option value="en">🇺🇸 English</option>
      <option value="ja">🇯🇵 日本語</option>
      <option value="zh">🇨🇳 中文</option>
    </select>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'

const { locale } = useI18n()
const currentLocale = ref(locale.value)

const changeLanguage = () => {
  locale.value = currentLocale.value
  localStorage.setItem('language', currentLocale.value)
}

onMounted(() => {
  const savedLanguage = localStorage.getItem('language')
  if (savedLanguage) {
    currentLocale.value = savedLanguage
    locale.value = savedLanguage
  }
})
</script>

<style scoped>
.language-toggle {
  position: fixed;
  top: 2rem;
  right: 2rem;
  z-index: 1000;
}

.language-select {
  padding: 0.5rem 1rem;
  border: 2px solid #e1e5e9;
  border-radius: 8px;
  background: white;
  font-size: 0.9rem;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.language-select:hover {
  border-color: #000;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.language-select:focus {
  outline: none;
  border-color: #000;
  box-shadow: 0 0 0 3px rgba(0, 0, 0, 0.1);
}

@media (max-width: 768px) {
  .language-toggle {
    top: 1rem;
    right: 1rem;
  }
  
  .language-select {
    font-size: 0.8rem;
    padding: 0.4rem 0.8rem;
  }
}
</style>
