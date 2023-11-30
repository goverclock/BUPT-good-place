<template>
    <el-card :body-style="{ padding: '0px' }" v-for="item in props.itemList" :key="item.id" class="card">
        <el-image fit="contain" :src="item.data.files[0]">
            <template #error>
                <div class="image-slot">
                    <el-icon><icon-picture /></el-icon>
                </div>
            </template>
        </el-image>

        <div style="padding: 14px">
            <el-row>
                <span>{{ item.title }}</span>
                <el-tag v-if="item.state == 0" type="info">暂无响应</el-tag>
                <el-tag v-else-if="item.state == 1">等待接受</el-tag>
                <el-tag v-else-if="item.state == 2" type="success">已接受</el-tag>
                <el-tag v-else type="info">已过期</el-tag>
            </el-row>
            <div class="bottom">
                <time class="time">{{ item.content }}</time>
                <el-button text class="button" @click="$emit('show', item)">详情</el-button>
            </div>
        </div>
    </el-card>
</template>

<script setup>
import { Picture as IconPicture } from '@element-plus/icons-vue'
const props = defineProps(['itemList'])
const emit = defineEmits(['show'])

</script>

<style scoped>
.card {
    width: calc(300px - 20px);
    margin: 10px;
}

.el-image {
    max-width: 300px;
    max-height: 200px;
    width: 100%;
    height: 200px;
}

.image-slot {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    background: var(--el-fill-color-light);
    color: var(--el-text-color-secondary);
    font-size: 30px;
}

.image-slot .el-icon {
    font-size: 30px;
}

.bottom {
    line-height: 12px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.time {
    font-size: 12px;
    color: #999;
}
</style>
