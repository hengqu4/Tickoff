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

      <view class="create-set-submit-button">
        <van-button
          color="linear-gradient(to right, #4bb0ff, #6149f6)"
          @click="onSubmit"
        >
          创建
        </van-button>
        <div />
        <van-button
          color="linear-gradient(to right, #4bb0ff, #6149f6)"
          @click="onReset"
        >
          重置
        </van-button>
      </view>
    </wux-form>
  </div>
</template>

<script>
import { $wuxForm } from "../../../static/wux/index";
import store from "../../store";
import miment from "miment";
export default {
  computed: {},
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
      var oid = store.state.openId;
      var createTime = miment().format("YYYY-MM-DD hh:mm:ss");
      console.log("onSubmit:", store.state.openId);

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
          console.log(subData.title);
          console.log(subData.description);
          //this.gotoDetail(1)
          wx.navigateBack({
            delta: 1, //返回的页面数，如果 delta 大于现有页面数，则返回到首页,
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    onReset() {
      const { getFieldsValue, setFieldsValue } = $wuxForm();
      const fields = getFieldsValue();

      for (let item in fields) {
        if ({}.hasOwnProperty.call(fields, item)) {
          if (Array.isArray(fields[item])) {
            fields[item] = [];
            if (item === "slider") {
              fields[item] = [10, 80];
            }
          } else if (typeof fields[item] === "boolean") {
            fields[item] = false;
          } else if (typeof fields[item] === "number") {
            fields[item] = 0;
          } else {
            fields[item] = "";
          }
        }
      }

      setFieldsValue({
        ...fields,
      });
    },
  },
};
</script>

<style>
.create-set-submit-button {
  padding-bottom: 10px;
  text-align: center;
}
</style>
