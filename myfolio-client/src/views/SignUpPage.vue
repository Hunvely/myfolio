<template>
  <div class="signup-page">
    <div class="signup-container">
      <div class="signup-header">
        <h2>{{ t('signup.title') }}</h2>
        <p>{{ t('signup.subtitle') }}</p>
      </div>
      
      <form @submit.prevent="submit">
        <div class="form-section">
          <h3>{{ t('signup.basicInfo') }}</h3>
          <div class="input-group">
            <input v-model="username" type="text" :placeholder="t('signup.username')" required />
          </div>
          <div class="input-group">
            <input v-model="email" type="email" :placeholder="t('signup.email')" required />
          </div>
          <div class="input-group">
            <input v-model="password" type="password" :placeholder="t('signup.password')" required />
          </div>
          <div class="input-group">
            <input v-model="name" type="text" :placeholder="t('signup.name')" required />
          </div>
          <div class="input-group">
            <input v-model="phoneNumber" type="text" :placeholder="t('signup.phoneNumber')" required />
          </div>
        </div>

        <div class="form-section">
          <h3>{{ t('signup.additionalInfo') }}</h3>
          <div class="input-group">
            <input v-model="profileImage" type="text" :placeholder="t('signup.profileImage')" />
          </div>
          <div class="input-group">
            <textarea v-model="bio" :placeholder="t('signup.bio')"></textarea>
          </div>
        </div>

        <button type="submit" class="signup-btn">{{ t('signup.signupButton') }}</button>
        
        <div class="footer-links">
          <router-link to="/login" class="link">{{ t('signup.login') }}</router-link>
          <router-link to="/" class="link">{{ t('signup.home') }}</router-link>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useI18n } from 'vue-i18n'
import axios from 'axios'

const { t } = useI18n()

const username = ref('')
const email = ref('')
const password = ref('')
const name = ref('')
const phoneNumber = ref('')
const profileImage = ref('')
const bio = ref('')

const submit = async () => {
  const signUpData = {
    username: username.value,
    email: email.value,
    password: password.value,
    name: name.value,
    phoneNumber: phoneNumber.value,
    profileImage: profileImage.value,
    bio: bio.value,
  }

  try {
    const response = await axios.post('/api/users', signUpData)
    console.log('회원가입 성공:', response.data)
    alert('회원가입 완료!')
  } catch (error) {
    console.error('회원가입 실패:', error)
    alert('회원가입에 실패했습니다.')
  }
}
</script>

<style scoped>
.signup-page {
  min-height: 100vh;
  background: linear-gradient(135deg, var(--bg-primary) 0%, var(--bg-secondary) 100%);
  padding: 2rem;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background 0.3s ease;
}

.signup-container {
  max-width: 500px;
  width: 100%;
  background: var(--bg-secondary);
  box-shadow: 0 20px 40px var(--shadow-light);
  border-radius: 16px;
  padding: 3rem;
  transition: all 0.3s ease;
}

.signup-header {
  text-align: center;
  margin-bottom: 2rem;
}

.signup-header h2 {
  font-size: 2rem;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 0.5rem;
  transition: color 0.3s ease;
}

.signup-header p {
  color: var(--text-secondary);
  font-size: 1rem;
  transition: color 0.3s ease;
}

form {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.form-section {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.form-section h3 {
  font-size: 1.2rem;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 0.5rem;
  padding-bottom: 0.5rem;
  border-bottom: 2px solid var(--border-color);
  transition: all 0.3s ease;
}

.input-group {
  position: relative;
}

input,
textarea {
  width: 100%;
  padding: 1rem;
  border: 1px solid #1a1a1a;
  border-radius: 12px;
  font-family: inherit;
  font-size: 1rem;
  transition: all 0.3s ease;
  background-color: var(--bg-tertiary);
  color: var(--text-primary);
}

input:focus,
textarea:focus {
  outline: none;
  border-color: var(--text-primary);
  background-color: var(--bg-secondary);
  box-shadow: 0 0 0 3px var(--shadow-light);
}

textarea {
  resize: vertical;
  min-height: 100px;
}

.signup-btn {
  padding: 1rem;
  background-color: var(--text-primary);
  color: var(--bg-secondary);
  border: none;
  border-radius: 12px;
  cursor: pointer;
  font-size: 1.1rem;
  font-weight: 600;
  transition: all 0.3s ease;
  margin-top: 1rem;
}

.signup-btn:hover {
  background-color: var(--text-secondary);
  transform: translateY(-2px);
  box-shadow: 0 8px 25px var(--shadow-heavy);
}

.footer-links {
  margin-top: 2rem;
  text-align: center;
  display: flex;
  justify-content: space-between;
  gap: 1rem;
}

.link {
  color: var(--text-primary);
  text-decoration: none;
  font-size: 0.95rem;
  font-weight: 500;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  transition: all 0.2s ease;
}

.link:hover {
  background-color: var(--bg-tertiary);
  color: var(--text-secondary);
}

@media (max-width: 600px) {
  .signup-page {
    padding: 1rem;
  }
  
  .signup-container {
    padding: 2rem;
  }
  
  .footer-links {
    flex-direction: column;
    gap: 0.5rem;
  }
}
</style>
