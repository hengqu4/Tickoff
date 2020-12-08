<template>
  <div>
    <wux-form id="wux-form" @change="onChange">

      <wux-cell-group title="标题">
        <wux-cell hover-class="none">
          <wux-field name="title" initialValue="请输入任务集标题">
            <wux-textarea rows="1" />
          </wux-field>
        </wux-cell>
      </wux-cell-group>

      <wux-cell-group title="描述">
        <wux-cell hover-class="none">
          <wux-field name="" initialValue="请输入任务集描述">
            <wux-textarea rows="3" />
          </wux-field>
        </wux-cell>
      </wux-cell-group>

      <view class="btn-area">
        <button @click="onSubmit">submit</button>
        <button @click="onReset">Reset</button>
      </view>
    </wux-form>
  </div>
</template>

<script>
import { $wuxForm } from '../../../static/wux/index'
export default {
  computed: {

  },
  methods: {
    onChange(e) {
      const { form, changedValues, allValues } = e.detail
      console.log
      ('onChange \n', changedValues, allValues)
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

