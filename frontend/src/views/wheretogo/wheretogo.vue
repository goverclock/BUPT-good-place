<template>
    <div class="top">
        <el-button type="primary" icon="edit"
            @click="publishDialogVisible = true; console.log(publishDialogVisible)">发布</el-button>
        <div class="space"></div>
        <el-input v-model="searchText" placeholder="搜索去处" style="width: 40%;" clearable prefix-icon="search"
            @change="handleSearch"></el-input>
        <div class="space"></div>
        <el-select v-model="selectedOption" @change="handleSelectChange" placeholder="选择类型">
            <el-option label="钓鱼" value="钓鱼"></el-option>
            <el-option label="老少皆宜休闲" value="老少皆宜休闲"></el-option>
            <el-option label="农家院" value="农家院"></el-option>
            <el-option label="温泉度假" value="温泉度假"></el-option>
            <el-option label="僻静休闲" value="僻静休闲"></el-option>
            <el-option label="游乐场" value="游乐场"></el-option>
        </el-select>
    </div>

    <el-pagination class="pagination" :total="totalItems" :page-size="pageSize"
        @current-change="handlePageChange"></el-pagination>

    <div class="main-content">
        <el-card :body-style="{ padding: '0px' }" v-for="item in displayedItems" :key="item.id" class="card">
            <el-image fit="contain" :src="item.data.files[0]">
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
                    <el-button text class="button" @click="handleCardDetail(item.data)">详情</el-button>
                </div>
            </div>
        </el-card>
    </div>

    <PublishDialog v-model="publishDialogVisible"></PublishDialog>
    <CardDetailDialog v-model="cardDetailVisible" :detail="cardDetail"></CardDetailDialog>
</template>

<script setup>
import { ref } from 'vue';
import { Picture as IconPicture } from '@element-plus/icons-vue'
import { GetAllRequests } from '@/request/api/wheretogo'
import PublishDialog from '@/views/wheretogo/PublishDialog.vue'
import CardDetailDialog from '@/views/wheretogo/CardDetailDialog.vue'

const store = useStore()
const userInfo = store.getters['user/userInfo'];
userInfo?.user_id || location.reload();

const searchText = ref('');
const selectedOption = ref('');

const cardItems = ref([]);
const totalItems = ref(cardItems.value.length);
const pageSize = ref(4);
const currentPage = ref(1);
const displayedItems = computed(() => {
    const start = (currentPage.value - 1) * pageSize.value;
    const end = start + pageSize.value;
    return cardItems.value.slice(start, end);
});

let publishDialogVisible = ref(false)

let data = {
    user_id: userInfo.user_id,
}
GetAllRequests(data).then(res => {
    let ind = 1;
    for (const d of res.data) {
        let card = {
            id: ind,
            title: d.topic_name || '暂无标题',
            content: d.description || '暂无描述',
            data: d,
        }
        cardItems.value.push(card)
        ind++;
    }
    totalItems.value = cardItems.value.length
})

// card detail dialog
const cardDetailVisible = ref(false)
const cardDetail = ref({})
const handleCardDetail = (data) => {
    cardDetailVisible.value = true
    let detail = {
        topic_name: data.topic_name,
        type: data.type,
        city: data.city,
        max_price: data.max_price,
        end_time: data.end_time,
        description: data.description,
        files: data.files,
        request_id: data.request_id,
    }
    // console.log(data)
    cardDetail.value = detail

    const date = new Date(cardDetail.value.end_time * 1000)
    const year = date.getFullYear()
    const month = String(date.getMonth() + 1).padStart(2, '0')
    const day = String(date.getDate()).padStart(2, '0')
    cardDetail.value.end_time = `${year}-${month}-${day}`
}

const handleSearch = () => {
    console.log('Search text:', searchText.value);
};

const handleSelectChange = (value) => {
    console.log('Selected category:', value);
};

const handlePageChange = (newPage) => {
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
