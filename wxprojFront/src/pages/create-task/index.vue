<template>
  <div>
    <toast message="请选择任务集" :visible.sync="visible1"></toast>
    <toast message="结束时间不能早于开始时间" :visible.sync="visible2"></toast>
    <toast message="推迟时间不能早于结束时间" :visible.sync="visible3"></toast>

    <wux-form id="wux-form" @change="onChange">
        <wux-cell-group title="任务标题">
          <wux-cell hover-class="none">
            <wux-field name="title" initialValue="任务标题1">
              <wux-textarea rows="1" />
            </wux-field>
          </wux-cell>
        </wux-cell-group>

        <wux-cell-group title="任务描述">
          <wux-cell hover-class="none">
            <wux-field name="description" initialValue="任务描述">
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
          <div class="buttonObject">
            <wux-button block outline type="dark" @click="newTaskSet">新建任务集</wux-button>
          </div>
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
import store from '../../store';


export default {
  data() {
    return {

     scrollTop: 0,


     userId:'',

     visible1: false,
     visible2: false,
     visible3: false,

      startDate: [],
      startDatePicker: "选择开始时间",

      endDate: [],
      endDatePicker: "选择结束时间",

      delayDate:[],
      delayDatePicker:"选择延后时间",

      isNeedNotice:false,
      noticeDate:[],
      noticeDatePicker:"选择提醒时间",

      isAdvanced: false,

      todayweekday:'',

      isLoading: false,
      radio: '',
      isLoop:["每日循环","每周循环","每月循环"],
      list:[],
      listNumber:"0",
      loopCode:"-1",
      isDelay:false,
      isRequire:true,
      workLoad:[1],
      oneHour:60*60*1000

    };
  },
  components: {
    toast
  },

  onLoad:function(options) {
    this.userId=options.uId
  },
  created(){

  },
  beforeMount() {

    var tempDate=[]
    this.startDatePicker=miment().format('YYYY-MM-DD hh:mm')
    tempDate[0]=parseInt(miment().format('YYYY')) 
    tempDate[1]=parseInt(miment().format('MM'))
    tempDate[2]=parseInt(miment().format('DD'))
    tempDate[3]=parseInt(miment().format('hh'))
    tempDate[4]=parseInt(miment().format('mm'))
    this.startDate=tempDate


    var tempEndDate=[]
    this.endDatePicker=miment().add(1,'hh').format('YYYY-MM-DD hh:mm')
    console.log(this.endDatePicker)
    tempEndDate[0]=parseInt(miment().add(1,'hh').format('YYYY')) 
    tempEndDate[1]=parseInt(miment().add(1,'hh').format('MM'))
    tempEndDate[2]=parseInt(miment().add(1,'hh').format('DD'))
    tempEndDate[3]=parseInt(miment().add(1,'hh').format('hh'))
    tempEndDate[4]=parseInt(miment().add(1,'hh').format('mm'))
    this.endDate=tempEndDate


    this.delayDate=tempEndDate
    this.delayDatePicker=miment().add(1,'hh').format('YYYY-MM-DD hh:mm')

    this.noticeDate=tempEndDate
    this.noticeDatePicker="00:00"

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
    console.log(store)

    this.userId=store.state.openId

    console.log(this.userId)

    this.$fly.request({
      method: 'get', // post/get 请求方式
      url: 'tickoff/api/mission_set/openid/'+this.userId,
    }).then(res => {
      console.log("正在通过后端请求list")
      console.log(res.data)
      this.list=res.data

      console.log(this.list)
    }).catch(function (error) {
        console.log("正在通过后端请求list，请求失败")

        console.log(error);
    });
   
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
      console.log()
      var rou=''
      if(this.loopCode==0){
        rou=1
      }
      else if(this.loopCode==1){
        var temp=this.startDatePicker
        temp=temp.split(" ")[0]
        temp=temp.split("-")
        var da=new Date(temp[0],temp[1]-1,temp[2])
        rou=da.getDay()*10;
      }
      else if(this.loopCode==2){
        rou=100
      }
      else{
        rou=0
      }

//赋值
      subData.title=value.title
      subData.description=value.description
      subData.createDate=miment().add(1,'hh').format('YYYY-MM-DD hh:mm:ss')
      subData.startDate=this.startDatePicker+":00"
      subData.endDate=this.endDatePicker+":00"
      subData.routine=rou
      subData.listNumber=value.taskSet
      subData.isDelay=this.isDelay
      subData.delayDate=this.delayDatePicker+":00"
      subData.workLoad=this.workLoad[0]
      subData.isRequire=this.isRequire
      subData.isNeedNotice=this.isNeedNotice
      subData.noticeBefore=this.noticeDatePicker+":00"
      subData.userId=this.userId
      subData.setId=this.list[subData.listNumber].mset_id
      subData.done=false

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
        method:"post", //post/get 请求方式
        url:"tickoff/api/missions",
        body:{
          "mset_id": subData.setId,
          "name": subData.title,
          "description": subData.description,
          "createDate": subData.createDate,
          "startDate": subData.startDate,
          "endDate": subData.endDate,
          "routine": subData.routine,
          "delay": subData.isDelay,
          "workLoad": subData.workLoad,
          "requireCheck": subData.isRequire,
          "needNotice": subData.isNeedNotice,
          "noticeTime": subData.noticeBefore,
          "done": subData.done,
          "delayDate": subData.delayDate
        }
      }).then(res =>{
        this.gotoDetail(1)
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
      var temp=this.startDatePicker
      temp=temp.split(" ")[0]
      temp=temp.split("-")
      var da=new Date(temp[0],temp[1]-1,temp[2])
      // console.log(da.getDay())
      // this.routine=da.getDay()*10;
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
      wx.navigateBack({
        delta: 1 //返回的页面数，如果 delta 大于现有页面数，则返回到首页,
      });
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