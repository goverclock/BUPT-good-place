<template>
    <div class="top">
        <el-button type="primary" icon="edit" @click="publishDialogVisible = true;">发布</el-button>
        <div class="space"></div>
        <el-input v-model="searchText" placeholder="搜索去处" style="width: 40%;" clearable prefix-icon="search"></el-input>
        <div class="space"></div>
        <el-select v-model="selectedOption" placeholder="选择类型">
            <el-option label="全部" value=""></el-option>
            <el-option label="钓鱼" value="钓鱼"></el-option>
            <el-option label="老少皆宜休闲" value="老少皆宜休闲"></el-option>
            <el-option label="农家院" value="农家院"></el-option>
            <el-option label="温泉度假" value="温泉度假"></el-option>
            <el-option label="僻静休闲" value="僻静休闲"></el-option>
            <el-option label="游乐场" value="游乐场"></el-option>
        </el-select>
    </div>

    <el-pagination class="pagination" :total="filteredItemsLength" :page-size="pageSize"
        @current-change="(newPage) => { currentPage = newPage }"></el-pagination>

    <el-skeleton :rows="5" :loading="loading" animated style="padding-top: 20px;">
        <template #default>
            <div class="main-content">
                <CardList :itemList="displayedItems"
                    @show="async (item) => { cardDetailVisible = true; cardDetail = { response: [] }; cardDetail = await getCardDetail(item); }">
                </CardList>
            </div>
        </template>
    </el-skeleton>
    <PublishDialog v-model="publishDialogVisible" @off="publishDialogVisible = false"></PublishDialog>
    <WTGCardDetail v-model="cardDetailVisible" @off="cardDetailVisible = false" :detail="cardDetail"></WTGCardDetail>
</template>

<script setup>
import { computed, ref } from 'vue';
import { GetResponseByRequestId, GetAllRequestsByUser } from '@/request/api/wheretogo'
import PublishDialog from '@/views/components/PublishDialog.vue'
import WTGCardDetail from '@/views/components/WTGCardDetail.vue'
import CardList from '@/views/components/CardList.vue'

const store = useStore()
const userInfo = store.getters['user/userInfo'];
userInfo?.user_id || location.reload();
const router = useRouter();
userInfo.identity_id || router.push("/user/verify")

let publishDialogVisible = ref(false)

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

const searchText = ref('');
const selectedOption = ref('');

// pagination
const cardItems = ref([]);
const filteredItems = ref([])
const filteredItemsLength = computed(() => { return filteredItems.value.length })
const pageSize = ref(4)
const currentPage = ref(1)
const displayedItems = computed(() => {
    filteredItems.value = []
    for (let i = 0; i < cardItems.value.length; i++) {
        let name = cardItems.value[i].data.topic_name
        let type = cardItems.value[i].data.type
        if (name.includes(searchText.value) && type.includes(selectedOption.value)) {
            filteredItems.value.push(cardItems.value[i])
        }
    }
    const start = (currentPage.value - 1) * pageSize.value;
    const end = start + pageSize.value;
    return filteredItems.value.slice(start, end);
});

// requests
const loading = ref(true)
GetAllRequestsByUser({
    user_id: userInfo.user_id,
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
    loading.value = false
})

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
