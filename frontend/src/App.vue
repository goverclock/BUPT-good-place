<template>
  <el-config-provider :locale="zhCn">
    <router-view></router-view>
  </el-config-provider>
</template>

<script setup>

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
</style>
