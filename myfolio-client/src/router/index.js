import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import SignUpPage from '../views/SignUpPage.vue'
import Login from '../views/Login.vue'
import DashboardPage from '../views/DashBoardPage.vue'


const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/home', name: 'HomePage', component: Home },
  { path: '/signup', name: 'SignUp', component: SignUpPage },
  { path: '/login', name: 'Login', component: Login },
  { path: '/dashboard', name: 'Dashboard', component: DashboardPage }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
