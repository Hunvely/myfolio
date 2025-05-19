<template>
  <div class="signup-container">
    <h2>Create your account</h2>
    <form @submit.prevent="submit">
      <input v-model="username" type="text" placeholder="Username" required />
      <input v-model="email" type="email" placeholder="Email" required />
      <input v-model="password" type="password" placeholder="Password" required />
      <input v-model="name" type="text" placeholder="Name" required />
      <input v-model="phoneNumber" type="text" placeholder="Phone Number" required />

      <!-- 선택 필드 (옵션) -->
      <input v-model="profileImage" type="text" placeholder="Profile Image URL" />
      <textarea v-model="bio" placeholder="Introduce yourself (Bio)"></textarea>

      <button type="submit">Sign up</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

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
.signup-container {
  max-width: 400px;
  margin: 5rem auto;
  padding: 2rem;
  background: white;
  box-shadow: 0 0 10px rgba(0,0,0,0.05);
  border-radius: 8px;
}

h2 {
  margin-bottom: 1.5rem;
  text-align: center;
}

form {
  display: flex;
  flex-direction: column;
}

input,
textarea {
  padding: 0.75rem;
  margin-bottom: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-family: inherit;
  font-size: 1rem;
}

textarea {
  resize: vertical;
  min-height: 80px;
}

button {
  padding: 0.75rem;
  background-color: black;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  opacity: 0.9;
}
</style>
