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
              <wux-radio :title="item" :value="index" />
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

        <p>高级选项</p>


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

      </div>
      <view class="btn-area">
        <button @click="onSubmit($event)">修改</button>
        <button v-if="!isAdvanced" @click="onAdvancedOptions($event)">显示高级选项</button>
        <button v-if="isAdvanced" @click="onAdvancedOptions($event)">恢复默认选项</button>
        <!-- <button @click="onReset($event)">重设属性</button> -->
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

     visible1: false,
     visible2: false,
     visible3: false,

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
      url: 'api/get_taskset?uId='+this.userId,
    }).then(res => {
      console.log(res.taskSetList)
      this.list=res.taskSetList
      console.log(this.list)
    }).catch(function (error) {
        console.log(error);
    });

    this.$fly.request({
      method: 'get', // post/get 请求方式
      url: 'api/getTask?taskId='+this.taskId,
    }).then(res => {
      console.log("changeTask 的获取")
      console.log(res)
      this.title=res.title
      this.description=res.description
      this.startDatePicker=res.startDate
      this.endDatePicker=res.endDate
      this.routine=res.routine
      this.listNumber=res.listNumber
      this.isDelay=res.isDelay
      this.delayDatePicker=res.delayDate
      this.workLoad=[res.workLoad]
      this.isRequire=res.isRequire
      this.isNeedNotice=res.isNeedNotice
      this.noticeDatePicker=res.noticeBefore
      this.description=res.description

      var str=this.routine.split("-")
      if(str[0]=="01"){
        this.loopText="每月循环"
      }
      else if(str[1]=="01"){
        this.loopText="每周循环"
      }
      else if( str[2]=="01"){
        this.loopText="每日循环"
      }
      else{
        this.loopText="不循环"
      }

      if(this.isNeedNotice){
        this.noticeText="提前"+this.noticeBefore
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

  computed: {},

  methods: {
      
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
      console.log()
      var rou=''
      if(this.loopCode==0){
        rou='00-00-01'
      }
      else if(this.loopCode==1){
        rou='00-01-00'
      }
      else if(this.loopCode==2){
        rou='01-00-00'
      }
      else{
        rou='00-00-00'
      }

//赋值
      subData.userId=this.userId
      subData.title=value.title
      subData.description=value.description
      subData.createDate=moment().add(1,'h').format('YYYY-MM-DD HH:mm:ss')
      subData.startDate=this.startDatePicker
      subData.endDate=this.endDatePicker
      subData.routine=rou
      subData.listNumber=value.taskSet
      subData.isDelay=this.isDelay
      subData.delayDate=this.delayDatePicker
      subData.workLoad=this.workLoad[0]
      subData.isRequire=this.isRequire
      subData.isNeedNotice=this.isNeedNotice
      subData.noticeBefore=this.noticeDatePicker

      console.log(subData)

      if(subData.listNumber==-1){
        this.setVisible1()
        return
      }
      else if(!moment(subData.startDate).isBefore(subData.endDate)){
        this.setVisible2()
        return 
      }
      else if(subData.isDelay &&moment(subData.delayDate).isBefore(subData.endDate)){
        this.setVisible3()
        return 
      }
      else{
        this.$fly.request({
        method:"post", //post/get 请求方式
        url:"api/changeTask",
        body:{
          "userId":subData.userId,
          "title":subData.title,
          "description":subData.description,
          "createDate":subData.createDate,
          "startDate":subData.startDate,
          "endDate":subData.endDate,
          "routine":subData.routine,
          "listNumber":subData.listNumber,
          "isDelay":subData.isDelay,
          "delayDate":subData.delayDate,
          "workLoad":subData.workLoad,
          "isRequire":subData.isRequire,
          "isNeedNotice":subData.isNeedNotice,
          "noticeBefore":subData.noticeBefore
        }
      }).then(res =>{

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

    gotoDetail(id) {
      wx.navigateTo({url: '/pages/taskDetail/main?tId='+id})
    }
  },
};
</script>

