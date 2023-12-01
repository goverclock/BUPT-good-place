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

        <div v-if="editing">
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
            <span v-if="!editing" class="dialog-footer">
                <el-button type="primary" plain @click="editing = true">欢迎来</el-button>
            </span>
            <span v-else class="dialog-footer">
                <el-button type="success" plain @click="submitWelcome">提交</el-button>
                <el-button type="danger" plain @click="editing = false">取消</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { SubmitWelcomeReq } from '@/request/api/welcome'
import { ElMessage } from 'element-plus';

const props = defineProps(['detail'])

const store = useStore()
const userInfo = store.getters['user/userInfo'];

const visible = ref(false)
const editing = ref(true)

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

const fileList = []
const submitWelcome = (value) => {
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

        SubmitWelcomeReq(fd)
            .then(res => {
                ElMessage({ message: "响应已提交!", type: "success" });
            })
    })
}
</script>
