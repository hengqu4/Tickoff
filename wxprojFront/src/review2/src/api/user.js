import http from '../utils/http'

// 登录验证接口
export function login(params){
    return http.post(`login/`,params)
}
// 注册
export function register(params){
    return http.post('register/',params)
}
// 获取验证码
export function getCheckCode(params){
    return http.get('checkCode',params)
}
// 获取个人信息
export function getUserInfo(userId){
    return http.get(`user/${userId}`)
}
// 修改个人信息
export function updateUserInfo(params){
    return http.post(`userInfo/`,params)
}
// 查询用户列表
export function getUserList(params){
    return http.get('userlist/',params)
}
// 重设密码
export function resetPassword(params){
    return http.post(`user/resetPassword/`,params)
}
// 上传头像等图片
export function uploadImg(params){
    return http.post(`uploadImg/`,params)
}
// 获取头像
export function getUserAvatar(params){
    return http.get(`userAvatar`,params)
}
// 获取上传 object-key
export function getObjectKey(params){
    return http.get(`user/object-key`,params)
}

// 消息列表
export function getMessages(params){
    return http.get(`messages/`,params)
}
//分页获取消息
export function getMesByPage(page) {
  return http.get(`messages/${page}`)
}

// 处理邀请
export function processVisit(params){
    return http.post(`${project}/join/`,params)
}
// 标记消息已经读取
export function readMessage(id){
    return http.get(`message/${id}`)
}

// 绑定手机号
export function bindMobileNum(params){
    return http.post(`user/setMobileNum/`, params)
}

//获取未读消息数
export function unReadMessage(params) {
  return http.get(`messages/unRead`,params)
}



