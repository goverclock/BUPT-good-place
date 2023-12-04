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

        <div v-if="editing && !hasResponse">
            <el-divider />
            <h3>填写响应信息</h3>
            <el-form ref="formRef" :model="form" :rules="rules">
                <el-form-item label="简介" prop="desc" label-width="140px">
                    <el-input v-model="form.desc" placeholder="输入简介" type="textarea" style="margin-right: 100px;" />
                </el-form-item>
                <el-upload drag multiple :on-change="function (file) { fileList.push(file) }" :auto-upload="false"
                    style="margin-left: 140px; margin-right: 100px;">
                    <el-icon class="el-icon--upload"><upload-filled /></el-icon>
                    <div class="el-upload__text">
                        拖动文件到此处或 <em>点击上传</em>
                    </div>
                </el-upload>
            </el-form>
        </div>

        <template #footer>
            <div v-if="hasResponse">
                <el-divider></el-divider>
                <el-descriptions title="我的响应" :column="1">
                    <el-descriptions-item label="响应描述">{{ props.detail.response[0].description }}</el-descriptions-item>
                    <el-descriptions-item label="附件">
                        <ul>
                            <li v-for="(file, index) in props.detail.response[0].files" :key="index">
                                <el-link type="primary" :href="file" target="_blank" download>附件 {{ index + 1 }}</el-link>
                            </li>
                        </ul>
                    </el-descriptions-item>
                </el-descriptions>
                <el-button type="primary" plain @click="hasResponse = false; editing = true">修改响应</el-button>
                <el-button type="danger" plain @click="deleteResponse">删除响应</el-button>
            </div>
            <span v-else-if="!editing" class="dialog-footer">
                <el-button type="primary" plain @click="editing = true">欢迎来</el-button>
            </span>
            <span v-else class="dialog-footer">
                <el-button type="success" plain @click="submitResponse">提交</el-button>
                <el-button type="danger" plain @click="$emit('off')">取消</el-button>
            </span>

        </template>
    </el-dialog>
</template>

<script setup>
import { SubmitResponseReq, DeleteResponseReq } from '@/request/api/welcome'
import { ElMessage } from 'element-plus';
import { watchEffect } from 'vue';

const props = defineProps(['detail'])
const emit = defineEmits(['off'])

const store = useStore()
const userInfo = store.getters['user/userInfo'];

const visible = ref(false)
const editing = ref(false)
const hasResponse = ref(false)
watchEffect(() => {
    hasResponse.value = props.detail.response?.length != 0
})

const formRef = ref();
const form = reactive({
    desc: '',
})
const rules = computed(() => {
    return {
        desc: {
            required: true,
            message: "请填写简介",
            trigger: ["change", "blur"],
        },
    }
});

const deleteResponse = () => {
    DeleteResponseReq({
        response_id: props.detail.response[0].response_id
    }).then(res => {
        ElMessage({ message: "响应已删除!", type: "success" })
        editing.value = false
        hasResponse.value = false
        visible.value = false
        console.log(visible.value)
    })
}

const fileList = []
const submitResponse = (value) => {
    formRef.value.validate((valid) => {
        if (!valid) return;
        let data = {
            request_id: props.detail.request_id,
            user_id: userInfo.user_id,
            description: form.desc,
        }
        let fd = new FormData();
        Object.keys(data).forEach(key => {
            fd.append(key, data[key])
        })
        fileList.forEach((file, index) => {
            fd.append(`files`, file.raw)
        })

        SubmitResponseReq(fd)
            .then(res => {
                ElMessage({ message: "响应已提交!", type: "success" })
                editing.value = false
                visible.value = false
            })
    })
}
</script>
