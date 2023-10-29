<template>
  <el-config-provider :locale="zhCn">
    <router-view></router-view>
  </el-config-provider>
</template>

<script setup>
  import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
</script>

<style>
html,
body {
  height: 100%;
}

#app {
  height: 100%;
  overflow: hidden;
}

.flex-center {
  display: flex;
  align-items: center;
  justify-content: center;
}

.cursor {
  cursor: pointer;
}
</style>
