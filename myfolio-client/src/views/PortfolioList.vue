<template>
  <div class="portfolio-list-page">
    <!-- 상단 네비게이션 헤더 (대시보드와 동일) -->
    <nav class="top-navbar">
      <div class="navbar-container">
        <!-- 좌측: 로고/서비스명 -->
        <div class="navbar-left">
          <router-link to="/dashboard" class="logo-link">
            <span class="logo-text">{{ t('dashboard.header.logo') }}</span>
          </router-link>
        </div>

        <!-- 중앙: 메인 메뉴 -->
        <div class="navbar-center">
          <router-link to="/dashboard/portfolios" class="nav-item">
            <span class="nav-icon">📁</span>
            <span class="nav-text">{{ t('dashboard.header.portfolios') }}</span>
          </router-link>
          <router-link to="/dashboard/projects" class="nav-item">
            <span class="nav-icon">💼</span>
            <span class="nav-text">{{ t('dashboard.header.projects') }}</span>
          </router-link>
          <router-link to="/dashboard/media" class="nav-item">
            <span class="nav-icon">🖼️</span>
            <span class="nav-text">{{ t('dashboard.header.media') }}</span>
          </router-link>
          <router-link to="/dashboard/templates" class="nav-item">
            <span class="nav-icon">🎨</span>
            <span class="nav-text">{{ t('dashboard.header.templates') }}</span>
          </router-link>
        </div>

        <!-- 우측: 유저 메뉴 -->
        <div class="navbar-right">
          <!-- 언어 선택 -->
          <div class="language-selector" @click.stop="toggleLanguageMenu">
            <span class="language-icon">🌐</span>
            <span class="language-text">{{ currentLanguageText }}</span>
            <span class="dropdown-arrow">▼</span>
            
            <!-- 언어 드롭다운 메뉴 -->
            <div v-if="showLanguageMenu" class="language-menu-dropdown">
              <div 
                v-for="lang in languages" 
                :key="lang.value"
                class="language-option"
                :class="{ active: currentLocale === lang.value }"
                @click="changeLanguage(lang.value)"
              >
                <span class="language-flag">{{ lang.flag }}</span>
                <span class="language-name">{{ lang.name }}</span>
                <span v-if="currentLocale === lang.value" class="check-icon">✓</span>
              </div>
            </div>
          </div>

          <!-- 알림 -->
          <div class="nav-icon-button" @click="toggleNotifications">
            <span class="nav-icon">🔔</span>
            <span v-if="notificationCount > 0" class="notification-badge">{{ notificationCount }}</span>
          </div>

          <!-- 마이페이지 드롭다운 -->
          <div class="user-menu" @click="toggleUserMenu">
            <div class="user-avatar">
              <span class="avatar-text">{{ userInitial }}</span>
            </div>
            <span class="user-name">{{ t('dashboard.header.myPage') }}</span>
            <span class="dropdown-arrow">▼</span>
            
            <!-- 드롭다운 메뉴 -->
            <div v-if="showUserMenu" class="user-menu-dropdown">
              <router-link to="/dashboard/profile" class="dropdown-item" @click="closeUserMenu">
                <span class="dropdown-icon">👤</span>
                <span>{{ t('dashboard.header.profile') }}</span>
              </router-link>
              <router-link to="/dashboard/settings" class="dropdown-item" @click="closeUserMenu">
                <span class="dropdown-icon">⚙️</span>
                <span>{{ t('dashboard.header.settings') }}</span>
              </router-link>
              <div class="dropdown-divider"></div>
              <router-link to="/dashboard/support" class="dropdown-item" @click="closeUserMenu">
                <span class="dropdown-icon">💬</span>
                <span>{{ t('dashboard.header.support') }}</span>
              </router-link>
              <div class="dropdown-item logout-item" @click="handleLogout">
                <span class="dropdown-icon">🚪</span>
                <span>{{ t('dashboard.logout') }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </nav>

    <!-- 페이지 컨텐츠 -->
    <div class="portfolio-list-container">
      <div class="page-header">
        <div class="header-content">
          <h1>{{ t('portfolio.list.title') }}</h1>
          <p class="subtitle">{{ t('portfolio.list.subtitle') }}</p>
        </div>
        <router-link to="/portfolio/create" class="btn-create">
          <span class="btn-icon">➕</span>
          <span>{{ t('portfolio.list.createButton') }}</span>
        </router-link>
      </div>

      <!-- 포트폴리오 리스트 -->
      <div class="portfolio-grid">
        <div v-if="portfolios.length === 0" class="empty-state">
          <div class="empty-icon">📁</div>
          <h3>{{ t('portfolio.list.emptyTitle') }}</h3>
          <p>{{ t('portfolio.list.emptyDescription') }}</p>
          <router-link to="/portfolio/create" class="btn-create-empty">
            {{ t('portfolio.list.createButton') }}
          </router-link>
        </div>

        <div 
          v-for="portfolio in portfolios" 
          :key="portfolio.id" 
          class="portfolio-card"
          @click="viewPortfolio(portfolio.id)"
        >
          <div class="portfolio-thumbnail">
            <img v-if="portfolio.thumbnail" :src="portfolio.thumbnail" :alt="portfolio.title" />
            <div v-else class="thumbnail-placeholder">
              <span class="placeholder-icon">📄</span>
            </div>
          </div>
          <div class="portfolio-info">
            <h3 class="portfolio-title">{{ portfolio.title || t('portfolio.list.untitled') }}</h3>
            <p class="portfolio-description">{{ portfolio.description || t('portfolio.list.noDescription') }}</p>
            <div class="portfolio-meta">
              <span class="meta-item">
                <span class="meta-icon">📅</span>
                <span>{{ formatDate(portfolio.updatedAt) }}</span>
              </span>
              <span class="meta-item">
                <span class="meta-icon">👁️</span>
                <span>{{ portfolio.views || 0 }} {{ t('portfolio.list.views') }}</span>
              </span>
            </div>
          </div>
          <div class="portfolio-actions">
            <button @click.stop="editPortfolio(portfolio.id)" class="action-btn edit-btn">
              {{ t('portfolio.list.edit') }}
            </button>
            <button @click.stop="deletePortfolio(portfolio.id)" class="action-btn delete-btn">
              {{ t('portfolio.list.delete') }}
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'

const { t, locale } = useI18n()
const router = useRouter()

// 사용자 정보 (임시)
const userName = ref('지훈')
const userInitial = computed(() => userName.value.charAt(0))

// 언어 선택
const currentLocale = ref(locale.value)
const showLanguageMenu = ref(false)

const languages = [
  { value: 'ko', flag: '🇰🇷', name: '한국어' },
  { value: 'en', flag: '🇺🇸', name: 'English' },
  { value: 'ja', flag: '🇯🇵', name: '日本語' },
  { value: 'zh', flag: '🇨🇳', name: '中文' }
]

const currentLanguageText = computed(() => {
  const lang = languages.find(l => l.value === currentLocale.value)
  return lang ? lang.name : '한국어'
})

const toggleLanguageMenu = () => {
  showLanguageMenu.value = !showLanguageMenu.value
  if (showLanguageMenu.value) {
    showUserMenu.value = false
    showNotifications.value = false
  }
}

const changeLanguage = (langValue) => {
  currentLocale.value = langValue
  locale.value = langValue
  localStorage.setItem('language', langValue)
  showLanguageMenu.value = false
}

// 메뉴 상태
const showUserMenu = ref(false)
const showNotifications = ref(false)
const notificationCount = ref(2)

const toggleUserMenu = () => {
  showUserMenu.value = !showUserMenu.value
  if (showUserMenu.value) {
    showNotifications.value = false
  }
}

const closeUserMenu = () => {
  showUserMenu.value = false
}

const toggleNotifications = () => {
  showNotifications.value = !showNotifications.value
  if (showNotifications.value) {
    showUserMenu.value = false
  }
}

// 포트폴리오 데이터 (임시 - 나중에 API로 가져올 예정)
const portfolios = ref([
  // 예시 데이터가 없으면 빈 배열
])

// 포트폴리오 관련 함수
const viewPortfolio = (id) => {
  // 포트폴리오 상세 페이지로 이동
  router.push(`/portfolio/${id}`)
}

const editPortfolio = (id) => {
  // 포트폴리오 편집 페이지로 이동
  router.push(`/portfolio/${id}/edit`)
}

const deletePortfolio = (id) => {
  if (confirm(t('portfolio.list.deleteConfirm'))) {
    // 포트폴리오 삭제 API 호출
    console.log('Delete portfolio:', id)
  }
}

const formatDate = (date) => {
  if (!date) return t('portfolio.list.noDate')
  return new Date(date).toLocaleDateString()
}

const handleLogout = () => {
  closeUserMenu()
  router.push('/')
}

// 외부 클릭 시 메뉴 닫기
const handleClickOutside = (event) => {
  if (!event.target.closest('.user-menu') && 
      !event.target.closest('.notification-dropdown') &&
      !event.target.closest('.language-selector')) {
    showUserMenu.value = false
    showNotifications.value = false
    showLanguageMenu.value = false
  }
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
  
  // 저장된 언어 불러오기
  const savedLanguage = localStorage.getItem('language')
  if (savedLanguage) {
    currentLocale.value = savedLanguage
    locale.value = savedLanguage
  }
  
  // 포트폴리오 목록 가져오기 (API 호출)
  // loadPortfolios()
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>

<style scoped>
.portfolio-list-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #f9f9fb 0%, #ffffff 100%);
  padding-top: 70px;
}

/* 네비게이션 바 스타일 (대시보드와 동일) */
.top-navbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  background: white;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 1000;
  border-bottom: 1px solid #e1e5e9;
}

