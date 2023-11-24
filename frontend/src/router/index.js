import { createRouter, createWebHashHistory } from 'vue-router'
import Layout from '@/layout/index.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Layout,
    meta: { title: " " },
    children: [
      {
        path: "home",
        name: "Main",
        component: () => import('@/views/home.vue'),
        meta: { title: "主页" },
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
