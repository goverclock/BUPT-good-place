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

const rules = computed(() => {
  return {
    old_pass: {
      required: true,
      validator: (rule, value, callback) => {
        if (!/^\d{11}$/.test(value)) {
          return callback(new Error("请输入有效的11位电话号码"))
        }
        console.log(changepass_form.new_pass, changepass_form.new_pass_repeat)
        callback()
      },
      trigger: ["change", "blur"],
    },
    new_pass: {
      required: true,
      trigger: ["change", "blur"],
    },
    new_pass_repeat: {
      required: true,
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
