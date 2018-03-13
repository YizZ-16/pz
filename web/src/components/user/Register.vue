<template>
  <div id = "register">
    <el-container>
      <el-header>
        <h2>注册</h2>
      </el-header>
      <el-main style="text-align:left">
        <el-form v-model="ruleForm" ref="ruleForm" labelWidth="43%">
          <el-form-item label="账户">
            <el-input type="text" v-model="ruleForm.account" clearable></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input type="password" v-model="ruleForm.password" clearable></el-input>
          </el-form-item>
          <el-form-item label="确认密码">
            <el-input type="password" v-model="ruleForm.checkPass"></el-input>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input type="text" v-model="ruleForm.name" clearable></el-input>
          </el-form-item>
          <el-form-item label="机场">
            <el-input type="text" v-model="ruleForm.airport" clearable></el-input>
          </el-form-item>
          <el-form-item label="用户类型">
            <el-radio-group v-model="ruleForm.type">
              <el-radio label="administrator"></el-radio>
              <el-radio label="normal_user"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button @click="submit(ruleForm)">提交</el-button>
            <el-button @click="reset(ruleForm)">重置</el-button>
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
          account:'',
          password:'',
          airport:'',
          checkPass:'',
          type:''
        },

      }
    },
    methods : {
      submit (formData) {
        let userInfo = {};
        userInfo['ACCOUNT'] = formData.account;
        userInfo['NAME'] = formData.name;
        userInfo['PASSWORD'] = formData.password;
        userInfo['AIRPORT'] = formData.airport;
        userInfo['TYPE'] = formData.type;
        this.$axios.post('/api/register', userInfo)
          .then((res) => {

        });
      },
      reset (formData) {

      }
    }
  }
</script>
<style>

  .el-input {
    width: 30%
  }
</style>
