<template>
  <div>
  <div class="userInfo">
    <wux-avatar v-if="isShow" class="userAvatar" :src="userInfo.avatarUrl" alt="Error Pic" size="large"/>
    <wux-button class="showUsrAvatar" v-else open-type="getUserInfo" @getuserinfo="getUserInfo">点击获取用户登录信息</wux-button>
    <p class="userName">{{userInfo.nickName}}</p>
    </div>
    <div class="btnNav">
    <wux-button class="monthView" type="calm" @click="swapCalendar">选择日期</wux-button>
    <wux-button class="addTaskSetBtn" type="calm">新建任务集合</wux-button>
    </div>
    <div class="blank"></div>
    <Calendar :events="events" v-if="isCalendarShow" @select="selectDay"/>
    <div class="taskSetListView">
    <van-collapse class="taskSetList" :value="activeNames" @change="onChange($event)" v-for="(item,index) in taskSetList" :key='index'>
      <wux-button  type="calm" size="small" :id='item.id' @click="viewTaskSet($event)">查看任务集</wux-button>
      <wux-button class="addTaskBtn" type="calm" size="small" :id='item.id' @click="addTask($event)">新建任务</wux-button>
      <van-collapse-item :title="item.title" :name="index" >
        <p class="taskListDesc">{{item.desc}}</p>
        <div class="taskListView">
        <swiper class="cont" @change="switchItem('switchItem',$event)" :current="currentTab" circular="true" skip-hidden-item-layout="true">
        <swiper-item  v-for="(task,i) in item.task" :key="i" :id="i">
        <div class="card"><navigator class="taskName" url="../../pages/view/main">{{task.name}}</navigator><p class="taskDesc">{{task.task_desc}}</p>
        <wux-button  type="calm" size="small" :id='task.id' @click="completeTask($event)"
        class="completeTaskBtn">任务打卡</wux-button>
        </div>
        </swiper-item>
      </swiper>
        </div>
      </van-collapse-item>
    </van-collapse>
    </div>
  </div>
</template>

<script>
import Calendar from 'mpvue-calendar'
/*此处用来写从后端get数据*/
/*下为测试数据*/
var todayEvents={'2020-12-9': '4/10','2020-12-10': '2/10'}
var dataList=[]
/*用来组件传值，不知道能用来干啥先放着*/
props:{
}
export default {
  data() {
    return{
    value:[],
    userInfo:{},
    isShow:false,
    userID:1,
    date:[],
    isCalendarShow:false,
    taskSetList:[],
    activeNames: [],
    /*测试任务集列表随日期切换*/
    events:todayEvents,
    }
    console.log(this.taskSetList)
  },
  components:{
    Calendar
  },
  beforeMount(){
    this.handleGetUserInfo()
    wx.login({
      success: function(res) {
      console.log(res);//这里的返回值里面便包含code
      },
    fail: function(res) {
      console.log('登陆失败');
    },
      complete: function(res) {},
    })
  },
  mounted(){
    this.$fly.request({
      method: 'get',
      url: 'http://mock-api.com/5g7AeqKe.mock/taskData?UserID='+this.userID+'&date=1',
    }).then(res => {
      console.log(res)
      this.taskSetList=res
    }).catch(function (error) {
        console.log(error);
    });
  },
  methods: {
    handleGetUserInfo(){
      wx.getUserInfo({
        success:(data)=>{
          console.log(data);
          this.userInfo=data.userInfo;
          this.isShow=true;
        },
        fail:()=>{
          console.log("获取用户登录信息失败");
        }
      })
    },
    getUserInfo(data){
      console.log(data);
      if(data.mp.detail.rawData){
        this.handleGetUserInfo();
      }
    },
    swapCalendar(){
      if(this.isCalendarShow==true){
        this.isCalendarShow=false;
      }
      else{
        this.isCalendarShow=true;
      }
    },
    /*选择当前日期并得到日期值*/
    selectDay(val){
      this.date[0]=val[0];
      this.date[1]=val[1];
      this.date[2]=val[2];
      console.log(this.date);
      /*需要回调任务集列表生成函数*/
      this.$fly.request({
      method: 'get',
      url: 'http://mock-api.com/5g7AeqKe.mock/taskData?UserID='+this.userID+'&date=1',
    }).then(res => {
      console.log(res)
      this.taskSetList=res
    }).catch(function (error) {
        console.log(error);
    });
    },
    /*新建任务*/
    addTask(event){
      console.log(event.currentTarget.id);
    },
    viewTaskSet(event){
      console.log(event.currentTarget.id);
    },
    onChange (event) {
    this.activeNames = event.mp.detail
    console.log(event.currentTarget)
    },
    switchItem: function (prompt,res) {
      //console.log(res.mp);
    },
    completeTask(event){
      console.log(event.currentTarget.id);
      this.$fly.request({
        method:"post",
        url:"http://mock-api.com/5g7AeqKe.mock/completeTask",
        body:{
          "userID":this.userID,
          "taskID":event.currentTarget.id
        }
      }).then(res =>{
        console.log(res)
      }).catch(function (error) {
        console.log(error);
    });
    }
  }
}
</script>

<style scope>
page{
  background-color:rgb(245,245,245);
}
.userInfo{
  height:50px;
  display:flex;
  align-items: center;
  background:rgb(245,245,245);
}
.userAvatar{
  height:auto;
  margin-left:10px;
}
.userName{
  color:orange;
  margin-left:20px;
}
.btnNav{
   height:70px;
   pedding:10px;
   background:rgb(245,245,245);
   margin-bottom:10px;
}
.blank{
  clear:both;
}
.addTaskSetBtn{
  float:right;
  margin-bottom:10px;
  margin-top:10px;
}
/*月视图样式*/
.mpvue-calendar{
  background:#fff !important;
}
.mpvue-calendar ._table{
  color:#000 !important;
}
.mpvue-calendar ._td .mc-text{
  color:#000 !important;
}
.calender-info>.div .mc-month{
  color:#000 !important;
}
.calendar-tools{
  color:#000 !important;
}
.monthView{
  float:left;
  margin-bottom:10px;
  margin-top:10px;
}
/*任务集合列表*/
.taskSetList{
  background:rgb(245,245,245)
}
.taskListSetView{
  height:auto;
}
.card{
  background:rgb(245,245,245);;
  height:120px;
  border:1px solid;
  border-radius:25px;
}
.taskName{
  font-size:30px;
  margin-left:10px;
  color:black;
}
.taskListDesc{
  font-size:15px;
  margin-left:10px;
  margin-bottom:10px;
}
.taskDesc{
  font-size:12px;
  margin-left:10px;
}
.addTaskBtn{
  margin-left:10px;
}
.completeTaskBtn{
  float:right;
  margin-right:10px;
}
</style>