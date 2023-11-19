<template>
  <div class="not-found-container">
    <el-container>
      <el-main>
        <el-row class="error-container">
          <el-col :span="24" class="error-content">
            <h1 class="error-title">404</h1>
            <p class="error-description">页面不存在</p>
            <el-button @click="goBack" class="go-back-button">返回上页</el-button>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
const router = useRouter();
const goBack = () => {
  router.go(-1);
};
</script>

<style scoped>
.not-found-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.error-container {
  text-align: center;
}

.error-title {
  font-size: 120px;
  font-weight: bold;
  margin: 0;
}

.error-description {
  font-size: 24px;
  margin: 10px 0;
}

.back-home-link {
  margin-top: 20px;
}

.go-back-button {
  margin-top: 10px;
}
</style>
