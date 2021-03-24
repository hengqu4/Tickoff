import http from '../utils/http'

// 管理员登录
export function adminLogin(params){
    return http.post(`admin/login/`,params)
}

// 用户管理
export function getUserList(params){
    return http.get(`admin/userManage`,params)
}

// 视频管理
export function getVideoList(params){
    return http.get(`admin/videoManage`,params)
}

// 项目管理
export function getProjectList(params){
    return http.get(`admin/projectManage`,params)
}

// 会议管理
export function getMeetingList(params){
    return http.get(`admin/meetingManage`,params)
}

