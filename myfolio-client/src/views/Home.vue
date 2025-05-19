<template>
  <div class="main">
    <h1 class="title">나만의 포트폴리오,<br />mypolio에서 시작하세요.</h1>
    <p class="subtitle">디자이너, 개발자, 누구든지 쉽게 만드는 온라인 포트폴리오</p>
    <div class="buttons">
      <router-link to="/signup" class="btn">지금 시작하기</router-link>
      <router-link to="/login" class="btn-outline">로그인</router-link>
    </div>
  </div>
</template>

<script setup>
</script>

<style scoped>
.main {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  text-align: center;
  background-color: #f9f9fb;
  padding: 2rem;
}

.title {
  font-size: 2.8rem;
  font-weight: 700;
  margin-bottom: 1rem;
  line-height: 1.4;
  color: black;;
}

.subtitle {
  font-size: 1.2rem;
  color: #555;
  margin-bottom: 2.5rem;
}

.buttons {
  display: flex;
  gap: 1rem;
}

.btn, .btn-outline {
  padding: 0.75rem 1.75rem;
  font-size: 1rem;
  border-radius: 8px;
  text-decoration: none;
  transition: background-color 0.2s;
}

.btn {
  background-color: #000;
  color: #fff;
}

.btn-outline {
  border: 1px solid #000;
  color: #000;
  background-color: transparent;
}

.btn:hover, .btn-outline:hover {
  opacity: 0.9;
}
</style>
