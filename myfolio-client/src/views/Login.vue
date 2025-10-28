<template>
  <div class="login-page">
    <div class="login-container">
      <div class="login-header">
        <h2>로그인</h2>
        <p>포트폴리오에 오신 것을 환영합니다</p>
      </div>
      
      <form @submit.prevent="submit">
        <div class="input-group">
          <input v-model="email" type="email" placeholder="이메일" required />
        </div>
        <div class="input-group">
          <input v-model="password" type="password" placeholder="비밀번호" required />
        </div>
        
        <button type="submit" class="login-btn">로그인</button>
        
        <div class="footer-links">
          <router-link to="/signup" class="link">회원가입</router-link>
          <router-link to="/" class="link">홈으로</router-link>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const email = ref('')
const password = ref('')

const submit = async () => {
  try {
    // 현재는 Security 없으니 실제 인증 없이 로그인 성공 처리
    alert('로그인 성공!')
    router.push('/dashboard') // 향후 Dashboard 페이지로 이동
  } catch (error) {
    console.error('로그인 실패:', error)
    alert('로그인에 실패했습니다.')
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #f9f9fb 0%, #ffffff 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem;
}

.login-container {
  max-width: 420px;
  width: 100%;
  background: white;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  border-radius: 16px;
  padding: 3rem;
}

.login-header {
  text-align: center;
  margin-bottom: 2rem;
}

.login-header h2 {
  font-size: 2rem;
  font-weight: 700;
  color: #000;
  margin-bottom: 0.5rem;
}

.login-header p {
  color: #666;
  font-size: 1rem;
}

form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.input-group {
  position: relative;
}

input {
  width: 100%;
  padding: 1rem;
  border: 2px solid #e1e5e9;
  border-radius: 12px;
  font-family: inherit;
  font-size: 1rem;
  transition: all 0.3s ease;
  background-color: #f8f9fa;
  color: #000;
}

input:focus {
  outline: none;
  border-color: #000;
  background-color: white;
  box-shadow: 0 0 0 3px rgba(0, 0, 0, 0.1);
}

.login-btn {
  padding: 1rem;
  background-color: #000;
  color: white;
  border: none;
  border-radius: 12px;
  cursor: pointer;
  font-size: 1.1rem;
  font-weight: 600;
  transition: all 0.3s ease;
  margin-top: 0.5rem;
}

.login-btn:hover {
  background-color: #333;
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.2);
}

.footer-links {
  margin-top: 2rem;
  text-align: center;
  display: flex;
  justify-content: space-between;
  gap: 1rem;
}

.link {
  color: #000;
  text-decoration: none;
  font-size: 0.95rem;
  font-weight: 500;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  transition: all 0.2s ease;
}

.link:hover {
  background-color: #f0f0f0;
  color: #333;
}

@media (max-width: 480px) {
  .login-page {
    padding: 1rem;
  }
  
  .login-container {
    padding: 2rem;
  }
  
  .footer-links {
    flex-direction: column;
    gap: 0.5rem;
  }
}
</style>
