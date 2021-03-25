<template>
  <div class='wrapper'>
    <wux-form id="wux-form" @change="onChange">
      <wux-cell-group prefixCls="cell-group" title="标题">
        <wux-cell hover-class="none">
          <wux-field name="title" initialValue="请输入任务集标题">
            <wux-textarea rows="1" />
          </wux-field>
        </wux-cell>
      </wux-cell-group>

      <wux-cell-group prefixCls="cell-group" title="描述">
        <wux-cell hover-class="none">
          <wux-field name="description" initialValue="请输入任务集描述">
            <wux-textarea rows="3" />
          </wux-field>
        </wux-cell>
      </wux-cell-group>
      <div class="buttonObject">
        <wux-button block outline type="positive" @click="onReset">清空</wux-button>
        <wux-button block outline type="positive" @click="onSubmit">创建</wux-button>
      </div>
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
      // console.log("onChange \n", changedValues, allValues);
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
page {
  background-color: rgb(245, 245, 245);
}
</style>

<style lang="less" scoped>
@import "../../../static/wux/styles/mixins/index.less";
@import "../../../static/wux/styles/themes/index.less";
// @import "../../style/group.less";

#wux-form .title-cell-group{
  /deep/ &__bd {
      font-size: 25px !important;
      // color: rgb(111, 145, 209) !important;
      text-align: center;
      font-family: "KaiTi";
      margin-top: 30px;
  }
}

#wux-form .cell-group {
  /deep/ &__hd {
      text-align: center;
      font-size: 20px !important;
      color: rgb(111, 145, 209) !important;
      margin-top: 30px;
  }

  /deep/  &__bd {
    border:5px solid rgb(10, 196, 253) !important;
    border-radius:10px;;
    background-color:#fff;
    margin:0 25px;
  }
}
.wrapper{
  display: flex;
  flex-direction: column;
  min-height: 100%;
}

.buttonObject {
  position:absolute;
  bottom:20px;
  left: 50%;
  transform:translate(-50%,0);
  margin: 0 auto;
  width: 70%;
  padding: 20px;
  // padding: 5rpx;
}
</style>
