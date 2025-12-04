<template>
  <div class="dashboard">
    <header class="dashboard-header">
      <div class="header-content">
        <h1>{{ t('dashboard.title') }}</h1>
        <p class="subtitle">{{ t('dashboard.subtitle') }}</p>
      </div>
    </header>
    
    <div class="dashboard-content">
      <div class="welcome-section">
        <div class="welcome-card">
          <div class="welcome-icon">🎉</div>
          <h3>{{ t('dashboard.welcome') }}</h3>
          <p>{{ t('dashboard.welcomeMessage') }}</p>
        </div>
      </div>
      
      <div class="features-grid">
        <div class="feature-card">
          <div class="feature-icon">📝</div>
          <h4>{{ t('dashboard.features.create.title') }}</h4>
          <p>{{ t('dashboard.features.create.description') }}</p>
        </div>
        <div class="feature-card">
          <div class="feature-icon">🎨</div>
          <h4>{{ t('dashboard.features.customize.title') }}</h4>
          <p>{{ t('dashboard.features.customize.description') }}</p>
        </div>
        <div class="feature-card">
          <div class="feature-icon">📊</div>
          <h4>{{ t('dashboard.features.stats.title') }}</h4>
          <p>{{ t('dashboard.features.stats.description') }}</p>
        </div>
      </div>
      
      <div class="action-buttons">
        <button @click="logout" class="btn-outline">{{ t('dashboard.logout') }}</button>
        <router-link to="/" class="btn-link">{{ t('dashboard.home') }}</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'

const { t } = useI18n()

const router = useRouter()

const logout = () => {
  router.push('/')
}
</script>

<style scoped>
.dashboard {
  min-height: 100vh;
  background: linear-gradient(135deg, var(--bg-primary) 0%, var(--bg-secondary) 100%);
  transition: background 0.3s ease;
}

.dashboard-header {
  background: var(--bg-secondary);
  padding: 3rem 2rem;
  box-shadow: 0 2px 10px var(--shadow-light);
  margin-bottom: 3rem;
  transition: all 0.3s ease;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  text-align: center;
}

.dashboard-header h1 {
  font-size: 3rem;
  font-weight: 700;
  margin-bottom: 1rem;
  color: var(--text-primary);
  transition: color 0.3s ease;
}

.subtitle {
  font-size: 1.3rem;
  color: var(--text-secondary);
  max-width: 600px;
  margin: 0 auto;
  transition: color 0.3s ease;
}

.dashboard-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 2rem 4rem;
}

.welcome-section {
  margin-bottom: 4rem;
}

.welcome-card {
  background: var(--bg-secondary);
  padding: 3rem;
  border-radius: 16px;
  box-shadow: 0 10px 30px var(--shadow-light);
  text-align: center;
  max-width: 600px;
  margin: 0 auto;
  transition: all 0.3s ease;
}

.welcome-icon {
  font-size: 4rem;
  margin-bottom: 1.5rem;
}

.welcome-card h3 {
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 1rem;
  color: var(--text-primary);
  transition: color 0.3s ease;
}

.welcome-card p {
  color: var(--text-secondary);
  line-height: 1.6;
  font-size: 1.1rem;
  transition: color 0.3s ease;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  margin-bottom: 4rem;
}

.feature-card {
  background: var(--bg-secondary);
  padding: 2.5rem;
  border-radius: 16px;
  box-shadow: 0 5px 20px var(--shadow-light);
  text-align: center;
  transition: all 0.3s ease;
}

.feature-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 30px var(--shadow-medium);
}

.feature-icon {
  font-size: 3rem;
  margin-bottom: 1.5rem;
}

.feature-card h4 {
  font-size: 1.4rem;
  font-weight: 600;
  margin-bottom: 1rem;
  color: var(--text-primary);
  transition: color 0.3s ease;
}

.feature-card p {
  color: var(--text-secondary);
  line-height: 1.6;
  font-size: 1rem;
  transition: color 0.3s ease;
}

.action-buttons {
  display: flex;
  gap: 1.5rem;
  justify-content: center;
  flex-wrap: wrap;
}

.btn-outline,
.btn-link {
  padding: 1rem 2rem;
  font-size: 1.1rem;
  border-radius: 12px;
  text-decoration: none;
  transition: all 0.3s ease;
  cursor: pointer;
  border: 2px solid var(--text-primary);
  background-color: transparent;
  color: var(--text-primary);
  font-weight: 600;
  min-width: 140px;
}

.btn-link {
  display: inline-block;
}

.btn-outline:hover,
.btn-link:hover {
  background-color: var(--text-primary);
  color: var(--bg-secondary);
  transform: translateY(-2px);
  box-shadow: 0 8px 25px var(--shadow-heavy);
}

button {
  font-family: inherit;
}

@media (max-width: 768px) {
  .dashboard-header {
    padding: 2rem 1rem;
  }
  
  .dashboard-header h1 {
    font-size: 2.2rem;
  }
  
  .dashboard-content {
    padding: 0 1rem 3rem;
  }
  
  .welcome-card {
    padding: 2rem;
  }
  
  .features-grid {
    grid-template-columns: 1fr;
    gap: 1.5rem;
  }
  
  .action-buttons {
    flex-direction: column;
    align-items: center;
  }
  
  .btn-outline,
  .btn-link {
    width: 100%;
    max-width: 280px;
  }
}
</style>
