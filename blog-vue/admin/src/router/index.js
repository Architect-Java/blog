import Vue from "vue";
import VueRouter from "vue-router";
import Layout from "@/layout/index";

import article from "./modules/Article";
import articleList from "./modules/ArticleList";
import message from "./modules/Message";
import security from "./modules/Security";
import user from "./modules/User";
import picture from "./modules/Picture";
import system from "./modules/System";
import log from "./modules/Log";

Vue.use(VueRouter);

// 公开路由表
export const routes = [
  {
    path: "/login",
    name: "Login",
    component: () => import("@/views/login/Login.vue"),
    meta: {
      title: "登录",
    },
    hidden: true,
  },
  {
    path: "/",
    redirect: "/dashboard",
    component: Layout,
    children: [
      // 首页仪表盘
      {
        path: "/dashboard",
        name: "仪表盘",
        component: () => import("@/views/dashboard/Dashboard"),
        meta: {
          title: "仪表盘",
          icon: "dashboard",
        },
      },
      // 个人中心
      {
        path: "/profile",
        name: "个人中心",
        component: () => import("@/views/profile/Profile"),
        meta: {
          title: "个人中心",
          icon: "user",
        },
        // 404
      },
      {
        path: "/404",
        name: "404",
        component: () => import("@/views/error-page/404"),
        meta: {
          title: "404",
        },
      },
      // 401
      {
        path: "/401",
        name: "401",
        component: () => import("@/views/error-page/401"),
        meta: {
          title: "401",
        },
      },
    ],
  },
];

// 私有路由表
export const privateRoutes = [
  article,
  articleList,
  message,
  security,
  user,
  picture,
  system,
  log,
];

const router = new VueRouter({
  mode: "hash",
  base: process.env.BASE_URL,
  routes: [...routes],
});

Vue.use(VueRouter);
const VueRouterPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(to) {
  return VueRouterPush.call(this, to).catch((err) => err);
};

export default router;
