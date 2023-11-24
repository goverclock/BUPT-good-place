<template>
    <div class="top">
        <el-button type="primary" icon="edit" @click="handlePublish">发布</el-button>
        <div class="space"></div>
        <el-input v-model="searchText" placeholder="搜索去处" style="width: 40%;" clearable prefix-icon="search"
            @change="handleSearch"></el-input>
        <div class="space"></div>
        <el-select v-model="selectedOption" @change="handleSelectChange" placeholder="选择类型">
            <el-option label="钓鱼" value="category1"></el-option>
            <el-option label="老少皆宜休闲" value="category2"></el-option>
            <el-option label="农家院" value="category3"></el-option>
            <el-option label="温泉度假" value="category4"></el-option>
            <el-option label="僻静休闲" value="category5"></el-option>
            <el-option label="游乐场" value="category6"></el-option>
        </el-select>
    </div>

    <el-pagination class="pagination" :total="totalItems" :page-size="pageSize"
        @current-change="handlePageChange"></el-pagination>

    <div class="main-content">
        <el-card :body-style="{ padding: '0px' }" v-for="item in displayedItems" :key="item.id" class="card">
            <!-- <div slot="header" class="card-header">{{ item.title }}</div> -->
            <!-- <div class="card-content">{{ item.content }}</div> -->
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
                    <el-button text class="button">详情</el-button>
                </div>
            </div>
        </el-card>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { Picture as IconPicture } from '@element-plus/icons-vue'

const currentDate = ref(new Date())
const searchText = ref('');
const selectedOption = ref('');
const cardItems = ref([
    // TODO: truncate too long content, end with '...'
    { id: 1, title: 'Card 1', content: 'Content for Card 1 a lot of content a lot of content a lot of conten a lot of conten a lot of conten a lot of conten a lot of conten a lot of contentttttt' },
    { id: 2, title: 'Card 2', content: 'Content for Card 2' },
    { id: 3, title: 'Card 3', content: 'Content for Card 3' },
    { id: 4, title: 'Card 4', content: 'Content for Card 4' },
    { id: 5, title: 'Card 5', content: 'Content for Card 5' },
    { id: 6, title: 'Card 6', content: 'Content for Card 6' },
]);

const totalItems = ref(cardItems.value.length);
const pageSize = ref(5);
const currentPage = ref(1);

const displayedItems = computed(() => {
    const start = (currentPage.value - 1) * pageSize.value;
    const end = start + pageSize.value;
    return cardItems.value.slice(start, end);
});

const handlePublish = () => {
    console.log('Publish button clicked');
};

const handleSearch = () => {
    console.log('Search text:', searchText.value);
};

const handleSelectChange = (value) => {
    console.log('Selected category:', value);
};

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

