<template>
  <div>
    <div :style="{textAlign:'center',marginTop:'30px'}">
      <p :style="{fontFamily:'Segoe Print',fontSize:'300%',color:'#341f97'}">
        invitition
      </p>
    </div>
    <div id="invite-div" :style="{marginTop:'30px'}">
      <wux-wing-blank size="default">
        <wux-card prefixCls="invite-card" :title="title">
          <view slot="body">
            <div :style="{height:'100px'}">{{description}}</div>
          </view>
        </wux-card>
        <view :style="{height:'40px'}" />
        <view class="invite-button">
          <wux-button outline type="positive" @click="onClick($event)">
            接受邀请
          </wux-button>
        </view>
      </wux-wing-blank>
    </div>
  </div>
</template>

<script>
import store from '../../store'
export default {
  data(){
    return {
      userId: "",
      // store.state.openId
      setId: "",
      title: "",
      description: "",
    };
  },


  onLoad: function (options) {
    console.log("!!!!!!!!!邀请onload!!!!!!", options);

    var pages = getCurrentPages() //获取加载的页面
    var currentPage = pages[pages.length-1] //获取当前页面的对象
    var url = currentPage.route //当前页面url
    console.log("url："+url)

    console.log("options："+options)
    this.setId=options.setId
    console.log("this.setId: "+this.setId);

    this.$fly
      .request({
        // console.log(store.state.openId)
        method: "get",
        url: "tickoff/api/mission_set/mset_info/" + this.setId,
      })
      .then((res) => {
        console.log("res");
        console.log(res);
        this.setId = res.data.mset_id;
        this.title = res.data.title;
        this.create_date = res.data.create_date;
        this.description = res.data.description;
        this.member = res.data.member;
      })
      .catch(function (error) {
        console.log("error");
        console.log(error);
      });
  },

  
  mounted() {
    this.$fly
      .request({
        // console.log(store.state.openId)
        method: "get",
        url: "tickoff/api/mission_set/mset_info/" + this.setId,
      })
      .then((res) => {
        console.log("res");
        console.log(res);
        this.setId = res.data.mset_id;
        this.title = res.data.title;
        this.create_date = res.data.create_date;
        this.description = res.data.description;
        this.member = res.data.member;
      })
      .catch(function (error) {
        console.log("error");
        console.log(error);
      });
  },

  methods:{
    // 页面跳转失败。。
    onClick (e) {
      console.log("！！！！！点击接受按钮！！！！！！！！！")
      console.log("！！！store.state.openId！！！")
      console.log(store.state.openId)
      console.log("！！！mset_id！！！")
      console.log(this.setId)
      this.$fly
        .request({
          method: "post", 
          url: "tickoff/api/user_mset",
          body: {
            userId:store.state.openId,
            setId: this.setId,
          },
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
    
  }
}
</script>

<style lang="less">

@import "../../../static/wux/styles/mixins/index.less";
@import "../../../static/wux/styles/themes/index.less";
@import "./style.less";

.invite-button{
  text-align:'center';
  z-index: 1;
  position: fixed;
  top:80%;
  left:40%
}
</style>