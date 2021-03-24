<!--
 * @Description: 
 * @Version: 2.0
 * @Autor: renlinjie
 * @Date: 2021-01-16 22:13:34
 * @LastEditors: youjianing
 * @LastEditTime: 2021-01-16 17:18
-->
<template>
  <div class="project-list">
    <el-table :data="list">
      <el-table-column prop="projectName" label="项目名称" align="left">
      </el-table-column>
      <el-table-column label="负责人" align="left">
        <template slot-scope="scope">
          <div class="ownerInfo">
            <img
              :src="scope.row.avatar"
              alt=""
              style="width: 30px; height: 30px; margin-right: 10px"
            />
            <div class="owner-name" style="height: 30px; line-height: 30px">
              {{ scope.row.ownerName }}
            </div>
            
          </div>
            
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="primary" plain @click="enterProject(scope.row)"
            >进入</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { setProjectId, setProjectName } from "@/utils/cache.js";
export default {
  props: {
    list: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {};
  },
  methods: {
    enterProject(row) {
      console.log(row);
      this.$nextTick(() => {
        setProjectId(row.projectId);
        console.log(row.projectId);
        setProjectName(row.projectName);
        this.$router.push({
          path: "/project/file",
          // query: {
          //   projectId: row.projectId,
          // },
        });
      });
    },
  },
  mounted() {},
};
</script>

<style lang="scss" scoped>
.project-list {
  margin: 10px auto;
  width: 90%;
}
.project-item {
  height: 100px;
  border: 1px solid red;
}
.ownerInfo {
  display: flex;
}
</style>
