<template>
  <div class="detail-page">
    <h1>修改密码</h1>
    <el-form ref="formRef" :model="changepass_form" :rules="rules" label-position="right" label-width="70px">
      <el-form-item label="原密码" prop="old_pass">
        <el-input class="inline-item" v-model="changepass_form.old_pass" type="password" placeholder="输入原密码" />
      </el-form-item>
      <el-form-item label="新密码" prop="new_pass">
        <el-input class="inline-item" v-model="changepass_form.new_pass" type="password" placeholder="输入新密码" />
      </el-form-item>
      <el-form-item prop="new_pass_repeat">
        <el-input class="inline-item" v-model="changepass_form.new_pass_repeat" type="password" placeholder="重复新密码" />
      </el-form-item>
      <br>

      <el-button type="success" @click="editConfirm">确认修改</el-button>
      <el-button type="danger" plain @click="editCancel">放弃修改</el-button>
    </el-form>
  </div>
</template>

<script setup>
import { UpdatePasswordReq } from '@/request/api/user'
import { reactive } from 'vue';

const store = useStore()
const userInfo = store.getters['user/userInfo'];

const formRef = ref();
const changepass_form = reactive({
  old_pass: '',
  new_pass: '',
  new_pass_repeat: '',
})

const router = useRouter();
function editConfirm() {
  let data = {
    user_id: userInfo.user_id,
    password: changepass_form.old_pass,
    new_password: changepass_form.new_pass,
  }

  formRef.value.validate((valid) => {
    if (!valid) return;
    UpdatePasswordReq(data)
      .then(res => {
        ElMessage({ message: "修改成功!", type: "success" });
        router.push('/user/detail')
      })
  })
}

function editCancel() {
  router.push("/user/detail")
}

const rules = computed(() => {
  return {
    old_pass: {
      required: true,
    },
    new_pass: {
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
    new_pass_repeat: {
      required: true,
      validator: (rule, value, callback) => {
        if (changepass_form.new_pass != changepass_form.new_pass_repeat) {
          return callback(new Error("两次输入的密码不一致"))
        }
        callback()
      },
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
