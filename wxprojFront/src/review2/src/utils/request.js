/****   request.js   ****/
// 导入axios
import axios from 'axios'
// 使用element-ui Message做消息提醒
import { Message} from 'element-ui';
//1. 创建新的axios实例，
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import {getToken} from '@/utils/auth'
const service = axios.create({
  // 跨域
  withCredentials : true,
  // 公共接口--这里注意后面会讲
  baseURL: 'https://api.video-review.top:1314/api/',
  // 超时时间 单位是ms，这里设置了3s的超时时间
  timeout: 5000
})
// 2.请求拦截器
// 
service.interceptors.request.use(
  config => {
    // do something before request is sent
    NProgress.start()
  //发请求前做的一些处理，数据转化，配置请求头，设置token,设置loading等，根据需求去添加
   config.data = JSON.stringify(config.data); //数据转化,也可以使用qs转换
   config.headers = {
     'Content-Type':'application/json' //配置请求头
   }
   config.headers.Authorization = getToken();

  return config
}, error => {
  console.log(error)
  Promise.reject(error)
})

// 3.响应拦截器
service.interceptors.response.use(response => {
  NProgress.done()
  //接收到响应数据并成功后的一些共有的处理，关闭loading等
  const res = response.data
  if (res.result !== 1){
    Message({
      message: res.message || 'Error',
      type: 'error',
      duration: 3 * 1000
    })
  }
  else{
    return res
  }
},
error => {
  console.log('err' + error)
  Message({
    message: error.message || 'Error',
    type: 'error',
    duration: 3 * 1000
  })
  return Promise.reject(error)
}
)
//4.导入文件
export default service
