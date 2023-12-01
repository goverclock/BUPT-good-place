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
        <CardList :itemList="displayedItems"
            @show="(item) => { cardDetail = getCardDetail(item); cardDetailVisible = true; }">
        </CardList>
    </div>

    <PublishDialog v-model="publishDialogVisible"></PublishDialog>
    <WTGCardDetail v-model="cardDetailVisible" :detail="cardDetail"></WTGCardDetail>
</template>

<script setup>
import { ref } from 'vue';
import { GetAllRequestsByUser } from '@/request/api/wheretogo'
import PublishDialog from '@/views/components/PublishDialog.vue'
import WTGCardDetail from '@/views/components/WTGCardDetail.vue'
import CardList from '@/views/components/CardList.vue'

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
GetAllRequestsByUser(data).then(res => {
    let ind = 1;
    for (const d of res.data) {
        let card = {
            id: ind,
            title: d.topic_name || '暂无标题',
            content: d.description || '暂无描述',
            state: Number(d.state),
            data: d,
        }
        if (card.title.length > 20) {
            card.title = card.title.slice(0, 10) + "..."
        }
        cardItems.value.push(card)  // TODO: sort
        ind++;
    }
    totalItems.value = cardItems.value.length
})

// card detail dialog
const cardDetailVisible = ref(false)
const cardDetail = ref({})
const getCardDetail = (item) => {
    let data = item.data
    let detail = {
        topic_name: data.topic_name,
        type: data.type,
        city: data.city,
        max_price: data.max_price,
        end_time: data.end_time,
        description: data.description,
        files: data.files,
        request_id: data.request_id,
        state: Number(data.state),
    }

    const date = new Date(detail.end_time * 1000)
    const year = date.getFullYear()
    const month = String(date.getMonth() + 1).padStart(2, '0')
    const day = String(date.getDate()).padStart(2, '0')
    detail.end_time = `${year}-${month}-${day}`
    return detail
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

.pagination {
    margin-top: 20px;
    text-align: center;
}


.space {
    margin-right: 10px;
}
</style>
