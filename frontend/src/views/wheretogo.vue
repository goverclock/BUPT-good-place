<template>
    <div class="top">
        <el-button type="primary" icon="edit" @click="publishDialogVisible = true">发布</el-button>
        <div class="space"></div>
        <el-input v-model="searchText" placeholder="搜索去处" style="width: 40%;" clearable prefix-icon="search"
            @change="handleSearch"></el-input>
        <div class="space"></div>
        <el-select v-model="selectedOption" @change="handleSelectChange" placeholder="选择类型">
            <el-option label="钓鱼" value="cate1"></el-option>
            <el-option label="老少皆宜休闲" value="cate2"></el-option>
            <el-option label="农家院" value="cate3"></el-option>
            <el-option label="温泉度假" value="cate4"></el-option>
            <el-option label="僻静休闲" value="cate5"></el-option>
            <el-option label="游乐场" value="cate6"></el-option>
        </el-select>
    </div>

    <el-pagination class="pagination" :total="totalItems" :page-size="pageSize"
        @current-change="handlePageChange"></el-pagination>

    <div class="main-content">
        <el-card :body-style="{ padding: '0px' }" v-for="item in displayedItems" :key="item.id" class="card">
            <el-image fit="contain" src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg">
                <template #error>
                    <div class="image-slot">
                        <el-icon><icon-picture /></el-icon>
                    </div>
                </template>
            </el-image>

            <div style="padding: 14px">
                <span>{{ item.title }}</span>
                <div class="bottom">
                    <time class="time">{{ item.content }}</time>
                    <el-button text class="button" @click="cardDetailVisible = true">详情</el-button>
                </div>
            </div>
        </el-card>
    </div>

    <!-- dialogs -->
    <!-- publish -->
    <el-dialog v-model="publishDialogVisible" title="发布寻去处">
        <el-form ref="publishRef" :model="publishForm" :rules="rules">
            <el-form-item label="主题" prop="topic_name" label-width=140px>
                <el-input v-model="publishForm.topic_name" placeholder="输入主题" style="margin-right: 100px;" />
            </el-form-item>
            <el-form-item label="类型" label-width=140px>
                <el-select v-model="publishForm.type" @change="handlePublishFormSelectChange" placeholder="选择类型">
                    <el-option label="钓鱼" value="cate1"></el-option>
                    <el-option label="老少皆宜休闲" value="cate2"></el-option>
                    <el-option label="农家院" value="cate3"></el-option>
                    <el-option label="温泉度假" value="cate4"></el-option>
                    <el-option label="僻静休闲" value="cate5"></el-option>
                    <el-option label="游乐场" value="cate6"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="地区" prop="city" label-width="140px">
                <el-cascader ref="cascaderAddr" :options="cityData" :props="city" placeholder="请选择地区"
                    @change="handleAddrChange"></el-cascader>
            </el-form-item>
            <el-form-item label="最高单价" label-width=140px>
                <el-input-number v-model="publishForm.max_price" :precision="2" :min="1" placeholder="最高单价"
                    style="margin-right: 100px;" />
            </el-form-item>
            <el-form-item label="结束日期" prop="end_time" label-width="140px">
                <el-date-picker v-model="publishForm.end_time" placeholder="选择请求结束日期" style="margin-right: 100px;" />
            </el-form-item>
            <el-form-item label="简介" label-width="140px">
                <el-input v-model="publishForm.desc" placeholder="输入简介" type="textarea" style="margin-right: 100px;" />
            </el-form-item>
            <el-upload drag multiple :on-change="handleNewFile" :auto-upload="false"
                style="margin-left: 140px; margin-right: 100px;">
                <el-icon class="el-icon--upload"><upload-filled /></el-icon>
                <div class="el-upload__text">
                    拖动文件到此处或 <em>点击上传</em>
                </div>
            </el-upload>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="publishDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="handlePublishConfirm">
                    发布
                </el-button>
            </span>
        </template>
    </el-dialog>

    <!-- detail -->
    <el-dialog v-model="cardDetailVisible" title="寻去处详情">
        <template #footer>
            <span class="dialog-footer">
                <el-button type="primary" plain>修改</el-button>
                <el-button type="danger" plain @click="handlePublishConfirm">
                    删除
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { ref } from 'vue';
import { Picture as IconPicture, UploadFilled } from '@element-plus/icons-vue'
import { PublishPlaceReq } from '@/request/api/wheretogo'
import cityData from '@/assets/pca-code.json'
import { ElMessage } from 'element-plus';
import { GetAllRequests } from '../request/api/wheretogo';

