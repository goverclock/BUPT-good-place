<template>
    <el-col :span="24">
        <el-menu router active-text-color="#ffd04b" background-color="#545c64" class="el-menu-vertical-demo"
            default-active="2" text-color="#fff" @open="handleOpen" @close="handleClose">
            <el-menu-item index="/home">
                <el-icon><home-filled /></el-icon>
                <span>主页</span>
            </el-menu-item>
            <el-menu-item index="/wheretogo">
                <el-icon>
                    <location />
                </el-icon>
                <span>我发布的</span>
            </el-menu-item>
            <el-menu-item index="/welcome">
                <el-icon>
                    <document-checked />
                </el-icon>
                <span>我要响应</span>
            </el-menu-item>
            <el-menu-item v-if="userInfo.user_id == 'admin'" index="/stat">
                <el-icon>
                    <document />
                </el-icon>
                <span>统计数据</span>
            </el-menu-item>
            <el-menu-item index="/user/detail">
                <el-icon>
                    <setting />
                </el-icon>
                <span>个人中心</span>
            </el-menu-item>
        </el-menu>
    </el-col>
</template>
  
<script lang="ts" setup>
import {
    Document,
    Menu as IconMenu,
    Location,
    Setting,
} from '@element-plus/icons-vue'

const store = useStore()
const userInfo = store.getters['user/userInfo'];
userInfo?.user_id || location.reload();

const handleOpen = (key: string, keyPath: string[]) => {
    // console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
    // console.log(key, keyPath)
}
</script>
  