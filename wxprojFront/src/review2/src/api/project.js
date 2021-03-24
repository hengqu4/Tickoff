import http from '../utils/http'

let projectId = window.sessionStorage.getItem('projectId')
const project = `project/${projectId}`

function getProjectId(){
    return window.sessionStorage.getItem('projectId')
}

// 获取当前用户的项目列表
export function getProjects(params){
    return http.get(`projects`,params)
}

// 新建项目
export function createProject(params){
    return http.post(`project/`,params)
}

// 获取项目视频列表和用户列表
export function getUserAndVideo(Id=projectId){
    return http.get(`project/${getProjectId()}/userAndVideo`)
}

// 分页获取project video
export function getVideoListByPage(id=projectId, page=page, pageNum = pageNum){
    return http.get(
        `project/${id}/getVideoList`,
        {
            page: page,
            pageNum: pageNum,
        }
    )
}

// 获取project的成员
export function getMemberList(id=projectId){
    return http.get(
        `project/${id}/getMemberList`,
    )
}

// 邀请用户加入项目
export function inviteUser(projectId, params){
    return http.post(`project/${projectId}/inviteUser/`, params)
}

// 加入项目
export function joinProject(params,projectId){
    return http.post(`project/${projectId}/join/`,params)
}


// 删除项目中的用户
export function deleteUser(params){
    return http.delete(`project/${getProjectId()}/removeUser`,params)
}

// 删除视频
export function deleteVideo(params){
    return http.delete(`project/${getProjectId()}/removeVideo`,params)
}

// 退出项目
export function leaveProject(projectId){
    return http.post(`project/${projectId}/leave`)
}
// 项目视频回收站
export function getVideoRecycle(params){
    return http.get(`${project}/recycle_video`,params)
}

// 获取项目的会议列表
export function getMeetings(projectId){
    return http.get(`project/${projectId}/getMeeting`)
}

// 获取项目中的代办会议
export function getTodoMeeting(projectId){
    return http.get(`project/${projectId}/getTodoMeeting`)
}

// 获取项目的历史会议
export function getHistoryMeeting(projectId){
    return http.get(`project/${projectId}/getPastMeeting`)
}