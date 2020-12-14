<template>
  <div>
  <div class="userInfo">
    <wux-avatar v-if="isShow" class="userAvatar" :src="userInfo.avatarUrl" alt="Error Pic" size="large"/>
    <wux-button class="showUsrAvatar" v-else open-type="getUserInfo" @getuserinfo="getUserInfo">点击获取用户登录信息</wux-button>
    <p class="userName">{{userInfo.nickName}}</p>
    </div>
    <div class="btnNav">
    <wux-button class="monthView" type="dark" @click="swapCalendar">选择日期</wux-button>
    <wux-button class="addTaskSetBtn" type="dark">新建任务集合</wux-button>
    </div>
    <div class="blank"></div>
    <Calendar :events="events" v-if="isCalendarShow" @select="selectDay"/>
    <div class="taskSetListView">
    <van-collapse :value="activeNames" @change="onChange($event)" v-for="(item,index) in dataList" :key='index' accordion>
      <wux-button class="addTaskBtn" type="calm" size="small" :id='index' @click="addTask($event)">新建任务</wux-button>
      <van-collapse-item :title="item.title" :name="index" >
        {{item.desc}}
        <div class="taskListView">
        <swiper class="cont" @change="switchItem('switchItem',$event)" :current="currentTab" circular="true" skip-hidden-item-layout="true">
        <swiper-item  v-for="(task,i) in item.task" :key="i" @click="taskCardClick($event)" :id="i">
        <div class="card"><h1 class="taskName">{{task.name}}</h1><p class="taskDesc">{{task.task_desc}}</p></div>
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
var dataListTest1=[
        {
          title:'任务集1',
          desc:'任务1任务1任务1',
          task:[
            {
              name:"任务1.1",
              task_desc:"任务1.1的描述",
            },
            {
              name:"任务2.1",
              task_desc:"任务2.1的描述",
            },
            {
              name:"任务3.1",
              task_desc:"任务3.1的描述",
            }
          ]
        },
        {
          title:'任务集2',
          desc:'任务2222222',
          task:[
            {
              name:"任务1.2",
              task_desc:"任务1.2的描述",
            },
            {
              name:"任务2.2",
              task_desc:"任务2.2的描述",
            },
            {
              name:"任务3.2",
              task_desc:"任务3.2的描述",
            }
          ]
        },
        {
          title:'任务集3',
          desc:'任务33333333',
          task:[
            {
              name:"任务1.3",
              task_desc:"任务1.3的描述",
            },
            {
              name:"任务2.3",
              task_desc:"任务2.3的描述",
            },
            {
              name:"任务3.3",
              task_desc:"任务3.3的描述",
            }
          ]
        }
      ]
      var dataListTest2=[
        {
          title:'任务集1',
          desc:'任务1任务1任务1',
          task:[
            {
              name:"任务1.1",
              task_desc:"任务1.1的描述",
            },
            {
              name:"任务2.1",
              task_desc:"任务2.1的描述",
            },
            {
              name:"任务3.1",
              task_desc:"任务3.1的描述",
            }
          ]
        },
        {
          title:'任务集4',
          desc:'任务33333333',
          task:[
            {
              name:"任务1.4",
              task_desc:"任务1.4的描述",
            },
            {
              name:"任务2.4",
              task_desc:"任务2.4的描述",
            },
            {
              name:"任务3.4",
              task_desc:"任务3.4的描述",
            }
          ]
        }
      ]

/*用来组件传值，不知道能用来干啥先放着*/
props:{

}
export default {
  data() {
    return{
    value:[],
    userInfo:{},
    isShow:false,
    date:[],
    isCalendarShow:false,
    activeNames: [],
    dataList:dataListTest1,
    /*测试任务集列表随日期切换*/
    dataFlag:true,
    events:todayEvents,
    }
  },
  components:{
    Calendar
  },
  beforeMount(){
    this.handleGetUserInfo()
  },

  methods: {
    handleGetUserInfo(){
      wx.getUserInfo({
        success:(data)=>{
          console.log(data);
          this.userInfo=data.userInfo
          this.isShow=true
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
      if(this.dataFlag){
        this.dataList=dataListTest2;
      }
      else{
        this.dataList=dataListTest1;
      }
      this.dataFlag=!this.dataFlag;
    },

    /*新建任务*/
    addTask(event){
      console.log(this.dataList[event.currentTarget.id]);
    },

    onChange (event) {
    this.activeNames = event.mp.detail
    console.log(event.currentTarget)
    },
    switchItem: function (prompt,res) {
      //console.log(res.mp);
    },
    taskCardClick(event){
      console.log(event.currentTarget);
    }
  }
}
</script>

<style scope>
.userInfo{
  height:50px;
  display:flex;
  align-items: center;
  background:linear-gradient(to right, lightcyan , lightblue);
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
   background:linear-gradient(to right, lightcyan ,lightblue );
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
  background:rgba(63,63,71) !important;
}
.mpvue-calendar ._table{
  color:#fff !important;
}
.mpvue-calendar ._td .mc-text{
  color:#fff !important;
}
.calender-info>.div .mc-month{
  color:#fff !important;
}
.calendar-tools{
  color:#fff !important;
}

.monthView{
  float:left;
  margin-bottom:10px;
  margin-top:10px;
}

/*任务集合列表*/
.taskListSetView{
  height:auto;
}
.card{
  background:linear-gradient(to right, lightcyan ,lightblue );
  height:120px;
  border:2px solid;
  border-radius:25px;
}
.taskName{
  font-size:30px;
  margin-left:10px;
  color:purple;
}
.taskDesc{
  font-size:12px;
  margin:5px;
}
</style>