.navbar-container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 2rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 70px;
}

.navbar-left {
  flex: 0 0 auto;
}

.logo-link {
  text-decoration: none;
  display: flex;
  align-items: center;
  transition: opacity 0.3s ease;
}

.logo-link:hover {
  opacity: 0.8;
}

.logo-text {
  font-size: 1.5rem;
  font-weight: 700;
  color: #000;
}

.navbar-center {
  flex: 1;
  display: flex;
  justify-content: center;
  gap: 0.5rem;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.75rem 1.25rem;
  border-radius: 8px;
  text-decoration: none;
  color: #333;
  font-weight: 500;
  transition: all 0.3s ease;
}

.nav-item:hover {
  background-color: #f5f5f5;
  color: #000;
}

.nav-item.router-link-active {
  background-color: #000;
  color: #fff;
}

.nav-icon {
  font-size: 1.2rem;
}

.nav-text {
  font-size: 0.95rem;
}

.navbar-right {
  flex: 0 0 auto;
  display: flex;
  align-items: center;
  gap: 1rem;
}

.nav-icon-button {
  position: relative;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.nav-icon-button:hover {
  background-color: #f5f5f5;
}

.notification-badge {
  position: absolute;
  top: 0;
  right: 0;
  background-color: #ff4444;
  color: white;
  font-size: 0.7rem;
  font-weight: 600;
  padding: 0.2rem 0.4rem;
  border-radius: 10px;
  min-width: 18px;
  text-align: center;
}

.language-selector {
  position: relative;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.language-selector:hover {
  background-color: #f5f5f5;
}

.language-icon {
  font-size: 1.2rem;
}

.language-text {
  font-size: 0.9rem;
  font-weight: 500;
  color: #333;
}

.language-selector .dropdown-arrow {
  font-size: 0.7rem;
  color: #666;
  transition: transform 0.3s ease;
}

.language-selector:hover .dropdown-arrow {
  transform: rotate(180deg);
}

.language-menu-dropdown {
  position: absolute;
  top: calc(100% + 0.5rem);
  right: 0;
  background: white;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
  min-width: 180px;
  padding: 0.5rem 0;
  z-index: 1001;
}

.language-option {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.75rem 1.25rem;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.language-option:hover {
  background-color: #f5f5f5;
}

.language-option.active {
  background-color: #f0f0f0;
  font-weight: 600;
}

.language-flag {
  font-size: 1.2rem;
  width: 24px;
  text-align: center;
}

.language-name {
  flex: 1;
  font-size: 0.9rem;
  color: #333;
}

.check-icon {
  color: #000;
  font-weight: 600;
  font-size: 1rem;
}

.user-menu {
  position: relative;
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.user-menu:hover {
  background-color: #f5f5f5;
}

.user-avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: 600;
  font-size: 0.9rem;
}

.user-name {
  font-size: 0.9rem;
  font-weight: 500;
  color: #333;
}

.dropdown-arrow {
  font-size: 0.7rem;
  color: #666;
  transition: transform 0.3s ease;
}

.user-menu:hover .dropdown-arrow {
  transform: rotate(180deg);
}

.user-menu-dropdown {
  position: absolute;
  top: calc(100% + 0.5rem);
  right: 0;
  background: white;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
  min-width: 200px;
  padding: 0.5rem 0;
  z-index: 1001;
}

.dropdown-item {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.75rem 1.25rem;
  text-decoration: none;
  color: #333;
  font-size: 0.9rem;
  transition: background-color 0.2s ease;
  cursor: pointer;
}

.dropdown-item:hover {
  background-color: #f5f5f5;
}

.dropdown-item.logout-item {
  color: #ff4444;
}

.dropdown-item.logout-item:hover {
  background-color: #ffe5e5;
}

.dropdown-icon {
  font-size: 1.1rem;
  width: 20px;
  text-align: center;
}

.dropdown-divider {
  height: 1px;
  background-color: #e1e5e9;
  margin: 0.5rem 0;
}

/* 페이지 컨텐츠 */
.portfolio-list-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 3rem 2rem;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 3rem;
}

.header-content h1 {
  font-size: 2.5rem;
  font-weight: 700;
  color: #000;
  margin-bottom: 0.5rem;
}

.subtitle {
  font-size: 1.1rem;
  color: #666;
}

.btn-create {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 1rem 2rem;
  background-color: #000;
  color: #fff;
  border-radius: 12px;
  text-decoration: none;
  font-weight: 600;
  font-size: 1rem;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.btn-create:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
}

.btn-icon {
  font-size: 1.2rem;
}

/* 포트폴리오 그리드 */
.portfolio-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 2rem;
}

.empty-state {
  grid-column: 1 / -1;
  text-align: center;
  padding: 4rem 2rem;
  background: white;
  border-radius: 16px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
}

.empty-icon {
  font-size: 4rem;
  margin-bottom: 1.5rem;
}

.empty-state h3 {
  font-size: 1.5rem;
  font-weight: 600;
  color: #000;
  margin-bottom: 0.5rem;
}

.empty-state p {
  color: #666;
  margin-bottom: 2rem;
}

.btn-create-empty {
  display: inline-block;
  padding: 1rem 2rem;
  background-color: #000;
  color: #fff;
  border-radius: 12px;
  text-decoration: none;
  font-weight: 600;
  transition: all 0.3s ease;
}

.btn-create-empty:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
}

