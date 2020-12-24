<template>
  <div>
    <div :style="{textAlign:'center'}">
      <p :style="{fontFamily:'Segoe Print',fontSize:'300%',color:'#341f97'}">
        invitition
      </p>
    </div>
    <div id="invite-div">
      <wux-wing-blank size="default">
        <wux-card prefixCls="invite-card" :title="title">
          <view slot="body">
            <div :style="{height:'100px'}">{{description}}</div>
          </view>
        </wux-card>
        <view :style="{textAlign:'center',marginTop:'30px'}">
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
  onLoad(){
    console.log("load")
  },
  onShow(){
    console.log("show")
  },
  onReady(){
    console.log("ready")
  },
  // onShow(){
  //   var pages = getCurrentPages() //获取加载的页面
  //   var currentPage = pages[pages.length-1] //获取当前页面的对象
  //   var url = currentPage.route //当前页面url
  //   var options = currentPage.options //如果要获取url中所带的参数可以查看options
  //   console.log(url)
  //   console.log(options)
    // this.$fly
    //   .request({
    //     // console.log(store.state.openId)
    //     method: "get",
    //     url: "tickoff/api/mission_set/mset_info/" + this.setId,
    //   })
    //   .then((res) => {
    //     console.log("res");
    //     console.log(res);
    //     this.setId = res.data.mset_id;
    //     this.title = res.data.title;
    //     this.create_date = res.data.create_date;
    //     this.description = res.data.description;
    //     this.member = res.data.member;
    //   })
    //   .catch(function (error) {
    //     console.log("error");
    //     console.log(error);
    //   });
  // },
  mounted() {
    console.log("mounted")
  },
  // mounted() {
  //   this.$fly
  //     .request({
  //       // console.log(store.state.openId)
  //       method: "get",
  //       url: "tickoff/api/mission_set/mset_info/" + this.setId,
  //     })
  //     .then((res) => {
  //       console.log("res");
  //       console.log(res);
  //       this.setId = res.data.mset_id;
  //       this.title = res.data.title;
  //       this.create_date = res.data.create_date;
  //       this.description = res.data.description;
  //       this.member = res.data.member;
  //     })
  //     .catch(function (error) {
  //       console.log("error");
  //       console.log(error);
  //     });
  // },

  methods:{
    // 页面跳转失败。。
    onClick (e) {
      console.log(e.mp.detail)
      var pages = getCurrentPages()    //获取加载的页面
      var currentPage = pages[pages.length-1]    //获取当前页面的对象
      var currentUrl = currentPage.route
      console.log(currentUrl)
      console.log("store.state.openId,"+store.state.openId)
      console.log("mset_id,"+this.setId)
      this.$fly
        .request({
          method: "post", //post/get 请求方式
          url: "tickoff/api/mission_set/openid/" + store.state.openId,
          body: {
            name: subData.title,
            description: subData.description,
            create_date: createTime,
          },
        })
        .then((res) => {
          const url = '../set-view/main'
          wx.navigateTo({ 
            url: url,
            success: function(res){
              console.log('跳转到页面成功')// success
            },
            fail: function() {
            console.log('跳转到页面失败')  // fail
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

</style>