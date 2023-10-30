<template>
  <h1>Login page</h1>
  <el-button @click="textAxios">测试Axios</el-button>
</template>

<script setup>
import axios from 'axios'
import config from '../../request/config'
function textAxios() {
  axios.get('/test/', {...config})
    .then(res => {
      alert(res.data);
    })
    .catch(error => {
      alert(error);
    })
}
</script>