/* 포트폴리오 카드 */
.portfolio-card {
  background: white;
  border-radius: 16px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  transition: all 0.3s ease;
  cursor: pointer;
  display: flex;
  flex-direction: column;
}

.portfolio-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
}

.portfolio-thumbnail {
  width: 100%;
  height: 200px;
  background-color: #f5f5f5;
  overflow: hidden;
}

.portfolio-thumbnail img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.thumbnail-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #f5f5f5 0%, #e0e0e0 100%);
}

.placeholder-icon {
  font-size: 3rem;
  opacity: 0.5;
}

.portfolio-info {
  padding: 1.5rem;
  flex: 1;
}

.portfolio-title {
  font-size: 1.3rem;
  font-weight: 600;
  color: #000;
  margin-bottom: 0.5rem;
}

.portfolio-description {
  font-size: 0.9rem;
  color: #666;
  line-height: 1.6;
  margin-bottom: 1rem;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.portfolio-meta {
  display: flex;
  gap: 1rem;
  font-size: 0.85rem;
  color: #999;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 0.25rem;
}

.meta-icon {
  font-size: 0.9rem;
}

.portfolio-actions {
  padding: 1rem 1.5rem;
  border-top: 1px solid #f0f0f0;
  display: flex;
  gap: 0.5rem;
}

.action-btn {
  flex: 1;
  padding: 0.75rem;
  border: none;
  border-radius: 8px;
  font-weight: 600;
  font-size: 0.9rem;
  cursor: pointer;
  transition: all 0.3s ease;
}

.edit-btn {
  background-color: #f5f5f5;
  color: #000;
}

.edit-btn:hover {
  background-color: #e0e0e0;
}

.delete-btn {
  background-color: #ffe5e5;
  color: #ff4444;
}

.delete-btn:hover {
  background-color: #ffcccc;
}

@media (max-width: 768px) {
  .portfolio-list-page {
    padding-top: 60px;
  }

  .navbar-container {
    padding: 0 1rem;
    height: 60px;
  }

  .logo-text {
    font-size: 1.2rem;
  }

  .nav-text {
    display: none;
  }

  .user-name {
    display: none;
  }

  .dropdown-arrow {
    display: none;
  }

  .language-text {
    display: none;
  }

  .language-selector {
    padding: 0.5rem;
  }

  .portfolio-list-container {
    padding: 2rem 1rem;
  }

  .page-header {
    flex-direction: column;
    gap: 1.5rem;
  }

  .header-content h1 {
    font-size: 2rem;
  }

  .btn-create {
    width: 100%;
    justify-content: center;
  }

  .portfolio-grid {
    grid-template-columns: 1fr;
  }
}
</style>


