<template>
  <div class="home-page">
    <el-carousel :interval="4000" arrow="always">
      <el-carousel-item v-for="(item, index) in carouselItems" :key="index">
        <img :src="item.src" alt="Carousel Item" style="width: 100%; height: 100%;">
      </el-carousel-item>
    </el-carousel>

    <h1>最近更新</h1>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const store = useStore()
const userInfo = store.getters['user/userInfo']
userInfo?.user_id || location.reload()

const carouselItems = ref([
  { src: 'src/assets/home_carousel/1.jpeg' },
  { src: 'src/assets/home_carousel/2.png' },
]);
</script>
