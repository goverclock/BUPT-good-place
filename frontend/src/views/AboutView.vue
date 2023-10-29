<template>
  <hr>
    This is an about page
    <br>
    <el-button @click="incCount">Add</el-button>
</template>

<script setup>
  import { useStore } from 'vuex';
  const store = useStore();
  let count = 3;
  function incCount() {
    count++;
    store.commit('increase', count)
  }
</script>
