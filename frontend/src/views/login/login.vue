<template>
  <div class="page flex-center">
    <div class="sign-box">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="86px">
        <h3 class="title">登录</h3>
        <el-form-item label="用户名" prop="account">
          <el-input v-model="form.account" placeholder="用户名" prefix-icon="user"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="密码" prefix-icon="lock"></el-input>
        </el-form-item>
        <el-form-item label>
          <el-button type="primary" :loading="loginLoading" class="w100p" @click="doLogin">登录</el-button>
          <el-link type="primary" href="/#/register" class="w100p">还没有账号?去注册</el-link>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
// import axios from 'axios';
import { LoginReq, RegisterReq } from '@/request/api'
const formRef = ref();
const form = reactive({
  account: "",
  password: "",
})

const rules = computed(() => {
  return {
    account: [
      {
        required: true,
        message: "请输入用户名",
        trigger: ["change", "blur"],
      },
      {
        pattern: /^[a-zA-Z][a-zA-Z0-9_-]{3,31}$/,
        message: "用户名由英文字母开头的长度为4-32位字母,_和-组成",
        trigger: ["change", "blur"],
      },
    ],
    password: {
      required: true,
      min: 0,
      // message: "请输入至少4个字符的密码",
      trigger: ["change", "blur"],
    },
  }
});

const loginLoading = ref(false)
const registerLoading = ref(false)
const store = useStore();
const router = useRouter();

function doLogin() {
  formRef.value.validate((valid) => {
    if (!valid) return;
    loginLoading.value = true;
    let data = {
      user_id: form.account,
      password: form.password,
    }

    LoginReq(data)
      .then((res) => {
        ElMessage({ message: "登录成功!", type: "success" });
        console.log("LoginReq OK:", res.data)
        store.commit('user/setToken', res.data);  // token
        // store.dispatch('user/refreshInfo');
        // store.commit("setRouteLoaded", false);
        localStorage.setItem('pm_token', res.data)
        router.push("/");
      }).catch(err => {
        console.log("LoginReq error:", err)
      }).finally(() => {
        loginLoading.value = false;
      })
  });
}

function doRegister() {
  registerLoading.value = true;
  let data = {
    user_id: form.account,
    password: form.password,
  }

  RegisterReq(data)
    .then((res) => {
      console.log("RegisterReq OK:", res.data)
    }).catch(err => {
      console.log("RegisterReq error:", err)
    }).finally(() => {
      registerLoading.value = false;
    })
}
</script>

<style lang="scss">
.page {
  height: 100%;
  background: url(@/assets/bg.jpg) no-repeat;
  background-size: cover;
}

.sign-box {
  width: 400px;
  background: #fff;
  padding: 30px 50px 20px 30px;
  border-radius: 4px;
  box-shadow: 0 0 10px #022c44;
}

.title {
  text-align: center;
  font-size: 20px;
  line-height: 30px;
  margin-top: 0;
  margin-bottom: 10px;
  color: #000;
}

.w100p {
  width: 50%;
}

</style>