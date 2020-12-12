<template>
  <div>
    <view class="page">
      <view class="page__bd">
        <view class="sub-title"></view>
        <wux-wing-blank size="default">
            <wux-card title="?" extra="额外内容">
                <view slot="body">
                  <p>tiaoti shi </p>
                </view>
                <view slot="footer">
                  <p>buzhidao shuo sm </p>
                </view>
            </wux-card>
        </wux-wing-blank>
      </view>
    </view>
  </div>
</template>

<script>

export default {
  data() {
    return {
      actions: [{
            type: 'default',
            text: '微信支付',
        }, {
            text: '现金支付',
            type: 'primary',
        }],
    
    };
  },

  onLoad() {},
   onAction(e) {
        console.log('onAction', e.detail)
    },
  created(){

   
  },

  beforeMount() {
    wx.getUserInfo({
      withCredentials: false,
      success: (res) => {
        // console.log(res.rawData);
      },
      fail: () => {
        console.log("shibai");
      },
      complete: () => {},
    });
  },
  mounted() {
    this.$fly.request({
      method: 'get', // post/get 请求方式
      url: 'api/getTask',
    }).then(res => {
      console.log(res)
    }).catch(function (error) {
        console.log(error);
    });
  },

  computed: {},

  methods: {

  }
};
</script>

