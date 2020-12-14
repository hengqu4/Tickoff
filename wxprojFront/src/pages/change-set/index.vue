<template>
  <div>
    <wux-form id="wux-form" @change="onChange">

      <wux-cell-group title="标题">
        <wux-cell hover-class="none">
          <wux-field name="title" initialValue="请输入任务集标题">
            <wux-textarea rows="1" />
          </wux-field>
        </wux-cell>
      </wux-cell-group>

      <wux-cell-group title="描述">
        <wux-cell hover-class="none">
          <wux-field name="description" initialValue="请输入任务集描述">
            <wux-textarea rows="3" />
          </wux-field>
        </wux-cell>
      </wux-cell-group>

      <wux-cell-group title="成员">
        <wux-cell hover-class="none">
          <div>
            <wux-button clear type="positive" open-type="share">
              邀请好友
            </wux-button>
          </div>
          <div v-for="(item, index) in memberList" :key="index" :style="{marginBottom: '5px'}">
            <view>
              <wux-avatar :src="item.avater" />
              <span>{{item.name}}</span>
            </view>
          </div>
        </wux-cell>
      </wux-cell-group>

      <view class="btn-area">
        <van-button round type="info" @click="onSubmit">
          保存
        </van-button>
      </view>
    </wux-form>

  </div>
</template>

<script>
import { $wuxForm, $wuxToast } from "../../../static/wux/index";
import moment from 'moment';
import toast from 'mpvue-toast'


export default {
  data() {
    return {
      userId:'',
      taskId:'',
      title:'',
      description:'',
      memberNum:'',
      member: [],
      memberList:[
        {
          name:'github',
          avater:"../../../static/images/user.png",
        },
        {
          name:'hq',
          avater:"http://cdn.skyvow.cn/logo.png",
        }
        
      ]
    };
  },
  components: {
    toast
  },

  onLoad:function(options) {
    console.log(options)
    this.setId=options.sId
    this.userId=options.uId
  },

  created(){

  },
  beforeMount() {
    wx.getUserInfo({
      withCredentials: false,
      success: (res) => {
        console.log(res.rawData);
      },
      fail: () => {
        console.log("shibai");
      },
      complete: () => {},
    });
    this.isLoading=!this.isLoading;
  },

  mounted() {
    this.$fly.request({
      method: 'get', // post/get 请求方式
      // url: 'api/getSet?setId='+this.setId,
      url: 'api/getSet?setId=1',
    }).then(res => {
      console.log(res)
      this.title=res.title
      this.description=res.description
      this.memberNum=res.memberNum
      this.member=res.member
      
    }).catch(function (error) {
        console.log(error);
    });
  },

  methods:{
    onSubmit() {
      const { getFieldsValue, getFieldValue, setFieldsValue } = $wuxForm()
      const value = getFieldsValue()
      console.log('Wux Form Submit \n', value)

      var subData={}
      subData.title=value.title
      subData.description=value.description

      this.$fly.request({
        method:"post", //post/get 请求方式
        url:"api/createTask",
        body:{
          "title":subData.title,
          "description":subData.description,
        }
      }).then(res =>{
        this.gotoDetail(1)
      })
    },

    gotoDetail(id) {
      // const url = '/pages/set-detail/main?tId='+id+'&uId='+this.userId
      const url = '../set-detail/main'
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
  },
};
</script>