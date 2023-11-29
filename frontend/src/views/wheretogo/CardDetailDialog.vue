<template>
    <el-dialog v-model="visible" title="寻去处详情">
        <el-descriptions :title="props.detail.topic_name" :column="1">
            <el-descriptions-item label="类型">{{ props.detail.type }}</el-descriptions-item>
            <el-descriptions-item label="地区">{{ props.detail.city }}</el-descriptions-item>
            <el-descriptions-item label="最高单价">{{ props.detail.max_price }} 元</el-descriptions-item>
            <el-descriptions-item label="请求描述">{{ props.detail.description }}</el-descriptions-item>
            <el-descriptions-item label="截止日期">{{ props.detail.end_time }}</el-descriptions-item>
            <el-descriptions-item label="附件">
                <ul>
                    <li v-for="(file, index) in props.detail.files" :key="index">
                        <el-link type="primary" :href="file" target="_blank" download>附件 {{ index + 1 }}</el-link>
                    </li>
                </ul>
            </el-descriptions-item>
        </el-descriptions>
        <template #footer>
            <span class="dialog-footer">
                <el-button type="primary" plain>修改</el-button>
                <el-button type="danger" plain @click="handleDelete(props.detail.request_id)">
                    删除
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { DeleteRequestReq } from '@/request/api/wheretogo'
import { ElMessage } from 'element-plus';

const visible = ref(false)

const props = defineProps(['detail'])
const handleDelete = (req_id) => {
    let data = {
        request_id: req_id,
    }
    DeleteRequestReq(data)
        .then(res => {
            ElMessage({ message: "已删除!", type: "success" });
        })
}

</script>