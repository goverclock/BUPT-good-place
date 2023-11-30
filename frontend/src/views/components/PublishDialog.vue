<template>
    <el-dialog v-model="visible" title="发布寻去处">
        <el-form ref="formRef" :model="form" :rules="rules">
            <el-form-item label="主题" prop="topic_name" label-width=140px>
                <el-input v-model="form.topic_name" placeholder="输入主题" style="margin-right: 100px;" />
            </el-form-item>
            <el-form-item label="类型" label-width=140px>
                <el-select v-model="form.type" placeholder="选择类型">
                    <el-option label="钓鱼" value="钓鱼"></el-option>
                    <el-option label="老少皆宜休闲" value="老少皆宜休闲"></el-option>
                    <el-option label="农家院" value="农家院"></el-option>
                    <el-option label="温泉度假" value="温泉度假"></el-option>
                    <el-option label="僻静休闲" value="僻静休闲"></el-option>
                    <el-option label="游乐场" value="游乐场"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="地区" prop="city" label-width="140px">
                <el-cascader ref="cascaderAddr" :options="cityData" :props="city" placeholder="请选择地区"
                    @change="handleAddrChange"></el-cascader>
            </el-form-item>
            <el-form-item label="最高单价" label-width=140px>
                <el-input-number v-model="form.max_price" :precision="2" :min="1" placeholder="最高单价"
                    style="margin-right: 100px;" />
            </el-form-item>
            <el-form-item label="结束日期" prop="end_time" label-width="140px">
                <el-date-picker v-model="form.end_time" placeholder="选择请求结束日期" style="margin-right: 100px;" />
            </el-form-item>
            <el-form-item label="简介" label-width="140px">
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
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="visible = false">取消</el-button>
                <el-button type="primary" @click="handlePublishConfirm">
                    发布
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { UploadFilled } from '@element-plus/icons-vue'
import { PublishPlaceReq } from '@/request/api/wheretogo'
import cityData from '@/assets/pca-code.json'

const store = useStore()
const userInfo = store.getters['user/userInfo'];

let city = {
    value: 'code',
    label: 'name',
    children: 'children',
}
const cascaderAddr = ref()
function handleAddrChange(e) {
    let addrNode = unref(cascaderAddr).getCheckedNodes()[0]
    let addrText = addrNode.pathLabels.join("-")
    form.city = addrText
}

const visible = ref(false)
const formRef = ref();
const form = reactive({
    topic_name: '',
    type: '钓鱼',
    desc: '',
    city: '',
    max_price: 0,
    end_time: Date(),
})

const rules = computed(() => {
    return {
        topic_name: {
            required: true,
            message: "请输入主题",
            trigger: ["change", "blur"],
        },
        city: {
            required: true,
            message: "请选择地区",
            trigger: ["change", "blur"],
        },
        end_time: {
            required: true,
            message: "请选择结束日期",
            trigger: ["change", "blur"],
        }
    }
});

const fileList = []
const handlePublishConfirm = (value) => {
    formRef.value.validate((valid) => {
        if (!valid) return;
        let data = {
            user_id: userInfo.user_id,
            type: form.type,
            topic_name: form.topic_name,
            description: form.desc,
            city: form.city,
            max_price: String(form.max_price),
            end_time: Date.parse(form.end_time) / 1000,
        }
        let fd = new FormData();
        Object.keys(data).forEach(key => {
            fd.append(key, data[key])
        })
        fileList.forEach((file, index) => {
            fd.append(`files`, file.raw)
        })

        PublishPlaceReq(fd)
            .then(res => {
                ElMessage({ message: "发布成功!", type: "success" });
            })
    })
}
</script>

