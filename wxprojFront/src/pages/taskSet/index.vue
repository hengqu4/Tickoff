<template>
  <div>
    <div v-for="(item, index) in dataList" :key="index" :style="{marginBottom: '5px'}">
      <wux-wing-blank size="default"  :key="index">
        <wux-card :title="item.title" extra="额外内容">
          <view slot="body">{{item.desc}}</view>
          <view slot="footer">
            <view>
              <p>成员</p>
              <wux-avatar src="../../../static/images/user.png" />
              <wux-avatar src="../../../static/images/user.png" />
              <wux-avatar src="../../../static/images/user.png" />
            </view>
          </view>
        </wux-card>
      </wux-wing-blank>
    </div>
  </div>
</template>

<script>

export default {
  data(){
    return{
      dataList:[
        {
          title:'任务集1',
          desc:'1111',
        },
        {
          title:'任务集2',
          desc:'2222',
        },
        {
          title:'任务集3',
          desc:'3333',
        },
      ]
    }
  }
}
</script>

<style>

</style>