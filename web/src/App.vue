<template>
  <div id = "app">
    <!--<el-container>-->
      <!--<el-header class="head"></el-header>-->
      <!--<el-container class="content">-->
        <!--<el-aside width="100px">-->
          <!--<el-menu  :default-active="$route.path"-->
                    <!--router>-->
            <!--<el-menu-item index="/statistics">统计</el-menu-item>-->
            <!--<el-menu-item index="/maintain">维护</el-menu-item>-->
            <!--<el-menu-item index="/look">查看</el-menu-item>-->
          <!--</el-menu>-->
        <!--</el-aside>-->
        <!--<el-main>-->
          <!--<router-view></router-view>-->
        <!--</el-main>-->
      <!--</el-container>-->
      <!--<el-footer style="height: 30px"></el-footer>-->
    <!--</el-container>-->
    <!--<div id="app">-->

    <!--<img src="./assets/logo.png">-->
    <div id = 'content'>
      <router-view></router-view>
    </div>
    <!--</div>-->
  </div>
</template>

<script>
import ElContainer from "../node_modules/element-ui/packages/container/src/main.vue";
import ElHeader from "../node_modules/element-ui/packages/header/src/main.vue";
import ElFooter from "../node_modules/element-ui/packages/footer/src/main.vue";

export default {
  components: {
    ElFooter,
    ElHeader,
    ElContainer},
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0px;
  margin-left: 0px;
  margin-right: 0px;
}
/*#head {*/
  /*height: 10%;*/
  /*}*/

body{
  margin:2px;
  height: 100%;
}
</style>
