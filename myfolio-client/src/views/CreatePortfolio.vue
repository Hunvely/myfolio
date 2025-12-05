<template>
  <div class="create-portfolio-page">
    <div class="portfolio-container">
      <div class="portfolio-header">
        <h2>{{ t('portfolio.create.title') }}</h2>
        <p>{{ t('portfolio.create.subtitle') }}</p>
      </div>

      <form @submit.prevent="submitPortfolio" class="portfolio-form">
        <!-- 기본 정보 섹션 -->
        <div class="form-section">
          <h3 class="section-title">{{ t('portfolio.sections.basicInfo') }}</h3>
          <div class="input-group">
            <label>{{ t('portfolio.fields.name') }}</label>
            <input v-model="formData.name" type="text" :placeholder="t('portfolio.fields.namePlaceholder')" />
          </div>
          <div class="input-group">
            <label>{{ t('portfolio.fields.bio') }}</label>
            <textarea v-model="formData.bio" :placeholder="t('portfolio.fields.bioPlaceholder')" rows="4"></textarea>
          </div>
          <div class="input-group">
            <label>{{ t('portfolio.fields.profileImage') }}</label>
            <input v-model="formData.profileImage" type="text" :placeholder="t('portfolio.fields.profileImagePlaceholder')" />
          </div>
        </div>

        <!-- 프로젝트 섹션 -->
        <div class="form-section">
          <div class="section-header">
            <h3 class="section-title">{{ t('portfolio.sections.projects') }}</h3>
            <button type="button" @click="addProject" class="btn-add">{{ t('portfolio.buttons.add') }}</button>
          </div>
          <div v-for="(project, index) in formData.projects" :key="index" class="item-card">
            <div class="item-header">
              <h4>{{ t('portfolio.fields.project') }} {{ index + 1 }}</h4>
              <button type="button" @click="removeProject(index)" class="btn-remove">×</button>
            </div>
            <div class="input-group">
              <label>{{ t('portfolio.fields.title') }}</label>
              <input v-model="project.title" type="text" :placeholder="t('portfolio.fields.titlePlaceholder')" />
            </div>
            <div class="input-group">
              <label>{{ t('portfolio.fields.description') }}</label>
              <textarea v-model="project.description" :placeholder="t('portfolio.fields.descriptionPlaceholder')" rows="3"></textarea>
            </div>
            <div class="input-row">
              <div class="input-group">
                <label>{{ t('portfolio.fields.startDate') }}</label>
                <input v-model="project.startDate" type="date" />
              </div>
              <div class="input-group">
                <label>{{ t('portfolio.fields.endDate') }}</label>
                <input v-model="project.endDate" type="date" />
              </div>
            </div>
            <div class="input-group">
              <label>{{ t('portfolio.fields.thumbnail') }}</label>
              <input v-model="project.thumbnail" type="text" :placeholder="t('portfolio.fields.thumbnailPlaceholder')" />
            </div>
            <div class="input-group">
              <label>{{ t('portfolio.fields.url') }}</label>
              <input v-model="project.url" type="url" :placeholder="t('portfolio.fields.urlPlaceholder')" />
            </div>
          </div>
        </div>

        <!-- 경력 섹션 -->
        <div class="form-section">
          <div class="section-header">
            <h3 class="section-title">{{ t('portfolio.sections.careers') }}</h3>
            <button type="button" @click="addCareer" class="btn-add">{{ t('portfolio.buttons.add') }}</button>
          </div>
          <div v-for="(career, index) in formData.careers" :key="index" class="item-card">
            <div class="item-header">
              <h4>{{ t('portfolio.fields.career') }} {{ index + 1 }}</h4>
              <button type="button" @click="removeCareer(index)" class="btn-remove">×</button>
            </div>
            <div class="input-group">
              <label>{{ t('portfolio.fields.companyName') }}</label>
              <input v-model="career.companyName" type="text" :placeholder="t('portfolio.fields.companyNamePlaceholder')" />
            </div>
            <div class="input-row">
              <div class="input-group">
                <label>{{ t('portfolio.fields.position') }}</label>
                <input v-model="career.position" type="text" :placeholder="t('portfolio.fields.positionPlaceholder')" />
              </div>
              <div class="input-group">
                <label>{{ t('portfolio.fields.location') }}</label>
                <input v-model="career.location" type="text" :placeholder="t('portfolio.fields.locationPlaceholder')" />
              </div>
            </div>
            <div class="input-group">
              <label>{{ t('portfolio.fields.employmentType') }}</label>
              <select v-model="career.employmentType">
                <option value="FULL_TIME">{{ t('portfolio.fields.fullTime') }}</option>
                <option value="PART_TIME">{{ t('portfolio.fields.partTime') }}</option>
                <option value="CONTRACT">{{ t('portfolio.fields.contract') }}</option>
                <option value="INTERN">{{ t('portfolio.fields.intern') }}</option>
              </select>
            </div>
            <div class="input-row">
              <div class="input-group">
                <label>{{ t('portfolio.fields.startDate') }}</label>
                <input v-model="career.startDate" type="date" />
              </div>
              <div class="input-group">
                <label>{{ t('portfolio.fields.endDate') }}</label>
                <input v-model="career.endDate" type="date" />
              </div>
            </div>
            <div class="input-group">
              <label>{{ t('portfolio.fields.description') }}</label>
              <textarea v-model="career.description" :placeholder="t('portfolio.fields.descriptionPlaceholder')" rows="3"></textarea>
            </div>
          </div>
        </div>

        <!-- 학력 섹션 -->
        <div class="form-section">
          <div class="section-header">
            <h3 class="section-title">{{ t('portfolio.sections.educations') }}</h3>
            <button type="button" @click="addEducation" class="btn-add">{{ t('portfolio.buttons.add') }}</button>
          </div>
          <div v-for="(education, index) in formData.educations" :key="index" class="item-card">
            <div class="item-header">
              <h4>{{ t('portfolio.fields.education') }} {{ index + 1 }}</h4>
              <button type="button" @click="removeEducation(index)" class="btn-remove">×</button>
            </div>
            <div class="input-group">
              <label>{{ t('portfolio.fields.schoolName') }}</label>
              <input v-model="education.schoolName" type="text" :placeholder="t('portfolio.fields.schoolNamePlaceholder')" />
            </div>
            <div class="input-row">
              <div class="input-group">
                <label>{{ t('portfolio.fields.major') }}</label>
                <input v-model="education.major" type="text" :placeholder="t('portfolio.fields.majorPlaceholder')" />
              </div>
              <div class="input-group">
                <label>{{ t('portfolio.fields.degree') }}</label>
                <input v-model="education.degree" type="text" :placeholder="t('portfolio.fields.degreePlaceholder')" />
              </div>
            </div>
            <div class="input-group">
              <label>{{ t('portfolio.fields.status') }}</label>
              <select v-model="education.status">
                <option value="ENROLLED">{{ t('portfolio.fields.enrolled') }}</option>
                <option value="GRADUATED">{{ t('portfolio.fields.graduated') }}</option>
                <option value="DROPPED_OUT">{{ t('portfolio.fields.droppedOut') }}</option>
              </select>
            </div>
            <div class="input-row">
              <div class="input-group">
                <label>{{ t('portfolio.fields.startDate') }}</label>
                <input v-model="education.startDate" type="date" />
              </div>
              <div class="input-group">
                <label>{{ t('portfolio.fields.endDate') }}</label>
                <input v-model="education.endDate" type="date" />
              </div>
            </div>
          </div>
        </div>

        <!-- 기술 스택 섹션 -->
        <div class="form-section">
          <div class="section-header">
            <h3 class="section-title">{{ t('portfolio.sections.skills') }}</h3>
            <button type="button" @click="addSkill" class="btn-add">{{ t('portfolio.buttons.add') }}</button>
          </div>
          <div v-for="(skill, index) in formData.skills" :key="index" class="item-card">
            <div class="item-header">
              <h4>{{ t('portfolio.fields.skill') }} {{ index + 1 }}</h4>
              <button type="button" @click="removeSkill(index)" class="btn-remove">×</button>
            </div>
            <div class="input-row">
              <div class="input-group">
                <label>{{ t('portfolio.fields.skillName') }}</label>
                <input v-model="skill.skillName" type="text" :placeholder="t('portfolio.fields.skillNamePlaceholder')" />
              </div>
              <div class="input-group">
                <label>{{ t('portfolio.fields.skillType') }}</label>
                <select v-model="skill.skillType">
                  <option value="LANGUAGE">{{ t('portfolio.fields.language') }}</option>
                  <option value="FRAMEWORK">{{ t('portfolio.fields.framework') }}</option>
                  <option value="DATABASE">{{ t('portfolio.fields.database') }}</option>
                  <option value="TOOL">{{ t('portfolio.fields.tool') }}</option>
                  <option value="DEVOPS">{{ t('portfolio.fields.devops') }}</option>
                  <option value="ETC">{{ t('portfolio.fields.etc') }}</option>
                </select>
              </div>
            </div>
            <div class="input-group">
              <label>{{ t('portfolio.fields.logoUrl') }}</label>
              <input v-model="skill.logoUrl" type="text" :placeholder="t('portfolio.fields.logoUrlPlaceholder')" />
            </div>
          </div>
        </div>

        <!-- 링크 섹션 -->
        <div class="form-section">
          <div class="section-header">
            <h3 class="section-title">{{ t('portfolio.sections.links') }}</h3>
            <button type="button" @click="addLink" class="btn-add">{{ t('portfolio.buttons.add') }}</button>
          </div>
          <div v-for="(link, index) in formData.links" :key="index" class="item-card">
            <div class="item-header">
              <h4>{{ t('portfolio.fields.link') }} {{ index + 1 }}</h4>
              <button type="button" @click="removeLink(index)" class="btn-remove">×</button>
            </div>
            <div class="input-group">
              <label>{{ t('portfolio.fields.title') }}</label>
              <input v-model="link.title" type="text" :placeholder="t('portfolio.fields.titlePlaceholder')" />
            </div>
            <div class="input-row">
              <div class="input-group">
                <label>{{ t('portfolio.fields.urlType') }}</label>
                <input v-model="link.urlType" type="text" :placeholder="t('portfolio.fields.urlTypePlaceholder')" />
              </div>
              <div class="input-group">
                <label>{{ t('portfolio.fields.url') }}</label>
                <input v-model="link.url" type="url" :placeholder="t('portfolio.fields.urlPlaceholder')" />
              </div>
            </div>
            <div class="input-group">
              <label>{{ t('portfolio.fields.iconUrl') }}</label>
              <input v-model="link.iconUrl" type="text" :placeholder="t('portfolio.fields.iconUrlPlaceholder')" />
            </div>
            <div class="input-group">
              <label>
                <input v-model="link.isVisible" type="checkbox" />
                {{ t('portfolio.fields.isVisible') }}
              </label>
            </div>
          </div>
        </div>

        <!-- 제출 버튼 -->
        <div class="form-actions">
          <button type="submit" class="btn-submit" :disabled="isSubmitting">
            {{ isSubmitting ? t('portfolio.buttons.submitting') : t('portfolio.buttons.submit') }}
          </button>
          <router-link to="/dashboard" class="btn-cancel">{{ t('portfolio.buttons.cancel') }}</router-link>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import axios from 'axios'

