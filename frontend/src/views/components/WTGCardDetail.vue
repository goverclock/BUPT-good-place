<template>
    <el-dialog v-model="visible" title="寻去处详情">
        <!-- request detail -->
        <ProfileDialog v-model="showUserProfile" :profile="showingUser" @off="showUserProfile = false">
        </ProfileDialog>
        <el-descriptions v-if="!editing" :title="props.detail.topic_name" :column="1">
            <el-descriptions-item v-if="userInfo.user_id == 'admin'" label="发布者">
                <el-link type="info" @click="showUserProfile = true; showingUser = props.detail.user_id">
                    {{ props.detail.user_id }}
                </el-link>
            </el-descriptions-item>
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

        <!-- editing -->
        <el-form v-else ref="formRef" :model="form" :rules="rules">
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
            <el-upload v-model:file-list="fileList" drag multiple :on-change="function (file) { fileList.push(file) }"
                :auto-upload="false" style="margin-left: 140px; margin-right: 100px;">
                <el-icon class="el-icon--upload"><upload-filled /></el-icon>
                <div class="el-upload__text">
                    拖动文件到此处或 <em>点击上传</em>
                </div>
            </el-upload>
        </el-form>

        <!-- responses -->
        <el-collapse v-if="!editing" v-model="showingResponse">
            <!-- response user's profile -->
            <el-collapse-item v-for="resp in props.detail.response" :name="resp.user_id">
                <template #title>
                    来自用户 {{ resp.user_id }} 的响应<el-tag v-if="resp.user_id == userInfo.user_id">我的响应</el-tag>
                    <el-tag v-if="resp.state == '1'" type="success">已接受</el-tag>
                    <el-tag v-else-if="resp.state == '2'" type="danger">已拒绝</el-tag>
                </template>
                <el-descriptions :column="1">
                    <el-descriptions-item v-if="userInfo.user_id == 'admin'" label="响应者">
                        <el-link type="info" @click="showUserProfile = true; showingUser = resp.user_id">{{ resp.user_id
                        }}</el-link>
                    </el-descriptions-item>
                    <el-descriptions-item label="响应描述">{{ resp.description }}</el-descriptions-item>
                    <el-descriptions-item label="附件">
                        <ul>
                            <li v-for="(  file, index  ) in   resp.files  " :key="index">
                                <el-link type="primary" :href="file" target="_blank" download>附件 {{ index + 1 }}</el-link>
                            </li>
                        </ul>
                    </el-descriptions-item>
                </el-descriptions>

                <div v-if="[0, 1].includes(props.detail.state)"> <!-- 请求还没有响应,或有响应但未接受 -->
                    <div v-if="resp.state == '0'"> <!-- 尚未被接受或拒绝的响应 -->
                        <el-button type="success" plain @click="acceptResp(resp)">接受</el-button>
                        <el-button type="danger" plain @click="rejectResp(resp)">拒绝</el-button>
                    </div>
                </div>
            </el-collapse-item>
        </el-collapse>

        <template #footer>
            <span v-if="!editing" class="dialog-footer">
                <el-button type="primary" plain :disabled="hasResponse" @click="beginEditing">修改请求</el-button>
                <el-popconfirm title="确定要删除请求吗?" @confirm="handleDelete(props.detail.request_id)">
                    <template #reference>
                        <el-button type="danger" plain :disabled="hasResponse">删除请求</el-button>
                    </template>
                </el-popconfirm>
            </span>
            <span v-else class="dialog-footer">
                <el-button type="success" plain @click="confirmEdit">完成</el-button>
                <el-button type="danger" plain @click="editing = false">取消</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { DeleteRequestReq, UpdateRequestReq, AcceptResponseReq, RejectResponseReq } from '@/request/api/wheretogo'
import { ElMessage } from 'element-plus';
import ProfileDialog from '@/views/components/ProfileDialog.vue'
import cityData from '@/assets/pca-code.json'

const props = defineProps(['detail'])
const emit = defineEmits(['off'])

const store = useStore()
const userInfo = store.getters['user/userInfo'];

const visible = ref(false)
const editing = ref(false)
const hasResponse = ref(false)
const hasMyResponse = ref(false)
watchEffect(() => {
    hasResponse.value = props.detail.response?.length != 0
    hasMyResponse.value = false
    if (hasResponse.value) {
        for (let i = 0; i < props.detail.response?.length; i++) {
            if (props.detail.response[i].user_id == userInfo.user_id) {
                hasMyResponse.value = true
                break
            }
        }
    }
})

const handleDelete = (req_id) => {
    DeleteRequestReq({
        request_id: req_id,
    }).then(res => {
        ElMessage({ message: "已删除请求!", type: "success" });
        emit('off')
        setTimeout(() => { location.reload() }, 1000)
    })
}

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
const beginEditing = () => {
    editing.value = true
    form.topic_name = props.detail.topic_name
    form.type = props.detail.type
    form.desc = props.detail.description
    form.max_price = Number(props.detail.max_price)
    // form.city = props.detail.city    // simply too complex to implement
    form.end_time = props.detail.end_time
}

const confirmEdit = () => {
    formRef.value.validate((valid) => {
        if (!valid) return;

        let data = {
            request_id: props.detail.request_id,
            type: form.type,
            topic_name: form.topic_name,
            description: form.desc,
            city: form.city,
            max_price: String(form.max_price),
            end_time: Date.parse(form.end_time) / 1000,
            files: [],
        }
        let fd = new FormData();
        Object.keys(data).forEach(key => {
            fd.append(key, data[key])
        })
        fileList.forEach((file, index) => {
            fd.append(`files`, file.raw)
        })

        UpdateRequestReq(fd)
            .then(res => {
                ElMessage({ message: "已更新请求!", type: "success" });
                editing.value = false
                emit('off')
                setTimeout(() => { location.reload() }, 1000)
            })
    })
}

const acceptResp = (resp) => {
    const response_id = resp.response_id
    AcceptResponseReq({
        response_id: response_id
    }).then(res => {
        ElMessage({ message: "已接受响应!", type: "success" });
        setTimeout(() => { location.reload() }, 1000)
        emit('off')
    })
}

const rejectResp = (resp) => {
    const response_id = resp.response_id
    RejectResponseReq({
        response_id: response_id
    }).then(res => {
        ElMessage({ message: "已拒绝响应!", type: "success" });
        resp.state = '2'
    })
}

const showingResponse = ref("")

// user info dialog
const userDetail = ref({})
const showUserProfile = ref(false)
const showingUser = ref('')

</script>
