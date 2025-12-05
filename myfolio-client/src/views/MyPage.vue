<template>
  <div class="mypage">
    <!-- 상단 네비게이션 헤더 -->
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
    <div class="mypage-container">
      <div class="page-header">
        <h1>{{ t('mypage.title') }}</h1>
        <p class="subtitle">{{ t('mypage.subtitle') }}</p>
      </div>

      <div class="mypage-content">
        <!-- 프로필 섹션 -->
        <div class="profile-section">
          <div class="section-card">
            <h2 class="section-title">{{ t('mypage.profile.title') }}</h2>
            
            <div class="profile-header">
              <div class="profile-avatar-container">
                <div class="profile-avatar" :style="{ backgroundImage: profileImage ? `url(${profileImage})` : 'none' }">
                  <span v-if="!profileImage" class="avatar-placeholder">{{ userInitial }}</span>
                </div>
                <button @click="triggerImageUpload" class="avatar-edit-btn">
                  <span class="edit-icon">📷</span>
                  <span>{{ t('mypage.profile.changePhoto') }}</span>
                </button>
                <input 
                  ref="imageInput" 
                  type="file" 
                  accept="image/*" 
                  @change="handleImageUpload" 
                  style="display: none"
                />
              </div>
              
              <div class="profile-info">
                <h3 class="profile-name">{{ userData.name || t('mypage.profile.noName') }}</h3>
                <p class="profile-email">{{ userData.email || '' }}</p>
                <p class="profile-bio" v-if="userData.bio">{{ userData.bio }}</p>
                <p class="profile-bio placeholder" v-else>{{ t('mypage.profile.noBio') }}</p>
              </div>
            </div>
          </div>
        </div>

        <!-- 계정 정보 섹션 -->
        <div class="account-section">
          <div class="section-card">
            <h2 class="section-title">{{ t('mypage.account.title') }}</h2>
            
            <form @submit.prevent="updateAccount" class="account-form">
              <div class="input-group">
                <label>{{ t('mypage.account.username') }}</label>
                <input 
                  v-model="formData.username" 
                  type="text" 
                  :placeholder="t('mypage.account.usernamePlaceholder')"
                  disabled
                />
                <p class="input-hint">{{ t('mypage.account.usernameHint') }}</p>
              </div>

              <div class="input-group">
                <label>{{ t('mypage.account.name') }}</label>
                <input 
                  v-model="formData.name" 
                  type="text" 
                  :placeholder="t('mypage.account.namePlaceholder')"
                />
              </div>

              <div class="input-group">
                <label>{{ t('mypage.account.email') }}</label>
                <input 
                  v-model="formData.email" 
                  type="email" 
                  :placeholder="t('mypage.account.emailPlaceholder')"
                />
              </div>

              <div class="input-group">
                <label>{{ t('mypage.account.phoneNumber') }}</label>
                <input 
                  v-model="formData.phoneNumber" 
                  type="tel" 
                  :placeholder="t('mypage.account.phoneNumberPlaceholder')"
                />
              </div>

              <div class="input-group">
                <label>{{ t('mypage.account.bio') }}</label>
                <textarea 
                  v-model="formData.bio" 
                  :placeholder="t('mypage.account.bioPlaceholder')"
                  rows="4"
                ></textarea>
              </div>

              <div class="input-group">
                <label>{{ t('mypage.account.profileImage') }}</label>
                <input 
                  v-model="formData.profileImage" 
                  type="text" 
                  :placeholder="t('mypage.account.profileImagePlaceholder')"
                />
              </div>

              <div class="form-actions">
                <button type="submit" class="btn-primary" :disabled="isSubmitting">
                  {{ isSubmitting ? t('mypage.account.saving') : t('mypage.account.save') }}
                </button>
                <button type="button" @click="resetForm" class="btn-cancel">
                  {{ t('mypage.account.cancel') }}
                </button>
              </div>
            </form>
          </div>
        </div>

        <!-- 비밀번호 변경 섹션 -->
        <div class="password-section">
          <div class="section-card">
            <h2 class="section-title">{{ t('mypage.password.title') }}</h2>
            
            <form @submit.prevent="changePassword" class="password-form">
              <div class="input-group">
                <label>{{ t('mypage.password.currentPassword') }}</label>
                <input 
                  v-model="passwordData.currentPassword" 
                  type="password" 
                  :placeholder="t('mypage.password.currentPasswordPlaceholder')"
                />
              </div>

              <div class="input-group">
                <label>{{ t('mypage.password.newPassword') }}</label>
                <input 
                  v-model="passwordData.newPassword" 
                  type="password" 
                  :placeholder="t('mypage.password.newPasswordPlaceholder')"
                />
              </div>

              <div class="input-group">
                <label>{{ t('mypage.password.confirmPassword') }}</label>
                <input 
                  v-model="passwordData.confirmPassword" 
                  type="password" 
                  :placeholder="t('mypage.password.confirmPasswordPlaceholder')"
                />
              </div>

              <div class="form-actions">
                <button type="submit" class="btn-primary" :disabled="isChangingPassword">
                  {{ isChangingPassword ? t('mypage.password.changing') : t('mypage.password.change') }}
                </button>
              </div>
            </form>
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
import axios from 'axios'

