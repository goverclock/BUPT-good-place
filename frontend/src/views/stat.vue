<template>
    TODO: statistics page
</template>

<script setup>

const store = useStore()
const userInfo = store.getters['user/userInfo'];
userInfo?.user_id || location.reload();
const router = useRouter();
userInfo.identity_id || router.push("/user/verify")

</script>