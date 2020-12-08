<template>
  <div>
    <wux-wing-blank size="default">
      <wux-card :title="data.title" extra="额外内容">
        <view slot="body">{{data.desc}}</view>
        <view slot="footer">尾部内容</view>
      </wux-card>
    </wux-wing-blank>
  </div>
</template>

<script>

const listData = [];
for (let i = 0; i < 3; i++) {
  listData.push({
    index: i,
    title: `ant design vue part ${i}`,
    desc:
      'Ant Design, a design language for background applications, is refined by Ant UED Team.',
  });
}

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