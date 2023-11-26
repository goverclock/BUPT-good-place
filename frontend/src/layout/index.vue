<template>
  <div class="page-container">
    <header>
      <PageHeader></PageHeader>
    </header>
    <main>
      <div v-if="showLeft" class="left">
        <PageSidebar></PageSidebar>
      </div>
      <div :class="{ 'right': showLeft, 'right-no-padding': !showLeft }">
        <router-view></router-view>
      </div>
    </main>
  </div>
</template>

<script setup>
import PageHeader from './components/PageHeader.vue'
import PageSidebar from './components/PageSidebar.vue'

const route = useRoute();
const showLeft = computed(() => {
  const routeName = route.name;
  return !['Login', 'Register', 'NotFound'].includes(routeName);
})

const store = useStore();
const router = useRouter();

const isLogin = store.getters['user/isLogin'];
if (isLogin) {
  const routeName = route.name;
  if (["Home", "Login"].includes(routeName)) {
    router.push('/user/detail')
  }
} else {
  router.push('/login')
}
</script>

<style lang="scss">
.common-layout {

  .el-header,
  .el-footer,
  .el-main,
  .el-aside {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .el-header,
  .el-footer {
    background-color: var(--el-color-primary-light-7);
    color: var(--el-text-color-primary);
    text-align: center;
  }

  .el-aside {
    background-color: var(--el-color-primary-light-8);
    color: var(--el-text-color-primary);
    text-align: center;
  }

  .el-main {
    background-color: var(--el-color-primary-light-9);
    color: var(--el-text-color-primary);
    text-align: center;

    height: 150px;
  }
}

.page-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  overflow: hidden;

  >header {
    height: 54px;
    background: #fff;
    color: #000;
  }

  >main {
    display: flex;
    flex: 1;
    overflow: auto;

    >.left {
      height: 100%;
      width: 13%;
      background-color: #545c64;
      color: #fff;
    }

    >.right {
      flex: 1;
      overflow: hidden;
      padding-top: 1%;
      padding-left: 2%;
      padding-right: 2%;

      >.main-body {
        padding: 16px 16px 16px;
        overflow: auto;
        height: 100%;
        box-sizing: border-box;
      }
    }

    >.right-no-padding {
      flex: 1;
      overflow: hidden;

      >.main-body {
        padding: 16px 16px 16px;
        overflow: auto;
        height: 100%;
        box-sizing: border-box;
      }
    }
  }
}
</style>