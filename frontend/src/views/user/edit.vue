<template>
  <div class="detail-page">
    <h1>编辑个人信息</h1>
    <el-form :model="edit_form" label-position="right" label-width="70px">
      <h4>基本信息</h4>
      <el-descriptions :column="1">
        <el-descriptions-item label="级别" width="400px">普通用户</el-descriptions-item>
        <el-descriptions-item label="注册城市">{{ register_city }}</el-descriptions-item>
        <el-descriptions-item label="注册时间">{{ register_time }}</el-descriptions-item>
      </el-descriptions>
      <el-form-item label="个人简介">
        <el-input class="inline-item" v-model="edit_form.new_desc" type="textarea" placeholder="输入个人简介" />
      </el-form-item>
      <el-divider />

      <h4>认证信息</h4>
      <el-descriptions :column="1">
        <el-descriptions-item label="姓名" width="400px">{{ name }}</el-descriptions-item>
        <el-descriptions-item label="证件类型">{{ idType }}</el-descriptions-item>
        <el-descriptions-item label="证件号码">{{ id }}</el-descriptions-item>
      </el-descriptions>
      <el-form-item label="手机号码">
        <el-input class="inline-item" v-model="edit_form.new_tele" placeholder="输入手机号码" />

      </el-form-item>
      <el-divider />

      <h4>修改密码</h4>
      <el-form-item label="原密码">
        <el-input class="inline-item" v-model="edit_form.old_pass" type="password" placeholder="输入原密码" />
      </el-form-item>
      <el-form-item label="新密码">
        <el-input class="inline-item" v-model="edit_form.new_pass" type="password" placeholder="输入新密码" />
      </el-form-item>
      <el-form-item>
        <el-input class="inline-item" v-model="edit_form.new_pass_repeat" type="password" placeholder="重复新密码" />
      </el-form-item>
      <br>

      <el-button type="success" @click="editConfirm">确认修改</el-button>
      <el-button type="danger" plain @click="editCancel">放弃修改</el-button>
    </el-form>
  </div>
</template>

<script setup>
import { computed, reactive } from 'vue';

const store = useStore()
const userInfo = store.getters['user/userInfo'];

const register_time = computed(() => userInfo?.register_time)
const register_city = computed(() => userInfo?.register_city)
const name = computed(() => userInfo?.name || "未实名认证")
const idType = computed(() => userInfo?.identity_type)
const id = computed(() => userInfo?.identity_id)

const edit_form = reactive({
  new_tele: '',
  old_pass: '',
  new_pass: '',
  new_pass_repeat: '',
  new_desc: '',
})

const router = useRouter();
function editConfirm() {
  console.log("confirm edit")

  let data = {
    user_id: userInfo.user_id,
    password: '',
    new_password: '',
    phone_num: '',
    description: '',
  }
  console.log(data)
}

function editCancel() {
  router.push("/user/detail")
}

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
