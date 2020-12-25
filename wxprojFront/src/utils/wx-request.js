import Fly from 'flyio/dist/npm/wx'
import store from '../store'
const fly = new Fly()
// const host = 'http://mock-api.com/vzMrDQgG.mock/'
// const host = 'http://mock-api.com/6KLpmWKk.mock/'
const host='http://121.4.69.102:8080/'
//const host='http://localhost:8080/'
// 添加请求拦截器

function getInfo() {
  if (store.state.token=='') {
    wx.getUserInfo({
      success: (data) => {
        console.log("getUserInfo()::", data);
        store.commit("USERINFO_MUTATION", data.userInfo);
        getOpenidAndToken();
      },
      fail: () => {
        console.log("getUserInfo()失败");
      },
    })
  }
  else {
    
  }

}
function getOpenidAndToken(){
  wx.login({
    success: function (r) {
      console.log("pages/loading::onLaunch()::success::return", r); //r包含code
      //通过code获得openid并存入store
      var code = r.code;
      var rawUrl = store.state.userInfo.avatarUrl;
      var newUrl = rawUrl.replace("/", "-");
      for (var i = 0; i < 100; i++) {
        newUrl = newUrl.replace("/", "-");
      }
      if (code) {
        //发送code到后台，分析openid
        fly.request({
            method: "post",
            url:
              "login/regist/code/" +
              code +
              "/avatar/" +
              newUrl +
              "/nickname/" +
              store.state.userInfo.nickName,
            header: {
              "content-type": "application/json",
            },
          })
          .then((res) => {
            console.log("oL,res:", res);
            console.log("token:", res.token);
            store.commit("TOKEN_MUTATION", res.token);
            store.commit("OPENID_MUTATION", res.openid);
          })
          .catch(function (error) {
            console.log(error);
          });
      }        
    },
    fail: function (res) {
      console.log("login()失败");
    },
    complete: function (res) {},
  });
}



fly.interceptors.request.use( (request) => {
  wx.showLoading({
    title: '加载中',
    mask: true
  })


  console.log(request)
  request.headers = {
    'X-Tag': 'flyio',
    'content-type': 'application/json',
    'Authorization':store.state.token
  }
 
  let authParams = {
    // 公共参数
    'categoryType': 'SaleGoodsType@sim',
    'streamNo': 'wxapp153570682909641893',
    'reqSource': 'MALL_H5',
    'appid': 'wxdefec0c13005a788',
    'timestamp': new Date().getTime(),
    'sign': 'string'
  }
 
  request.body && Object.keys(request.body).forEach((val) => {
    if (request.body[val] === '') {
      delete request.body[val]
    };
  })
  request.body = {
    ...request.body,
    ...authParams
  }
  return request
})
 
// 添加响应拦截器
fly.interceptors.response.use(
  (response) => {
    wx.hideLoading()
    return response.data // 请求成功之后将返回值返回
  },
  (err) => {
    // 请求出错，根据返回状态码判断出错原因
    console.log(err)
    wx.hideLoading()
    if (err) {
      return '请求失败'
    };
  }
)
 
fly.config.baseURL = host
 
export default fly