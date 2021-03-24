<template>
  <div class="set-detail">
    <!-- <wux-cell-group title="任务集信息"></wux-cell-group> -->

    <wux-cell-group prefixCls="title-cell-group">
      <wux-cell hover-class="none">
        <wux-field id="wux-field" name="title" :initialValue="title">
          <wux-textarea prefixCls="title-disabled-textarea" disabled rows="1" />
        </wux-field>
      </wux-cell>
    </wux-cell-group>

    <wux-cell-group prefixCls="cell-group" title="描述">
      <wux-cell hover-class="none">
        <wux-field id="wux-field" name="description" :initialValue="description">
          <wux-textarea prefixCls="disabled-textarea" disabled rows="3" />
        </wux-field>
      </wux-cell>
    </wux-cell-group>

    <wux-cell-group prefixCls="cell-group" title="成员">
      <wux-cell hover-class="none">
        <div>
          <wux-button clear type="balanced" open-type="share">
            邀请好友
          </wux-button>
        </div>
        <div v-for="(item, index) in member" :key="index" :style="{marginBottom: '5px'}">
          <view>
            <wux-avatar :src="item.avatar_url" />
            <span>{{item.nickname}}</span>
          </view>
        </div>
      </wux-cell>
    </wux-cell-group>

    <div :style="{height:'80px'}"/>
    <view class="exit-button">
      <van-button round plain type="danger" @click="onExit($event,setId)">
          退出
      </van-button>
    </view>
    <view class="set-edit-button">
      <van-button round type="info" @click="gotoChange(setId)">
          修改
      </van-button>
    </view>
  </div>
</template>

<script>
import store from '../../store'

export default {
  data() {
    return {
      userId:'',
      setId:'',
      title: "",
      description: "",
      member:[]
    };
  },

  onLoad:function(options) {
    console.log(options)
    this.setId=options.setId
    this.userId=options.uId
  },

  onShareAppMessage: function (res) {
    console.log(`/pages/invite/main?setId=${this.setId}`)
    return {
      title: '快来Tickoff和我一起吧',
      path: `/pages/invite/main?setId=${this.setId}`,
      success: function (res) {
        // 转发成功
        wx.showToast({
          title: "分享成功",
          icon: 'success',
          duration: 2000
          })
        },
      fail: function (res) {
        // 分享失败
      },
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
      url: 'tickoff/api/mission_set/mset_info/'+this.setId,
    }).then(res => {
      console.log("res")
      console.log(res)
      this.title=res.data.title
      this.description=res.data.description
      this.member=res.data.member
    }).catch(function (error) {
      console.log("error")
      console.log(error);
    });
  },

  methods:{
    onExit(e, key){
      console.log(e.mp.detail)
      console.log(store.state.openId)
      console.log(key)
      this.$fly
        .request({
          method: "delete",
          //   "tickoff/api/User_mset/openid/{openid}/mset_id/{mset_id}"
          url:'tickoff/api/User_mset/openid/'+store.state.openId+'/mset_id/'+ key,
        })
        .then((res) => {
          wx.switchTab({  
            url:'/pages/set-view/main',
            success: function(res){
              console.log('跳转到组集页面成功')// success
            },
            fail: function() {
            console.log('跳转到组集页面失败')  // fail
            },
          })
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    gotoChange(id) {
      console.log(id)
      const url='/pages/change-set/main?setId='+id+"&uId="+this.userId
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
@import "../../style/group.less";
@import "../../style/textarea.less";

page {
  background-color: rgb(245, 245, 245);
}

.exit-button{
  z-index: 1;
  position: fixed;
  left: 80%;
  top:75%
}

.set-edit-button{
  z-index: 1;
  position: fixed;
  left: 80%;
  top:85%
}

</style>