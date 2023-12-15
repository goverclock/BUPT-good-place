<template>
  <div style="display: inline-flex; align-items: center; justify-content: center;">
    <div style="width: 400px; height: 140%;">
      <h1>BUPT好去处: 寻找,分享,欢迎来!</h1>
      <el-text size="large" style="line-height: 1.8;">
        连接您与好去处的平台,BUPT好去处为普通用户提供便捷的注册、发布、响应服务,让寻找和分享好去处变得更加轻松愉快.欢迎加入,共同打造一个充满温馨和友善的社区!
      </el-text>
      <br>
      <br>
      <br>
      <br>
      <div style="padding-left: 35%; transform: scale(1.5);">
        <el-button type="primary" :icon="Edit" round @click="gotoPath('wheretogo')">立即发布</el-button>
      </div>
    </div>

    <div class="space-40"></div>
    <el-carousel :interval="5000" arrow="always" style="width: 800px; height: 100%; padding-top: 30px;">
      <el-carousel-item v-for="(item, index) in carouselItems" :key="index">
        <img :src="item.src" alt="Carousel Item" style="width: 100%; height: 100%;">
      </el-carousel-item>
    </el-carousel>
  </div>

  <br>
  <br>
  <br>
  <br>
  <br>
  <!-- TODO -->
  <HomeDialog v-model="homeDialogVisible" @off="homeDialogVisible = false" :content="showingItem.content"
    :title="showingItem.title"></HomeDialog>
  <el-row :gutter="70">
    <el-col :span="7">
      <el-icon color="#409EFC" style="font-size: 70px;">
        <Reading />
      </el-icon>
      <h3>如何使用</h3>
      <ul style="padding-left: 10px;">
        <li v-for="item in how_to_use_items">
          <el-link type="primary" @click="showingItem = item; homeDialogVisible = true" class="float-right">{{ item.title
          }}</el-link>
        </li>
      </ul>
    </el-col>
    <el-col :span="7">
      <el-icon color="#409EFC" style="font-size: 70px;">
        <Document />
      </el-icon>
      <h3>最新公告</h3>
      <el-text style="line-height: 1.5;">
        亲爱的《好去处》平台用户：
        根据国家相关法律规定，为保障您的账户安全和提供更加个性化的服务，我们需要您进一步完善个人基本信息。这些信息将被用作您的账户认证信息，以确保您在平台上的身份真实可靠。
      </el-text>
    </el-col>
    <!-- <el-col :span="6">
      <el-icon color="#409EFC" style="font-size: 70px;">
        <Place />
      </el-icon>
      <h3>随机推荐</h3>
    </el-col> -->
    <el-col :span="7">
      <el-icon color="#409EFC" style="font-size: 70px;">
        <DataLine />
      </el-icon>
      <h3>累计数据</h3>
      <el-text size="large">注册用户: 788,999,123</el-text>
      <br>
      <el-text size="large">请求数量: 888,999,122</el-text>
      <br>
      <el-text size="large">响应数量: 888,999,123</el-text>
    </el-col>
  </el-row>
</template>

<script setup>
import { ref } from 'vue';
import { Edit } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router';
import HomeDialog from '@/views/components/HomeDialog.vue'

const store = useStore()
const userInfo = store.getters['user/userInfo']
userInfo?.user_id || location.reload()

const carouselItems = ref([
  { src: 'src/assets/home_carousel/1.jpeg' },
  { src: 'src/assets/home_carousel/2.png' },
]);

const router = useRouter()
function gotoPath(path) {
  router.push(path)
}

