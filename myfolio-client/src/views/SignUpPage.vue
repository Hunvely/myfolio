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
            <input 
              v-model="email" 
              type="email" 
              :placeholder="t('signup.email')" 
              :class="{ 'input-error': emailErrorMsg, 'input-success': emailValid }"
              @blur="validateEmail"
              required 
            />
            <span v-if="emailErrorMsg" class="error-msg">{{ emailErrorMsg }}</span>
            <span v-if="emailValid" class="success-msg">{{ t('signup.emailAvailable') }}</span>
            <span v-if="emailChecking" class="checking-msg">{{ t('signup.emailChecking') }}</span>
          </div>
          <div class="input-group">
            <input v-model="password" type="password" :placeholder="t('signup.password')" required />
          </div>
          <div class="input-group">
            <input v-model="name" type="text" :placeholder="t('signup.name')" required />
          </div>
          <div class="input-group">
            <input 
              v-model="phoneNumber" 
              type="text" 
              :placeholder="t('signup.phoneNumber')" 
              @input="formatPhoneNumber"
              maxlength="13"
              required 
            />
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
const emailErrorMsg = ref('')
const emailValid = ref(false)
const emailChecking = ref(false)
const password = ref('')
const name = ref('')
const phoneNumber = ref('')
const profileImage = ref('')
const bio = ref('')

// 이메일 형식 유효성 검사
const isValidEmail = (value) => {
  const pattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/
  return pattern.test(value)
}

// 이메일 중복 체크 API
const checkEmailDuplicate = async (emailValue) => {
  try {
    const response = await axios.get('/api/users/check-email', {
      params: { email: emailValue }
    })
    return response.data.available
  } catch (error) {
    console.error('이메일 중복 체크 실패:', error)
    throw error
  }
}

// 이메일 유효성 검사 (형식 + 중복)
const validateEmail = async () => {
  emailErrorMsg.value = ''
  emailValid.value = false
  
  if (!email.value) {
    return
  }
  
  // 형식 검사
  if (!isValidEmail(email.value)) {
    emailErrorMsg.value = t('signup.emailInvalidFormat')
    return
  }
  
  // 중복 체크
  emailChecking.value = true
  try {
    const isAvailable = await checkEmailDuplicate(email.value)
    if (isAvailable) {
      emailValid.value = true
    } else {
      emailErrorMsg.value = t('signup.emailDuplicate')
    }
  } catch (error) {
    emailErrorMsg.value = t('signup.emailCheckError')
  } finally {
    emailChecking.value = false
  }
}

// 전화번호 포맷팅 (010-1234-5678)
const formatPhoneNumber = (event) => {
  let value = event.target.value.replace(/[^0-9]/g, '')
  
  if (value.length > 11) {
    value = value.slice(0, 11)
  }
  
  if (value.length <= 3) {
    phoneNumber.value = value
  } else if (value.length <= 7) {
    phoneNumber.value = `${value.slice(0, 3)}-${value.slice(3)}`
  } else {
    phoneNumber.value = `${value.slice(0, 3)}-${value.slice(3, 7)}-${value.slice(7)}`
  }
}

// 회원가입 저장
const submit = async () => {
  // 이메일 유효성 검사 확인
  if (!emailValid.value) {
    await validateEmail()
    if (!emailValid.value) {
      alert(t('signup.emailValidationRequired'))
      return
    }
  }
  
  const signUpData = {
    username: username.value,
    email: email.value,
    password: password.value,
    name: name.value,
    phoneNumber: phoneNumber.value.replace(/-/g, ''),
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
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
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

input.input-error {
  border-color: #e74c3c;
}

input.input-success {
  border-color: #27ae60;
}

.error-msg {
  color: #e74c3c;
  font-size: 0.85rem;
  margin-top: 0.25rem;
}

.success-msg {
  color: #27ae60;
  font-size: 0.85rem;
  margin-top: 0.25rem;
}

.checking-msg {
  color: var(--text-secondary);
  font-size: 0.85rem;
  margin-top: 0.25rem;
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
