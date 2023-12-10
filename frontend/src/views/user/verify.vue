<template>
    <h1>实名认证</h1>
    <el-text>根据国家相关法律,您需要进一步完善个人基本信息.该信息将作为您的账号认证信息,请务必谨慎填写</el-text>
    <br>
    <br>
    <el-form ref="formRef" :model="verify_form" :rules="rules" label-position="right" label-width="80px">
        <el-form-item label="姓名" prop="name">
            <el-input class="inline-item" v-model="verify_form.name" placeholder="输入姓名" style="width: 200px;" />
        </el-form-item>
        <el-form-item label="证件类型" prop="identity_type">
            <el-select v-model="verify_form.identity_type" placeholder="选择证件类型" style="width: 200px;">
                <el-option label="身份证" value="身份证"></el-option>
                <el-option label="护照" value="护照"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="证件号码" prop="identity_id">
            <el-input class="inline-item" v-model="verify_form.identity_id" placeholder="输入证件号码" style="width: 200px;" />
        </el-form-item>
        <br>

        <el-button type="success" @click="verifyComfirm">提交认证信息</el-button>
    </el-form>
</template>

<script setup>
import { VerifyReq } from '@/request/api/user'
import { GetUserInfoReq } from '@/request/api/user'

const store = useStore()
const userInfo = store.getters['user/userInfo'];
userInfo?.user_id || location.reload();

const formRef = ref();
const verify_form = reactive({
    name: '',
    identity_type: '',
    identity_id: '',
})
const rules = computed(() => {
    return {
        name: {
            required: true,
            message: "请输入姓名",
        },
        identity_type: {
            required: true,
            message: "请选择证件类型",
            trigger: ["change", "blur"],
        },
        identity_id: {
            required: true,
            trigger: ["change", "blur"],
            validator: (rule, value, callback) => {
                if (!/^\d{18}$/.test(value)) {
                    return callback(new Error("请输入有效的18位身份证号码"))
                }
                callback()
            },
        },
    }
});

const router = useRouter();
const redirectReload = async (p) => {
    await router.push({ path: p })
    router.go()
}
function verifyComfirm() {
    let data = {
        user_id: userInfo.user_id,
        name: verify_form.name,
        identity_type: verify_form.identity_type,
        identity_id: verify_form.identity_id,
    }

    formRef.value.validate((valid) => {
        if (!valid) return;
        VerifyReq(data).then(res => {
            ElMessage({ message: "认证成功!", type: "success" });
            GetUserInfoReq(data)
                .then((res) => {
                    store.commit('user/setUserInfo', res.data)
                    redirectReload('/home')
                })
        })
    })
}

</script>

<style lang="scss">
.detail-page {
    background: #fff;
    height: 100%;
}

.inline-item {
    width: 300px;
}
</style>
