<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="资产名称" prop="assetName">
        <el-input v-model="queryParams.assetName" placeholder="请输入资产名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['asset:info:add']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['asset:info:edit']">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['asset:info:remove']">删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="assetList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="资产编码" align="center" prop="assetNo" />
      <el-table-column label="资产名称" align="center" prop="assetName" />
      <el-table-column label="资产型号" align="center" prop="assetModel" />
      <el-table-column label="使用人" align="center" prop="userTenantsName" />
      <el-table-column label="领用人" align="center" prop="receiverTenantsName" />
      <el-table-column label="存放地点" align="center" prop="storageLocation" />
      <el-table-column label="保管部门" align="center" prop="custodyDepartment" />
      <el-table-column label="采购时间" align="center" prop="purchaseTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.purchaseTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-tickets" @click="queryAsset(scope.row)"
            v-hasPermi="['asset:info:query']">详情
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['asset:info:edit']">修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['asset:info:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改报修反馈基本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="资产编码" prop="assetNo">
              <el-input :disabled="isQuery" v-model="form.assetNo" placeholder="请输入资产编码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资产名称" prop="assetName">
              <el-input :disabled="isQuery" v-model="form.assetName" placeholder="请输入资产名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="资产型号" prop="assetModel">
              <el-input :disabled="isQuery" v-model="form.assetModel" placeholder="请输入资产型号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="存放地点" prop="storageLocation">
              <el-input :disabled="isQuery" v-model="form.storageLocation" placeholder="请输入存放地点" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="使用人" prop="userTenantsId">
              <el-select :disabled="isQuery" v-model="form.userTenantsId" filterable remote clearable
                placeholder="请输入关键词" :remote-method="getTenantsList" :loading="loading">
                <el-option v-for="item in tenantsOptions" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="领用人" prop="receiverTenantsId">
              <el-select :disabled="isQuery" v-model="form.receiverTenantsId" filterable remote clearable
                placeholder="请输入关键词" :remote-method="getTenantsList" :loading="loading">
                <el-option v-for="item in tenantsOptions" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="保管部门" prop="custodyDepartment">
              <el-input :disabled="isQuery" v-model="form.custodyDepartment" placeholder="请输入保管部门" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="采购时间" prop="purchaseTime">
              <el-date-picker :disabled="isQuery" clearable v-model="form.purchaseTime" type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss" placeholder="请选择采购时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="描述" prop="remark">
              <el-input :readonly="isQuery" v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer" v-if="!isQuery">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
      <div slot="footer" class="dialog-footer" v-if="isQuery">
        <el-button type="primary" @click="cancel">确 认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listAsset, getAsset, addAsset, updateAsset, delAsset } from "../../../api/asset/asset";
  import { getToken } from "../../../utils/auth";
  import { listTenantsNoScope } from "../../../api/tenants/info";
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  import { listRoomNoScope, listRoomByTenantsId } from "../../../api/room/info";
  import { intCovString } from "../../../api/common/common";
  import { listUser } from "../../../api/system/user";

  const spaceId = sessionStorage.getItem("spaceId")

  export default {
    name: "Asset",
    components: {
      Treeselect
    },
    dicts: [],
    data() {
      return {

        isStatus0: true,

        userList: [],

        isUser: true,

        roomQueryParams: {
          spaceId: spaceId,
        },

        roomList: [],

        isQuery: false,

        treeData: [],

        tenantsOptions: [],

        tenantsQueryParams: {
          spaceId: spaceId,
        },

        tenantsList: [],

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

        //请求头
        headers: {
          Authorization: 'Bearer ' + getToken()
        },

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
        // 报修反馈基本信息表格数据
        assetList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          spaceId: spaceId,
          assetName: null,
        },
        // 表单参数
        form: {}
        ,
        // 表单校验
        rules: {}
      };
    },
    created() {
      this.getList();
      this.initTenants();
    },
    methods: {

      queryAsset(row) {
        this.title = '固定资产详情';
        this.isQuery = true;
        this.isUser = true;
        getAsset(row.assetId).then(respone => {
          this.tenantsOptions = this.tenantsList;
          this.form = respone.data;
        })
        this.open = true
      },

      getTenantsList(query) {
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
          this.tenantsOptions = this.tenantsList.filter(item => {
            if (query != null) {
              return item.label.indexOf(query) > -1;
            }
          });
        }, 200);
      },

      initTenants() {
        listTenantsNoScope(this.tenantsQueryParams).then(respone => {
          let list = respone.rows;
          let tenantList = [];
          let tenants = {};
          list.forEach(l => {
            tenants = {
              label: l.tenantsName,
              value: l.tenantsId,
              type: intCovString(l.cardType)
            };
            tenantList.push(tenants)
          })
          this.tenantsList = tenantList;
          this.tenantsOptions = this.tenantsList;
        })
      },

      /** 查询固定资产基本信息列表 */
      getList() {
        this.loading = true;
        listAsset(this.queryParams).then(response => {
          this.assetList = response.rows;
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
          assetId: null,
          assetNo: null,
          assetName: null,
          assetModel: null,
          userTenantsId: null,
          userTenantsName: null,
          receiverTenantsId: null,
          receiverTenantsName: null,
          storageLocation: null,
          custodyDepartment: null,
          purchaseTime: null,
          remark: null,
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
        this.ids = selection.map(item => item.assetId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },

      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加固定资产基本信息";
      },

      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        this.isQuery = false;
        const assetId = row.assetId || this.ids
        getAsset(assetId).then(response => {
          this.tenantsOptions = this.tenantsList;
          this.form = response.data;
          this.open = true;
          this.title = "修改固定资产基本信息";
        });
      },

      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.isQuery) {
              this.open = false;
              this.getList();
            } else {
              if (this.form.assetId != null) {
                updateAsset(this.form).then(response => {
                  this.$modal.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                });
              } else {
                addAsset(this.form).then(response => {
                  this.$modal.msgSuccess("新增成功");
                  this.open = false;
                  this.getList();
                });
              }
            }
          }
        });
      },

      /** 删除按钮操作 */
      handleDelete(row) {
        const assetIds = row.assetId || this.ids;
        this.$modal.confirm('是否确认删除？').then(function () {
          return delAsset(assetIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {
        });
      },

      /** 导出按钮操作 */
      handleExport() {
        this.download('asset/export', {
          ...this.queryParams
        }, `asset_${new Date().getTime()}.xlsx`)
      },

    },
    watch: {
      //监听查看图片
      cover: {
        handler(newVal, oldVal) {
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
<style scoped>
  .el-form-item__content>.el-input,
  .el-select,
  .vue-treeselect {
    width: 80% !important;
  }

  .el-form-item__content>.el-textarea {
    width: 91% !important;
  }
</style>