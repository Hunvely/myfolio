<template>
  <div class="main">
    <div class="hero-section">
      <h1 class="title">나만의 포트폴리오,<br />mypolio에서 시작하세요.</h1>
      <p class="subtitle">디자이너, 개발자, 누구든지 쉽게 만드는 온라인 포트폴리오</p>
      <div class="buttons">
        <router-link to="/signup" class="btn">지금 시작하기</router-link>
        <router-link to="/login" class="btn-outline">로그인</router-link>
      </div>
    </div>
    
    <div class="features-section">
      <div class="feature-card">
        <div class="feature-icon">🎨</div>
        <h3>아름다운 디자인</h3>
        <p>모던하고 세련된 템플릿으로 포트폴리오를 꾸며보세요</p>
      </div>
      <div class="feature-card">
        <div class="feature-icon">⚡</div>
        <h3>빠른 제작</h3>
        <p>몇 분만에 전문적인 포트폴리오를 완성할 수 있습니다</p>
      </div>
      <div class="feature-card">
        <div class="feature-icon">📱</div>
        <h3>반응형 디자인</h3>
        <p>모든 기기에서 완벽하게 보이는 반응형 포트폴리오</p>
      </div>
    </div>
  </div>
</template>

<script setup>
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
