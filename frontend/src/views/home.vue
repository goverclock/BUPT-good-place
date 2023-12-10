<template>
  <div style="display: inline-flex; align-items: center; justify-content: center;">
    <div style="width: 400px">
      <h1>BUPT好去处: 寻找,分享,欢迎来!</h1>
      <el-text size="large">
        连接您与好去处的平台,BUPT好去处为普通用户提供便捷的注册、发布、响应服务,让寻找和分享好去处变得更加轻松愉快.欢迎加入,共同打造一个充满温馨和友善的社区!
      </el-text>
      <br>
      <br>
      <br>
      <div style="padding-left: 35%; transform: scale(1.5);">
        <!-- TODO: handle click -->
        <el-button type="primary" :icon="Edit" round>立即发布</el-button>
      </div>
    </div>

    <div class="space-40"></div>
    <el-carousel :interval="5000" arrow="always" style="width: 800px;">
      <el-carousel-item v-for="(item, index) in carouselItems" :key="index">
        <img :src="item.src" alt="Carousel Item" style="width: 100%; height: 100%;">
      </el-carousel-item>
    </el-carousel>
  </div>

  <br>
  <br>
  <!-- TODO -->
  <el-row :gutter="20">
    <el-col :span="8">
      <h3>如何使用</h3>
    </el-col>
    <el-col :span="8">
      <h3>新闻公告</h3>
    </el-col>
    <el-col :span="8">
      <h3>随机推荐</h3>
    </el-col>
  </el-row>
</template>

<script setup>
import { ref } from 'vue';
import { Edit } from '@element-plus/icons-vue'

const store = useStore()
const userInfo = store.getters['user/userInfo']
userInfo?.user_id || location.reload()

const carouselItems = ref([
  { src: 'src/assets/home_carousel/1.jpeg' },
  { src: 'src/assets/home_carousel/2.png' },
]);
</script>

<style>
.el-row {
  margin-bottom: 20px;
}

.el-row:last-child {
  margin-bottom: 0;
}

.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.space-40 {
  margin-right: 40px;
}
</style>
