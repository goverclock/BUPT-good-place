import { createRouter, createWebHashHistory } from 'vue-router'
import Layout from '@/layout/index.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: Layout,
    children: [
      {
        path: "login",
        name: "Login",
        component: () => import('@/views/login/login.vue'),
        meta: { title: "登录" },
      },
      {
        path: "register",
        name: "Register",
        component: () => import('@/views/register/register.vue'),
        meta: { title: "注册" },
      },
      {
        path: "404",
        name: "NotFound",
        component: () => import('@/views/404.vue'),
        meta: { title: "页面不存在" },
      },
      {
        path: "personal",
        name: "Personal",
        meta: {
          requireAuth: true,
        },
        component: () => import('@/views/personal/index.vue'),
        children: [
          {
            path: 'message',
            name: 'PersonalMessage',
            meta: {
              requireAuth: true
            },
            component: () => import('@/views/personal/Message.vue'),
          },
        ],
      },
      {
        path: 'app',
        name: 'App',
        meta: {
          requireAuth: true,
        },
        component: () => import('@/views/app/index.vue')
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
