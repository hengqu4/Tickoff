<template>
  <div>
    <div name="calendar">
      <xl-calendar2
        
        :enableArea="['2020-11-12', '2020-11-30']"
      />
      <!-- <button @takeoverTap="takeoverTap">test</button> -->
    </div>
  </div>
</template>

<script>
// import plugin from '../../../static/xl_calendar/v2/plugins'
// import selectable from '../../../static/xl_calendar/v2/plugins/selectable'
// import time-range from '../../../static/wx_calendar/plugins/time-range.js'

export default {
  data(){
    return{
      calendarConfig: 
      {
        multi: true, // 是否开启多选,
        weekMode: true, // 周视图模式
        theme: 'elegant', // 日历主题，目前共两款可选择，默认 default 及 elegant，自定义主题色在参考 /theme 文件夹
        markToday: '今', // 当天日期展示不使用默认数字，用特殊文字标记
      },
      toSet:[
        {
          year: 2020,
          month: 12,
          date: 15
        },
        {
          year: 2020,
          month: 12,
          date: 18
        }
      ]
    }
  },

  methods:{
 

    // takeoverTap(){
    //   console.log('takeoverTap', e.mp.detail) // => { year: 2019, month: 12, date: 3, ...}
    // },
  }
}
</script>

<style>

</style>