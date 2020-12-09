<template>
  <div>
    <wux-form id="wux-form" @change="onChange">
      <div v-if="isLoading">
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


        <wux-field name="taskSet" initialValue="">
          <wux-radio-group title="选择任务集">
            <div v-for="(item,index) in list" :key="index">
              <wux-radio :title="item" :value="index" />
            </div>
          </wux-radio-group>
          <wux-button block outline type="balanced" @click="newTaskSet">新建任务集</wux-button>
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
      </div>


      <div v-if="isAdvanced">
        <wux-field name="Loop" initialValue="-1">
          <wux-radio-group title="是否循环">
            <div v-for="(item,index) in isLoop" :key="index">
              <wux-radio :title="item" :value="index" />
            </div>
          </wux-radio-group>
        </wux-field>

        <wux-field name="switch" initialValue="false">
          <wux-cell-group title="Switch">
            <wux-cell>
                <wux-switch name="switch" slot="footer" @change="onSwitchChange" />
            </wux-cell>
          </wux-cell-group>
        </wux-field>

      </div>
      <view class="btn-area">
        <button @click="onSubmit($event)">创建</button>
        <button v-if="!isAdvanced" @click="onAdvancedOptions($event)">显示高级选项</button>
        <button v-if="isAdvanced" @click="onAdvancedOptions($event)">恢复默认选项</button>
        <!-- <button @click="onReset($event)">重设属性</button> -->
      </view>
    </wux-form>
  </div>
</template>

<script>
import { $wuxForm } from "../../../static/wux/index";
import moment from 'moment';
export default {
  data() {
    return {
      startDate: [],
      startDatePicker: "选择开始时间",
      endDate: [],
      endDatePicker: "选择结束时间",
      isAdvanced: false,
      isLoading: false,
      radio: '',
      isLoop:["每日循环","每周循环","每月循环"],
      switch: false,
      list:["php","java"]
    };
  },
  onLoad() {},
  beforeMount() {
    var tempDate=[]
    this.startDatePicker=moment().format('YYYY-MM-DD HH:mm')
    tempDate[0]=parseInt(moment().format('YYYY')) 
    tempDate[1]=parseInt(moment().format('MM'))
    tempDate[2]=parseInt(moment().format('DD'))
    tempDate[3]=parseInt(moment().format('HH'))
    tempDate[4]=parseInt(moment().format('mm'))
    this.startDate=tempDate
    console.log('start date:::'+this.startDate)


    var tempEndDate=[]
    this.endDatePicker=moment().add(1,'h').format('YYYY-MM-DD HH:mm')
    tempEndDate[0]=parseInt(moment().add(1,'h').format('YYYY')) 
    tempEndDate[1]=parseInt(moment().add(1,'h').format('MM'))
    tempEndDate[2]=parseInt(moment().add(1,'h').format('DD'))
    tempEndDate[3]=parseInt(moment().add(1,'h').format('HH'))
    tempEndDate[4]=parseInt(moment().add(1,'h').format('mm'))
    this.endDate=tempEndDate
    console.log('end date:::'+this.endDate)

    
    wx.getUserInfo({
      withCredentials: false,
      success: (res) => {
        console.log(res);
      },
      fail: () => {
        console.log("shibai");
      },
      complete: () => {},
    });
    this.isLoading=!this.isLoading;
  },
  mounted() {},

  computed: {},

  methods: {
    onChange(event) {
      const { form, changedValues, allValues } = event.mp.detail;
      console.log("onChange \n", changedValues, allValues);
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
    onRadioChange(e) {
        this.setData({ radio: e.detail.value })
    },
     onSwitchChange(e) {
       this.switch=!this.switch
    },

    onAdvancedOptions() {
      this.isAdvanced = !this.isAdvanced;
    },
    newTaskSet(){
      const url = '../create-set/main'
      wx.navigateTo({ url })    
    },
    onSubmit() {
      const { getFieldsValue, getFieldValue, setFieldsValue } = $wuxForm();
      const value = getFieldsValue();

      var createData=value;
      if(createData.startTime===null){
        createData.startTime=this.startDate
      }
      else{
        createData.startTime[1]++
      }
      if(createData.endTime===null){
        createData.endTime=this.endDate
      }
      else{
        createData.endTime[1]++
      }
      console.log("createData is:\n", createData)
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

