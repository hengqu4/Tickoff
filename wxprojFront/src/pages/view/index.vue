<template>
  <div>
    <movable-area>
      <movable-view direction="all" x="300" y="500">
        <van-button
          class="floatBtn"
          round
          type="info"
          size="large"
          @click="plusBtnClick"
          ><wux-icon class="floatBtnIcon" type="ios-add" size="30"
        /></van-button>
      </movable-view>
    </movable-area>
    <wux-popup
      :visible="visible"
      title="本日任务完成情况"
      :content="todayCount"
    >
      <view slot="footer" class="popup__button" @click="closecountToday"
        >OK</view
      >
    </wux-popup>
    <div class="btnNav">
      <wux-button
        class="monthView"
        type="calm"
        @click="swapCalendar"
        size="small"
        ><wux-icon type="ios-calendar" size="16"
      /></wux-button>
      <wux-button class="monthView" type="calm" @click="countToday" size="small"
        >结算本日</wux-button
      >
    </div>
    <Calendar v-if="isCalendarShow" @select="selectDay" />
    <div class="taskSetListView">
      <!-- 单人任务集合 -->
      <ul class="defaultSetCards">
        <li
          class="card_unfold"
          v-for="(task, i) in defaultTask"
          :key="i"
          :id="task.missionId" @click="toTaskDetail($event)"
        >
           <wux-wing-blank size="default">
            <wux-card :title="task.name"             >
              <view slot="body"
              :id="task.missionId"
              class="completeTaskBtn"
              @click="clickTodetail($event)"
              >
                {{ task.description }}
              </view>
              <view slot="footer">
                <wux-button
                  type="calm"
                  size="small"
                  :id="task.missionId"
                  @click="completeTask($event)"
                  class="completeTaskBtn"
                  :disabled="taskSetList[index].missions[i].done"
                >
                  <wux-icon type="ios-checkmark." size="16" />
                </wux-button>
              </view>
            </wux-card>
          </wux-wing-blank>
        </li>
      </ul>

      <ul
        class="cards"
        v-for="(item, index) in taskSetList"
        :key="index"
        :id="index"
      >
        <p class="taskSetDesc">{{ item.setName }}</p>
        <wux-button
          type="calm"
          @click="foldCards($event)"
          size="small"
          :id="index"
          ><wux-icon :type="taskSetListFoldIcon[index]" size="16"
        /></wux-button>
        <wux-button
          class="addTaskInSetBtn"
          type="calm"
          @click="plusTaskBtnClick"
          size="small"
          :id="item.id"
          ><wux-icon type="ios-add" size="16"
        /></wux-button>

        <li
          :class="{
            card_fold: taskSetListFold[index],
            card_unfold: !taskSetListFold[index],
          }"
          v-for="(task, i) in item.missions"
          :key="i"
          :id="task.missionId"
        >
          <wux-wing-blank size="default">
            <wux-card :title="task.name"             >
              <view slot="body"
              :id="task.missionId"
              class="completeTaskBtn"
              @click="clickTodetail($event)"
              >
                {{ task.description }}
              </view>
              <view slot="footer">
                <wux-button
                  type="calm"
                  size="small"
                  :id="task.missionId"
                  @click="completeTask($event)"
                  class="completeTaskBtn"
                  :disabled="taskSetList[index].missions[i].done"
                >
                  <wux-icon type="ios-checkmark." size="16" />
                </wux-button>
              </view>
            </wux-card>
          </wux-wing-blank>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import Calendar from "mpvue-calendar";
import store from "../../store";
import fly from "../../utils/wx-request";

