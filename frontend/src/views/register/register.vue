<template>
        <div class="page flex-center">
                <div class="sign-box">
                        <el-form ref="formRef" :model="form" :rules="rules" label-width="86px" label-position="top">
                                <h3 class="title">注册</h3>
                                <el-form-item label="用户名" prop="account">
                                        <el-input v-model="form.account" placeholder="用户名"></el-input>
                                </el-form-item>
                                <el-form-item label="密码" prop="password">
                                        <el-input v-model="form.password" type="password" placeholder="密码"></el-input>
                                </el-form-item>
                                <el-form-item label="联系电话" prop="tele">
                                        <el-input v-model="form.tele"></el-input>
                                </el-form-item>
                                <el-form-item label="地区" prop="city">
                                        <el-cascader ref="cascaderAddr" :options="cityData" :props="city" placeholder="请选择地区"
                                                @change="handleAddrChange"></el-cascader>
                                </el-form-item>
                                <el-form-item label="个人简介" prop="desc">
                                        <el-input v-model="form.desc" type="textarea"></el-input>
                                </el-form-item>
                                <el-form-item label>
                                        <el-button type="primary" :loading="registerLoading" class="w100p"
                                                @click="doRegister">注册</el-button>
                                </el-form-item>

                        </el-form>
                        <br>
                        <el-link type="primary" href="/#/login" class="float-right">已有账号?在这里登录</el-link>
                </div>
        </div>
</template>

<script setup>
import { RegisterReq } from '@/request/api/user'
import cityData from '@/assets/pca-code.json'

const formRef = ref();

const form = reactive({
        account: "",
        password: "",
        tele: "",
        city: "",
        desc: "",
})

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
const rules = computed(() => {
        return {
                account: [
                        {
                                required: true,
                                message: "请输入用户名",
                                trigger: ["change", "blur"],
                        },
                        {
                                required: true,
                                pattern: /^[a-zA-Z][a-zA-Z0-9_-]{3,11}$/,
                                message: "用户名由英文字母开头的长度为4-12位字母,_和-组成",
                                trigger: ["change", "blur"],
                        },
                ],
                password: {
                        required: true,
                        validator: (rule, value, callback) => {
                                const numCount = (value.match(/\d/g) || []).length;
                                if (numCount < 2) {
                                        callback(new Error("密码必须包含至少2个数字"));
                                } else if (value.toLowerCase() === value || value.toUpperCase() === value) {
                                        callback(new Error("密码必须同时包含大写和小写字母"));
                                } else if (value.length < 6) {
                                        callback(new Error("密码不能少于6位字符"));
                                }

                                callback();
                        },
                        trigger: ["change", "blur"],
                },
                tele: {
                        required: true,
                        validator: (rule, value, callback) => {
                                if (!/^\d{11}$/.test(value)) {
                                        return callback(new Error("请输入有效的11位电话号码"))
                                }
                                callback()
                        },
                        trigger: ["change", "blur"],
                },
                city: {
                        required: true,
                        message: "请选择地区",
                        trigger: ["change", "blur"],
                },
                desc: {
                        required: false,
                        trigger: ["change", "blur"],
                },
        }
});

const registerLoading = ref(false)
const store = useStore();
const router = useRouter();

function doRegister() {
        formRef.value.validate((valid) => {
                if (!valid) return;

                registerLoading.value = true;
                let data = {
                        user_id: form.account,
                        password: form.password,
                        user_name: form.account,
                        user_type: "user",
                        name: "",
                        identity_type: "",
                        identity_id: "",
                        phone_num: form.tele,
                        user_rank: "normal",
                        description: form.desc,
                        register_city: form.city,
                }
                RegisterReq(data)
                        .then(res => {
                                ElMessage({ message: "注册成功!", type: "success" });
                                router.push("/login")
                        })
                        .catch(err => {
                                console.error("RegisterReq error", err)
                        })
                        .finally(() => {
                                registerLoading.value = false;
                        })

        })
}
</script>

<style lang="scss">
.page {
        height: 100%;
        background: url(@/assets/bg.jpg) no-repeat;
        background-size: cover;
}

.sign-box {
        width: 400px;
        background: #fff;
        padding: 30px 50px 20px 30px;
        border-radius: 4px;
        box-shadow: 0 0 10px #022c44;
}

.title {
        text-align: center;
        font-size: 20px;
        line-height: 30px;
        margin-top: 0;
        margin-bottom: 10px;
        color: #000;
}

.w100p {
        width: 100%;
}

.float-right {
        float: right;
}
</style>