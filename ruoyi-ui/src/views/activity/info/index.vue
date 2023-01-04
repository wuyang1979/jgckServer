<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="活动标题" prop="activityTitle">
        <el-input
          v-model="queryParams.activityTitle"
          placeholder="请输入活动标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发布状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择发布状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_notice_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['activity:info:add']"
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
          v-hasPermi="['activity:info:edit']"
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
          v-hasPermi="['activity:info:remove']"
        >删除
        </el-button>
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="warning"-->
      <!--          plain-->
      <!--          icon="el-icon-download"-->
      <!--          size="mini"-->
      <!--          @click="handleExport"-->
      <!--          v-hasPermi="['activity:info:export']"-->
      <!--        >导出-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="活动标题" align="center" prop="activityTitle">
        <template slot-scope="scope">
          <el-link @click="goDetail(scope.row.activityId)" type="primary">{{ scope.row.activityTitle }}</el-link>
        </template>
      </el-table-column>
      <el-table-column label="报名人数" align="center" prop="registerNumber" width="80px">
        <template slot-scope="scope">
          <span>{{ scope.row.signNumber }}</span>
          <span>/{{ scope.row.registerNumber }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发布状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_notice_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="活动地址" align="center" prop="activityAddress"/>
      <el-table-column label="上架时间" align="center" prop="releaseTime" width="180px">
        <template slot-scope="scope">
          <span v-if="scope.row.status=='1'">{{ parseTime(scope.row.releaseTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="报名截止时间" align="center" prop="register_deadline" width="180px">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.registerDeadline, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动开始时间" align="center" prop="activityStartTime" width="180px">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.activityStartTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="left" class-name="small-padding fixed-width" width="250px">
        <template slot-scope="scope">
          <el-button
            v-if="selectIsSign(scope.row.activityId)==='0'&&scope.row.status==='1'"
            :disabled="getToday()>scope.row.registerDeadline"
            size="mini"
            type="text"
            icon="el-icon-thumb"
            @click="SignActivity(scope.row)"
            v-hasPermi="['activity:relation:add']"
          >报名
          </el-button>
          <el-button
            v-if="scope.row.status==='1'&&selectIsSign(scope.row.activityId)!='0'"
            size="mini"
            type="text"
            icon="el-icon-thumb"
            @click="canceSign(scope.row)"
            v-hasPermi="['activity:relation:remove']"
          >取消报名
          </el-button>
          <el-button
            v-if="scope.row.status==='1'"
            size="mini"
            type="text"
            icon="el-icon-document-delete"
            @click="cancelRelease(scope.row)"
            v-hasPermi="['activity:info:edit']"
          >下架
          </el-button>
          <el-button
            v-else
            size="mini"
            type="text"
            icon="el-icon-document-checked"
            @click="release(scope.row)"
            v-hasPermi="['activity:info:edit']"
          >上架
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['activity:info:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['activity:info:remove']"
          >删除
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-upload"
            @click="handleUpload(scope.row)"
            v-hasPermi="['activity:info:remove']"
          >上传
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

    <!-- 添加或修改活动基本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="活动标题" prop="activityTitle">
              <el-input v-model="form.activityTitle" placeholder="请输入活动标题"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报名人数" prop="registerNumber">
              <el-input type="number" v-model="form.registerNumber" placeholder="请输入报名人数"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="活动开始时间" prop="activityStartTime">
              <el-date-picker clearable
                              v-model="form.activityStartTime"
                              type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              placeholder="请选择活动开始时间"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报名截止时间" prop="registerDeadline">
              <el-date-picker clearable
                              v-model="form.registerDeadline"
                              type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              placeholder="请选择报名截止时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="活动地址" prop="activityAddress">
              <el-input v-model="form.activityAddress" placeholder="请输入活动地址"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="内容">
              <editor v-model="form.activityContent" :min-height="192"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


    <el-dialog :title="title" :visible.sync="upload" width="600px" append-to-body>
      <el-upload
        class="upload-demo"
        ref="upload"
        :file-list="fileList"
        :headers="headers"
        :limit="12"
        :auto-upload="true"
        drag
        :action="url"
        list-type="picture"
        accept=".jpg,.png"
        :on-exceed="handleExceed"
        :on-preview="handlePreview"
        :on-change="handleChange"
        :on-remove="handleRemove"
        multiple>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传jpg/png文件</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitUpload">保 存</el-button>
        <el-button @click="cancelUpload">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {
  listActivity,
  getActivity,
  delActivity,
  addActivity,
  updateActivity,
  selectSign,
} from "@/api/activity/info";
import {getNowDateTime} from "@/api/common/common";
import {
  addRelation,
  cancelRelation,
  delRelation,
  delRelationAll,
  editFileByBusinessId,
  listFile
} from "@/api/activity/relation";
import {getToken} from "@/utils/auth";

export default {
  name: "Info",
  dicts: ['sys_notice_status'],
  data() {
    return {

      businessId: null,

      fileIds: [],

      headers: {
        Authorization: getToken()
      },

      url: process.env.VUE_APP_BASE_API + 'common/upload',

      upload: false,

      fileList: [],

      signList: [],

      signQueryParams: {
        activityId: null,
      },

      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],

      names: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 活动基本信息表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        activityTitle: null,
        activityContent: null,
        status: null,
        releaseTime: null,
        activityStartTime: null,
        activityAddress: null,
        registerDeadline: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        activityTitle: [{required: true, message: '请输入活动标题', trigger: 'blur'},
          {max: 20, message: "活动标题长度不能超过20字符"}],
        registerNumber: [{required: true, message: '请输入报名人数', trigger: 'blur'},
          {required: true, pattern: /^[0-9]*$/, message: "请输入0-9之间的数字", trigger: "blur"},
          {max: 5, message: "报名人数长度不能超过5字符"}],
        activityStartTime: [{required: true, message: "请选择活动开始时间", trigger: 'blur'},
          {max: 20, message: "活动开始时间长度不能超过20字符"}],
        registerDeadline: [{required: true, message: '请选择截止时间', trigger: 'blur'},
          {max: 20, message: "截止时间长度不能超过20字符"}],
        activityAddress: [{required: true, message: '请输入活动地址', trigger: 'blur'},
          {max: 20, message: "活动地址长度不能超过20字符"}],
      }
    };
  },
  created() {
    this.getList();
    this.getSign();
  },
  methods: {

    handleRemove(file, fileList) {
      this.fileIds = [];
      fileList.forEach(fl => {
        this.fileIds.push(fl.id);
      })
      console.info("移除文件=>", file)
      console.info("移除文件list=>", fileList)
    },

    listFile() {
      listFile(this.businessId).then(respone => {
        var list = [];
        var rows = respone.rows;
        rows.forEach(r => {
          var rep = {};
          rep = r;
          list.push({
            name: r.originalFileName,
            id: r.fileId,
            url: r.url,
          })
        })
        this.fileList = list;
      })

    },

    cancelUpload() {
      this.upload = false;
    },

    editFileByBusinessId(businessId) {
      editFileByBusinessId(businessId, this.fileIds).then(respone => {
        this.$modal.msgSuccess("保存成功")
      })
    },


    handleChange(file, fileList) {
      var fileIds = [];
      if (fileList.length > 0) {
        fileList.forEach(fl => {
          if (fl.id) {
            fileIds.push(fl.id)
          } else if (fl.response) {
            fileIds.push(fl.response.id);
          }
        })
      } else {
        fileIds = [];
      }
      this.fileIds = fileIds
      console.info("文件变化file=>", file)
      console.info("文件变化fileList=>", fileList)
    },
    handlePreview(file) {
      window.open(file.url)
    },

    submitUpload() {
      this.editFileByBusinessId(this.businessId)
      this.upload = false;
    },

    handleExceed() {
      this.$modal.msgError('最多上传12个文件！');
    },
    handleUpload(row) {
      this.fileList = [];
      this.title = '活动图片上传';
      this.businessId = row.activityId;
      this.listFile();
      this.upload = true;

    },
    getToday() {
      return getNowDateTime();
    },

    delRelationaAll(val) {
      return delRelationAll(val)
    },
    goDetail(val) {
      this.$router.push({
        path: '/service/person',
        query: {
          id: val,
        }
      })
    },
    canceSign(row) {
      this.$modal.confirm('是否确认取消活动标题为"' + row.activityTitle + '"的报名？').then(function () {
        return cancelRelation(row.activityId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("取消报名成功");
      }).catch(() => {
      });
    },

    selectIsSign(activityId) {
      var list = this.signList;
      var result;
      list.forEach(l => {
        if (activityId === l.activityId) {
          result = l.isSign
        }
      })
      return result;
    },
    getSign() {
      selectSign().then(respone => {
        this.signList = respone.rows;
      }).catch(() => {
      })
    },

    SignActivity(row) {
      this.signQueryParams.activityId = row.activityId;
      addRelation(this.signQueryParams).then(respone => {
        this.$modal.msgSuccess('报名成功');
        this.getList();
      }).catch(() => {
      })

    },
    //取消发布
    cancelRelease(row) {
      this.$modal.confirm('是否确认取消发布标题为"' + row.activityTitle + '"的活动？').then(function () {
        row.status = '0';
        return updateActivity(row);
      }).then(() => {
        this.delRelationaAll(row.activityId);
        this.getList();
        this.$modal.msgSuccess("取消成功");
      }).catch(() => {
      });
    },

    //发布
    release(row) {
      this.$modal.confirm('是否确认发布标题为"' + row.activityTitle + '"的活动？').then(function () {
        row.status = '1';
        row.releaseTime = getNowDateTime();
        return updateActivity(row);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("发布成功");
      }).catch(() => {
        this.getList();
      });
    },


    /** 查询活动基本信息列表 */
    getList() {
      this.getSign()
      this.loading = true;
      listActivity(this.queryParams).then(response => {
        this.infoList = response.rows;
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
        activityId: null,
        activityTitle: null,
        activityContent: null,
        status: null,
        releaseTime: null,
        activityStartTime: null,
        activityEndTime: null,
        activityAddress: null,
        registerNumber: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
      };
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
      this.ids = selection.map(item => item.activityId)
      this.names = selection.map(item => item.activityTitle)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加活动基本信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.activityTime = [];
      this.reset();
      const activityId = row.activityId || this.ids
      getActivity(activityId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改活动基本信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.activityId != null) {
            updateActivity(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.status = '0';
            addActivity(this.form).then(response => {
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
      const activityIds = row.activityId || this.ids;
      const activityNames = row.activityTitle || this.names;
      this.$modal.confirm('是否确认删除活动标题为"' + activityNames + '"的活动？').then(function () {
        return delActivity(activityIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('activity/info/export', {
        ...this.queryParams
      }, `info_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style scoped>

.el-form-item__content > .el-input, .el-textarea, .el-select, .el-date-editor {
  width: 80% !important;
}

/deep/ .el-upload-dragger {
  height: 200px !important;
  width: 560px !important;
}

</style>
