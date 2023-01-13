<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
             label-width="68px">
      <el-form-item label="文字" prop="brandContent">
        <el-input
          v-model="queryParams.brandContent"
          placeholder="请输入文字"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['brand:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['brand:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['brand:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['brand:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="brandList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="文字" align="center" prop="brandContent"/>
      <el-table-column label="视频" align="center" prop="brandVideoUrl"/>
      <el-table-column label="公益形象" align="center" prop="publicWelfare"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['brand:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['brand:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改品牌中心基本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="文字" prop="brandContent">
          <el-input v-model="form.brandContent" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="视频" prop="brandVideoUrl">
          <div class="upload-box">
            <div class="avatar-uploader-box">
              <!-- 图片预览 -->
              <div :key="index" class="video-preview" v-for="(item, index) in videoList">
                <video :src="item.url" @mouseover.stop="item.isShowPopup = true" class="avatar">您的浏览器不支持视频播放</video>
                <!-- 显示查看和删除的按钮弹窗 -->
                <div
                  @mouseleave="item.isShowPopup = false"
                  class="avatar-uploader-popup"
                  v-show="item.url && item.isShowPopup"
                >
                  <i @click="previewVideo(item)" class="el-icon-zoom-in"></i>
                  <i @click="deleteVideo(index)" class="el-icon-delete"></i>
                </div>
              </div>

              <!-- 方框样式 -->
              <el-upload
                action="#"
                :auto-upload="false"
                :on-change="handleAvatarChange"
                :show-file-list="false"
                class="avatar-uploader"
                ref="avatarUploader"
                v-show="uploadShow"
              >
        <span
          element-loading-background="rgba(0, 0, 0, 0.8)"
          element-loading-spinner="el-icon-loading"
          element-loading-text="上传中"
          style="display:block;"
          v-loading="loading"
        >
          <i class="el-icon-plus avatar-uploader-icon"></i>
        </span>
              </el-upload>

              <!-- 上传提示文字样式 -->
              <div class="upload-tip">
                <slot>建议：视频格式支持MP4</slot>
              </div>
            </div>
            <!-- 查看大图 -->
            <el-dialog
              :close-on-click-modal="false"
              :visible.sync="dialogVisible"
              append-to-body
              center
              title="视频查看"
              :before-close="handleClose"
            >
              <video :src="videoSrc" ref="video" controls alt width="100%">您的浏览器不支持视频播放</video>
            </el-dialog>
          </div>
        </el-form-item>
        <el-form-item label="公益形象" prop="publicWelfare">
          <editor v-model="form.publicWelfare" :min-height="192"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listBrand,
  getBrand,
  delBrand,
  addBrand,
  updateBrand
} from "@/api/brand/brand";
import { uploadvideos } from '@/api/upload'

const spaceId = sessionStorage.getItem("spaceId");

export default {
  name: "Brand",
  props:{
    videos: {
      type: Array,
      default: function () {
        return []
      },
    },
    numLimit: {
      // 最大允许上传个数
      type: Number,
      default: 1,
    },
    sizeLimit: {
      // 最大单文件大小
      type: Number,
      default: 50,
    },
  },
  data() {
    return {

      dialogVisible: false,
      videoSrc: '',
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 品牌中心基本信息表格数据
      brandList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        brandContent: null,
        brandVideoUrl: null,
        publicWelfare: null,
        spaceId: spaceId,
      },
      // 表单参数
      form: {}
      ,
      // 表单校验
      rules: {
        brandContent:[{required:true,message:'请输入文字',trigger:'blur'}],
        publicWelfare:[{required:true,message:'请输入公益形象',trigger:'blur'}],
      },
      videoList:[]
    }
      ;
  },
  computed: {
    uploadShow() {
      return this.videoList.length < this.numLimit
    },
  },
  watch: {
    videos: {
      handler() {
        const isArray = Array.isArray(this.videos)
        if (isArray && this.videos.length > 0) {
          this.videos.map((item) => {
            item.isShowPopup = false
          })
          this.videoList = JSON.parse(JSON.stringify(this.videos))
        } else {
          this.videoList = []
        }
      },
      deep: true,
      immediate: true,
    },
  },
  created() {
    this.getList();
  },
  methods: {

    // 上传之前
    beforeAvatarUpload(file) {
      return new Promise((resolve, reject) => {
        if (
          [
            'video/mp4',
            'video/ogg',
            'video/flv',
            'video/avi',
            'video/wmv',
            'video/rmvb',
            'video/mov',
          ].indexOf(file.raw.type) == -1
        ) {
          // eslint-disable-next-line prefer-promise-reject-errors
          return reject('请上传正确的视频格式!')
        }

        if (file.size / 1024 / 1024 > this.sizeLimit) {
          // eslint-disable-next-line prefer-promise-reject-errors
          return reject(`视频大小不能超过${this.sizeLimit}M!`)
        }

        if (this.videoList.length === this.numLimit) {
          // eslint-disable-next-line prefer-promise-reject-errors
          return reject(`最多上传${this.numLimit}个视频`)
        }
        resolve('符合表单规则')
      })
    },

    // 上传改变
    async handleAvatarChange(file, fileList) {
      try {
        await this.beforeAvatarUpload(file)
        this.uploadImgApi(file)
      } catch (e) {
        this.$message.warning(JSON.stringify(e))
      }
    },

    // 上传视频准备
    uploadImgApi(data) {
      uploadvideos(data.raw).then((res) => {
        if (res.videoUrl) {
          this.videoList.push({
            url: res.videoUrl,
            isShowPopup: false,
          })
          this.$emit('change', this.videoList)
        }
      })
    },

    // 预览视频
    previewVideo(data) {
      this.videoSrc = data.url
      this.dialogVisible = true
    },

    // 删除视频
    deleteVideo(index) {
      this.videoList.splice(index, 1)
      this.$emit('change', this.videoList)
    },

    handleClose() {
      const video = document.getElementsByTagName('video')[1]
      if (!video.paused) {
        video.currentTime = 0
        video.pause()
      }
      this.dialogVisible = false
    },

    /** 查询品牌中心基本信息列表 */
    getList() {
      this.loading = true;
      listBrand(this.queryParams).then(response => {
        this.brandList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },

    // 表单重置
    reset() {
      this.form = {
        brandId: null,
        brandContent: null,
        brandVideoUrl: null,
        publicWelfare: null,
        spaceId: spaceId,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.videoList=[];
      this.resetForm("form");
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },

    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.brandId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加品牌中心基本信息";
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const brandId = row.brandId || this.ids
      getBrand(brandId).then(response => {
        console.log(this.videoList)
        if (response.data.brandVideoUrl!=null){
          this.videoList.push({url:response.data.brandVideoUrl,isShowPopup:false});
        }
        console.log(this.videoList)
        this.form = response.data;
        this.open = true;
        this.title = "修改品牌中心基本信息";
      });
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid){
          console.info("videoList=>",this.videoList)
          if (this.videoList.length>0) {
            this.form.brandVideoUrl = this.videoList[0].url;
          }
          if (this.form.brandId != null) {
            updateBrand(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBrand(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const brandIds = row.brandId || this.ids;
      this.$modal.confirm('是否确认删除品牌中心基本信息编号为"' + brandIds + '"的数据项？').then(function () {
        return delBrand(brandIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('brand/brand/export', {
        ...this.queryParams
      }, `brand_${new Date().getTime()}.xlsx`)
    }
  }
}
;
</script>

<style lang="scss">
$width: 150px;
$height: 150px;

.upload-box {
  .avatar-uploader-box {
    position: relative;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    padding-bottom: 20px;
    min-width: 350px;
    .avatar-uploader {
      .el-upload {
        width: $width;
        height: $height;
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        &:hover {
          border-color: #409eff;
        }
        .el-loading-spinner {
          width: $width;
          height: $height;
          position: relative;
          display: flex;
          flex-direction: column;
          align-items: center;
          justify-content: center;
          margin-top: 0;
          top: 0;
          .el-loading-text {
            margin: 0;
          }
        }
      }
      .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: $width;
        height: $height;
        line-height: $height;
        text-align: center;
      }
    }
    .video-preview {
      position: relative;
      padding-right: 20px;
      .avatar {
        width: $width;
        height: $height;
        display: block;
        border-radius: 6px;
        margin-bottom: 13px;
      }
      .avatar-uploader-popup {
        position: absolute;
        top: 0;
        left: 0;
        z-index: 2;
        width: $width;
        height: $height;
        background: rgba($color: #000000, $alpha: 0.5);
        display: flex;
        justify-content: center;
        align-items: center;
        color: #fff;
        font-size: 20px;
        border-radius: 6px;
        i {
          width: 30%;
          text-align: center;
          padding: 0 5%;
          font-size: 24px;
        }
      }
    }
    .upload-tip {
      position: absolute;
      bottom: 0;
      left: 0;
      font-size: 12px;
      color: #606266;
      max-width: 350px;
      line-height: 20px;
    }
  }
}
</style>


