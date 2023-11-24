<template>
  <div class="home-page">
    <el-carousel :interval="4000" arrow="always">
      <el-carousel-item v-for="(item, index) in carouselItems" :key="index">
        <!-- Your carousel item content goes here -->
        <img :src="item.src" alt="Carousel Item" style="width: 100%; height: 100%;">
      </el-carousel-item>
    </el-carousel>

    <h1>最近更新</h1>
    <!-- TODO: recent placed -->
  </div>
</template>

<script setup>
import { ref } from 'vue';

const carouselItems = ref([
  { src: 'src/assets/carousel/1.jpeg' },
  { src: 'src/assets/carousel/2.png' },
  // Add more items as needed
]);
</script>

<style scoped>
.home-page {
  padding: 2%;
}
</style>

