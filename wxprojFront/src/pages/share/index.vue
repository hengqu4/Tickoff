<template>
  <div>
    <P>{{sence}}</P>
  </div>
</template>

<script>

export default {
  data() {
    return {

    };
  },



  onLoad:function(options) {
    var sence =decodeURIComponent(options.scene)
    this.sence=sence
  },

  created(){
  },

  beforeMount() {
  },
  mounted() {

  },

  computed: {},

  methods: {
   
  }
};
</script>

