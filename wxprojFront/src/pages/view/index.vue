<template>
  <div>
  	<movable-area>
  	  <movable-view direction="all"  x=300 y=50>
		    <van-button class="floatBtn" round type="info" size="large" @click="plusBtnClick"><wux-icon class="floatBtnIcon"  type="ios-add" size="30"/></van-button>
	    </movable-view>
    </movable-area>
  <div class="userInfo">
    <wux-avatar v-if="isShow" class="userAvatar" :src="userInfo.avatarUrl" alt="Error Pic" size="large"/>
    <wux-button class="showUsrAvatar" v-else open-type="getUserInfo" @getuserinfo="getUserInfo">点击获取用户登录信息</wux-button>
    <p class="userName">{{userInfo.nickName}}</p>
    </div>
    <div class="btnNav">
    <wux-button class="monthView" type="calm" @click="swapCalendar" size="small"><wux-icon type="ios-calendar" size="16"/></wux-button>
    </div>
    <Calendar v-if="isCalendarShow" @select="selectDay"/>
    <div class="taskSetListView">

<!--单人任务集合-->
    <ul class="defaultSetCards">
      <li class="card_unfold" v-for=" (task,i) in defaultTask" :key="i" :id="task.id">
        <h1>{{task.name}}</h1>  
        <p>{{task.description}}</p>
        <wux-button  type="calm" size="small" :id='task.id' @click="completeTask($event)"
        class="completeTaskBtn" :disabled="!defaultTask[i].done"><wux-icon type="ios-checkmark." size="16"/></wux-button>
      </li>
    </ul>

  <ul class="cards" v-for="(item,index) in taskSetList" :key='index' :id='index'>
  <p class="taskSetDesc">{{item.setName}}</p>
  <wux-button class="foldCard" type="calm" @click="foldCards($event)" size="small" :id='index'><wux-icon :type="taskSetListFoldIcon[index]" size="16"/></wux-button>
  <wux-button class="addTaskInSetBtn" type="calm" @click="plusTaskBtnClick" size="small" :id='item.id'><wux-icon type="ios-add" size="16"/></wux-button>
  <div class="cardList">
    <li :class="{card_fold:taskSetListFold[index],card_unfold:!taskSetListFold[index]}" v-for="(task,i) in item.missions" :key="i" :id="task.id">
      <h1>{{task.name}}</h1>  
      <p>{{task.description}}</p>
      <wux-button  type="calm" size="small" :id='task.id' @click="completeTask($event)"
        class="completeTaskBtn" :disabled="taskSetList[index].missions[i].done"><wux-icon type="ios-checkmark." size="16"/></wux-button>
    </li>
    </div>
  </ul>
    </div>
  </div>
</template>

