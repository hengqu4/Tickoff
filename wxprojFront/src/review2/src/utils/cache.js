// 通过sessionStorage存储projectId
export function getProjectId(){
    return window.sessionStorage.getItem('projectId')
}
export function setProjectId(projectId){
    return window.sessionStorage.setItem('projectId',projectId)
}
export function removeProjectId(){
    return window.sessionStorage.removeItem('projectId')
}
// 通过sessionStorage存储projectName
export function getProjectName(){
    return window.sessionStorage.getItem('projectName')
}
export function setProjectName(name){
    return window.sessionStorage.setItem('projectName',name)
}
export function removeProjectName(){
    return window.sessionStorage.removeItem('projectName')
}
// 通过sessionStorage存储videoId
export function getVideoId(){
    return window.sessionStorage.getItem('videoId')
}
export function setVideoId(id){
    return window.sessionStorage.setItem('videoId',id)
}
export function removeVideoId(){
    return window.sessionStorage.removeItem('videoId')
}
// 通过sessionStorage存储userId
export function getUserId(){
    return window.localStorage.getItem('userId')
}
export function setUserId(id){
    return window.localStorage.setItem('userId',id)
}
export function removeUserId(){
    return window.localStorage.removeItem('userId')
}
// 通过sessionStorage存储userName
export function getUserName(){
    return window.sessionStorage.getItem('userName')
}
export function setUserName(name){
    return window.sessionStorage.setItem('userName',name) 
}
export function removeUserName(){
    return window.sessionStorage.removeItem('userName') 
}
// 通过sessionStorage存储userAvatar
export function getUserAvatar(){
    return window.sessionStorage.getItem('userAvatar')
}
export function setUserAvatar(url){
    return window.sessionStorage.setItem('userAvatar',url)
}
export function removeUserAvatar(){
    return window.sessionStorage.removeItem('userAvatar')
}
// 通过sessionStorage存储userMobile
export function getUserMobile(){
    return window.sessionStorage.getItem('userMobile')
}
export function setUserMobile(mobile){
    return window.sessionStorage.setItem('userMobile',mobile)
}
export function removeUserMobile(){
    return window.sessionStorage.removeItem('userMobile')
}