<template>
  <div>
    <div name="calendar">
      <xl-calendar1>
      </xl-calendar1>
    </div>
  </div>
</template>

<script>
// import plugin from '../../../static/wx_calendar/plugins'
// import selectable from '../../../static/wx_calendar/plugins/selectable.js'
// import time-range from '../../../static/wx_calendar/plugins/time-range.js'

// plugin
//   .use(selectable)
//   .use(time-range)

export default {
  data(){
    return{
      activeNames: [],
      dataList:[
        {
          title:'任务集1',
          desc:'任务1任务1任务1'
        },
        {
          title:'任务集2',
          desc:'任务2222222'
        },
        {
          title:'任务集3',
          desc:'任务33333333'
        }
      ]
    }
  },

  methods:{
     onChange (event) {
      console.log(event.mp)
      this.activeNames = event.mp.detail
    }
  }
}
</script>

<style>

</style>