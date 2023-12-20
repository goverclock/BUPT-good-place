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

    <el-skeleton :rows="5" :loading="loading" animated style="padding-top: 20px;">
        <template #default>
            <div class="main-content">
                <el-table v-if="tableVisible" :data="tableData">
                    <el-table-column prop="date" label="日期" width="240" />
                    <el-table-column prop="city" label="地区" width="250" />
                    <el-table-column prop="type" label="类型" width="180" />
                    <el-table-column prop="count" label="交易数" width="160" />
                    <el-table-column prop="profit" label="中介费" width="160" />
                </el-table>
                <LineChart v-if="chartVisible" :monthData="chartData"></LineChart>
            </div>
            <el-empty v-if="!loading && !chartVisible" description="点击查询获取统计数据" />
        </template>
    </el-skeleton>
</template>

<script setup>
import cityData from '@/assets/pca-code.json'
import { MockQueryProfitReq, MockQueryProfitMonthReq, QueryProfitReq, QueryProfitMonthReq } from '@/request/api/stat'
import LineChart from '@/views/components/LineChart.vue'

const store = useStore()
const userInfo = store.getters['user/userInfo'];
userInfo?.user_id || location.reload();
const router = useRouter();
userInfo.identity_id || router.push("/user/verify")
if (userInfo.user_id != "admin") {
    router.push("/home")
}

const loading = ref(false)

// form
const formRef = ref();
const end = new Date()
const start = new Date()    // current time
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
const tableVisible = ref(false)
const chartVisible = ref(false)
const tableData = ref([])
const chartData = ref([])

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
        tableVisible.value = false

        let data = {
            start_time: Date.parse(form.start_end[0]) / 1000,
            end_time: Date.parse(form.start_end[1]) / 1000,
            city: form.city,
            type: ''
        }
        async function fetchChartData() {
            // for table
            const categoryResult = []
            let promises = categories.map(async (cate) => {
                data.type = cate;
                try {
                    const res = await QueryProfitReq(data);
                    categoryResult.push({ type: cate, count: res.data[0], profit: res.data[1] })
                } catch (e) {
                    console.error(e)
                }
            })
            await Promise.all(promises)

            // for line chart
            const monthResult = []
            const months = []
            const startDate = new Date(data.start_time * 1000)
            startDate.setMonth(startDate.getMonth() + 1)
            const endDate = new Date(data.end_time * 1000)
            endDate.setMonth(endDate.getMonth() + 1)
            let curDate = new Date(startDate)
            while (curDate < endDate) {
                months.push(curDate.getTime() / 1000)
                curDate.setMonth(curDate.getMonth() + 1)
            }
            promises = months.map(async (m) => {
                const st = m
                let et = new Date(st * 1000)
                et.setMonth(et.getMonth() + 1)
                et = et.getTime() / 1000
                try {
                    const res = await QueryProfitMonthReq({ start_time: st, end_time: et });
                    monthResult.push(res)
                } catch (e) {
                    console.error(e)
                }
            })
            await Promise.all(promises)

            // resolve result as table data and chart data
            tableData.value = []
            categoryResult.forEach((cr) => {
                let bg_date = new Date(data.start_time * 1000)
                let ed_date = new Date(data.end_time * 1000)
                tableData.value.push({
                    date: bg_date.toLocaleDateString() + '-' + ed_date.toLocaleDateString(),
                    city: data.city,
                    type: cr.type,
                    count: cr.count,
                    profit: cr.profit
                })
            })

            chartData.value = []
            let cd1 = []    // 成交数
            let cd2 = []    // 中介费
            monthResult.forEach((mr) => {
                cd1.push({
                    label: mr.date,
                    y: mr.count,
                })
                cd2.push({
                    label: mr.date,
                    y: mr.agency_fee,
                })
            })
            chartData.value.push(cd1)
            chartData.value.push(cd2)

            loading.value = false
            chartVisible.value = true
            tableVisible.value = true
        }
        fetchChartData()
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
