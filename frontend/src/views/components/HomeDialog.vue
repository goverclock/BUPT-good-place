<template>
    <el-dialog v-model="visible" :title="props.title">
        <p v-for="line in lines">{{ line }}</p>
        <template #footer>
            <span class="dialog-footer">
                <el-button type="primary" @click="emit('off')">
                    确定
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
const props = defineProps(['content', 'title'])
const emit = defineEmits(['off'])

const visible = ref(false)
const content = computed(() => props.content)
const lines = computed(() => content.value?.split(/\r\n|\r|\n/))

</script>