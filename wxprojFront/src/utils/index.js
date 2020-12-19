function formatNumber(n) {
  const str = n.toString()
  return str[1] ? str : `0${str}`
}

export function formatTime(date) {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()

  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  const t1 = [year, month, day].map(formatNumber).join('/')
  const t2 = [hour, minute, second].map(formatNumber).join(':')

  return `${t1} ${t2}`
}

export default {
  formatNumber,
  formatTime
}

export const formatTimeHash = (date) => {
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()

  var hour = date.getHours()
  var minute = date.getMinutes()
  var second = date.getSeconds();

  return [year, month, day].map(formatNumber).join('-')
}

export const monthStr = (num) => {
  switch(num){
      case 0:
          return 'Jan'
          break;
      case 1:
          return 'Feb'
          break;
      case 2:
          return 'Mar'
          break;
      case 3:
          return 'Apr'
          break;
      case 4:
          return 'May'
          break;
      case 5:
          return 'Jun'
          break;
      case 6:
          return 'Jul'
          break;
      case 7:
          return 'Aug'
          break;
      case 8:
          return 'Sep'
          break;
      case 9:
          return 'Oct'
          break;
      case 10:
          return 'Nov'
          break;
      case 11:
          return 'Dec'
          break;
      default:
          return 'bug'
  }
}