<template>
  <div id="app">
    <!-- <vue-scroll :ops="ops"> -->
      <router-view></router-view>
    <!-- </vue-scroll> -->
  </div>
</template>

<script>
import Rtc from "@/common/Rtc";
export default {
  name: "App",
  components: {
    // 挂载子组件 (其它组件通过router加载, 所以不需要写在这里)
    Rtc
  },
  data() {
    return {
      ops: {
        vuescroll: {
          checkShiftKey: true,
          locking: false
        },
        bar: {
          showDelay: 500,
          onlyShowBarOnScroll: true,
          keepShow: true,
          background: "#c1c1c1",
          opacity: 1,
          hoverStyle: false,
          specifyBorderRadius: false,
          minSize: 0,
          size: "6px",
          disable: false
        }
      }
    };
  }
};
</script>

<style lang="scss">
 
.video-js.vjs-paused .vjs-big-play-button {
  z-index: 100;
}
.vjs-has-started .vjs-control-bar {
  z-index: 100;
}
</style>
