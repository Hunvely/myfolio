<template>
  <div class="main">
    <div class="hero-section">
      <h1 class="title" v-html="t('home.title')"></h1>
      <p class="subtitle">{{ t('home.subtitle') }}</p>
      <div class="buttons">
        <router-link to="/signup" class="btn">{{ t('home.startNow') }}</router-link>
        <router-link to="/login" class="btn-outline">{{ t('home.login') }}</router-link>
      </div>
    </div>
    
    <div class="features-section">
      <div class="feature-card">
        <div class="feature-icon">🎨</div>
        <h3>{{ t('home.features.design.title') }}</h3>
        <p>{{ t('home.features.design.description') }}</p>
      </div>
      <div class="feature-card">
        <div class="feature-icon">⚡</div>
        <h3>{{ t('home.features.speed.title') }}</h3>
        <p>{{ t('home.features.speed.description') }}</p>
      </div>
      <div class="feature-card">
        <div class="feature-icon">📱</div>
        <h3>{{ t('home.features.responsive.title') }}</h3>
        <p>{{ t('home.features.responsive.description') }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useI18n } from 'vue-i18n'

const { t } = useI18n()
</script>

<style scoped>
.main {
  min-height: 100vh;
  background-color: #f9f9fb;
  padding: 0;
}

.hero-section {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  text-align: center;
  padding: 2rem;
  background: linear-gradient(135deg, #f9f9fb 0%, #ffffff 100%);
}

.title {
  font-size: 3.2rem;
  font-weight: 700;
  margin-bottom: 1.5rem;
  line-height: 1.3;
  color: #000;
  max-width: 800px;
}

.subtitle {
  font-size: 1.3rem;
  color: #555;
  margin-bottom: 3rem;
  max-width: 600px;
  line-height: 1.6;
}

.buttons {
  display: flex;
  gap: 1.5rem;
  flex-wrap: wrap;
  justify-content: center;
}

.btn, .btn-outline {
  padding: 1rem 2rem;
  font-size: 1.1rem;
  border-radius: 12px;
  text-decoration: none;
  transition: all 0.3s ease;
  font-weight: 600;
  min-width: 160px;
}

.btn {
  background-color: #000;
  color: #fff;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.btn-outline {
  border: 2px solid #000;
  color: #000;
  background-color: transparent;
  transition: all 0.3s ease;
}

.btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
}

.btn-outline:hover {
  background-color: #000;
  color: #fff;
  transform: translateY(-2px);
}

.features-section {
  padding: 4rem 2rem;
  background-color: #fff;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.feature-card {
  text-align: center;
  padding: 2rem;
  border-radius: 16px;
  background-color: #f8f9fa;
  transition: all 0.3s ease;
}

.feature-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}

.feature-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.feature-card h3 {
  font-size: 1.5rem;
  margin-bottom: 1rem;
  color: #000;
}

.feature-card p {
  color: #666;
  line-height: 1.6;
  font-size: 1rem;
}

@media (max-width: 768px) {
  .title {
    font-size: 2.2rem;
  }
  
  .subtitle {
    font-size: 1.1rem;
  }
  
  .buttons {
    flex-direction: column;
    align-items: center;
  }
  
  .btn, .btn-outline {
    width: 100%;
    max-width: 280px;
  }
}
</style>
