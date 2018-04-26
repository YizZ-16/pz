<template>
  <div class="dialog">
    <div class="loginPage">
      <h1>登录</h1>
      <el-form>
        <el-form-item label="用户名">
          <el-input type="text" id="user" v-model="formName.user" @blur="inputBlur('user',formName.user)"></el-input>
          <p>{{formName.userError}}</p>
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" id="password" v-model="formName.password" @blur="inputBlur('password',formName.password)"></el-input>
          <p>{{formName.passwordError}}</p>
        </el-form-item>
        <el-button type="primary" @click="submitForm(formName)" v-bind:disabled="formName.beDisabled">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form>
    </div>
  </div>
</template>

<style scoped>
  .loginPage{
    position: absolute;
    top: 40%;
    left: 50%;
    margin-top: -150px;
    margin-left: -175px;
    width: 350px;
    min-height: 300px;
    padding: 30px 20px 20px;
    border-radius: 8px;
    box-sizing: border-box;
    /*background-color: #fff;*/
    background-image: url("../../assets/test.jpg");
  }
  .loginPage p{
    color: red;
    text-align: left;
  }
</style>

<script>

  export default {
    name: '',
    data () {
      return {
        formName: {//表单中的参数
          user: '',
          userError: '',
          password: '',
          passwordError: '',
          beDisabled: true
        }
      }
    },
    /*props:[
            'fromParent'
    ],*/
    watch: {
      'formName.user': function () {
        this.formName.beDisabled = true;
        if ( this.formName.user !== '' && this.formName.password !== '') {
          this.formName.beDisabled = false;
        }
      },
      'formName.password': function () {
        this.formName.beDisabled = true;
        if ( this.formName.user !== '' && this.formName.password !== '') {
          this.formName.beDisabled = false;
        }
      }

    },
    methods: {
      resetForm:function(){
        this.formName.user = '';
        this.formName.userError = '';
        this.formName.password = '';
        this.formName.passwordError = '';
      },
      submitForm:function(formName){
        let params = {};
        params['ACCOUNT'] = formName.user;
        params['PASSWORD'] = formName.password;
        this.$axios.post('/api/user/login',params)
          .then((res) => {
            if (res.data.CODE === 0) {
              this.$store.commit('set', res.data.DATA)
              this.$router.push('/main')
            }else{
              alert("login failed")
            }
          }, (err) =>{
          console.log(err);
            alert("login failed")
          })
      },
      inputBlur:function(errorItem,inputContent){
        if (errorItem === 'user') {
          if (inputContent === '') {
            this.formName.userError = '用户名不能为空'
          }else{
            this.formName.userError = '';
          }
        }else if(errorItem === 'password') {
          if (inputContent === '') {
            this.formName.passwordError = '密码不能为空'
          }else{
            this.formName.passwordError = '';
          }
        }

      }
    }
  }
</script>