const store = useStore()
const userInfo = store.getters['user/userInfo'];
userInfo?.user_id || location.reload();

const searchText = ref('');
const selectedOption = ref('');

const cardItems = ref([
    // TODO: truncate too long content, end with '...'
]);
const totalItems = ref(cardItems.value.length);
const pageSize = ref(4);
const currentPage = ref(1);

let data = {
    user_id: userInfo.user_id,
}
GetAllRequests(data).then(res => {
    let ind = 1;
    for (const d of res.data) {
        console.log(d)
        let card = {
            id: ind,
            title: d.topic_name || '暂无标题',
            content: d.description || '暂无描述',
        }
        cardItems.value.push(card)
        ind++;
    }
    totalItems.value = cardItems.value.length
})

const displayedItems = computed(() => {
    const start = (currentPage.value - 1) * pageSize.value;
    const end = start + pageSize.value;
    return cardItems.value.slice(start, end);
});

// dialog
const publishDialogVisible = ref(false)
const publishRef = ref();
const publishForm = reactive({
    topic_name: '',
    type: 'cate1',
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
let city = {
    value: 'code',
    label: 'name',
    children: 'children',
}
const cascaderAddr = ref()
function handleAddrChange(e) {
    let addrNode = unref(cascaderAddr).getCheckedNodes()[0]
    let addrText = addrNode.pathLabels.join("-")
    publishForm.city = addrText
}

// card detail dialog
const cardDetailVisible = ref(false)

const handleSearch = () => {
    console.log('Search text:', searchText.value);
};

const handleSelectChange = (value) => {
    console.log('Selected category:', value);
};

const handlePublishFormSelectChange = (value) => {
    console.log('Selected category:', value);
};

const fileList = []
const handleNewFile = (file) => {
    fileList.push(file);
    console.log(typeof file)
    console.log(file)
};

const handlePublishConfirm = (value) => {
    publishRef.value.validate((valid) => {
        if (!valid) return;

        let data = {
            user_id: userInfo.user_id,
            type: publishForm.type,
            topic_name: publishForm.topic_name,
            description: publishForm.desc,
            // files: fileList,
            city: publishForm.city,
            max_price: String(publishForm.max_price),
            end_time: publishForm.end_time,
        }
        console.log("handlePublishConfirm", data)

        let fd = new FormData();
        Object.keys(data).forEach(key => {
            fd.append(key, data[key])
        })
        fileList.forEach((file, index) => {
            fd.append(`files[]`, file.raw)
        })
        // fd.append('files[]', fs.createReadStream(path.join(__dirname, 'test.png')), 'test.png')

        PublishPlaceReq(fd)
            .then(res => {
                ElMessage({ message: "发布成功!", type: "success" });
            })
    })
}

const handlePageChange = (newPage) => {
    console.log('Current page:', newPage);
    currentPage.value = newPage;
};
</script>

<style scoped>
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

.top {
    display: flex;
    align-items: center;
    padding-top: 1%;
}

.main-content {
    padding-top: 10px;
    display: flex;
    flex-wrap: wrap;
}

.card {
    width: calc(300px - 20px);
    margin: 10px;
}

.pagination {
    margin-top: 20px;
    text-align: center;
}

.time {
    font-size: 12px;
    color: #999;
}

.bottom {
    line-height: 12px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.space {
    margin-right: 10px;
}
</style>