<script>
import Calendar from 'mpvue-calendar'
/*用来组件传值，不知道能用来干啥先放着*/
props:{
}
export default {
  data() {
    return{
    value:[],
    userInfo:{},
    isShow:false,
    userID:'aaaas123456',
    date:[],
    isCalendarShow:false,
    defaultTask:[],
    taskSetList:[],
    taskSetListFold:[],/*卡片堆叠样式*/
    taskSetListFoldIcon:[],
    activeNames: [],
    }
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
    var timestamp = Date.parse(new Date());
    var date = new Date(timestamp);
    //获取年份  
    var Y =date.getFullYear();
    //获取月份  
    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1);
    //获取当日日期 
    var D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate(); 
    this.date[0]=Y
    this.date[1]=M
    this.date[2]=D
    this.$fly.request({
      method: 'get',
      url: 'http://localhost:8080/tickoff/api/indexMsetTest/'+this.userID+'/date/'+this.date[0]+'-'+this.date[1]+'-'+this.date[2],
    }).then(res => {
      console.log(res)
      for(var i=0;i<res.length;i++){
        /*此处替换为默认任务集的id*/
        if(res[i].id==0){
          this.defaultTask=res[i].task;
          res.splice(i,1)
          break;
        }
      }
      /*初始化卡片堆折叠信息*/
      var foldArray=Array(this.taskSetList.length).fill(true);
      this.taskSetListFold=foldArray;
      var foldArrayIcon=Array(this.taskSetList.length).fill("ios-arrow-down");
      this.taskSetListFoldIcon=foldArrayIcon;
    }).catch(function (error) {
        console.log(error);
    });
  },
  computed:{
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
    /*切换日历*/
    swapCalendar(){
      if(this.isCalendarShow==true){
        this.isCalendarShow=false;
      }
      else{
        this.isCalendarShow=true;
      }
    },

    /*浮动按钮点击事件*/
    plusBtnClick(){
      const url = '../create-task/main'
      wx.navigateTo({ 
        url: url,
        success: function(res){
          console.log('跳转到页面成功')
        },
        fail: function() {
        console.log('跳转到页面失败')
        },
      })
    },

    /*任务集下增加任务按钮事件*/
    plusTaskBtnClick(){
      const url = '../create-task/main'
      wx.navigateTo({ 
        url: url,
        success: function(res){
          console.log('跳转到页面成功')
        },
        fail: function() {
        console.log('跳转到页面失败')
        },
      })
    },

    /*选择当前日期并得到日期值*/
    selectDay(val){
      this.date[0]=val[0];
      this.date[1]=val[1];
      this.date[2]=val[2];
      /*需要回调任务集列表生成函数*/
      this.$fly.request({
      method: 'get',
      url: 'http://localhost:8080/tickoff/api/indexMsetTest/'+this.userID+'/date/'+this.date[0]+'-'+this.date[1]+'-'+this.date[2],
    }).then(res => {
      console.log(res)
      this.defaultTask=new Array();
      for(var i=0;i<res.length;i++){
        /*此处替换为默认任务集的id*/
        if(res[i].setName=="默认任务集"){
          this.defaultTask=res[i].missions;
          res.splice(i,1)
          break;
        }
      }
      this.taskSetList=res
      /*初始化卡片堆折叠信息*/
      var foldArray=Array(this.taskSetList.length).fill(true);
      this.taskSetListFold=foldArray;
      var foldArrayIcon=Array(this.taskSetList.length).fill("ios-arrow-down");
      this.taskSetListFoldIcon=foldArrayIcon;
    }).catch(function (error) {
        console.log(error);
    });
    },
    /*处理任务卡展开效果*/
    foldCards(event){
      console.log(event.currentTarget.id)
      this.taskSetListFold.splice(event.currentTarget.id,1,!this.taskSetListFold[event.currentTarget.id])
      if(this.taskSetListFoldIcon[event.currentTarget.id]==="ios-arrow-up"){
        this.taskSetListFoldIcon.splice(event.currentTarget.id, 1,"ios-arrow-down")
      }
      else{
        this.taskSetListFoldIcon.splice(event.currentTarget.id, 1,"ios-arrow-up")
      }
    },
    completeTask(event){
      console.log(typeof(this.userID));
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

/*浮动按钮组件*/
movable-area{
  pointer-events: none;
  height: 90%;
  width: 100%;
  position:fixed;
  left:0px;
  top:50px;
  z-index:100;
}
movable-view{
  pointer-events: auto;
  height: 50px; 
  width: 50px;
  position:relative;
}
li{
  pointer-events:none;
}
wux-button{
  border-radius:30%;
}

.userInfo{
  height:80px;
  display:flex;
  align-items: center;
  background:rgb(135,206,250);
}
.userAvatar{
  height:auto;
  margin-left:10px;
  box-shadow: 0 0 2px #fff;
  border-radius:50%;
}
.userName{
  color:white;
  margin-left:20px;
}
.btnNav{
  height:45px;
   pedding:10px;
   background:rgb(245,245,245);
   margin-bottom:10px;
}
.floatBtn{
  position: relative;
}
.floatBtnIcon{
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  margin: auto;
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
  margin-left:10px;
  margin-top:10px;
}
/*任务集合列表*/
.taskSetListView{
  height:auto;
}
/*任务卡片堆*/
.cards {
  position: relative;  
  padding: 20px;
  min-height:300px;
  height:auto;
  margin-top:10px;
  margin-botton:10px;
  width:80%;
  margin-left:5%;
  z-index:1;
  border-radius:10px;
}
.defaultSetCards{
  position: relative;  
  padding: 5px;
  min-height:300px;
  height:auto;
  margin-top:10px;
  margin-botton:10px;
  width:80%;
  margin-left:10%;
  z-index:1
}

.card_fold {
  height:80px;
  margin-bottom:-75px;
  width:200px;
  background: #fff;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 0 4px #000;
  transform: translateY(0) translateX(25px) scale(1);
  transform-origin: 0 0;
  transition: transform 0.6s cubic-bezier(.8,.2,.1,0.8) 0.1s,
  background 0.4s linear;
  cursor: pointer;
  user-select: none;
  z-index:5;
}
.card_unfold {
  height:80px;
  margin-bottom:10px;
  width:200px;
  background: #fff;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 0 4px #000;
  transform: translateY(0) translateX(25px) scale(1);
  transform-origin: 0 0;
  transition: transform 0.6s cubic-bezier(.8,.2,.1,0.8) 0.1s,
  background 0.4s linear;
  cursor: pointer;
  user-select: none;
  z-index:5;
}

.addTaskInSetBtn{
  float:right;
}
.foldCard{
  margin-left:10px;
}
.taskSetDesc{
  float:left;
}
.cardList{
  margin-top:15px;
  margin-left:5px;
}
.completeTaskBtn{
  float:right;
  pointer-events:auto;
  z-index:10;
}
</style>