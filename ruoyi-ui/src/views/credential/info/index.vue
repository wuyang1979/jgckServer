<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="用户名称" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="证件到期时间" prop="credentialExpireTime">
        <el-date-picker clearable
                        v-model="queryParams.credentialExpireTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择证件到期时间">
        </el-date-picker>
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
          v-hasPermi="['credential:info:add']"
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
          v-hasPermi="['credential:info:edit']"
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
          v-hasPermi="['credential:info:remove']"
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
      <!--          v-hasPermi="['credential:info:export']"-->
      <!--        >导出</el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="证件类型" align="center" prop="credentialType" width="120">
        <template slot-scope="scope">
          <g :options="dict.type.credential_type" :value="scope.row.credentialType"/>
        </template>
      </el-table-column>
      <el-table-column label="用户名称" align="center" prop="userName" width="160"/>
      <el-table-column label="证件到期时间" align="center" prop="credentialExpireTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.credentialExpireTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="添加时间" align="center" prop="createTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['credential:info:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['credential:info:remove']"
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

    <!-- 添加或修改证件基本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" :show-close="false" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户姓名" prop="bindUserId">
              <treeselect :disabled="isUser" v-model="form.bindUserId" :options="treeData" :disable-branch-nodes="true"
                          :show-count="true"
                          placeholder="请选择用户姓名"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="证件类型" prop="credentialType">
              <el-select :disabled="isType" v-model="form.credentialType" placeholder="请选择证件类型">
                <el-option
                  v-for="dict in dictCredential"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="证件到期时间" prop="credentialExpireTime">
              <el-date-picker clearable
                              v-model="form.credentialExpireTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择证件到期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listCredential, getCredential, delCredential, addCredential, updateCredential} from "@/api/credential/info";
import {getTree} from "@/api/room/look";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {intCovString} from "@/api/common/common";

const spaceId=sessionStorage.getItem("spaceId");

export default {
  name: "Info",
  dicts: ['credential_type'],
  components: {
    Treeselect
  },
  data() {
    return {

      isUser: false,

      isType: true,

      dictCredential: [],

      treeData: [],
      // 遮罩层
      loading: true,

      names: [],
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
      // 证件基本信息表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        spaceId:spaceId,
        bindUserId:null,
        credentialType: null,
        credentialExpireTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        bindUserId: [{required: true, message: '请选择用户姓名', trigger: 'change'}],
        credentialType: [{required: true, message: '请选择证件类型', trigger: 'change'}],
        credentialExpireTime: [{required: true, message: '请选择证件到期时间', trigger: 'change'}],
      }
    };
  },
  created() {
    this.getList();
    this.initTree();
  },
  watch: {
    'form.bindUserId': 'handleChangeUserId'
  },
  methods: {

    handleChangeUserId(val) {
      var params = {};
      params.pageSize = 999;
      params.bindUserId = val;
      var credentialList = [];
      listCredential(params).then(respone => {
        credentialList = respone.rows;
        if (val) {
          if (!this.form.update) {
            this.isType = false;
          }
          var oldDictType = this.dict.type.credential_type;
          var newDictType = oldDictType;
          if (this.form.credentialType) {
            oldDictType.forEach(old => {
              if (this.form.credentialType == old.value) {
                return
              }
              credentialList.forEach(cl => {
                if (old.value == cl.credentialType) {
                  newDictType = newDictType.filter((item) => {
                    return item.value != cl.credentialType
                  })
                }
              })
            })
            this.dictCredential = newDictType;
          } else {
            oldDictType.forEach(old => {
              credentialList.forEach(cl => {
                if (old.value == cl.credentialType) {
                  newDictType = newDictType.filter((item) => {
                    return item.value != cl.credentialType
                  })
                }
              })
            })
            this.dictCredential = newDictType;
          }
        } else {
          this.isType = true;
          // 没有选择用户直接全部id
          this.dictCredential = this.dict.type.credential_type;
        }
      })
    },

    //递归删除为0的children属性
    delectChildren(tree) {
      tree.forEach(t => {
        if (t.children.length > 0) {
          this.delectChildren(t.children)
        } else {
          delete t.children;
        }
      })
    },

    initTree() {
      getTree().then(response => {
        this.treeData.push(response.data)
        this.delectChildren(this.treeData)
      })
    },
    /** 查询证件基本信息列表 */
    getList() {
      this.loading = true;
      listCredential(this.queryParams).then(response => {
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
        credentialId: null,
        userId: null,
        credentialType: null,
        credentialExpireTime: null,
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
      this.ids = selection.map(item => item.credentialId)
      this.names = selection.map(item => item.userName)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.isUser = false;
      this.open = true;
      this.title = "添加证件基本信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.isUser = true;
      const credentialId = row.credentialId || this.ids
      getCredential(credentialId).then(response => {
        this.form = response.data;
        this.form.credentialType = intCovString(this.form.credentialType);
        this.form.update = "1"
        this.open = true;
        this.title = "修改证件基本信息"
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.spaceId=spaceId;
          if (this.form.credentialId != null) {
            updateCredential(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCredential(this.form).then(response => {
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
      const credentialIds = row.credentialId || this.ids;
      const names = row.userName || this.names;
      this.$modal.confirm('是否确认删除用户姓名为"' + names + '"的数据项？').then(function () {
        return delCredential(credentialIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('credential/info/export', {
        ...this.queryParams
      }, `info_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style scoped>
.el-col > .el-form-item > .el-form-item__content > .el-input, .el-select, .el-date-editor {
  width: 100% !important;
}
</style>
