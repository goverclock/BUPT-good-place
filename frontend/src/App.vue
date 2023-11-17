<template>
  <el-config-provider :locale="zhCn">
    <router-view></router-view>
  </el-config-provider>
</template>

<script setup>
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
const store = useStore();
const router = useRouter();

const isLogin = store.getters['user/isLogin'];
if (isLogin) {
  router.push('/user/detail')
} else {
  router.push('/login')
}

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
