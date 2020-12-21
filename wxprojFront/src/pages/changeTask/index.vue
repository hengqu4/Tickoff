<template>
  <div>
    <toast message="请选择任务集" :visible.sync="visible1"></toast>
    <toast message="结束时间不能早于开始时间" :visible.sync="visible2"></toast>
    <toast message="推迟时间不能早于结束时间" :visible.sync="visible3"></toast>

    <wux-form id="wux-form" @change="onChange">
        <wux-cell-group title="任务标题">
          <wux-cell hover-class="none">
            <wux-field name="title" :initialValue="title">
              <wux-textarea rows="1" />
            </wux-field>
          </wux-cell>
        </wux-cell-group>

        <wux-cell-group title="任务描述">
          <wux-cell hover-class="none">
            <wux-field name="description" :initialValue="description">
              <wux-textarea rows="2" />
            </wux-field>
          </wux-cell>
        </wux-cell-group>


        <wux-field name="taskSet" :initialValue="listNumber">
          <wux-radio-group title="选择任务集">
            <div v-for="(item,index) in list" :key="index">
              <wux-radio :title="item.name" :value="index" />
            </div>
          </wux-radio-group>
        </wux-field>
 
        <wux-cell-group title="开始时间">
          <wux-cell hover-class="none">
            <wux-field name="startTime" :initValue="startDatePicker">
              <wux-date-picker 
                @confirm="onConfirmStartDatePicker($event)" 
                @value="startDate" 
                minDate="2020-01-01 00:00"
                >
                <wux-cell is-link @extra="startDatePicker">
                  <p>{{startDatePicker}}</p>
                </wux-cell>
              </wux-date-picker>
            </wux-field>
          </wux-cell>
        </wux-cell-group>
        
        <wux-cell-group title="结束时间">
          <wux-cell hover-class="none">
            <wux-field name="endTime" :initValue="endDatePicker">
              <wux-date-picker 
                @confirm="onConfirmEndDatePicker($event)" 
                @value="endDate"
                minDate="2020-01-01 00:00"
                >
                <wux-cell is-link @extra="endDatePicker">
                  {{endDatePicker}}
                </wux-cell>
              </wux-date-picker>
            </wux-field>
          </wux-cell>
        </wux-cell-group>


      <div v-if="isAdvanced">

      <wux-sticky scrollTop="scrollTop">
       <wux-sticky-item>

        <view slot="title">高级选项</view>

            
          <view slot="content">


        <wux-cell-group title="是否需要结算">
          <wux-cell>
              <wux-field name="require" :initialValue="isRequire" valuePropName="inputChecked" slot="footer">
                <wux-switch  @change="onRequireChange"/>
              </wux-field>
          </wux-cell>
        </wux-cell-group>

        <wux-cell-group title="工作量">
          <wux-cell hover-class="none">
            <wux-field name="workLoad" :initialValue="workLoad">
              <wux-slider showValue step="1" min="1" max="10" />
            </wux-field>
          </wux-cell>
        </wux-cell-group>


        <wux-cell-group title="是否循环">
          <wux-field name="Loop" :initialValue="loopCode">
            <wux-radio-group>
              <div v-for="(item,index) in isLoop" :key="index">
                <wux-radio :title="item" :value="index" />
              </div>
            </wux-radio-group>
          </wux-field>
        </wux-cell-group>

        
        <wux-cell-group title="是否提醒">
          <wux-cell>
              <wux-field name="needNotice" :initialValue="isNeedNotice" valuePropName="inputChecked" slot="footer">
                <wux-switch  @change="onNoticeChange"/>
              </wux-field>
          </wux-cell>
        </wux-cell-group>
        <div v-if="isNeedNotice">
          <wux-cell-group title="提醒时间">
          <wux-cell hover-class="none">
            <wux-field name="noticeTime" :initValue="noticeDatePicker">
                <wux-date-picker 
                  mode="time" 
                  @confirm="onConfirmNoticeDatePicker($event)"
                  @value="noticeDate" 
                  data-mode="time">
                  <wux-cell is-link @extra="noticeDatePicker">
                      {{noticeDatePicker}}
                  </wux-cell>
              </wux-date-picker>
              </wux-field>
          </wux-cell>
        </wux-cell-group>
        </div>


        <wux-cell-group title="是否允许拖延">
          <wux-cell>
              <wux-field name="delay" :initialValue="isDelay" valuePropName="inputChecked" slot="footer">
                <wux-switch  @change="onDelayChange"/>
              </wux-field>
          </wux-cell>
        </wux-cell-group>
        <div v-if="isDelay">
          <wux-cell-group title="最晚延后日期">
          <wux-cell hover-class="none">
            <wux-field name="delayTime" :initValue="delayDatePicker">
              <wux-date-picker 
                @confirm="onConfirmDelayDatePicker($event)" 
                @value="delayDate"
                minDate="2020-01-01 00:00"
                >
                <wux-cell is-link @extra="delayDatePicker">
                  {{delayDatePicker}}
                </wux-cell>
              </wux-date-picker>
            </wux-field>
          </wux-cell>
        </wux-cell-group>
        </div>
          </view>
       </wux-sticky-item>
      </wux-sticky>



      </div>
      <view class="btn-area">


        <div class="buttonObject"> 
          <wux-button block outline type="dark" @click="onSubmit($event)">创建</wux-button>
        </div>
        <div class="buttonObject"> 
          <wux-button block outline type="dark" v-if="!isAdvanced" @click="onAdvancedOptions($event)">显示高级选项</wux-button>
        </div>
        <div class="buttonObject"> 
          <wux-button block outline type="dark" v-if="isAdvanced" @click="onAdvancedOptions($event)">恢复默认选项</wux-button>
        </div>
        <!-- <button @click="onReset($event)">重设属性</button> -->
      </view>
    </wux-form>


  </div>
