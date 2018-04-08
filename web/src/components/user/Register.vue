<template>
  <div id = "register">
    <el-container>
      <el-header>
        <h2>注册</h2>
      </el-header>
      <el-main style="text-align:left">
        <el-form v-model="ruleForm" ref="ruleForm" labelWidth="43%">
          <el-form-item label="账户">
            <el-input type="text" v-model="ruleForm.account" @blur="checkAccount(ruleForm.account)"
                      clearable></el-input>
            <a style="color: red">{{ruleForm.accountError}}</a>
          </el-form-item>
          <el-form-item label="密码">
            <el-input type="password" v-model="ruleForm.password" clearable></el-input>
          </el-form-item>
          <el-form-item label="确认密码">
            <el-input type="password" v-model="ruleForm.checkPass" @blur="checkPassword"
                      clearable></el-input>
            <a style="color: red">{{ruleForm.passwordError}}</a>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input type="text" v-model="ruleForm.name" @blur="checkName(ruleForm.name)"
                      clearable></el-input>
            <a style="color: red">{{ruleForm.nameError}}</a>
          </el-form-item>
          <el-form-item label="机场">
            <el-input type="text" v-model="ruleForm.airport" @blur="checkAirport(ruleForm.airport)"
                      clearable></el-input>
            <a style="color: red">{{ruleForm.airportError}}</a>
          </el-form-item>
          <el-form-item label="用户类型">
            <el-radio-group v-model="ruleForm.type">
              <el-radio label="管理员"></el-radio>
              <el-radio label="普通用户"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button @click="submit(ruleForm)">提交</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>
<script>
  import ElContainer from "../../../node_modules/element-ui/packages/container/src/main.vue";
  import ElHeader from "../../../node_modules/element-ui/packages/header/src/main.vue";
  import ElFormItem from "../../../node_modules/element-ui/packages/form/src/form-item.vue";
  import ElButton from "../../../node_modules/element-ui/packages/button/src/button.vue";
  import BaseUrl from "../../../config/url.config"

  export default {
    components: {
      ElButton,
      ElFormItem,
      ElHeader,
      ElContainer},
    name:'register',
    data () {
      return {
        ruleForm: {
          name:'',
          nameError: '',
          account:'',
          accountError:'',
          password:'',
          passwordError: '',
          airport:'',
          airportError: '',
          checkPass:'',
          type:''
        },
        beDisabled: true,

      }
    },
    methods : {
      submit (formData) {
        let userInfo = {};
        userInfo['ACCOUNT'] = formData.account;
        userInfo['NAME'] = formData.name;
        userInfo['PASSWORD'] = formData.password;
        userInfo['AIRPORT'] = formData.airport;
        if ('管理员' === formData.type) {
          userInfo['TYPE'] = 'administrator';
        }else {
          userInfo['TYPE'] = 'user'
        }

        this.$axios.post('/api/register', userInfo)
          .then((res) => {
          if(res.data.CODE === 0) {
            this.$router.push('/login');
          }else{
            alert('注册失败')
          }
        }, (err) => {
          console.log(err);
          alert('注册失败')
          });
      },
      resetForm () {
//        this.ruleForm.account = '';
//        this.ruleForm.password = '';
//        this.ruleForm.checkPass = '';
//        this.ruleForm.name = '';
//        this.ruleForm.airport = '';
//        this.ruleForm.type = '';
        for (let key in this.ruleForm) {
          this.ruleForm[key] = '';
        }
      },
      checkAccount (account) {
        if (account === '') {
          this.ruleForm.accountError = '账号不能为空';
        }
      },
      checkPassword () {
        if (this.ruleForm.password === '') {
          this.ruleForm.passwordError = '密码不能为空';
        }
        if (this.ruleForm.password !== this.ruleForm.checkPass) {
          this.ruleForm.passwordError = '两次输入的密码不一致'
        }
      },
      checkName (name) {
        if (name === '') {
          this.ruleForm.nameError = '姓名不能为空'
        }
      },
      checkAirport (airport) {
        if (airport === '') {
          this.ruleForm.accountError = '机场不能为空'
        }
      }
    }
  }
</script>
<style>

  .el-input {
    width: 30%
  }
</style>