// home page posts
const homeDialogVisible = ref(false)
const showingItem = ref({})
const how_to_use_items = [
  {
    title: 'BUPT好去处-新用户必读向导',
    content: `欢迎加入《好去处》平台！为了让您更好地享受我们提供的服务，我们为新用户准备了一个简明的向导，帮助您快速修改个人信息。请按照以下步骤操作，个性化您的账户，让您的《好去处》之旅更加完美。

步骤一：登录您的账户

打开《好去处》平台并使用您的账户信息登录。
步骤二：进入个人信息管理页面

登录成功后，点击页面右上角的个人头像或用户名。
从下拉菜单中选择“个人信息”或类似选项，进入个人信息管理页面。
步骤三：查看并修改基本信息

在个人信息页面，您可以查看当前的基本信息，如用户名、手机号码等。
若要修改任何信息，请点击相应的编辑按钮，通常是一个小铅笔图标。
步骤四：修改联系方式

在页面中找到联系方式相关的部分，可以修改手机号码或其他联系方式。
确保您输入的信息准确无误，然后保存修改。
步骤五：更新密码

如果您想修改登录密码，找到密码修改部分。
输入当前密码和新密码，确保新密码符合平台的要求。
保存修改。
步骤六：完善用户简介

在个人信息页面中，找到用户简介的部分。
编辑或完善您的用户简介，以便其他用户更好地了解您。
步骤七：保存修改

确保您已完成所有想要修改的内容。
点击页面底部的“保存”按钮或类似选项，以保存您的修改。
通过以上步骤，您可以轻松地完成个人信息的修改。请时常更新您的信息，以确保平台上的互动更加顺畅。如果您有任何疑问或需要帮助，请随时联系我们的客服团队。祝您在《好去处》平台上度过愉快的时光！
`
  },
  {
    title: '教程:如何发布寻去处',
    content:
      `欢迎来到《好去处》平台！为了确保您能够轻松而成功地发布寻找去处的请求或欢迎他人来到您的地区，我们为您准备了一份详细的发布指南。无论您是普通用户还是管理员，以下步骤将帮助您快速掌握平台的各项功能。

步骤一：注册与登录

首先，您需要在平台上注册账户。请填写您的基本信息，并确保密码符合要求。注册成功后，您可以使用用户名和密码登录系统。

步骤二：完善个人信息

在登录后，您可以进入个人信息管理页面。在这里，您可以浏览您的注册基本信息，并有权修改手机号码、用户简介和密码。确保您的信息准确无误。

步骤三：发布寻找去处请求

如果您是发布“寻去处”的请求者，请按以下步骤操作：

点击“寻去处”模块，进入请求信息管理页面。
默认显示您已发布的所有请求信息。您可以点击“发布新的请求信息”来填写新的请求内容。
如果您的请求已经发布，但尚未得到响应，您可以在页面上找到并修改或删除该请求。
在填写请求信息时，请确保详细描述您寻找去处的主题、位置、单价等要求。
点击发布后，系统将根据规则计算中介费，并将信息存储在数据库中。
步骤四：发布欢迎来响应

如果您是“欢迎来”的发布者，请按以下步骤操作：

进入“欢迎来”模块，浏览您所在地区的所有“寻去处”请求信息。
点击您感兴趣的请求，查看详细需求介绍信息。
点击“欢迎来”按钮，填写相关信息，上传图片、视频等文件，并提交您的响应信息。
如果您的响应已经提交，但尚未被接受，您可以在页面上找到并修改或删除该响应。
步骤五：统计分析功能

平台还提供了统计分析功能，以便您了解平台活动。您可以：

输入起始年月、终止年月、某个地域等查询条件，查看请求达成中介费、笔数的明细。
以图表方式展示每月累计成交单数、中介费金额的变化趋势。
最后：尽情探索与互动

以上是一个简要的指南，希望能够帮助您更好地使用《好去处》平台。在这里，您可以尽情探索寻找去处的乐趣，与他人互动，创造美好的相遇。如果您有任何疑问，随时联系我们的管理员。祝您在《好去处》平台上度过愉快的时光！
`
  },
  {
    title: '教程:如何响应寻去处',
    content: `欢迎来到《好去处》平台！在这里，我们鼓励用户积极响应寻找去处的请求，搭建一个更加友善和互助的社区。以下是一个简明的指南，帮助您快速而顺利地响应寻找去处的请求。

步骤一：浏览请求信息

登录您的《好去处》账户。
在首页或相关模块中浏览寻找去处的请求信息。
步骤二：选择合适的请求

查看各个请求的详细信息，包括主题、位置、单价等要求。
选择您感兴趣或符合您条件的请求，点击进入详细页面。
步骤三：提交响应信息

在请求详细页面中，找到并点击“欢迎来”按钮。
填写相关响应信息，可以上传图片、视频等文件以介绍您的好去处。
提交响应信息后，系统会将您的响应存储在数据库中。
步骤四：管理您的响应

在您的个人信息管理页面，您可以查看您所有已提交的响应信息。
对于尚未被接受的响应，您可以进行修改或删除操作。
步骤五：建立联系

如果请求者接受了您的响应，系统将按照规则计算中介费，并存储成功响应的明细。
在平台上与请求者建立联系，商讨具体的去处安排。
通过以上步骤，您可以轻松参与到《好去处》平台的互动中，为他人提供帮助，同时也享受到更多丰富的体验。祝您在《好去处》平台上度过愉快的时光！
`
  },
]

</script>

<style>
.el-row {
  margin-bottom: 20px;
}

.el-row:last-child {
  margin-bottom: 0;
}

.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.space-40 {
  margin-right: 40px;
}
</style>
