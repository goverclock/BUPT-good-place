<template>
    <el-row class="top">
        <el-text size="large">为您展示</el-text>
        <el-text size="large" tag="b">{{ userInfo.register_city }}</el-text>
        <el-text size="large">的请求</el-text>
    </el-row>
    <el-pagination class="pagination" :total="totalItems" :page-size="pageSize"
        @current-change="handlePageChange"></el-pagination>

    <el-skeleton :rows="5" :loading="loading" animated style="padding-top: 20px;">
        <template #default>
            <div class="main-content">
                <CardList :itemList="displayedItems"
                    @show="async (item) => { cardDetailVisible = true; cardDetail = { response: [] }; cardDetail = await getCardDetail(item); }">
                </CardList>
            </div>
        </template>
    </el-skeleton>

    <WCMCardDetail v-model="cardDetailVisible" @off="cardDetailVisible = false" :detail="cardDetail"></WCMCardDetail>
</template>

<script setup>
import { GetAllRequestsByCityReq } from '@/request/api/welcome'
import { GetResponseByRequestId } from '@/request/api/wheretogo'
import CardList from '@/views/components/CardList.vue'
import WCMCardDetail from '@/views/components/WCMCardDetail.vue'

const store = useStore()
const userInfo = store.getters['user/userInfo'];
userInfo?.user_id || location.reload();
const router = useRouter();
userInfo.identity_id || router.push("/user/verify")

// card detail dialog
const cardDetailVisible = ref(false)
const cardDetail = ref({})
const getCardDetail = async (item) => {
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
        user_id: data.user_id,
        state: Number(data.state),
    }

    async function getResponse() {
        try {
            const resp = await GetResponseByRequestId({ request_id: detail.request_id });
            detail.response = resp.data
        } catch (e) {
            console.error(e)
        }
    }
    await getResponse()

    const date = new Date(detail.end_time * 1000)
    const year = date.getFullYear()
    const month = String(date.getMonth() + 1).padStart(2, '0')
    const day = String(date.getDate()).padStart(2, '0')
    detail.end_time = `${year}-${month}-${day}`
    return detail
}

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

// requests
const loading = ref(true)
GetAllRequestsByCityReq({
    city: userInfo.register_city
}).then(res => {
    for (const d of res.data) {
        let card = {
            title: d.topic_name || '暂无标题',
            content: d.description || '暂无描述',
            state: Number(d.state),
            data: d,
        }
        if (card.title.length > 20) {
            card.title = card.title.slice(0, 10) + "..."
        }
        cardItems.value.push(card)
    }
    cardItems.value.sort((a, b) => {
        return b.data.create_time - a.data.create_time
    });
    totalItems.value = cardItems.value.length
    loading.value = false
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
