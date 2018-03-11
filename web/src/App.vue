<template>
  <div id = "app">
  <el-container>
    <el-header class="head"></el-header>
    <el-container class="content">
      <el-aside width="100px">
        <el-menu  :default-active="$route.path"
                  router>
          <el-menu-item index="/">统计</el-menu-item>
          <el-menu-item index="/list">维护</el-menu-item>
          <el-menu-item index="/">查看</el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
    <el-footer style="height: 30px"></el-footer>
  </el-container>
  <!--<div id="app">-->
    <!--<div id = 'head'>-->
      <!--<h2>配载基础数据平台</h2>-->
    <!--</div>-->
    <!--&lt;!&ndash;<img src="./assets/logo.png">&ndash;&gt;-->
    <!--<div id = 'content'>-->
      <!--<router-view/>-->
    <!--</div>-->
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
  /*font-family: 'Avenir', Helvetica, Arial, sans-serif;*/
  /*-webkit-font-smoothing: antialiased;*/
  /*-moz-osx-font-smoothing: grayscale;*/
  /*text-align: center;*/
  /*color: #2c3e50;*/
  /*margin-top: 100px;*/
  /*margin-left: 50px;*/
  /*margin-right: 50px;*/
}
/*#head {*/
  /*height: 10%;*/
  /*}*/

body{
  margin:2px;
  height: 100%;
}

.el-header.head {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}
.el-footer {
  background-color: #999999;
  color: #333;
  text-align: center;
  line-height: 30px;
}
.el-aside {
  /*background-color: #2c3e50;*/
  border-right: dotted #999999 1px;
  border-left: solid #999999 1px;
  }

.el-container.content {
  height: auto;

  }
</style>
