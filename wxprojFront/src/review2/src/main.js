// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

Vue.config.productionTip = false

// element-ui
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
Vue.use(ElementUI, { size: "small", zIndex: 3000 });

// tjs
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';// 加载 CSS
import VueSocketIO from 'vue-socket.io'
import VideoPlayer from 'vue-video-player';
import 'video.js/dist/video-js.css';
import 'vue-video-player/src/custom-theme.css';
// import MediaInfoFactory from "mediainfo.js";
Vue.use(VideoPlayer)
Vue.use(Antd)
// Vue.use(MediaInfoFactory)
Vue.use(new VueSocketIO({
  debug: true,
  connection: 'wss://api.video-review.top:1314/meetingRoom',
}
))

// 引入滚动条
import vuescroll from 'vuescroll';

Vue.use(vuescroll);

// 全局样式
import './assets/css/global.css'

// 引入过滤器
import * as filters from './filters'
Object.keys(filters).forEach(key => {
  Vue.filter(key,filters[key])
})


// // 定义directive
// Vue.directive('drag', {

//   inserted: function(el, binding, vnode) {
//     var odiv = el.parentNode;
//     odiv.onmousedown = function(eve) {
//         eve = eve || window.event;
//         var clientX = eve.clientX;
//         var clientY = eve.clientY;
//         var odivX = odiv.offsetLeft;
//         var odivY = odiv.offsetTop;
//         var odivLeft = clientX - odivX;
//         var odivTop = clientY - odivY;
//         var clientWidth = document.documentElement.clientWidth;
//         var oWidth = odiv.clientWidth;
//         var odivRight = clientWidth - oWidth;
//         var clientHeight = document.documentElement.clientHeight;
//         var oHeight = odiv.clientHeight;
//         var odivBottom = clientHeight - oHeight;
//         document.onmousemove = function(e) {
//             e.preventDefault();
//             var left = e.clientX - odivLeft;
//             if (left < 0) {
//                 left = 0
//             }
//             if (left > odivRight) {
//                 left = odivRight
//             }
//             var Top = e.clientY - odivTop;
//             if (Top < 0) {
//                 Top = 0
//             }
//             if (Top > odivBottom) {
//                 Top = odivBottom
//             }
//             odiv.style.left = left + "px";
//             odiv.style.top = Top + "px";
//         }
//         document.onmouseup = function() {
//             document.onmouseup = "";
//             document.onmousemove = "";
//         }
//     }
// }
// })
// Vue.directive(
//   'stopdrag',{
//     inserted: function(el, binding, vnode) {
//       let element = el;
//       element.onmousedown = function(e) {
//           e.stopPropagation()
//       }
//   }
//   }
// )
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
