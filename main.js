// #ifndef VUE3
import App from './App'
import Vue from 'vue'
import store from './store'

Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
    ...App,
	store
})
app.$mount()
// #endif

wx.cloud.init({
  env: 'echarger-7gak78km70d53193', //填上你的云开发环境id
  traceUser: true,
})

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif