<template>
  <div>
    <div v-for="(item, index) in dataList" :key="index" :style="{marginBottom: '5px'}">
      <wux-wing-blank size="default"  :key="index">
        <!-- <wux-card prefixCls="set-card" :title="item.title" :actions="actions" @action="onAction($event,item.mset_id)"> -->
        <wux-card prefixCls="set-card" :title="item.title" @click="onDetail($event,item.mset_id)">
          <view slot="extra">
            <wux-button 
                clear type="positive"
                :data-id="item.mset_id"
                @click.stop="onExit($event,item.mset_id)"
                :style="{height:'10px'}"
              >
                退出
            </wux-button>
          </view>
          <view slot="body" >
            <view class="wux-ellipsis">{{item.description}}</view>
          </view>
          <view slot="footer" >
              <!-- 去掉成员，节省行高 -->
              <!-- <p>成员</p> -->
              <span v-for="(mem, aid) in item.member" :key="aid">
                <wux-avatar :src="mem.avatar_url" />
              </span>
          </view>
        </wux-card>
      </wux-wing-blank>
    </div>
    <div :style="{height:'80px'}"/>
    <div class="set-create-button">
      <van-button round type="info" class="floatBtn" @click="onClick">
        <van-icon name="plus" />
      </van-button>
      <!-- <button open-type="getUserInfo" @bindgetuserinfo="bindGetUserInfo($event)">点击授权</button> -->
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
    // bindGetUserInfo(e) {
    //   console.log(e.mp.detail);
    //   consolo.log("授权")
    //   if (res.detail.userInfo) {
    //     console.log("点击了同意授权");
    //   } else {
    //     console.log("点击了拒绝授权");
    //   }
    // },

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
        
    onDetail(e, key){
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
    },

    onExit(e, key){
      console.log(e.mp.detail)
      console.log(store.state.openId)
      console.log(key)
      console.log("退出")
      this.$fly
        .request({
          method: "delete",
          //   "tickoff/api/User_mset/openid/{openid}/mset_id/{mset_id}"
          url:'tickoff/api/User_mset/openid/'+store.state.openId+'/mset_id/'+ key,
        })
        .then((res) => {
          const pages = getCurrentPages();
          const perpage = pages[pages.length - 1];
          perpage.onShow();
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  }
}
</script>


<style lang="less">
@import "../../../static/wux/styles/mixins/index.less";
@import "../../../static/wux/styles/themes/index.less";
@import "./style.less";
page {
  background-color: rgb(245, 245, 245);
}
.set-create-button{
  z-index: 1;
  position: fixed;
  left: 80%;
  top:85%
}
.floatBtn {
  position: relative;
}
.floatBtnIcon {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  margin: auto;
}

</style>