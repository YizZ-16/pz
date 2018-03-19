import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/user/Login'
//import Upload from '@/components/Upload'
import Maintain from '@/components/statistics/Maintain'
import Main from '@/components/Main'
import Register from '@/components/user/Register'
import Statis from '@/components/statistics/Statis'
import Look from '@/components/statistics/Look'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'first page',
      component: Login
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/register',
      name: 'register',
      component: Register
    },
    {
      path: '/main',
      name: 'main',
      component: Main,
      children:[
        {
          path: '/maintain',
          name: 'Maintain',
          component: Maintain
        },
        {
          path: '/statis',
          name:'Statis',
          component: Statis
        },
        {
          path:'/look',
          name:'Look',
          component: Look
        }
      ]
    },



  ]
})
