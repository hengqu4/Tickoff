<template>
  <div>
    <van-collapse :value="activeNames" @change="onChange($event)">
      <van-collapse-item title="任务集1" name="1">
        任务1
      </van-collapse-item>
      <van-collapse-item title="任务集2" name="2">
        任务2任务2
      </van-collapse-item>
      <van-collapse-item title="任务集3" name="3">
        任务3任务3任务3
      </van-collapse-item>
    </van-collapse>
  </div>
</template>

<script>
export default {
  data(){
    return{
      activeNames: ["1"],
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