const { t } = useI18n()
const router = useRouter()

const isSubmitting = ref(false)

// 임시 userId (실제로는 로그인한 사용자 정보에서 가져와야 함)
const userId = ref(1)

const formData = reactive({
  name: '',
  bio: '',
  profileImage: '',
  projects: [],
  careers: [],
  educations: [],
  skills: [],
  links: []
})

const addProject = () => {
  formData.projects.push({
    title: '',
    description: '',
    startDate: '',
    endDate: '',
    thumbnail: '',
    url: ''
  })
}

const removeProject = (index) => {
  formData.projects.splice(index, 1)
}

const addCareer = () => {
  formData.careers.push({
    companyName: '',
    location: '',
    employmentType: 'FULL_TIME',
    position: '',
    startDate: '',
    endDate: '',
    description: ''
  })
}

const removeCareer = (index) => {
  formData.careers.splice(index, 1)
}

const addEducation = () => {
  formData.educations.push({
    schoolName: '',
    status: 'ENROLLED',
    major: '',
    degree: '',
    startDate: '',
    endDate: ''
  })
}

const removeEducation = (index) => {
  formData.educations.splice(index, 1)
}

const addSkill = () => {
  formData.skills.push({
    skillName: '',
    skillType: 'LANGUAGE',
    logoUrl: ''
  })
}

