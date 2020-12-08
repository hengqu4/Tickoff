<template>
  <div>
    <wux-calendar id="wux-calendar" />
    <wux-cell-group title="Calendar">
      <wux-cell title="Direction = Vertical" :extra="value" @click="openCalendar"></wux-cell>
    </wux-cell-group>
  </div>
</template>

<script>
import { $wuxCalendar } from '../../../static/wux/index'
export default {
  data: {
    value: []
  },

  methods: {
    openCalendar() {
      $wuxCalendar().open({
        direction: 'vertical',
        onChange: (values, displayValues) => {
          console.log('onChange', values, displayValues)
          this.value = displayValues
        },
      })
    },
  }
}
</script>

