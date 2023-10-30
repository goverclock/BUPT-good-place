<template>
  <h1>Login page</h1>
  <el-button @click="textAxios">测试Axios</el-button>
</template>

<script setup>
import axios from 'axios'
function textAxios() {
  axios.get('http://localhost:8080/')
    .then(res => {
      alert(res.data);
    })
    .catch(error => {
      alert(error);
    })
}
</script>