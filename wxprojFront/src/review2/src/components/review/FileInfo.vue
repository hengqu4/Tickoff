<template>
  <div>
    <div class="discuss">
      <el-form
        :model="videoInfo"
        ref="videoInfo"
        label-width="80px"
        label-suffix=":"
      >
        <el-form-item label="所属项目">
          <span>
            {{ videoInfo.project }}
          </span>
        </el-form-item>
        <el-form-item label="视频时长">
          <span>
            {{ videoInfo.duration }}
          </span>
        </el-form-item>
        <el-form-item label="上传时间">
          <span>
            {{ videoInfo.createDate }}
          </span>
        </el-form-item>
        <el-form-item label="视频名称">
          <span>
            {{ videoInfo.videoName }}
          </span>
        </el-form-item>
        <el-form-item label="上传者">
          <span>
            {{ videoInfo.uploader }}
          </span>
        </el-form-item>
        <el-form-item label="描述">
          <span>
            {{ videoInfo.description }}
          </span>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    videoInfo: {
      required: true
    }
  },
  data() {
    return {};
  },
  methods: {}
};
</script>

<style>
.discuss {
  height: fit-content;
  float: bottom;
  color: black;
  padding: 18px;
  background: #cfd3d694;
}
.info {
  width: 100%;
}
</style>
