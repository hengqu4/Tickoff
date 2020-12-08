<template>
  <div>
    <wux-form id="wux-form" @change="onChange">
      <wux-cell-group title="Textarea">
        <wux-cell hover-class="none">
          <wux-field name="textarea" initialValue="456">
            <wux-textarea rows="3" />
          </wux-field>
        </wux-cell>
      </wux-cell-group>
    </wux-form>
  </div>
</template>

<script>

export default {
  computed: {

  },
  methods: {

  }
}
</script>

