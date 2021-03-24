// 过滤器

// 只显示视频名称
export function videoNameFormat(name){
    return name.slice(0,name.lastIndexOf('.'))
}

// 转换时间的格式
export function dateFormat(originVal){
  const dt = new Date(originVal)
  const y = dt.getFullYear()
  const m = (dt.getMonth() + 1 + '').padStart(2, '0')
  const d = (dt.getDate() + '').padStart(2, '0')
  const hh = (dt.getHours() + '').padStart(2, '0')
  const mm = (dt.getMinutes() + '').padStart(2, '0')
  const ss = (dt.getSeconds() + '').padStart(2, '0')
  return `${y}-${m}-${d} ${hh}:${mm}:${ss}` 
}

// 多长时间之前
export function dataFilter(value){
  value = value * 1000
    var year = 24*60*60*1000*365;//拿到一年的毫秒数
    var month = 24*60*60*1000*30;//拿到一个月的毫秒数
    var day = 24*60*60*1000;//拿到一天的毫秒数
    var hour = 60*60*1000;//拿到一个小时的毫秒数
    var minute = 60*1000;//拿到一分钟的毫秒数 

   //getTime()   返回从 1970 年 1 月 1 日至今的毫秒数
    var newDate = new Date().getTime();//拿到1970年1月1日距当前的时间的毫秒数
    var time = new Date(value).getTime();//拿到json里面的关于时间的值，计算1970.1.1距json给的时间的毫秒数
    var date = newDate - time;//计算两个时间的差值

    var years = Math.floor(date/year)>0 ? `${Math.floor(date/year)}年前` : ""; //两个时间的差值，除以一年的毫秒数，看整数是否大于0，如果是的话就返回,当前的整数数据，如果不是,返回空。
 
    var months = Math.floor(date/month)>0 ? `${Math.floor(date/month)}月前` : "";//两个时间的差值，除以一月的毫秒数，看整数是否大于0，如果是的话就返回,当前的整数数据，如果不是,返回空。
    var days = Math.floor(date/day)>0 ? `${Math.floor(date/day)}天前` : "";//两个时间的差值，除以一天的毫秒数，看整数是否大于0，如果是的话就返回,当前的整数数据，如果不是,返回空。
    var hours = Math.floor(date/hour)>0 ? `${ Math.floor(date/hour)}小时前` : "";//两个时间的差值，除以一小时的毫秒数，看整数是否大于0，如果是的话就返回,当前的整数数据，如果不是,返回空。
    var minutes =  Math.floor(date/minute)>0 ? `${Math.floor(date/minute)}分钟前` : "";//两个时间的差值，除以一分钟的毫秒数，看整数是否大于0，如果是的话就返回,当前的整数数据，如果不是,返回空。
  
  
    if(years.length != 0){
      return years;
    } else if(months.length != 0){
      return months;
    } else if(days.length != 0){
      return days;
    } else if(hours.length != 0){
      return hours;
    } else if(minutes.length != 0){
      return minutes;
    } else {
      return parseInt((date)/1000)>0?`${parseInt((date)/1000)}秒前`:"";
    }
}

 
// 视频时长
export function timeFormat(time){
    var hours = Math.floor(time / 3600)
    var minutes = Math.floor(time %3600 /60 )
    var seconds = time % 60
    minutes = minutes>10? minutes:'0'+minutes
    seconds = seconds>10 ? seconds:'0'+seconds
    return `${hours}:${minutes}:${seconds}` 
}

// 消息类型
export function messageType(type){
    let message =''
    switch (type) {
        case 0:  message="在上传了新的视频";break;
      case 1: message='审阅了你上传的视频';break;
        case 2: message='你的某个项目里的成员预定了新的会议';break;
      case 3: message='邀请你加入项目';break;
      case 4: message='处理了你的邀请';break;
      case 5:message= '你被移除出了项目';break;
  
      default:
        break;
    }
   return message
}