import http from '../utils/http'


export function searchMeeting(params){
    return http.get(`meeting/search`,params)
}
 
export function getMyMeeting(params){
    return http.get(`meeting/mine`,params)
}

export function getTodoMeeting(params){
    return http.get(`meeting/todo`,params)
}

export function getHistoryMeeting(params){
    return http.get(`meeting/history`,params)
}

export function createMeeting(params){
    return http.post(`meeting/`,params)
}