<template>
  <div>
    <wux-form id="wux-form" @change="onChange">

      <wux-cell-group title="标题">
        <wux-cell hover-class="none">
          <wux-field name="title" initialValue="请输入任务标题">
            <wux-textarea rows="1" />
          </wux-field>
        </wux-cell>
      </wux-cell-group>

      <wux-cell-group title="描述">
        <wux-cell hover-class="none">
          <wux-field name="" initialValue="请输入任务描述">
            <wux-textarea rows="3" />
          </wux-field>
        </wux-cell>
      </wux-cell-group>

      <wux-cell-group title="开始时间">
        <wux-cell hover-class="none">
          <wux-field >
            <wux-date-picker @confirm="onConfirmStartDatePicker($event)" @value="startDate">
              <wux-cell title="请选择开始时间" is-link @extra="startDatePicker"></wux-cell>
            </wux-date-picker>
          </wux-field>
        </wux-cell>
      </wux-cell-group>
      
      <wux-cell-group title="结束时间">
        <wux-cell hover-class="none">
          <wux-field name="" initValue="endDatePicker">
            <wux-date-picker @confirm="onConfirmEndDatePicker($event)" @value="endDate">
              <wux-cell title="请选择结束时间" is-link @extra="endDatePicker"></wux-cell>
            </wux-date-picker>
          </wux-field>
        </wux-cell>
      </wux-cell-group>

      <view class="btn-area">
        <button @click="onSubmit($event)">submit</button>
        <button @click="onReset($event)">Reset</button>
      </view>
    </wux-form>
  </div>
</template>

<script>
import { $wuxForm } from '../../../static/wux/index'
export default {
  data(){
    return{
      startDate:[ ],
      startDatePicker: " ",
      endDate:[ ],
      endDatePicker: " ",
    }
  },

  computed: {

  },

  methods: {
    onChange(event) {
      const { form, changedValues, allValues } = event.mp.detail
      console.log('onChange \n', changedValues, allValues)
    },

    onConfirmStartDatePicker(event) {
      console.log(event.mp.detail.value)
      console.log(event.mp.detail.label)
      this.startDate=event.mp.detail.value
      this.startDatePicker=event.mp.detail.label
    },

    onConfirmEndDatePicker(event) {
      console.log(event.mp.detail.value)
      console.log(event.mp.detail.label)
      this.endDate=event.mp.detail.value
      this.endDatePicker=event.mp.detail.label
    },

    onSubmit() {
      const { getFieldsValue, getFieldValue, setFieldsValue } = $wuxForm()
      const value = getFieldsValue()
      console.log('Wux Form Submit \n', value)
    },

    onReset() {
      const { getFieldsValue, setFieldsValue } = $wuxForm()
       const fields = getFieldsValue()

      for (let item in fields) {
        if ({}.hasOwnProperty.call(fields, item)) {
          if (Array.isArray(fields[item])) {
            fields[item] = []
            if (item === 'slider') {
              fields[item] = [10, 80]
            }
          } else if (typeof fields[item] === 'boolean') {
              fields[item] = false
            } else if (typeof fields[item] === 'number') {
              fields[item] = 0
            } else {
              fields[item] = ''
            }
        }
      }

      setFieldsValue({
        ...fields,
      })
    },
  }
}
</script>

