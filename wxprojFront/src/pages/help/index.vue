<template>
  <div>
    <div class="container">
      <wux-wing-blank size="default">
        <wux-card title="适用场景">
          <view slot="body">
            <view class="wux-ellipsis">
              ·朋友、家庭、情侣之间，及简单工作共享的需求。
              如：工作计划日程、家庭日历、旅游行程、活动安排、资源时间预约、请假记录、课程表等。
            </view>
          </view>
        </wux-card>
        <wux-card title="未显示数据？">
          <view slot="body">
            <view class="wux-ellipsis">
              ·请确认您是否授权Tickoff获取您的微信个人信息
            </view>
          </view>
        </wux-card>
      </wux-wing-blank>
    </div>
  </div>
</template>

<script>

</script>

<style>
.container {
    margin-top: 30px 0;
    text-align: center;
}
.wux-card{
  margin-top:20px;
  margin-bottom: 20px;
}

</style>
