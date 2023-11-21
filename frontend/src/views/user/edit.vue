<template>
  <div class="detail-page">
    <h1>编辑个人信息</h1>
    <el-form ref="formRef" :model="edit_form" :rules="rules" label-position="right" label-width="80px">
      <h4>基本信息</h4>
      <el-descriptions :column="1">
        <el-descriptions-item label="级别" width="400px">普通用户</el-descriptions-item>
        <el-descriptions-item label="注册城市">{{ register_city }}</el-descriptions-item>
        <el-descriptions-item label="注册时间">{{ register_time }}</el-descriptions-item>
      </el-descriptions>
      <el-form-item label="个人简介" prop="new_desc">
        <el-input class="inline-item" v-model="edit_form.new_desc" type="textarea" placeholder="输入个人简介" />
      </el-form-item>
      <el-divider />

      <h4>认证信息</h4>
      <el-descriptions :column="1">
        <el-descriptions-item label="姓名" width="400px">{{ name }}</el-descriptions-item>
        <el-descriptions-item label="证件类型">{{ idType }}</el-descriptions-item>
        <el-descriptions-item label="证件号码">{{ id }}</el-descriptions-item>
      </el-descriptions>
      <el-form-item label="手机号码" prop="new_tele">
        <el-input class="inline-item" v-model="edit_form.new_tele" placeholder="输入手机号码" />
      </el-form-item>

      <el-button type="success" @click="editConfirm">确认修改</el-button>
      <el-button type="danger" plain @click="editCancel">放弃修改</el-button>
    </el-form>
  </div>
</template>

<script setup>
import { UpdateInformationReq } from '@/request/api/user'
import { computed, reactive } from 'vue';

const store = useStore()
const userInfo = store.getters['user/userInfo'];

const register_time = computed(() => userInfo?.register_time)
const register_city = computed(() => userInfo?.register_city)
const name = computed(() => userInfo?.name || "未实名认证")
const idType = computed(() => userInfo?.identity_type)
const id = computed(() => userInfo?.identity_id)

const formRef = ref();
const edit_form = reactive({
  new_tele: '',
  new_desc: '',
})
edit_form.new_tele = userInfo.phone_num
edit_form.new_desc = userInfo.description

const router = useRouter();
function editConfirm() {
  let data = {
    user_id: userInfo.user_id,
    phone_num: edit_form.new_tele,
    description: edit_form.new_desc,
  }

  formRef.value.validate((valid) => {
    if (!valid) return;
    UpdateInformationReq(data)
      .then(res => {
        console.log(res.data);
      })
  })
}

function editCancel() {
  router.push("/user/detail")
}

const rules = computed(() => {
  return {
    new_tele: {
      required: true,
      validator: (rule, value, callback) => {
        if (!/^\d{11}$/.test(value)) {
          return callback(new Error("请输入有效的11位电话号码"))
        }
        callback()
      },
      trigger: ["change", "blur"],
    },
    new_desc: {
      required: false,
      trigger: ["change", "blur"],
    },
  }
});

</script>

<style lang="scss">
.detail-page {
  background: #fff;
  height: 100%;
  padding-left: 3%;
}

.inline-item {
  // display: inline-flex;
  width: 300px;
}
</style>