/*用来组件传值，不知道能用来干啥先放着*/
props: {
}
export default {
  data() {
    return {
      visible: false,
      todayCount: "",
      value: [],
      isShow: false,
      userID: "",
      date: [],
      isCalendarShow: false,
      defaultTask: [],
      taskSetList: [],
      taskSetListFold: [] /*卡片堆叠样式*/,
      taskSetListFoldIcon: [],
      activeNames: [],
      albumDisabled: true,
      bindDisabled: false
    };
  },
  components: {
    Calendar,
  },
  onLoad:function(){

  },

  onShow:function() {
    this.userID = store.state.openId;
    console.log("this.userID", this.userID);
    var timestamp = Date.parse(new Date());
    var date = new Date(timestamp);
    //获取年份
    var Y = date.getFullYear();
    //获取月份
    var M =
      date.getMonth() + 1 < 10
        ? "0" + (date.getMonth() + 1)
        : date.getMonth() + 1;
    //获取当日日期
    var D = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    this.date[0] = Y;
    this.date[1] = M;
    this.date[2] = D;
    this.$fly
      .request({
        method: "get",
        url:
          "tickoff/api/indexMissions/" +
          this.userID +
          "/date/" +
          this.date[0] +
          "-" +
          this.date[1] +
          "-" +
          this.date[2],
      })
      .then((res) => {
        console.log(res);
        this.defaultTask = new Array();
        for (var i = 0; i < res.length; i++) {
          /*此处替换为默认任务集的id*/
          if (res[i].setName == "默认任务集") {
            this.defaultTask = res[i].missions;
            res.splice(i, 1);
            break;
          }
        }
        this.taskSetList = res;
        /*初始化卡片堆折叠信息*/
        var foldArray = Array(this.taskSetList.length).fill(true);
        this.taskSetListFold = foldArray;
        var foldArrayIcon = Array(this.taskSetList.length).fill(
          "ios-arrow-down"
        );
        this.taskSetListFoldIcon = foldArrayIcon;
      })
      .catch(function (error) {
        console.log(error);
      });
    console.log(this.taskSetList);
  },
  computed: {},
  methods: {
    yourFunction() {
      console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
    },
    getPromiseInfo:function(){
      wx.getUserInfo({
        success: (data) => {
          console.log("getUserInfo()::", data);
          store.commit("USERINFO_MUTATION", data.userInfo);
          this.getOpenidAndToken();
        },
        fail: () => {
          console.log("getUserInfo()失败");
        },
      })
    },
    getOpenidAndToken(){
      wx.login({
        success: function (r) {
          console.log("pages/loading::onLaunch()::success::return", r); //r包含code
          //通过code获得openid并存入store
          var code = r.code;
          var rawUrl = store.state.userInfo.avatarUrl;
          var newUrl = rawUrl.replace("/", "-");
          for (var i = 0; i < 100; i++) {
            newUrl = newUrl.replace("/", "-");
          }
          if (code) {
            //发送code到后台，分析openid
            fly.request({
                method: "post",
                url:
                  "login/regist/code/" +
                  code +
                  "/avatar/" +
                  newUrl +
                  "/nickname/" +
                  store.state.userInfo.nickName,
                header: {
                  "content-type": "application/json",
                },
              })
              .then((res) => {
                console.log("oL,res:", res);
                console.log("token:", res.token);
                store.commit("TOKEN_MUTATION", res.token);
                store.commit("OPENID_MUTATION", res.openid);
              })
              .catch(function (error) {
                console.log(error);
              });
          }        
        },
        fail: function (res) {
          console.log("login()失败");
        },
        complete: function (res) {},
      });
    },
    // randomNaviToView() {
    //   var randomTime = Math.random;
    //   wx.showLoading({
    //     title: "加载中！",
    //   });
    //   setTimeout(function () {
    //     wx.hideLoading();
    //   }, 2000);
    // },
    gotoChange(id) {
      console.log(id)
      wx.navigateTo({url: '/pages/taskDetail/main?tId='+id+"&uId="+store.state.openId})
    },
    clickTodetail(event) {
      wx.navigateTo({url: '/pages/taskDetail/main?tId='+event.currentTarget.id+"&uId="+store.state.openId})
    },
    /*切换日历*/
    swapCalendar() {
      if (this.isCalendarShow == true) {
        this.isCalendarShow = false;
      } else {
        this.isCalendarShow = true;
      }
    },

    /*浮动按钮点击事件*/

    plusBtnClick() {
      const url = "../create-task/main";
      wx.navigateTo({
        url: url,
        success: function (res) {
          console.log("跳转到页面成功");
        },
        fail: function () {
          console.log("跳转到页面失败");
        },
      });
    },

    toTaskDetail(event) {
      console.log("fuck");
      console.log(event.currentTarget.id);
    },

    /*结算本日*/
    countToday() {
      var timestamp = Date.parse(new Date());
      var date = new Date(timestamp);
      //获取年份
      var Y = date.getFullYear();
      //获取月份
      var M =
        date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1;
      //获取当日日期
      var D = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      this.visible = !this.visible;
      this.$fly
        .request({
          method: "get",
          url:
            "tickoff/api/doneMissions/" +
            this.userID +
            "/date/" +
            Y +
            "-" +
            M +
            "-" +
            D,
        })
        .then((res) => {
          console.log(res);
          this.todayCount = "今日您已完成" + res + "个任务";
        })
        .catch(function (error) {
          console.log(error);
          this.todayCount = "今日您尚未完成任务打卡";
        });
    },

    closecountToday() {
      this.visible = !this.visible;
    },

    /*任务集下增加任务按钮事件*/
    plusTaskBtnClick() {
      const url = "../create-task/main";
      wx.navigateTo({
        url: url,
        success: function (res) {
          console.log("跳转到页面成功");
        },
        fail: function () {
          console.log("跳转到页面失败");
        },
      });
    },

    /*选择当前日期并得到日期值*/
    selectDay(val) {
      this.date[0] = val[0];
      this.date[1] = val[1];
      this.date[2] = val[2];
      /*需要回调任务集列表生成函数*/
      this.$fly
        .request({
          method: "get",
          url:
            "tickoff/api/indexMissions/" +
            this.userID +
            "/date/" +
            this.date[0] +
            "-" +
            this.date[1] +
            "-" +
            this.date[2],
        })
        .then((res) => {
          console.log(res);
          this.defaultTask = new Array();
          for (var i = 0; i < res.length; i++) {
            /*此处替换为默认任务集的id*/
            if (res[i].setName == "默认任务集") {
              this.defaultTask = res[i].missions;
              res.splice(i, 1);
              break;
            }
          }
          this.taskSetList = res;
          /*初始化卡片堆折叠信息*/
          var foldArray = Array(this.taskSetList.length).fill(true);
          this.taskSetListFold = foldArray;
          var foldArrayIcon = Array(this.taskSetList.length).fill(
            "ios-arrow-down"
          );
          this.taskSetListFoldIcon = foldArrayIcon;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    /*处理任务卡展开效果*/
    foldCards(event) {
      console.log(event.currentTarget.id);
      this.taskSetListFold.splice(
        event.currentTarget.id,
        1,
        !this.taskSetListFold[event.currentTarget.id]
      );
      if (this.taskSetListFoldIcon[event.currentTarget.id] === "ios-arrow-up") {
        this.taskSetListFoldIcon.splice(
          event.currentTarget.id,
          1,
          "ios-arrow-down"
        );
      } else {
        this.taskSetListFoldIcon.splice(
          event.currentTarget.id,
          1,
          "ios-arrow-up"
        );
      }
    },
    completeTask(event) {
      var timestamp = Date.parse(new Date());
      var date = new Date(timestamp);
      //获取年份
      var Y = date.getFullYear();
      //获取月份
      var M =
        date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1;
      //获取当日日期
      var D = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      this.$fly
        .request({
          method: "put",
          url: "tickoff/api/tickoffMission",
          body: {
            missionId: event.currentTarget.id,
          },
        })
        .then((res) => {
          console.log(res);
        })
        .catch(function (error) {
          console.log(error);
        });
      this.$fly
        .request({
          method: "put",
          url: "tickoff/api/record",
          body: {
            open_id: this.userID,
            date: Y + "-" + M + "-" + D,
          },
        })
        .then((res) => {
          console.log(res);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scope>
page {
  background-color: rgb(245, 245, 245);
}

/*浮动按钮组件*/
movable-area {
  pointer-events: none;
  height: 100%;
  width: 100%;
  position: fixed;
  left: 0px;
  z-index: 100;
}
movable-view {
  pointer-events: auto;
  height: 50px;
  width: 50px;
  position: relative;
}
li {
  pointer-events: none;
}
wux-button {
  border-radius: 30%;
}

.userInfo {
  height: 80px;
  display: flex;
  align-items: center;
  background: rgb(135, 206, 250);
}
.userAvatar {
  height: auto;
  margin-left: 10px;
  box-shadow: 0 0 2px #fff;
  border-radius: 50%;
}
.userName {
  color: white;
  margin-left: 20px;
}
.btnNav {
  height: 45px;
  background: rgb(245, 245, 245);
  margin-bottom: 10px;
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

.popup__button {
  margin-left: 135px;
  color: green;
}
/*月视图样式*/
.mpvue-calendar {
  background: #fff !important;
}
.mpvue-calendar ._table {
  color: #000 !important;
}
.mpvue-calendar ._td .mc-text {
  color: #000 !important;
}
.calender-info > .div .mc-month {
  color: #000 !important;
}
.calendar-tools {
  color: #000 !important;
}
.monthView {
  float: left;
  margin-bottom: 10px;
  margin-left: 10px;
  margin-top: 10px;
}
/*任务集合列表*/
.taskSetListView {
  height: auto;
}
/*任务卡片堆*/
.cards {
  position: relative;
  padding: 20px;
  min-height: 200px;
  height: auto;
  width: 80%;
  margin-left: 5%;
  z-index: 1;
  border-radius: 10px;
}
.defaultSetCards {
  position: relative;
  padding: 5px;
  height: auto;
  margin-top: 10px;
  width: 80%;
  margin-left: 10%;
  z-index: 1;
}

.card_fold {
  height: 150px;
  margin-bottom: -75px;
  width: 300px;
  border-radius: 10px;
  background: 0.4s linear;
  z-index: 1;
}
.card_unfold {
  height: 150px;
  margin-bottom: 10px;
  width: 300px;
  border-radius: 10px;
  background: 0.4s linear;
  z-index: 1;
}

.addTaskInSetBtn {
  float: right;
}
.foldCard {
  margin-left: 10px;
}
.taskSetDesc {
  float: left;
}
.cardList {
  margin-top: 15px;
  margin-left: 5px;
}
.completeTaskBtn {
  pointer-events: auto;
  z-index: 200;
}
</style>
