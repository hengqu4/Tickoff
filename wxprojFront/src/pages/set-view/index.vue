<template>
  <div>
    <div v-for="(item, index) in dataList" :key="index" :style="{marginBottom: '5px'}">
      <wux-wing-blank size="default"  :key="index">
        <wux-card prefixCls="set-card" :title="item.title" :actions="actions" @action="onAction($event,item.mset_id)">
          <view slot="body">
            <view class="wux-ellipsis">{{item.description}}</view>
          </view>
          <view slot="footer">
              <p>成员</p>
              <span v-for="(mem, aid) in item.member" :key="aid">
                <wux-avatar :src="mem.avatar_url" />
              </span>
          </view>
        </wux-card>
      </wux-wing-blank>
    </div>
    <div :style="{height:'80px'}"/>
    <div class="set-create-button">
      <van-button round type="info" @click="onClick">
          +
      </van-button>
    </div>
  </div>
</template>

<script>
import '../../../static/wux/styles/index.wxss'
import store from '../../store'
export default {
  data(){
    return{
      dataList:[],
      actions:[
        {
          text:'详情',
          type:'primary'
        }
      ]
    }
  },

  beforeMount() {
    wx.getUserInfo({
      withCredentials: false,
      success: (res) => {
      },
      fail: () => {
        console.log("获取失败");
      },
      complete: () => {},
    });
  },
  
  onShow() {
    this.$fly.request({
      method: 'get', 
      url: 'tickoff/api/mission_set/mset_info_list/'+store.state.openId,
    }).then(res => {
      console.log("res")
      console.log(res)
      this.dataList=res.data
    }).catch(function (error) {
      console.log("error")
      console.log(error);
    });
  },

  methods:{
    // 页面跳转失败。。
    onClick () {
      var pages = getCurrentPages()    //获取加载的页面
      var currentPage = pages[pages.length-1]    //获取当前页面的对象
      var currentUrl = currentPage.route
      console.log(currentUrl)
      const url = '../create-set/main'
      wx.navigateTo({ 
        url: url,
        success: function(res){
          console.log('跳转到页面成功')// success
        },
        fail: function() {
        console.log('跳转到页面失败')  // fail
        },
      })
    },

    onAction(e, key){
      console.log(e.mp.detail)
      console.log(key)
      //const url='/pages/set-detail/main?setId='+id+"&uId="+this.userId
      const url='/pages/set-detail/main?setId='+key
      wx.navigateTo({ 
        url: url,
        success: function(res){
          console.log('跳转到页面成功')// success
        },
        fail: function() {
        console.log('跳转到页面失败')  // fail
        },
      })
    }
  }
}
</script>


<style lang="less">
@import "../../../static/wux/styles/mixins/index.less";
@import "../../../static/wux/styles/themes/index.less";
@import "./style.less";

.set-create-button{
  z-index: 1;
  position: fixed;
  left: 80%;
  top:85%
}

</style>