</template>

<script>
import { $wuxForm, $wuxToast } from "../../../static/wux/index";
import miment from 'miment';
import toast from 'mpvue-toast'


export default {
  data() {
    return {

           scrollTop: 0,

     userId:'',      
     taskId:'',

     mset_id:'',
    
     visible1: false,
     visible2: false,
     visible3: false,
     done:'',

     title:'',
     description:'',

      startDate: [],
      startDatePicker: "",

      endDate: [],
      endDatePicker: "",

      delayDate:[],
      delayDatePicker:"",

      isNeedNotice:false,
      noticeDate:[],
      noticeDatePicker:"",

      isAdvanced: false,

      isLoading: false,
      radio: '',
      isLoop:["每日循环","每周循环","每月循环"],
      list:[],
      listNumber:"0",
      loopCode:"-1",
      isDelay:false,
      isRequire:true,
      workLoad:[1],
    };
  },
  components: {
    toast
  },

  onLoad:function(options) {
    console.log(options)
    this.taskId=options.tId
    this.userId=options.uId
  },
  created(){

  },
  beforeMount() {
    // this.userId='aaaas123456'
    // this.taskId='1fdaf32b-dbe6-4d6d-8d1c-d0e3f96e99b5'
    
    this.$fly.request({
      method: 'get', // post/get 请求方式
      url: 'tickoff/api/mission_set/openid/'+this.userId,
    }).then(res => {
      console.log(res.data)
      this.list=res.data
    }).catch(function (error) {
        console.log(error);
    });

    this.$fly.request({
      method: 'get', // post/get 请求方式
      url: 'tickoff/api/missions/'+this.taskId,
    }).then(ress => {
      console.log("changeTask 的获取")
      console.log(ress)
      var res=ress.data
      this.title=res.name
      this.description=res.description
      this.routine=res.routine
      this.mset_id=res.mset_id

      this.isDelay=res.delay
      this.workLoad=[res.workLoad]
      this.isRequire=res.requireCheck
      this.isNeedNotice=res.needNotice
      this.done=res.done

      var temp=res.startDate.split(':')
      temp=temp[0]+":"+temp[1]
      this.startDatePicker=temp

      var temp=res.endDate.split(':')
      temp=temp[0]+":"+temp[1]
      this.endDatePicker=temp

      var temp=res.noticeTime.split(':')
      temp=temp[0]+":"+temp[1]
      this.noticeDatePicker=temp

      var temp=res.delayDate.split(':')
      temp=temp[0]+":"+temp[1]
      this.delayDatePicker=temp

      if(this.routine==0){
        this.loopCode=-1
      }
      else if(this.routine==1){
        this.loopCode=0
      }
      else if(this.routine==100){
        this.loopCode=2
      }
      else{
        this.loopCode=1
      }


      if(this.isNeedNotice){
        this.noticeText="提前"+this.noticeDatePicker
      }
      else{
        this.noticeText="不需要提醒"
      }

      if(this.isDelay){
        this.delayText=this.delayDate
      }
      else{
        this.delayText="不需要延期"
      }
      
    }).catch(function (error) {
        console.log(error);
    });

  },
  mounted() {
      for(var i=0;i<this.list.length;i++){
        if(this.mset_id==this.list[i].mset_id){
          this.listNumber=i+1
          break
        }
      }
  },

  computed: {},

  methods: {
    onPageScroll(e){
        console.log('onPageScroll', e.scrollTop)
        this.setData({
            scrollTop: e.scrollTop,
        })
    },
      
    onChange(event) {
      const { form, changedValues, allValues } = event.mp.detail;
      console.log("onChange \n", changedValues, allValues);
    },

    onSubmit(event) {
      //事前处理
      const { getFieldsValue, getFieldValue, setFieldsValue } = $wuxForm()
      const value = getFieldsValue()
      console.log(value)
      var subData={}

      var rou
      if(this.loopCode==0){
        rou=1
      }
      else if(this.loopCode==2){
        rou=100
      }
      else if(this.loopCode==1){
        var temp=this.startDatePicker
        temp=temp.split(" ")[0]
        temp=temp.split("-")
        var da=new Date(temp[0],temp[1]-1,temp[2])
        rou=da.getDay()*10;
      }
      else{
        rou=0
      }
      var msid=this.list[value.taskSet].mset_id
//赋值
      subData.missionId=this.taskId
      subData.mset_id=msid
      subData.name=value.title
      subData.description=value.description
      subData.createDate=miment().add(1,'h').format('YYYY-MM-DD HH:mm:ss')
      subData.startDate=this.startDatePicker+':00'
      subData.endDate=this.endDatePicker+':00'
      subData.routine=rou
      subData.delay=this.isDelay
      subData.delayDate=this.delayDatePicker+':00'
      subData.workLoad=this.workLoad[0]
      subData.requireCheck=this.isRequire
      subData.needNotice=this.isNeedNotice
      subData.noticeTime=this.noticeDatePicker+':00'
      subData.done=this.done


      console.log(subData)

      if(subData.listNumber==-1){
        this.setVisible1()
        return
      }
      else if(!miment(subData.startDate).isBefore(subData.endDate)){
        this.setVisible2()
        return 
      }
      else if(subData.isDelay &&miment(subData.delayDate).isBefore(subData.endDate)){
        this.setVisible3()
        return 
      }
      else{
        this.$fly.request({
        method:"put", //post/get 请求方式
        url:"tickoff/api/missions",
        body:{
          "m_id": subData.missionId,
          "mset_id": subData.mset_id,
          "name": subData.name,
          "description": subData.description,
          "createDate": subData.createDate,
          "startDate": subData.startDate,
          "endDate":subData.endDate,
          "routine": subData.routine,
          "delay": subData.delay,
          "workLoad": subData.workLoad,
          "requireCheck": subData.requireCheck,
          "needNotice": subData.needNotice,
          "noticeTime": subData.noticeTime,
          "done": subData.done,
          "delayDate": subData.delayDate
        }
      }).then(res =>{
          this.gotoDetail()
      })
    }



    },
    showToastErr() {
      $wuxToast().show({
        type: 'forbidden',
        duration: 1500,
        color: '#fff',
        text: '禁止操作',
        success: () => console.log('禁止操作')
      })
    },

    onConfirmStartDatePicker(event) {
      console.log(event.mp.detail);
      this.startDate = event.mp.detail.value;
      this.startDatePicker = event.mp.detail.label;
    },

    onConfirmEndDatePicker(event) {
      console.log(event.mp.detail.value);
      console.log(event.mp.detail.label);
      this.endDate = event.mp.detail.value;
      this.endDatePicker = event.mp.detail.label;
    },
    onConfirmDelayDatePicker(event) {
      console.log(event.mp.detail.value);
      console.log(event.mp.detail.label);
      this.delayDate = event.mp.detail.value;
      this.delayDatePicker = event.mp.detail.label;
    },
    onConfirmNoticeDatePicker(event) {
      console.log(event.mp.detail.value);
      console.log(event.mp.detail.label);
      this.noticeDate = event.mp.detail.value;
      this.noticeDatePicker = event.mp.detail.label;
    },
    onRadioChange(e) {
        this.setData({ radio: e.detail.value })
    },
     onDelayChange(e) {
       this.isDelay=!this.isDelay
    },
    onRequireChange(e){
      this.isRequire=!this.isRequire
    },
    onNoticeChange(e){
      this.isNeedNotice=!this.isNeedNotice
    },

    onAdvancedOptions() {
      this.isAdvanced = !this.isAdvanced;
    },
    newTaskSet(){
      const url = '../create-set/main'
      wx.navigateTo({ url })    
    },
    setVisible1() {
      this.visible1 = !this.visible1
    },
    setVisible2() {
      this.visible2 = !this.visible2
    },
    setVisible3() {
      this.visible3 = !this.visible3
    },





    gotoDetail(id) {
      // wx.navigateTo({url: '/pages/taskDetail/main?tId='+id})
    wx.navigateBack()
  }
  },
};
</script>


<style scoped>
    .buttonObject {
        margin: auto;
        width: 70%;
        padding: 5rpx;
    }

</style>
