import http from '../utils/http'

 
// form-data
// 创建视频
export function createVideo(params){
    return http.post(`video/`,params)
}
// 使用ali-oss
// 快速创建视频
export function fastCreateVideo(params){
    return http.post(`fast-video/`,params)
}
// 完成审阅
export function finishReview(params,videoId){
    return http.post(`video/${videoId}/review/`,params)
}
// 获取我的视频
export function getMyVideo(params){
    return http.get(`video/mine/`,params)
}
// 上传视频文件  上传到后端的嘛?
export function uploadVideo(params){
    return http.post(`uploadVideo/`,params )
}

// 返回视频的批注列表
export function getComments(videoId){
    return http.get(`video/${videoId}/comments`)
}

// 新建批注
export function createComment(params,videoId){
    return http.post(`video/${videoId}/comment/`,params)
}

// 获取视频某一帧
export function getVideoFrame(params,videoId){
    return http.get(`video/${videoId}/frame`,params)
}

// 获取视频信息
export function getVideoInfo(videoId){
    return http.get(`video/${videoId}/info`)
}

// 删除视频批注 (有bug,弃用)
// export function deleteVideoComment(params,videoId){
//     return http.delete(`video/${videoId}/comment`,params)
// }

// 删除视频批注
export function deleteVideoComment(params,videoId){
    return http.get(`video/${videoId}/remove_comment`,params)
}

//回复批注
export function replyComment(params,videoId){
    return http.post(`video/${videoId}/reply/`,params)
}
// 删除批注的回复
export function deleteReply(videoId,commentId,replyId){
    return http.delete(`video/${videoId}/comment/${commentId}/reply?replyId=${replyId}`)
}