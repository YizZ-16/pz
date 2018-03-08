<template>
<div id = 'one'>
<head>
  <meta charset="utf-8">
  <title>配载基础数据维护平台</title>
  <link rel="stylesheet" href="css/login.css">
</head>
<body>
<div class="container">
  <div class="login-box">
    <div class="login-title">
      <a>登录</a>
    </div>
    <div class="login-input">
      <p>
        <a >帐号:</a>
        <input name="username"  id="username" value="" v-model="username">
      </p>
      <p>
        <a >密码:</a>
        <input type="password" name="password" id="password" value="" v-model="password">
      </p>

    </div>
    <div class="login-btn">
      <input type="button" name="login" value="登录" id="login" @click="login">
    </div>
  </div>
</div>
</body>
</div>
</template>
<script>
  export default {
    name: 'one',
    data () {
      return {
        username: '',
        password: ''
      }
    },
    methods: {
      login () {
        this.$http.get('http://localhost:8081/login/info')
          .then((data) => {console.log(data)})

      }
    }
  }
</script>
<style scoped>
  body{

  }
  .container{
    position: fixed;
    top: 50%;
    left: 50%;
    -webkit-transform: translateX(-50%) translateY(-50%);
    -moz-transform: translateX(-50%) translateY(-50%);

  }
  .login-box{
    background: hsla(0,0%,100%,.3);
    padding: 60px;
    border-radius: 20px;
  }
  .login-title{
    font-size: 40px;
    text-align: center;
    margin-left: auto;
    margin-right: auto;
  }
  .login-input{

  }
  .login-input input{
    border-left-style: none;
    border-right-style:none;
    border-top-style: none;
    background-color:transparent;
  }
  .login-btn{
    text-align: center;
    margin-left: auto;
    margin-right: auto;
  }
  .login-btn input{
    width: 130px;
    height: 30px;
    border-radius: 6px;
    background-color: #999999;
  }
  .login-btn input:hover{

    background-color: #999999;
  }

</style>
