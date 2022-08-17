import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "./permission";
// ======================= 导入外部组件 ========================
import ElementUI from "element-ui";
import MavonEditor from "mavon-editor";
import SvgIcons from "@/icons";
import filters from "@/filter/index";
// ======================= 导入样式 ========================
import "./styles/index.scss";
import "nprogress/nprogress.css";
import "element-ui/lib/theme-chalk/index.css";
import "mavon-editor/dist/css/index.css";

Vue.use(ElementUI);
Vue.use(SvgIcons);
Vue.use(MavonEditor);

Vue.config.productionTip = false;

Object.keys(filters).forEach((key) => {
  Vue.filter(key, filters[key]);
});

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
