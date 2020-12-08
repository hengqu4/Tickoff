<template>
  <div>
    <van-collapse :value="activeNames" @change="onChange($event)" v-for="(item,index) in dataList" :key='index'>
      <van-collapse-item :title="item.title" :name="index">
        {{item.desc}}
      </van-collapse-item>
    </van-collapse>
  </div>
</template>

<script>
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