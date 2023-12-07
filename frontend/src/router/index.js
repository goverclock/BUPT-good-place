import { createRouter, createWebHashHistory } from 'vue-router'
import Layout from '@/layout/index.vue'

const routes = [
  {
    path: '/',
    name: 'Root',
    component: Layout,
    meta: { title: " " },
    children: [
      {
        path: "home",
        name: "Home",
        component: () => import('@/views/home.vue'),
        meta: { title: "主页" },
      },
      {
        path: "wheretogo",
        name: "WhereToGo",
        component: () => import('@/views/wheretogo.vue'),
        meta: { title: "我发布的" },
      },
      {
        path: "welcome",
        name: "Welcome",
        component: () => import('@/views/welcome.vue'),
        meta: { title: "我要响应" },
      },
      {
        path: "stat",
        name: "Statistics",
        component: () => import('@/views/stat.vue'),
        meta: { title: "统计数据" },
      },
      {
        path: "login",
        name: "Login",
        component: () => import('@/views/login.vue'),
        meta: { title: "登录" },
      },
      {
        path: "register",
        name: "Register",
        component: () => import('@/views/register.vue'),
        meta: { title: "注册" },
      },
      {
        path: "404",
        name: "NotFound",
        component: () => import('@/views/404.vue'),
        meta: { title: "页面不存在" },
      },
      {
        path: "user",
        redirect: to => {
          return '/user/detail'
        },
        name: "User",
        meta: {
          requireAuth: true,
        },
        meta: { title: "个人中心" },
        children: [
          {
            path: 'edit',
            name: 'UserEdit',
            meta: {
              requireAuth: true
            },
            component: () => import('@/views/user/edit.vue'),
          },
          {
            path: 'changepass',
            name: 'ChangePass',
            meta: {
              requireAuth: true
            },
            component: () => import('@/views/user/change_pass.vue'),
          },
          {
            path: 'detail',
            name: 'UserDetail',
            meta: {
              requireAuth: true
            },
            component: () => import('@/views/user/detail.vue'),
          },
          {
            path: 'verify',
            name: 'UserVerify',
            meta: {
              requireAuth: true
            },
            component: () => import('@/views/user/verify.vue'),
          },
        ],
      },
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    name: '404',
    redirect: '/404',
    component: () => import('@/views/404.vue'),
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
});

// set title for each page
router.beforeEach((to, from, next) => {
  document.title = (to.meta.title || "no title") + " - BUPT好去处"
  next()
});

export default router
