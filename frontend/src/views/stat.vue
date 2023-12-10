<template>
    <el-skeleton :rows="5" :loading="loading" animated style="padding-top: 20px;">
        <template #default>
            <div class="main-content">
                TODO: statistics page
            </div>
        </template>
    </el-skeleton>
</template>

<script setup>

const store = useStore()
const userInfo = store.getters['user/userInfo'];
userInfo?.user_id || location.reload();
const router = useRouter();
userInfo.identity_id || router.push("/user/verify")

const loading = ref(true)

</script>