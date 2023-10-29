<template>
  <hr />
  This is home page.
  <br>
  <div class="wrap">
    <p>count: {{ count }}</p>
  </div>

  <div class="home">
    <HelloWorld msg="Welcome to Your Vue.js App" />
  </div>
</template>

<script setup>
// import { computed } from 'vue'
// import { useStore } from 'vuex'
const store = useStore()
const count = computed(() => store.state.count)
</script>

<style lang="scss">
.wrap {
  background: yellow;
  p {
    color: blue;
    font-size: 24px;
  }
}
</style>