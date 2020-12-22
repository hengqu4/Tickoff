<template>
  <div class="set-detail">
    <wux-form id="wux-form" @change="onChange">
      <wux-cell-group title="任务集信息"></wux-cell-group>

      <wux-cell-group title="标题">
        <wux-cell hover-class="none">
          <wux-field name="title" :initialValue="title">
            <wux-textarea rows="1" />
          </wux-field>
        </wux-cell>
      </wux-cell-group>

      <wux-cell-group title="描述">
        <wux-cell hover-class="none">
          <wux-field name="description" :initialValue="description">
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
          <div
            v-for="(item, index) in member"
            :key="index"
            :style="{ marginBottom: '5px' }"
          >
            <view>
              <wux-avatar :src="item.avatar_url" />
              <span>{{ item.nickname }}</span>
            </view>
          </div>
        </wux-cell>
      </wux-cell-group>

      <div :style="{ height: '80px' }" />

      <div class="set-edit-button">
        <van-button round type="info" @click="onSubmit"> 保存 </van-button>
      </div>
    </wux-form>
  </div>
</template>

<script>
import { $wuxForm, $wuxToast } from "../../../static/wux/index";
import miment from "miment";
import toast from "mpvue-toast";

export default {
  data() {
    return {
      userId: "",
      setId: "",
      title: "",
      description: "",
      create_date: "",
      member: [],
    };
  },
  components: {
    toast,
  },

  onLoad: function (options) {
    console.log("!!!!!!!!!!!!!!!", options);
    this.setId = options.setId;
    this.userId = options.uId;
  },

  beforeMount() {
    wx.getUserInfo({
      withCredentials: false,
      success: (res) => {},
      fail: () => {
        console.log("获取失败");
      },
      complete: () => {},
    });
  },

  mounted() {
    this.$fly
      .request({
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

  methods: {
    onChange(event) {
      const { form, changedValues, allValues } = event.mp.detail;
      console.log("onChange \n", changedValues, allValues);
    },

    onSubmit() {
      const { getFieldsValue, getFieldValue, setFieldsValue } = $wuxForm();
      const value = getFieldsValue();
      console.log("Wux Form Submit \n", value);

      var subData = {};
      subData.title = value.title;
      subData.description = value.description;

      this.$fly
        .request({
          method: "put", //post/get 请求方式
          url: "tickoff/api/mission_set",
          body: {
            name: subData.title,
            description: subData.description,
            mset_id: this.setId,
            creat_date: this.create_date,
          },
        })
        .then((res) => {
          wx.navigateBack({
            delta: 1, //返回的页面数，如果 delta 大于现有页面数，则返回到首页,
          });
        });
    },
  },
};
</script>

<style>
.set-edit-button {
  z-index: 1;
  position: fixed;
  left: 80%;
  top: 85%;
}
</style>
