<template>
    <el-dialog v-model="visible" title="响应者详情">
        {{ props.profile }}
        <br>
        <br>
        <el-descriptions title="基本信息" :column="2">
            <el-descriptions-item label="级别" width="400px">普通用户</el-descriptions-item>
            <el-descriptions-item label="注册城市">{{ userInfo.register_city }}</el-descriptions-item>
            <el-descriptions-item label="个人简介">{{ userInfo.description }}</el-descriptions-item>
            <el-descriptions-item label="注册时间">{{ userInfo.register_time }}</el-descriptions-item>
        </el-descriptions>
        <el-divider />
        <el-descriptions title="认证信息" :column="2">
            <el-descriptions-item label="姓名" width="400px">{{ userInfo.name }}</el-descriptions-item>
            <el-descriptions-item label="证件类型">{{ userInfo.identity_type }}</el-descriptions-item>
            <el-descriptions-item label="手机号码">{{ userInfo.phone_num }}</el-descriptions-item>
            <el-descriptions-item label="证件号码">{{ userInfo.identity_id }}</el-descriptions-item>
        </el-descriptions>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="emit('off')">关闭</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { watchEffect } from 'vue';
import { GetUserInfoReq } from '@/request/api/user'

const visible = ref(false)
const props = defineProps(['profile'])
const emit = defineEmits(['off'])

let userInfo = ref({
    register_time: '',
    register_city: '',
    description: '',
    name: '',
    phone_num: '',
    identity_type: '',
    identity_id: '',
})
watchEffect(() => {
    if (props.profile == '') return;
    userInfo.value = {}
    GetUserInfoReq({ user_id: props.profile })
        .then(res => {
            userInfo.value.register_time = res.data.register_time
            userInfo.value.register_city = res.data.register_city
            userInfo.value.description = res.data.description
            userInfo.value.name = res.data.name || "未实名认证"
            userInfo.value.phone_num = res.data.phone_num
            userInfo.value.identity_type = res.data.identity_type
            userInfo.value.identity_id = res.data.identity_id
        })
})

</script>