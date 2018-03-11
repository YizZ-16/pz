import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Login from '@/components/user/Login'
//import Upload from '@/components/Upload'
import Maintain from '@/components/Maintain'

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
    // {
    //   path: '/upload',
    //   name: 'upload',
    //   component: Upload
    // },
    {
      path: '/maintain',
      name: 'Maintain',
      component: Maintain
    }

  ]
})
