<template>
    <el-col :span="24">
        <el-menu router>
            <el-menu-item index="/home">
                <el-icon><home-filled /></el-icon>
                <span>主页</span>
            </el-menu-item>
            <el-menu-item index="/wheretogo">
                <el-icon>
                    <location />
                </el-icon>
                <span v-if="userInfo.user_id != 'admin'">我发布的</span>
                <span v-else>所有请求</span>
            </el-menu-item>
            <el-menu-item v-if="userInfo.user_id != 'admin'" index="/welcome">
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

</script>
  