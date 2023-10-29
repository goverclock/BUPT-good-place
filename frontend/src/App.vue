<template>
  <div id="nav">
    <router-link to="/">Home</router-link>
    <router-link to="/about">About</router-link>
  </div>
  <el-button type="primary" @click="showMsg">按钮</el-button>
  <hr />
  <el-icon :size="36" :color="color">
    <edit></edit>
  </el-icon>
  <hr />

  <el-config-provider :locale="zhCn">
    <router-view></router-view>
  </el-config-provider>
</template>

<script setup>
const showMsg = () => {
  ElMessageBox.alert('你好！', '提示')
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
