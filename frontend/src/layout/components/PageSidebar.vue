<template>
    <el-col :span="24">
        <el-menu router="true" active-text-color="#ffd04b" background-color="#545c64" class="el-menu-vertical-demo" default-active="2"
            text-color="#fff" @open="handleOpen" @close="handleClose">
            <el-menu-item index="/home">
                <el-icon><home-filled /></el-icon>
                <span>主页</span>
            </el-menu-item>
            <el-sub-menu index="2">
                <template #title>
                    <el-icon>
                        <location />
                    </el-icon>
                    <span>寻去处</span>
                </template>
                <el-menu-item-group title="Group One">
                    <el-menu-item index="1-1">item one</el-menu-item>
                    <el-menu-item index="1-2">item two</el-menu-item>
                </el-menu-item-group>
                <el-menu-item-group title="Group Two">
                    <el-menu-item index="1-3">item three</el-menu-item>
                </el-menu-item-group>
                <el-sub-menu index="1-4">
                    <template #title>item four</template>
                    <el-menu-item index="1-4-1">item one</el-menu-item>
                </el-sub-menu>
            </el-sub-menu>
            <el-menu-item index="3">
                <el-icon><icon-menu /></el-icon>
                <span>欢迎来</span>
            </el-menu-item>
            <el-menu-item index="4">
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
const handleOpen = (key: string, keyPath: string[]) => {
    // console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
    // console.log(key, keyPath)
}
</script>
  