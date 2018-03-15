<template>
  <div id = "main">
    <el-container>
      <el-header class="head">
        <marquee  behavior="scroll">
          {{message}}
        </marquee>
        <el-dropdown>
          <span class="el-dropdown-link">
            {{user.name}}
            <i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>{{user.account}}</el-dropdown-item>
            <el-dropdown-item>{{user.airport}}</el-dropdown-item>
            <el-dropdown-item>{{user.type}}</el-dropdown-item>
            <!--<el-dropdown-item disabled>双皮奶</el-dropdown-item>-->
            <el-dropdown-item divided @click="logout">退出</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
      <el-container class="content">
        <el-aside width="100px">
          <el-menu  :default-active="$route.path"
                    router>
            <el-menu-item index="/statistics">统计</el-menu-item>
            <el-menu-item index="/maintain" v-if = "isAdministrator">维护</el-menu-item>
            <el-menu-item index="/look">查看</el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
      <el-footer style="height: 30px"></el-footer>
    </el-container>
  </div>
</template>

<script>
  import ElContainer from "../../node_modules/element-ui/packages/container/src/main.vue";
  import ElHeader from "../../node_modules/element-ui/packages/header/src/main.vue";
  import ElFooter from "../../node_modules/element-ui/packages/footer/src/main.vue";

  export default {
    components: {
      ElFooter,
      ElHeader,
      ElContainer},
    name: 'App',
    mounted () {
      if (JSON.stringify(this.$store.state.user)==='{}'){
        this.$router.push('/login');
      }
      const type = this.$store.state.user.TYPE;
      if (type === 'administrator') {
        this.isAdministrator = true
      }
      this.user.name = this.$store.state.user.NAME;
      this.user.account = this.$store.state.user.ACCOUNT;
      this.user.airport = this.$store.state.user.AIRPORT;
      this.user.type = this.$store.state.user.TYPE
     },
    data () {
      return {
        message:'hello world',
        isAdministrator: false,
        user: {
          name:'default',
          account:'unknown',
          airport:'unknown',
          type:'default'
        }
      }
    },
    methods : {
      logout () {

      }
    }
  }
</script>

<style>

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
    height: 50em;

  }

  .el-dropdown {
    position: absolute;
    right: 10%;
  }

  marquee {
    width: 850px;
    position: absolute;
    left:10%;
  }
</style>

