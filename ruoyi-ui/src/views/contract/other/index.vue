<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
             label-width="68px">
      <el-form-item label="项目名称" prop="projectName">
        <el-input
          v-model="queryParams.projectName"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户名称" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="请输入客户名称"
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
          v-hasPermi="['contract:other:add']"
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
          v-hasPermi="['contract:other:edit']"
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
          v-hasPermi="['contract:other:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="otherList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="项目名称" align="center" prop="projectName"/>
      <el-table-column label="客户名称" align="center" prop="customerName"/>
      <el-table-column label="缴纳费用" align="center" prop="payMoney"/>
      <el-table-column label="租赁时间" align="center" width="220">
        <template slot-scope="scope">
          <span>{{
              parseTime(scope.row.contractStartTime, '{y}-{m}-{d}') + '~' + parseTime(scope.row.contractEndTime, '{y}-{m}-{d}')
            }}</span>
        </template>
      </el-table-column>
      <el-table-column label="签约日期" align="center" prop="signTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.signTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="queryOther(scope.row)"
            v-hasPermi="['contract:other:query']"
          >详情
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['contract:other:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['contract:other:remove']"
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

    <!-- 添加或修改其他合同对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="客户名称" prop="customerId">
                <el-select
                  :disabled="isQuery"
                  v-model="form.customerId"
                  filterable
                  remote
                  clearable
                  placeholder="请输入关键词"
                  :remote-method="getTenantsList"
                  :loading="loading">
                  <el-option
                    v-for="item in tenantsOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目名称" prop="projectName">
              <el-input :readonly="isQuery" v-model="form.projectName" placeholder="请输入项目名称"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="缴纳周期" prop="payPeriod">
              <el-select :disabled="isQuery" v-model="form.payPeriod" placeholder="请选择缴纳周期">
                <el-option
                  v-for="dict in dict.type.pay_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="缴纳费用" prop="payMoney">
              <el-input type="number" oninput="if (value.length>10)value=value.slice(0,10)" :readonly="isQuery" v-model="form.payMoney" placeholder="请输入缴纳费用"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="开始时间" prop="contractStartTime">
              <el-date-picker clearable
                              :disabled="isQuery"
                              v-model="form.contractStartTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              :picker-options="pickerOptionsStart"
                              placeholder="请选择合同开始时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="截止时间" prop="contractEndTime">
              <el-date-picker clearable
                              :disabled="isQuery"
                              v-model="form.contractEndTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              :picker-options="pickerOptionsEnd"
                              placeholder="请选择合同截止时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="签约日期" prop="signTime">
              <el-date-picker clearable
                              :disabled="isQuery"
                              v-model="form.signTime"
                              type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              placeholder="请选择签约日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="备注" prop="remark">
              <el-input :disabled="isQuery" v-model="form.remark" type="textarea" placeholder="请输入内容"/>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button v-if="!isQuery" @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listOther,getOther,updateOther,addOther,delOther} from "../../../api/contract/other";
import {listTenantsNoScope} from "../../../api/tenants/info";
import {intCovString} from "../../../api/common/common";

const spaceId=sessionStorage.getItem("spaceId")

export default {
  name: "Other",
  dicts: ['pay_type'],
  data() {
    return {

      isQuery:false,

      tenantsOptions:[],

      tenantsList:[],
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
      // 其他合同表格数据
      otherList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        customerId: null,
        projectName: null,
        payMoney: null,
        contractStartTime: null,
        contractEndTime: null,
        signTime: null,
        spaceId: spaceId,
      },
      // 表单参数
      form: {}
      ,
      // 表单校验
      rules: {
        customerId: [{required:true,message:'请选择客户',trigger:'change'}],
        projectName: [{required:true,message:'请输入项目名称',trigger:'blur'}],
        payPeriod: [{required:true,message:'请选择缴纳周期',trigger:'change'}],
        payMoney: [{required:true,message:'请输入缴纳费用',trigger:'blur'}],
        contractStartTime: [{required:true,message:'请选择开始时间',trigger:'change'}],
        contractEndTime: [{required:true,message:'请选择结束时间',trigger:'change'}],
        signTime: [{required:true,message:'请选择签约时间',trigger:'change'}],
      },

      pickerOptionsStart: {//结束时间不能大于开始时间
      },
      pickerOptionsEnd: {
        disabledDate: (time) => {
          if (this.form.contractStartTime) {
            let beginDateVal = new Date(this.form.contractStartTime).getTime()
            if (beginDateVal) { // 等于的时候是临界值00:00:00
              return (time.getTime()) <= beginDateVal - 8.64e7
            } else {
              return time.getTime() < Date.now() - 8.64e7
            }
          } else {
            this.form.contractEndTime='';
            return true;
          }
        }
      },
    }
      ;
  },
  created() {
    this.getList();
    this.initTenants();
  },
  methods: {



    queryOther(row){
      this.title="合同详情";
      getOther(row.contractId).then(response=>{
        this.form=row;
        this.isQuery=true
        this.rules={};
        this.open=true;
      })
    },

    //筛选客户
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

    handleStartTimeChange(){
      // this.form.contractEndTime='';
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
      })
    },
    /** 查询其他合同列表 */
    getList() {
      this.loading = true;
      listOther(this.queryParams).then(response => {
        this.otherList = response.rows;
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
        contractId: null,
        contractNumber: null,
        customerId: null,
        projectName: null,
        payMoney: null,
        payPeriod: 1,
        contractStartTime: null,
        contractEndTime: null,
        signTime: null,
        remark: null,
        spaceId: spaceId,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
      this.isQuery=false;
      this.tenantsOptions=this.tenantsList;
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
      this.ids = selection.map(item => item.contractId)
      this.names = selection.map(item => item.projectName)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.tenantsOptions=this.tenantsList;
      this.open = true;
      this.title = "添加其他合同";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const contractId =
        row.contractId || this.ids
      getOther(contractId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改其他合同";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.contractId != null) {
            updateOther(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOther(this.form).then(response => {
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
      const contractIds = row.contractId || this.ids;
      const names=row.projectName || this.names;
      this.$modal.confirm('是否确认删除其他合同项目名称为"' + names + '"的数据项？').then(function () {
        return delOther(contractIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('contract/other/export', {
        ...this.queryParams
      }, `other_${new Date().getTime()}.xlsx`)
    }
  },
  watch:{
    'form.contractStartTime':'handleStartTimeChange'
  }
}
;
</script>

<style scoped>
.el-form-item__content > .el-input, .el-select {
  width: 80% !important;
}
.el-form-item__content > .el-textarea {
  width: 91% !important;
}
</style>
