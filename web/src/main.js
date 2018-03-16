// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import VueResource from 'vue-resource'
import axios from 'axios'
//import VueAxios from 'vue-axios'
import uploader from 'vue-simple-uploader'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
// 引入样式
import 'vue-easytable/libs/themes-base/index.css'
// 导入 table 和 分页组件
import {VTable,VPagination} from 'vue-easytable'

import Vuex from 'vuex'
import echarts from 'echarts'

Vue.config.productionTip = false
Vue.use(VueResource)
Vue.use(uploader)
Vue.use(ElementUI)
//Vue.use(VueAxios, axios)
//Vue.use(axios)
Vue.use(Vuex)
// 注册到全局
Vue.component(VTable.name, VTable)
Vue.component(VPagination.name, VPagination)
/* eslint-disable no-new */
Vue.prototype.$axios = axios
Vue.prototype.$echarts = echarts
const store = new Vuex.Store({
  state: {
    user: {

    }
  },
  mutations: {
    set (state,user) {
      state.user = user;
    }
  }

})

new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