const removeSkill = (index) => {
  formData.skills.splice(index, 1)
}

const addLink = () => {
  formData.links.push({
    title: '',
    urlType: '',
    iconUrl: '',
    url: '',
    isVisible: true
  })
}

const removeLink = (index) => {
  formData.links.splice(index, 1)
}

const submitPortfolio = async () => {
  isSubmitting.value = true
  
  try {
    // 1. User 정보 업데이트 (필요시)
    if (formData.name || formData.bio || formData.profileImage) {
      // User 업데이트 API 호출 (현재는 생략)
    }

    // 2. 프로젝트 생성
    for (const project of formData.projects) {
      if (project.title) {
        await axios.post('/api/projects', {
          userId: userId.value,
          ...project
        })
      }
    }

    // 3. 경력 생성
    for (const career of formData.careers) {
      if (career.companyName) {
        await axios.post('/api/careers', {
          userId: userId.value,
          ...career
        })
      }
    }

    // 4. 학력 생성
    for (const education of formData.educations) {
      if (education.schoolName) {
        await axios.post('/api/educations', {
          userId: userId.value,
          ...education
        })
      }
    }

    // 5. 기술 스택 생성 및 연결
    for (const skill of formData.skills) {
      if (skill.skillName) {
        // Skill 생성
        const skillResponse = await axios.post('/api/skills', {
          skillName: skill.skillName,
          skillType: skill.skillType,
          logoUrl: skill.logoUrl
        })
        
        // UserSkill 연결
        await axios.post('/api/user-skills', {
          userId: userId.value,
          skillId: skillResponse.data.id
        })
      }
    }

    // 6. 링크 생성
    for (const link of formData.links) {
      if (link.title && link.url) {
        await axios.post('/api/links', {
          userId: userId.value,
          ...link
        })
      }
    }

    alert(t('portfolio.messages.success'))
    router.push('/dashboard')
  } catch (error) {
    console.error('포트폴리오 생성 실패:', error)
    alert(t('portfolio.messages.error'))
  } finally {
    isSubmitting.value = false
  }
}
</script>

