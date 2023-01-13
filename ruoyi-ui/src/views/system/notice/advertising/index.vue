<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="广告标题" prop="noticeTitle">
        <el-input
          v-model="queryParams.noticeTitle"
          placeholder="请输入广告标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
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
          v-hasPermi="['system:notice:add']"
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
          v-hasPermi="['system:notice:edit']"
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
          v-hasPermi="['system:notice:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="noticeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="序号" align="center" prop="noticeId" width="100" />-->
      <el-table-column
        label="消息标题"
        align="center"
        prop="noticeTitle"
        :show-overflow-tooltip="true"
      />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_notice_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="发布时间" align="center" prop="releaseTime">
        <template slot-scope="scope">
          <span v-if="scope.row.status=='0'">{{ parseTime(scope.row.releaseTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="left" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="queryNotice(scope.row)"
            v-hasPermi="['system:notice:query']"
          >详情
          </el-button>
          <el-button
            v-if="scope.row.status==='0'"
            size="mini"
            type="text"
            icon="el-icon-document-delete"
            @click="cancelRelease(scope.row)"
            v-hasPermi="['system:notice:edit']"
          >取消发布
          </el-button>
          <el-button
            v-else
            size="mini"
            type="text"
            icon="el-icon-document-checked"
            @click="release(scope.row)"
            v-hasPermi="['system:notice:edit']"
          >发布
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            :disabled="isRelease(scope.row)"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:notice:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:notice:remove']"
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

    <!-- 添加或修改公告对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="广告标题" prop="noticeTitle">
              <el-input v-model="form.noticeTitle" placeholder="请输入信息标题"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="封面">
              <el-upload
                ref="upload"
                :file-list="fileList"
                :headers="headers"
                :limit="1"
                :auto-upload="true"
                :action="uploadUrl"
                list-type="picture-card"
                accept=".jpg,.png"
                :on-exceed="handleExceed"
                :on-preview="handlePreview"
                :on-change="handleChange"
                :on-remove="handleRemove"
                :on-success="handleSuccess"
                multiple>
                <i class="el-icon-plus"></i>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="内容">
              <editor v-model="form.noticeContent" :min-height="192"/>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="查看图片"
      :close-on-click-modal="false"
      width="850px"
      :visible.sync="dialogVisible"
      append-to-body
    >
      <el-image :src="url" style="width: 100%; height: 400px" fit="contain"></el-image>
    </el-dialog>

    <el-dialog title="信息详情" :visible.sync="query" width="780px" append-to-body>
      <div v-html="queryForm.noticeContent"></div>
    </el-dialog>
  </div>
</template>

<script>
import {listNotice, getNotice, delNotice, addNotice, updateNotice} from "@/api/system/notice";
import {getNowDateTime} from "@/api/common/common";
import {editFileByBusinessId, listFile} from "../../../../api/activity/relation";
import {getToken} from "../../../../utils/auth";

const spaceId = sessionStorage.getItem("spaceId");

export default {
  name: "Notice",
  dicts: ['sys_notice_status', 'sys_notice_type'],
  data() {
    return {

      //查看图片是否显示弹出层
      dialogVisible: false,

      //上传图片地址
      uploadUrl: process.env.VUE_APP_BASE_API + '/common/upload/img/',

      //查看图片
      cover: '',

      //查看图片url
      url: '',

      //上传图片id集合
      fileIds: [],

      fileUrls: [],

      //请求头
      headers: {
        Authorization: 'Bearer ' + getToken()
      },

      //文件集合
      fileList: [],

      // 详情参数
      queryForm: {},

      // 详情弹框
      query: false,

      //选中数组标题
      names: [],
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
      // 公告表格数据
      noticeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        noticeType: 2,
        noticeTitle: undefined,
        createBy: undefined,
        status: '',
        spaceId: spaceId,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        noticeTitle: [
          {required: true, message: "公告标题不能为空", trigger: "blur"}
        ],
        noticeType: [
          {required: true, message: "公告类型不能为空", trigger: "change"}
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    isRelease(row) {
      if (row.status == '0') {
        return true;
      } else {
        return false;
      }
    },


    handleSuccess(response, file, fileList) {
      file.url = response.url;
    },

    //删除文件
    handleRemove(file, fileList) {
      this.fileIds = [];
      this.fileUrls = [];
      fileList.forEach(fl => {
        this.fileIds.push(fl.id);
        this.fileUrls.push(fl.url);
      })
    },

    //获取文件list
    listFile(businessId) {
      listFile(businessId).then(respone => {
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

    //文件绑定业务id
    editFileByBusinessId(businessId) {
      editFileByBusinessId(businessId, this.fileIds).then(respone => {
      })
    },

    //上传文件变化
    handleChange(file, fileList) {
      var fileIds = [];
      var fileUrls = [];
      if (fileList.length > 0) {
        fileList.forEach(fl => {
          if (fl.id) {
            fileIds.push(fl.id)
          } else if (fl.response) {
            fileIds.push(fl.response.id);
          }
          if (fl.url) {
            fileUrls.push(fl.url)
          } else if (fl.response.url) {
            fileUrls.push(fl.response.url)
          }
        })
      } else {
        fileIds = [];
      }
      this.fileIds = fileIds;
      this.fileUrls = fileUrls;
    },

    //查看图片
    handlePreview(file) {
      this.cover = file.url || file.response.src
      this.dialogVisible = true
    },

    //最多上传文件个数
    handleExceed() {
      this.$modal.msgError('最多上传1张图片！');
    },

    queryNotice(row) {
      this.queryForm = row;
      this.$router.push({
        path: '/service/program/advertising/detail',
        query: {
          id: row.noticeId
        }
      })
    },

    //取消发布
    cancelRelease(row) {
      this.$modal.confirm('是否确认取消发布标题为"' + row.noticeTitle + '"的信息').then(function () {
        row.status = '1';
        return updateNotice(row);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("取消成功");
      }).catch(() => {
      });
    },

    //发布
    release(row) {
      this.$modal.confirm('是否确认发布标题为"' + row.noticeTitle + '"的信息').then(function () {
        row.status = '0';
        row.releaseTime = getNowDateTime();
        return updateNotice(row);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("发布成功");
      }).catch(() => {
        this.getList();
      });
    },
    /** 查询公告列表 */
    getList() {
      this.loading = true;
      listNotice(this.queryParams).then(response => {
        this.noticeList = response.rows;
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
        noticeId: undefined,
        noticeTitle: undefined,
        noticeType: 2,
        noticeContent: undefined,
        status: "0",
        spaceId: spaceId,
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
      this.ids = selection.map(item => item.noticeId)
      this.names = selection.map(item => item.noticeTitle)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加公告";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.fileList = [];
      this.reset();
      const noticeId = row.noticeId || this.ids
      getNotice(noticeId).then(response => {
        this.form = response.data;
        if (this.form.coverUrl) {
          this.fileList.push({url: this.form.coverUrl});
        }
        this.open = true;
        this.title = "修改公告";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.coverUrl = this.fileUrls[0];
          console.info("fileList=>", this.fileUrls)
          if (this.form.noticeId != undefined) {
            updateNotice(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addNotice(this.form).then(response => {
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
      const noticeIds = row.noticeId || this.ids
      const noticeNames = row.noticeTitle || this.names
      this.$modal.confirm('是否确认删除标题为"' + noticeNames + '"的数据？').then(function () {
        return delNotice(noticeIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    }
  },
  watch: {
    //监听查看图片
    cover: {
      handler(newVal, oldVal) {
        console.info("newVal+>", newVal);
        console.info("oldVal+>", oldVal);
        if (newVal === '' || newVal === null || !newVal) {
          this.url = ''
          this.showUpload = true
        } else {
          this.url = newVal
          this.showUpload = false
        }
      },
      immediate: true,
      deep: true,
    },
  }
};
</script>
