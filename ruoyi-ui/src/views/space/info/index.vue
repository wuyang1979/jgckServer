<template>
  <div class="app-container">
    <el-form class="el_form_search" :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
             label-width="68px">
      <el-form-item label="空间名称" prop="spaceName">
        <el-input
          v-model="queryParams.spaceName"
          placeholder="请输入空间名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
    </el-form>
    <el-form>
      <el-form-item>
        <div class="div_search" style="float: right">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </div>
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
          v-hasPermi="['space:info:add']"
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
          v-hasPermi="['space:info:edit']"
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
          v-hasPermi="['space:info:remove']"
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
      <!--          v-hasPermi="['space:info:export']"-->
      <!--        >导出-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="空间名称" align="center" prop="spaceName"/>
      <el-table-column label="公司名称" align="center" prop="companyName"/>
      <el-table-column label="空间地址" align="center" prop="spaceAddress"/>
      <el-table-column label="添加时间" align="center" prop="createTime"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="querySpace(scope.row)"
            v-hasPermi="['space:info:query']"
          >详情
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['space:info:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['space:info:remove']"
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

    <!-- 添加或修改空间基本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="空间名称" prop="spaceName">
          <el-input v-model="form.spaceName" placeholder="请输入空间名称"/>
        </el-form-item>
        <el-form-item label="公司名称" prop="companyName">
          <el-input v-model="form.companyName" placeholder="请输入空间名称"/>
        </el-form-item>
        <el-form-item label="开户银行" prop="accountBank">
          <el-input v-model="form.accountBank" placeholder="请输入空间名称"/>
        </el-form-item>
        <el-form-item label="开户账号" prop="accountNum">
          <el-input v-model="form.accountNum" placeholder="请输入空间名称"/>
        </el-form-item>
        <el-form-item label="空间地址" prop="spaceAddress">
          <el-input v-model="form.spaceAddress" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


    <el-dialog :title="title" :visible.sync="query" width="700px" append-to-body>
      <el-form ref="queryForm" :model="queryForm" :rules="rules" label-width="80px">
        <el-form-item label="空间名称" prop="spaceName">
          <el-input readonly v-model="queryForm.spaceName" placeholder="请输入空间名称"/>
        </el-form-item>
        <el-form-item label="公司名称" prop="companyName">
          <el-input readonly v-model="queryForm.companyName" placeholder="请输入公司名称"/>
        </el-form-item>
        <el-form-item label="开户银行" prop="accountBank">
          <el-input readonly v-model="queryForm.accountBank" placeholder="请输入开户银行"/>
        </el-form-item>
        <el-form-item label="开户账号" prop="accountNum">
          <el-input readonly v-model="queryForm.accountNum" placeholder="请输入开户账号"/>
        </el-form-item>

        <el-form-item label="空间地址" prop="spaceAddress">
          <el-input readonly v-model="queryForm.spaceAddress" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input readonly v-model="queryForm.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormQuery">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listSpace, getSpace, delSpace, addSpace, updateSpace} from "@/api/space/info";
import {listRoom} from "@/api/room/info";
import {arrCovString, intCovString} from "@/api/common/common";

export default {
  name: "Info",
  dicts: ['del_status'],
  data() {
    return {

      queryForm: {},
      // 是否显示详情弹出层
      query: false,
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
      // 空间基本信息表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        spaceName: null,
        spaceAddress: null,
        delstatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        spaceName: [{required: true, message: '请选择空间名称', trigger: 'blur'}
          , {max: 50, message: "空间名称长度不能超过50字符", trigger: "blur"}],
        companyName: [{required: true, message: '请输入公司名称', trigger: 'blur'},
          {max: 50, message: "公司名称长度不能超过50字符"}],
        accountBank: [{required: true, message: '请输入开户银行', trigger: 'blur'},
          {max: 50, message: "开户银行长度不能超过50字符"}],
        accountNum: [{required: true, message: '请输入开户账号', trigger: 'blur'},
          // {pattern:'/^([1-9]{1})(\\d{15}|\\d{16}|\\d{18})$/',message: '请输入正确的银行卡号'}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    // 空间详情确定按钮
    submitFormQuery() {
      this.query = false;
      this.queryForm = {};
    },
    // 查看空间详情
    querySpace(row) {
      this.query = true;
      this.title = "空间基本信息详情";
      this.queryForm = row;
    },
    /** 查询空间基本信息列表 */
    getList() {
      this.loading = true;
      listSpace(this.queryParams).then(response => {
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
        spaceId: null,
        spaceName: null,
        spaceAddress: null,
        companyName: null,
        accountBank: null,
        accountNum: null,
        remark: null,
        delstatus: 0,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.ids = selection.map(item => item.spaceId)
      this.names = selection.map(item => item.spaceName)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加空间基本信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const spaceId = row.spaceId || this.ids
      getSpace(spaceId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改空间基本信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.spaceId != null) {
            updateSpace(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSpace(this.form).then(response => {
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
      const spaceIds = row.spaceId || this.ids;
      const spaceNames = row.spaceName || this.names;
      var params = {
        spaceId: arrCovString(spaceIds),
      }
      listRoom(params).then(respone => {
        if (respone.rows.length > 0) {
          this.$modal.msgError("该记录已被引用，无法被删除！")
        } else {
          this.$modal.confirm('是否确认删除空间名称为"' + spaceNames + '"的数据项？').then(function () {
            return delSpace(spaceIds);
          }).then(() => {
            this.getList();
            this.$modal.msgSuccess("删除成功");
          }).catch(() => {
          });
        }
      })

    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('space/info/export', {
        ...this.queryParams
      }, `info_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style scoped>
.el-form-item__content > .el-input, .el-textarea, .el-select {
  width: 75% !important;
}

.el-dialog__body > .el-form {
  margin-left: 10%;
}

.el_form_search > .el-form-item {
  margin-bottom: 0px;
}

.div_search {
  float: right;
}
</style>
