<template>
  <div class="map">
    <view class="userinfo">
      <wux-avatar
        class="userAvatar"
        :src="userInfo.avatar_url"
        alt="登陆失败"
        size="large"
        shape="square"
        scale="true"
      />
      <wux-white-space size="small" />
      <p class="userName">微信名:{{ userInfo.nickname }}</p>
    </view>
    <div class="title">{{ this.activityNum }} updates in the last year</div>
    <div v-if="scene">
      <div v-if="!haveLiked">
        <wux-icon @click="makelike" type="ios-heart-empty"/>
      </div>
      <div v-if="haveLiked">
        <wux-icon type="ios-heart" />
      </div>
    </div>
    <div class="map-inner clearfix">
      <div class="block-box">
        <div class="week">
          <div class="wday">Tue</div>
          <div class="wday">Thu</div>
          <div class="wday">Sat</div>
        </div>
        <div class="block">
          <!-- 此处test1换成activity.data[i].mission -->
          <div
            class="item"
            ref="blockItem"
            v-for="i in dayNum"
            :key="i"
            v-bind:class="{
              lvl1: 5 > activity[i] && activity[i] >= 1,
              lvl2: 10 > activity[i] && activity[i] >= 5,
              lvl3: 15 > activity[i] && activity[i] >= 10,
              lvl4: activity[i] && activity[i] >= 15,
            }"
            @click="calDate(i)"
          >
            <!-- <div class="month" v-if="calTime(i).getDay() === 1 && calTime(i).getDate() >=12 && calTime(i).getDate() <= 18">{{calMonth(i)}}</div> -->
            <!-- {{ i }} -->
          </div>
        </div>
      </div>
      <div class="legend">
        <div class="less">Less</div>
        <div class="item-box">
          <div class="item"></div>
          <div class="item"></div>
          <div class="item"></div>
          <div class="item"></div>
        </div>
        <div class="more">More</div>
      </div>
      <!-- <div class="allLog"><a href="">show all contributions</a></div> -->
    </div>
    <wux-button @click="callWxMSG()">ssss</wux-button>
  </div>
