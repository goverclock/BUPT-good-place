<template>
    <el-row class="top">
        <el-text size="large">为您展示</el-text>
        <el-text size="large" tag="b">{{ userInfo.register_city }}</el-text>
        <el-text size="large">的请求</el-text>
    </el-row>
    <el-pagination class="pagination" :total="totalItems" :page-size="pageSize"
        @current-change="handlePageChange"></el-pagination>

    <div class="main-content">
        <CardList :itemList="displayedItems" @show="showCardDetail"></CardList>
    </div>
</template>

<script setup>
import { GetAllRequestsByCity } from '@/request/api/welcome'
import CardList from '@/views/components/CardList.vue'

const store = useStore()
const userInfo = store.getters['user/userInfo'];
userInfo?.user_id || location.reload();

// pagination
const cardItems = ref([]);
const totalItems = ref(cardItems.value.length);
const pageSize = ref(4);
const currentPage = ref(1);

const handlePageChange = (newPage) => {
    currentPage.value = newPage;
};

const displayedItems = computed(() => {
    const start = (currentPage.value - 1) * pageSize.value;
    const end = start + pageSize.value;
    return cardItems.value.slice(start, end);
});

// request
let data = {
    city: userInfo.register_city
}
GetAllRequestsByCity(data).then(res => {
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
        cardItems.value.push(card)
        ind++;
    }
    totalItems.value = cardItems.value.length
})
</script>

<style scoped>
.top {
    padding-top: 1%;
}

.pagination {
    margin-top: 20px;
    text-align: center;
}

.main-content {
    padding-top: 10px;
    display: flex;
    flex-wrap: wrap;
}
</style>
