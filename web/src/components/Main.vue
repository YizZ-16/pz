<template>
  <div id = "main">
    <el-container>
      <el-header class="head">
        <marquee  behavior="scroll">
          最近更新数据【
          所属航空：{{message.PLANE_AIRLINES}},
          机号：{{message.PLANE_REG}},
          机型：{{message.PLANE_TYPE}},
          飞机布局：{{message.PLANE_BUJU}},
          CND表号：{{message.PLANE_CND}},
          基本重量：{{message.PLANE_BOW}},
          基本指数：{{message.PLANE_BOI}},
          最大无油重量：{{message.PLANE_MZDW}},
          最大起飞重量：{{message.PLANE_MZFW}},
          最大落地重量：{{message.PLANE_MTOW}},
          更新时间：{{message.PLANE_UPDATE_DATE}}
          】
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
            <el-dropdown-item><a @click="registerUser" v-if="isAdministrator">注册用户</a></el-dropdown-item>
            <el-dropdown-item ><a @click="logout">退出</a></el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
      <el-container class="content">
        <el-aside width="100px">
          <el-menu  :default-active="$route.path"
                    router>
            <el-menu-item index="/statis">
              <i class="el-icon-document"></i>
              统计
            </el-menu-item>
            <el-menu-item index="/maintain" v-if="isAdministrator">
              <i class="el-icon-setting"></i>
              维护
            </el-menu-item>
            <el-menu-item index="/look">
              <i class="el-icon-menu"></i>
              查看
            </el-menu-item>
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
      this.user.type = this.$store.state.user.TYPE;
      this.queryRecentEditRecord();
      setInterval(() => {
        this.queryRecentEditRecord();
      }, 1000*60*5);
      this.$router.push('/statis');
     },
    data () {
      return {
        message:'hello world',
        isAdministrator: false,
        user: {
          name:'default',
          airport:'unknown',
          type:'default'
        }
      }
    },
    methods : {
      logout () {
        this.$axios.get('/api/user/logout')
          .then((res)=>{
            if (res.data.CODE === 0) {
              this.$store.commit('clear');
              this.$router.push('/login');
            }else {
              alert("logout failed")
            }
          });
      },
      queryRecentEditRecord(){
        this.$axios.get('/api/pz/recent')
          .then((res) =>{
            this.message = res.data.DATA;
          })
      },
      registerUser () {
        this.$router.push('/register');
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

  div.el-dropdown {
    position: absolute;
    right: 10%;
  }

  marquee {
    width: 850px;
    position: absolute;
    left:10%;
  }
</style>

