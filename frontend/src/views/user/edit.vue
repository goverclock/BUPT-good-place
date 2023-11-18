<template>
  <h1>user edit page</h1>
  <el-button type="primary" plain @click="testAPI">test api</el-button>
</template>

<script setup>
import { ElMessage } from 'element-plus';
import { GetUserInfoReq } from '@/request/api/user';

function testAPI() {
  let data = {
    user_id: "testUser01",
  }
  GetUserInfoReq(data).then(res => {
    ElMessage({ message: res })
  }).catch(err => {
    console.log(err)
  })
}

</script>
