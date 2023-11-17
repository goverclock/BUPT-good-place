<template>
    <div class="page">
        <h1>{{ welcomeWord }}</h1>
        <el-descriptions title="基本信息" :column="2">
            <el-descriptions-item label="级别" width="400px">普通用户</el-descriptions-item>
            <el-descriptions-item label="注册城市">{{ registerCity }}</el-descriptions-item>
            <el-descriptions-item label="个人简介">{{ desc }}</el-descriptions-item>
            <el-descriptions-item label="注册时间">{{ registerTime }}</el-descriptions-item>
        </el-descriptions>
        <el-divider />
        <el-descriptions title="认证信息" :column="2">
            <el-descriptions-item label="姓名" width="400px">lol the name</el-descriptions-item>
            <el-descriptions-item label="证件类型">lol the ID type</el-descriptions-item>
            <el-descriptions-item label="手机号码">lol the tele</el-descriptions-item>
            <el-descriptions-item label="证件号码">lol the ID number</el-descriptions-item>
        </el-descriptions>

        <br>
        <el-button type="primary" plain @click="editProfile">修改个人信息</el-button>
    </div>
</template>

<script setup>
const store = useStore()
const userInfo = store.getters['user/userInfo'];
console.log(userInfo)

const welcomeWord = computed(() => "欢迎," + userInfo.user_id)
const registerTime = computed(() => userInfo.register_time)
const registerCity = computed(() => userInfo.register_city)
const desc = computed(() => userInfo.description)

const router = useRouter();
function editProfile() {
    router.push("/user/edit")
}

</script>

<style lang="scss">
.page {
    height: 100%;
    padding-left: 3%;
}
</style>