</template>
<script>
import store from "../../store";
import { formatTimeHash, monthStr } from "../../utils/index";
export default {
  name: "activityMap",
  mounted() {
    this.getActivity();
    //console.log("mounted", this.$refs.blockItem);
    console.log("mounted", this.dayNum);
    //console.log(this.calMonth(1));
    //console.log("fucked up");
  },
  created() {
    console.log("mounted", this.dayNum);
    this.calDayNum();
  },
  data() {
    return {
      haveLiked: false,
      userInfo: {},
      scene: false,
      dayNum: 100,
      activity: [
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
      ],
      missionNum: 0,
      doneNum: 0,
    };
  },
  onLoad: function (options) {
    console.log("onload");
    this.userId = options.uId;
    if (store.state.scene == 1007) {
      this.userId = options.uId;
    } else {
      this.userId = store.state.openId;
    }
  },
  onShow() {
    this.handleGetUserInfo();
  },
  beforeMount() {
    if (store.state.scene == 1007) {
      this.scene = true;
    } else {
      this.scene = false;
    }
    this.$fly
      .request({
        method: "get",
        url: "tickoff/api/user/UserID/" + this.userId,
      })
      .then((res) => {
        console.log(res);
        this.userInfo=res.data;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  onShareAppMessage: function () {
    return {
      title: "来看看我的历史记录",
      path: "/pages/history/main?uid=" + store.state.openId,
    };
  },
  methods: {
    makelike() {
      console.log("clike like");
      this.haveLiked = true;
      this.$fly
        .request({
          method: "put", // post/get 请求方式
          url: "tickoff/api/user/like/openid/" + this.userId,
        })
        .then((res) => {
          console.log("成功点赞了");
        })
        .catch(function (error) {
          console.log(error);
        });
<<<<<<< HEAD

        
  },
  onShareAppMessage: function() {
    return{
      title:'来看看我的历史记录',
      path:'/pages/history/main?uid='+store.state.openId
    }
  },
  methods: {
    makelike(){
      console.log("clike like")
      this.haveLiked=true
       this.$fly.request({
      method: 'put', // post/get 请求方式
      url: 'tickoff/api/user/like/openid/'+this.userId,
    }).then(res => {
      console.log("成功点赞了")
    }).catch(function (error) {
        console.log(error);
    });

=======
>>>>>>> 4e5ed0594fa23d789cbfc0aae34c0abc55523223
    },
    handleGetUserInfo() {
      this.userInfo = store.state.userInfo;
    },
    calDayNum() {
      let day = new Date().getDay();
      this.dayNum = day + 133;
      console.log("dayNumUpdateNow:", this.dayNum);
    },
    callWxMSG() {
      wx.requestSubscribeMessage({
        tmplIds: ["IW9tcQJ8L1-9W6CqQysyh0PgruxfxjH-5HPHOuBdM1M"],
        success(res) {
          console.log("res of select", res);
        },
      });
    },
    getActivity() {
      this.$fly
        .request({
          method: "get",
          url: "tickoff/api/record/HistoryRecord/UserID/" + this.userId,
        })
        .then((res) => {
          console.log(res);
          console.log(res.data);
          for (var i = 0; i < res.data.length; i++) {
            this.activity[i] = res.data[i].done;
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    //向前数dayNum-index天的日期 格式2020-12-18
    calDate(index) {
      let indexTime =
        new Date().getTime() - (this.dayNum - index) * 24 * 60 * 60 * 1000;
      let indexDate = new Date(indexTime);
      let dateStr = formatTimeHash(indexDate);
      console.log("caldate1", dateStr, this.$refs.blockItem);
      this.$nextTick(() => {
        console.log("caldate2", dateStr, this.$refs.blockItem);
      });
      return dateStr;
    },
    calTime(index) {
      let indexTime =
        new Date().getTime() - (this.dayNum - index) * 24 * 60 * 60 * 1000;
      let indexDate = new Date(indexTime);

      return indexDate;
    },
    calMonth(index) {
      const monthNum = this.calTime(index).getMonth() + 1;
      return monthStr(monthNum);
    },
    colorful(data) {
      for (let i = 0; i < this.$refs.item.length; i++) {
        const date = this.calDate(i + 1);
        for (let j = 0; j < data.length; j++) {
          if (data[j].date === date) {
            const logLen = data[j].logLen;
            const elem = this.$refs.item[i];
            if (logLen >= 1 && logLen < 5) {
              elem.style.backgroundColor = "#c6e48b";
            } else if (logLen >= 5 && logLen < 9) {
              elem.style.backgroundColor = "#7bc96f";
            } else if (logLen >= 9 && logLen < 13) {
              elem.style.backgroundColor = "#239a3b";
            } else if (logLen >= 13) {
              elem.style.backgroundColor = "#196127";
            }
          }
        }
      }
    },
  },
  computed: {},
};
</script>
<style lang="less" scoped>
.map {
  margin-bottom: 30px;
  margin-top: 20px;

  .title {
    font-size: 16px;
    margin: 0;
    color: #333;
  }
  .map-inner {
    max-width: 1012px;
    // height: 170px;
    border: 1px solid rgba(27, 31, 35, 0.15);
    border-radius: 3px;
    margin: 0 auto;
    margin-top: 10px;
    .block-box {
      margin-top: 25px;
      margin: 0 auto;
      margin-top: 40px;
      margin-bottom: 10px;
      width: 375px;
      display: flex;
      .week {
        display: flex;
        flex-direction: column;
        margin-left: 5px;
        .wday {
          font-size: 9px;
          color: #767676;
          line-height: 17px;
          margin-top: 17px;
        }
      }
      .block {
        display: flex;
        flex-direction: column;
        height: 119px;
        width: 340px;
        // margin: 0 auto;
        margin-left: 5px;
        flex-wrap: wrap;
        .item {
          height: 17px;
          width: 17px;
          box-sizing: border-box;
          background-color: #ebedf0;
          border-right: 2px solid #fff;
          border-bottom: 2px solid #fff;
          border-radius: 3px;
          cursor: pointer;
          .month {
            position: relative;
            top: -25px;
            height: 14px;
            line-height: 14px;
            font-size: 10px;
            color: #767676;
          }
        }
        .lvl4 {
          background-color: #196127;
        }
        .lvl3 {
          background-color: #239a3b;
        }
        .lvl2 {
          background-color: #7bc96f;
        }
        .lvl1 {
          background-color: #c6e48b;
        }
      }
    }
    .legend {
      display: flex;
      // position: absolute;
      float: right;
      margin-top: 15px;
      margin-right: 30px;
      .less,
      .more {
        height: 15px;
        line-height: 12px;
        font-size: 12px;
        color: #767676;
      }
      .item-box {
        display: flex;
        margin: 0 10px;
        .item {
          width: 15px;
          height: 15px;
          flex-direction: row;
          background: green;
          margin: 0 2px;
        }
        .item:nth-child(1) {
          background: #c6e48b;
        }
        .item:nth-child(2) {
          background: #7bc96f;
        }
        .item:nth-child(3) {
          background: #239a3b;
        }
        .item:nth-child(4) {
          background: #196127;
        }
      }
    }
    .allLog {
      font-size: 14px;
      line-height: 12px;
      margin-left: 70px;
      a {
        text-decoration: none;
        color: #0366d6;
      }
    }
  }
}
</style>
