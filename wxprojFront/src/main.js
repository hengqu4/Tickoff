import Vue from 'vue'
import App from './App'

import fly from './utils/wx-request'
import {Status} from '@beautywe/plugin-status';
Vue.prototype.$fly = fly


Vue.config.productionTip = false
App.mpType = 'app'

const app = new Vue(App)
app.$mount()