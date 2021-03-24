<!--
 * @Description:
 * @Version: 2.0
 * @Autor: renlinjie
 * @Date: 2021-01-06 23:22:18
 * @LastEditors: renlinjie
 * @LastEditTime: 2021-01-14 19:55:51
-->
<template>
  <div class="show-user-list">
    <vue-scroll :ops="ops">
      <el-row
        v-for="user in userList"
        :key="user.userId"
        style="display: flex; align-items: center"
      >
        <el-col
          :offset="2"
          :span="9"
          class="flex-left"
          style="display: flex; align-items: center"
        >
          <el-avatar
            style="width: 35px; height: 35px; object-fit: scale-down"
            :src="user.avatar"
          ></el-avatar>
          <span style="margin-left: 5px">{{ user.userName }}</span>
        </el-col>
        <el-col :span="10" class="flex-center">
          <div>{{ user.title }}</div>
        </el-col>
        <el-col :span="2" class="flex-center">
          <el-popover placement="right" width="100" trigger="click">
            <el-button-group style="display: flex; flex-direction: column">
              <el-button type="text" @click.native="removeUserById(user.userId)"
                >移除成员</el-button
              >
              <!-- <el-button type="text">设为管理员</el-button> -->
            </el-button-group>

            <el-button
              slot="reference"
              type="text"
              icon="el-icon-more"
              style="transform: rotate(90deg); margin-left: 15px"
            ></el-button>
          </el-popover>
        </el-col>
      </el-row>
    </vue-scroll>
  </div>
</template>

<script>
import { deleteUser } from "@/api/project";
import { Message } from "element-ui";
export default {
  name: "UserList",
  props: {
    userList: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      ops: {
        vuescroll: {
          mode: "native",
          sizeStrategy: "number",
          // checkShiftKey: true,
          // locking: false
        },
        scroller: {
          bouncing: true,
          /** Enable locking to the main axis if user moves only slightly on one of them at start */
          locking: true,
          /** 最小缩放级别 */
          minZoom: 0.5,
          /** 最大缩放级别 */
          maxZoom: 3,
          /** 滚动速度的倍速 **/
          speedMultiplier: 1,
          /** 到达边界时应用于减速的改变量  **/
          penetrationDeceleration: 0.03,
          /** 到达边界时应用于加速的改变量  **/
          penetrationAcceleration: 0.08,
          /** Whether call e.preventDefault event when sliding the content or not */
          preventDefault: true,
          /** Whether call preventDefault when (mouse/touch)move*/
          preventDefaultOnMove: true,
        },
        bar: {
          showDelay: 500,
          onlyShowBarOnScroll: true,
          keepShow: true,
          background: "#409eff",
          opacity: 0.8,
          hoverStyle: false,
          specifyBorderRadius: false,
          minSize: 0,
          size: "6px",
          disable: false,
        },
      },
    };
  },
  methods: {
    removeUserById(id) {
      deleteUser({
        userId: id,
      }).then((res) => {
        // Message.success("用户删除成功");
        this.$emit("deleteUser");
      });
    },
  },
  mounted: {},
};
</script>

<style lang="scss" scoped>
.show-user-list {
  display: flex;
  flex-direction: column;
  max-height: 150px;
}
.flex-center {
  display: flex;
  height: 40px;
  line-height: 40px;
}
</style>
