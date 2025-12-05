import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import SignUpPage from '../views/SignUpPage.vue'
import Login from '../views/Login.vue'
import DashboardPage from '../views/DashBoardPage.vue'
import PortfolioList from '../views/PortfolioList.vue'
import CreatePortfolio from '../views/CreatePortfolio.vue'
import MyPage from '../views/MyPage.vue'


const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/home', name: 'HomePage', component: Home },
  { path: '/signup', name: 'SignUp', component: SignUpPage },
  { path: '/login', name: 'Login', component: Login },
  { path: '/dashboard', name: 'Dashboard', component: DashboardPage },
  { path: '/dashboard/portfolios', name: 'PortfolioList', component: PortfolioList },
  { path: '/portfolio/create', name: 'CreatePortfolio', component: CreatePortfolio },
  { path: '/dashboard/profile', name: 'MyPage', component: MyPage }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
