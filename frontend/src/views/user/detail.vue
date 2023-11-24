<template>
    <div class="detail-page">
        <h1>{{ welcomeWord }}</h1>
        <el-descriptions title="基本信息" :column="2">
            <el-descriptions-item label="级别" width="400px">普通用户</el-descriptions-item>
            <el-descriptions-item label="注册城市">{{ registerCity }}</el-descriptions-item>
            <el-descriptions-item label="个人简介">{{ desc }}</el-descriptions-item>
            <el-descriptions-item label="注册时间">{{ registerTime }}</el-descriptions-item>
        </el-descriptions>
        <el-divider />
        <el-descriptions title="认证信息" :column="2">
            <el-descriptions-item label="姓名" width="400px">{{ name }}</el-descriptions-item>
            <el-descriptions-item label="证件类型">{{ idType }}</el-descriptions-item>
            <el-descriptions-item label="手机号码">{{ tele }}</el-descriptions-item>
            <el-descriptions-item label="证件号码">{{ id }}</el-descriptions-item>
        </el-descriptions>

        <br>
        <el-button type="primary" plain @click="editProfile">修改个人信息</el-button>
        <el-button type="primary" plain @click="editPass">修改密码</el-button>
    </div>
</template>

<script setup>
import { computed } from 'vue';

const store = useStore()
const userInfo = store.getters['user/userInfo'];

userInfo?.user_id || location.reload();
const welcomeWord = computed(() => "欢迎," + userInfo?.user_id)
const registerTime = computed(() => userInfo?.register_time)
const registerCity = computed(() => userInfo?.register_city)
const desc = computed(() => userInfo?.description)

const name = computed(() => userInfo?.name || "未实名认证")
const tele = computed(() => userInfo?.phone_num)
const idType = computed(() => userInfo?.identity_type)
const id = computed(() => userInfo?.identity_id)

const router = useRouter();
function editProfile() {
    router.push("/user/edit")
}
function editPass() {
    router.push("/user/changepass")
}

</script>

<style lang="scss">
.detail-page {
    background: #fff;
    height: 100%;
    padding-left: 2%;
}
</style>