<style scoped>
.create-portfolio-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #f9f9fb 0%, #ffffff 100%);
  padding: 2rem;
}

.portfolio-container {
  max-width: 900px;
  margin: 0 auto;
  background: white;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  border-radius: 16px;
  padding: 3rem;
}

.portfolio-header {
  text-align: center;
  margin-bottom: 3rem;
}

.portfolio-header h2 {
  font-size: 2.5rem;
  font-weight: 700;
  color: #000;
  margin-bottom: 0.5rem;
}

.portfolio-header p {
  color: #666;
  font-size: 1.1rem;
}

.portfolio-form {
  display: flex;
  flex-direction: column;
  gap: 3rem;
}

.form-section {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.section-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: #000;
  padding-bottom: 0.5rem;
  border-bottom: 2px solid #e1e5e9;
}

.btn-add {
  padding: 0.5rem 1.5rem;
  background-color: #000;
  color: #fff;
  border: none;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn-add:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.item-card {
  background-color: #f8f9fa;
  padding: 1.5rem;
  border-radius: 12px;
  border: 1px solid #e1e5e9;
}

.item-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.item-header h4 {
  font-size: 1.2rem;
  font-weight: 600;
  color: #000;
}

.btn-remove {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  border: none;
  background-color: #ff4444;
  color: white;
  font-size: 1.5rem;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  line-height: 1;
}

.btn-remove:hover {
  background-color: #cc0000;
  transform: scale(1.1);
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
.input-group textarea,
.input-group select {
  padding: 0.75rem;
  border: 1px solid #e1e5e9;
  border-radius: 8px;
  font-size: 1rem;
  transition: all 0.3s ease;
  font-family: inherit;
}

.input-group input:focus,
.input-group textarea:focus,
.input-group select:focus {
  outline: none;
  border-color: #000;
  box-shadow: 0 0 0 3px rgba(0, 0, 0, 0.1);
}

.input-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}

.form-actions {
  display: flex;
  gap: 1rem;
  justify-content: center;
  margin-top: 2rem;
  padding-top: 2rem;
  border-top: 2px solid #e1e5e9;
}

.btn-submit {
  padding: 1rem 3rem;
  background-color: #000;
  color: #fff;
  border: none;
  border-radius: 12px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  min-width: 180px;
}

.btn-submit:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
}

.btn-submit:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.btn-cancel {
  padding: 1rem 3rem;
  background-color: transparent;
  color: #000;
  border: 2px solid #000;
  border-radius: 12px;
  font-size: 1.1rem;
  font-weight: 600;
  text-decoration: none;
  transition: all 0.3s ease;
  min-width: 180px;
  text-align: center;
}

.btn-cancel:hover {
  background-color: #000;
  color: #fff;
  transform: translateY(-2px);
}

@media (max-width: 768px) {
  .portfolio-container {
    padding: 2rem 1.5rem;
  }

  .portfolio-header h2 {
    font-size: 2rem;
  }

  .input-row {
    grid-template-columns: 1fr;
  }

  .form-actions {
    flex-direction: column;
  }

  .btn-submit,
  .btn-cancel {
    width: 100%;
  }
}
</style>


