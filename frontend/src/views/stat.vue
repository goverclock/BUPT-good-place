<template>
    <div class="top">
        <el-form ref="formRef" :model="form" :rules="rules" inline>
            <el-form-item label="日期范围" prop="start_end">
                <el-date-picker v-model="form.start_end" type="daterange" unlink-panels range-separator="到"
                    start-placeholder="起始日期" end-placeholder="结束日期" :shortcuts="shortcuts" :disabled-date="disabledDate" />
            </el-form-item>
            <el-form-item label="地区" prop="city">
                <el-cascader ref="cascaderAddr" :options="cityData" :props="city" placeholder="请选择地区"
                    @change="handleAddrChange"></el-cascader>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="handleSearch">
                    查询
                </el-button>
            </el-form-item>
        </el-form>
    </div>

    <LineChart v-if="chartVisible"></LineChart>
    <el-skeleton :rows="5" :loading="loading" animated style="padding-top: 20px;">
        <template #default>
            <div class="main-content">
                TODO: statistics page
            </div>
        </template>
    </el-skeleton>
</template>

<script setup>
import cityData from '@/assets/pca-code.json'
import { MockQueryProfitReq, QueryProfitReq, QueryProfitMonthReq } from '@/request/api/stat'
// import LineChart from '@/views/components/LineChart.vue'
import LineChart from '@/views/components/LineChart.vue'

const store = useStore()
const userInfo = store.getters['user/userInfo'];
userInfo?.user_id || location.reload();
const router = useRouter();
userInfo.identity_id || router.push("/user/verify")
if (userInfo.user_id != "admin") {
    router.push("/home")
}

const loading = ref(true)

// form
const formRef = ref();
const end = new Date()
const start = new Date()
start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
const form = reactive({
    start_end: [start, end],  // default to last 3 month
    city: '',
})
const rules = computed(() => {
    return {
        start_end: {
            required: true,
            message: "请选择起止日期",
            trigger: ["change", "blur"],
        },
        city: {
            required: true,
            message: "请选择地区",
            trigger: ["change", "blur"],
        },
    }
});

// city picker
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

// date picker
const shortcuts = [
    {
        text: '今天',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24)
            return [start, end]
        },
    },
    {
        text: '最近一周',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            return [start, end]
        },
    },
    {
        text: '最近一个月',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            return [start, end]
        },
    },
    {
        text: '最近三个月',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            return [start, end]
        },
    },
]
const disabledDate = (t) => {
    return t.getTime() > Date.now()
}

// chart
const chartVisible = ref(false)

// requests
const categories = [
    "钓鱼",
    "老少皆宜休闲",
    "农家院",
    "温泉度假",
    "僻静休闲",
    "游乐场",
]
const searchResult = ref([])
function handleSearch() {
    formRef.value.validate((valid) => {
        if (!valid) return
        loading.value = true
        chartVisible.value = false

        let data = {
            start_time: Date.parse(form.start_end[0]) / 1000,
            end_time: Date.parse(form.start_end[1]) / 1000,
            city: form.city,
            type: ''
        }
        async function fetchAllCategoriesData() {
            const result = []
            const promises = categories.map(async (cate) => {
                data.type = cate;
                try {
                    const res = await MockQueryProfitReq(data);
                    result.push({ t: cate, r: res.data })
                } catch (e) {
                    console.error(e)
                }
            })
            await Promise.all(promises)

            console.log(result)
            loading.value = false
            chartVisible.value = true
        }

        fetchAllCategoriesData()
    })
}

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