const { t, locale } = useI18n()
const router = useRouter()

// 사용자 정보 (임시 - 나중에 Store에서 가져올 예정)
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

// 사용자 데이터
const userData = ref({
  name: '지훈',
  email: 'jihoon9611@gmail.com',
  phoneNumber: '010-1234-5678',
  bio: '포트폴리오를 만들어보는 개발자입니다.',
  profileImage: ''
})

// 폼 데이터
const formData = ref({
  username: 'jihoon',
  name: '',
  email: '',
  phoneNumber: '',
  bio: '',
  profileImage: ''
})

// 비밀번호 데이터
const passwordData = ref({
  currentPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// 상태
const isSubmitting = ref(false)
const isChangingPassword = ref(false)
const imageInput = ref(null)
const profileImage = ref('')

// 초기 데이터 로드
const loadUserData = async () => {
  try {
    // API 호출로 사용자 데이터 가져오기
    // const response = await axios.get('/api/users/me')
    // userData.value = response.data
    // formData.value = { ...response.data }
    
    // 임시로 userData를 formData에 복사
    formData.value = {
      username: 'jihoon',
      name: userData.value.name,
      email: userData.value.email,
      phoneNumber: userData.value.phoneNumber,
      bio: userData.value.bio,
      profileImage: userData.value.profileImage
    }
    profileImage.value = userData.value.profileImage
  } catch (error) {
    console.error('사용자 데이터 로드 실패:', error)
  }
}

// 계정 정보 업데이트
const updateAccount = async () => {
  isSubmitting.value = true
  
  try {
    // API 호출
    // await axios.put('/api/users/me', formData.value)
    
    // 임시 처리
    userData.value = { ...formData.value }
    profileImage.value = formData.value.profileImage
    
    alert(t('mypage.account.updateSuccess'))
  } catch (error) {
    console.error('계정 정보 업데이트 실패:', error)
    alert(t('mypage.account.updateError'))
  } finally {
    isSubmitting.value = false
  }
}

// 비밀번호 변경
const changePassword = async () => {
  if (passwordData.value.newPassword !== passwordData.value.confirmPassword) {
    alert(t('mypage.password.mismatch'))
    return
  }

  if (passwordData.value.newPassword.length < 8) {
    alert(t('mypage.password.tooShort'))
    return
  }

  isChangingPassword.value = true

  try {
    // API 호출
    // await axios.put('/api/users/me/password', {
    //   currentPassword: passwordData.value.currentPassword,
    //   newPassword: passwordData.value.newPassword
    // })
    
    alert(t('mypage.password.changeSuccess'))
    passwordData.value = {
      currentPassword: '',
      newPassword: '',
      confirmPassword: ''
    }
  } catch (error) {
    console.error('비밀번호 변경 실패:', error)
    alert(t('mypage.password.changeError'))
  } finally {
    isChangingPassword.value = false
  }
}

// 폼 리셋
const resetForm = () => {
  formData.value = {
    username: 'jihoon',
    name: userData.value.name,
    email: userData.value.email,
    phoneNumber: userData.value.phoneNumber,
    bio: userData.value.bio,
    profileImage: userData.value.profileImage
  }
}

// 프로필 이미지 업로드
const triggerImageUpload = () => {
  imageInput.value?.click()
}

const handleImageUpload = (event) => {
  const file = event.target.files[0]
  if (file) {
    // 실제로는 이미지를 서버에 업로드하고 URL을 받아와야 함
    // 임시로 FileReader 사용
    const reader = new FileReader()
    reader.onload = (e) => {
      profileImage.value = e.target.result
      formData.value.profileImage = e.target.result
    }
    reader.readAsDataURL(file)
  }
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
  
  // 사용자 데이터 로드
  loadUserData()
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>

<style scoped>
.mypage {
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
.mypage-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 3rem 2rem;
}

.page-header {
  text-align: center;
  margin-bottom: 3rem;
}

.page-header h1 {
  font-size: 2.5rem;
  font-weight: 700;
  color: #000;
  margin-bottom: 0.5rem;
}

.subtitle {
  font-size: 1.1rem;
  color: #666;
}

.mypage-content {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

/* 섹션 카드 */
.section-card {
  background: white;
  border-radius: 16px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
  padding: 2.5rem;
  transition: all 0.3s ease;
}

.section-card:hover {
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
}

.section-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: #000;
  margin-bottom: 2rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid #e1e5e9;
}

/* 프로필 섹션 */
.profile-header {
  display: flex;
  gap: 2rem;
  align-items: flex-start;
}

.profile-avatar-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
}

.profile-avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  background-size: cover;
  background-position: center;
  border: 4px solid white;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.avatar-placeholder {
  font-size: 3rem;
  font-weight: 600;
  color: white;
}

.avatar-edit-btn {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.5rem 1rem;
  background-color: #f5f5f5;
  border: 1px solid #e1e5e9;
  border-radius: 8px;
  cursor: pointer;
  font-size: 0.9rem;
  transition: all 0.3s ease;
}

.avatar-edit-btn:hover {
  background-color: #e0e0e0;
}

.edit-icon {
  font-size: 1rem;
}

.profile-info {
  flex: 1;
}

.profile-name {
  font-size: 1.8rem;
  font-weight: 700;
  color: #000;
  margin-bottom: 0.5rem;
}

.profile-email {
  font-size: 1rem;
  color: #666;
  margin-bottom: 1rem;
}

.profile-bio {
  font-size: 1rem;
  color: #333;
  line-height: 1.6;
}

.profile-bio.placeholder {
  color: #999;
  font-style: italic;
}

/* 폼 스타일 */
.account-form,
.password-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.input-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.input-group label {
  font-weight: 600;
  color: #333;
  font-size: 0.95rem;
}

.input-group input,
.input-group textarea {
  padding: 0.75rem;
  border: 1px solid #e1e5e9;
  border-radius: 8px;
  font-size: 1rem;
  transition: all 0.3s ease;
  font-family: inherit;
  background-color: #fff;
}

.input-group input:focus,
.input-group textarea:focus {
  outline: none;
  border-color: #000;
  box-shadow: 0 0 0 3px rgba(0, 0, 0, 0.1);
}

.input-group input:disabled {
  background-color: #f5f5f5;
  color: #999;
  cursor: not-allowed;
}

.input-hint {
  font-size: 0.85rem;
  color: #999;
  margin: 0;
}

.form-actions {
  display: flex;
  gap: 1rem;
  margin-top: 1rem;
}

.btn-primary {
  padding: 1rem 2rem;
  background-color: #000;
  color: #fff;
  border: none;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  min-width: 140px;
}

.btn-primary:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
}

.btn-primary:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.btn-cancel {
  padding: 1rem 2rem;
  background-color: transparent;
  color: #000;
  border: 2px solid #000;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  min-width: 140px;
}

.btn-cancel:hover {
  background-color: #000;
  color: #fff;
  transform: translateY(-2px);
}

@media (max-width: 768px) {
  .mypage {
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

  .mypage-container {
    padding: 2rem 1rem;
  }

  .page-header h1 {
    font-size: 2rem;
  }

  .section-card {
    padding: 1.5rem;
  }

  .profile-header {
    flex-direction: column;
    align-items: center;
    text-align: center;
  }

  .form-actions {
    flex-direction: column;
  }

  .btn-primary,
  .btn-cancel {
    width: 100%;
  }
}
</style>

