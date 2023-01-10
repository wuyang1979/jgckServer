<template>
  <div class="app-container">
    <el-form class="el_form_search" :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
             label-width="68px">
      <el-form-item label="租客名称" label-width="auto" prop="tenantsName">
        <el-input
          v-model="queryParams.tenantsName"
          placeholder="请输入租客名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="证件类型" label-width="auto" prop="cardType">
        <el-select v-model="queryParams.cardType" placeholder="请选择证件类型" clearable>
          <el-option
            v-for="dict in dict.type.card_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="联系人姓名" label-width="auto" prop="contactName">
        <el-input
          v-model="queryParams.contactName"
          placeholder="请输入联系人姓名码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系人电话" label-width="auto" prop="contactPhone">
        <el-input
          v-model="queryParams.contactPhone"
          placeholder="请输入联系人电话"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

    </el-form>
    <el-form>
      <el-form-item>
        <div class="div_search">
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
          v-hasPermi="['tenants:info:add']"
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
          v-hasPermi="['tenants:info:edit']"
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
          v-hasPermi="['tenants:info:remove']"
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
      <!--          v-hasPermi="['tenants:tenants:export']"-->
      <!--        >导出-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tenantsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="租客名称" align="center" prop="tenantsName"/>
      <el-table-column label="证件类型" align="center" prop="cardType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.card_type" :value="scope.row.cardType"/>
        </template>
      </el-table-column>
      <el-table-column label="证件号码" align="center" prop="cardNumber"/>
      <el-table-column label="联系人名称" align="center" prop="contactName"/>
      <el-table-column label="联系人电话" align="center" prop="contactPhone"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="queryTenants(scope.row)"
            v-hasPermi="['tenants:info:query']"
          >详情
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['tenants:info:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['tenants:info:remove']"
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

    <!-- 添加或修改租客基本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="ruleForm" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="租客名称" prop="tenantsName">
          <el-input :readonly="isQuery" v-model="form.tenantsName" placeholder="请输入租客名称"/>
        </el-form-item>
        <el-form-item label="证件类型" prop="cardType">
          <el-select :disabled="isQuery" @change="handleChangeCardType" v-model="form.cardType">
            <el-option
              v-for="dict in dict.type.card_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="证件号码" prop="cardNumber">
          <el-input :readonly="isQuery" v-model="form.cardNumber" placeholder="请输入证件号码"/>
        </el-form-item>
        <el-form-item label="联系人名称" prop="contactName">
          <el-input :readonly="isQuery" v-model="form.contactName" placeholder="请输入联系人名称"/>
        </el-form-item>
        <el-form-item label="联系人电话" prop="contactPhone">
          <el-input :readonly="isQuery" v-model="form.contactPhone" placeholder="请输入联系人电话"/>
        </el-form-item>
        <div v-if="isShowCompany">
          <el-form-item label="注册地址" prop="registerAddress">
            <el-input :readonly="isQuery" v-model="form.registerAddress" placeholder="请输入注册地址"/>
          </el-form-item>
          <el-form-item label="办公地址" prop="officeAddress">
            <el-input :readonly="isQuery" v-model="form.officeAddress" placeholder="请输入办公地址"/>
          </el-form-item>
          <el-form-item label="电话" prop="phone">
            <el-input :readonly="isQuery" v-model="form.phone" placeholder="请输入电话"/>
          </el-form-item>
          <el-form-item label="传真" prop="fax">
            <el-input :readonly="isQuery" v-model="form.fax" placeholder="请输入传真"/>
          </el-form-item>
          <el-form-item label="邮编" prop="email">
            <el-input :readonly="isQuery" v-model="form.email" placeholder="请输入邮编"/>
          </el-form-item>
        </div>
        <div v-if="isShowPerson">
          <el-form-item label="性别" prop="sex">
            <el-select :disabled="isQuery" v-model="form.sex">
              <el-option
                v-for="dict in dict.type.sex_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="职业" prop="profession">
            <el-input :readonly="isQuery" v-model="form.profession" placeholder="请输入职业"/>
          </el-form-item>
          <el-form-item label="通讯地址" prop="communicationAddress">
            <el-input :readonly="isQuery" v-model="form.communicationAddress" placeholder="请输入通讯地址"/>
          </el-form-item>
          <el-form-item label="紧急联系人姓名" prop="emergencyContactName">
            <el-input :readonly="isQuery" v-model="form.emergencyContactName" placeholder="请输入紧急联系人姓名"/>
          </el-form-item>
          <el-form-item label="紧急联系人电话" prop="emergencyContactPhone">
            <el-input :readonly="isQuery" v-model="form.emergencyContactPhone" placeholder="请输入紧急联系人电话"/>
          </el-form-item>
          <el-form-item label="附加情况说明" prop="remark">
            <el-input :readonly="isQuery" v-model="form.remark" type="textarea" placeholder="请输入内容"/>
          </el-form-item>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button v-show="!isQuery" @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listTenants, getTenants, delTenants, addTenants, updateTenants} from "@/api/tenants/info";
import {intCovString} from "@/api/common/common";

export default {
  name: "Tenants",
  dicts: ['sex_type', 'card_type'],
  data() {
    return {

      // 是否为只读
      isQuery: false,

      // 是否显示公司字段
      isShowCompany: false,

      // 是否显示个人字段
      isShowPerson: true,

      // 遮罩层
      loading: true,

      // 选中id数组
      ids: [],

      // 选中名称数组
      names: [],

      // 非单个禁用
      single: true,

      // 非多个禁用
      multiple: true,

      // 显示搜索条件
      showSearch: true,

      // 总条数
      total: 0,

      // 租客基本信息表格数据
      tenantsList: [],

      // 弹出层标题
      title: "",

      // 是否显示弹出层
      open: false,

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tenantsName: null,
        cardType: null,
        cardNumber: null,
        registerAddress: null,
        officeAddress: null,
        phone: null,
        fax: null,
        email: null,
        contactPhone: null,
        contactName: null,
        sex: null,
        profession: null,
        communicationAddress: null,
        emergencyContactName: null,
        emergencyContactPhone: null,
        remark: null,
      },

      // 表单参数
      form: {},

      // 表单校验
      rules: {},

      // 公司校验规则
      companyRules: {
        tenantsName: [{required: true, message: '请输入租客名称', trigger: "blur"}, {
          max: 50,
          message: "租客名称长度不能超过50字符",
          trigger: "blur"
        }],
        cardType: [{required: true, message: '请选择证件类型', trigger: "change"}],
        cardNumber: [{required: true, message: '请输入证件号码', trigger: "blur"},
          {
            required: true,
            pattern: /^([0-9A-HJ-NPQRTUWXY]{2}\d{6}[0-9A-HJ-NPQRTUWXY]{10}|[1-9]\d{14})$/,
            message: '请输入正确的身份证号码',
            trigger: "blur"
          }],
        registerAddress: [{max: 50, message: "注册地址长度不能超过50字符", trigger: "blur"}],
        officeAddress: [{max: 50, message: "办公地址长度不能超过50字符", trigger: "blur"}],
        phone: [{max: 20, message: "电话长度不能超过20字符", trigger: "blur"}],
        fax: [{max: 20, message: "传真长度不能超过20字符", trigger: "blur"}],
        email: [{max: 40, message: "游戏长度不能超过40字符", trigger: "blur"}],
        contactName: [{required: true, message: '请输入联系人', trigger: "blur"},
          {max: 25, message: "联系人长度不能超过25字符", trigger: "blur"}],
        contactPhone: [{required: true, message: '请输入联系人号码', trigger: "blur"},
          {required: true, pattern: /^[1][3,4,5,7,8,9][0-9]{9}$/, message: '请输入正确的手机号码', trigger: 'blur',}],
      },

      // 个人校验规则
      personRules: {
        tenantsName: [{required: true, message: '请输入租客名称', trigger: "blur"},
          {max: 50, message: "租客名称长度不能超过50字符"}],
        cardType: [{required: true, message: '请选择证件类型', trigger: "change"}],
        cardNumber: [{required: true, message: '请输入证件号码', trigger: "blur"},
          {
            required: true,
            pattern: /^([1-6][1-9]|50)\d{4}(18|19|20)\d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
            message: '请输入正确的身份证号码',
            trigger: "blur"
          }],
        contactName: [{required: true, message: '请输入联系人名称', trigger: "blur"},
          {max: 50, message: "联系人名称长度不能超过50字符"}],
        contactPhone: [{required: true, message: '请输入联系人号码', trigger: "blur"},
          {required: true, pattern: /^[1][3,4,5,7,8,9][0-9]{9}$/, message: '请输入正确的手机号码', trigger: 'blur',}],
        sex: [{required: true, message: '请选择性别', trigger: "change"}],
        profession: [{max: 20, message: "职业长度不能超过20字符", trigger: "blur"}],
        communicationAddress: [{max: 50, message: "通讯地址长度不能超过50字符", trigger: "blur"}],
        emergencyContactName: [{max: 25, message: "紧急联系人长度不能超过25字符", trigger: "blur"}],
        emergencyContactPhone: [{max: 20, message: "紧急联系人号码不能超过20字符", trigger: "blur"},
          {required: false, pattern: /^[1][3,4,5,7,8,9][0-9]{9}$/, message: '请输入正确的手机号码', trigger: 'blur',}],
        remark: [{max: 250, message: "附加情况说明长度不能超过250字符", trigger: "blur"}],
      }

    };
  },
  created() {
    this.getList();
    this.rules = this.personRules;
  },
  methods: {

    // 证件类型变化事件
    handleChangeCardType(val) {
      if (val === '0') {
        this.isShowPerson = true;
        this.isShowCompany = false;
        this.rules = this.personRules;
      } else {
        this.isShowCompany = true;
        this.isShowPerson = false;
        this.rules = this.companyRules;
      }
      this.$nextTick(() => this.$refs.ruleForm.clearValidate());
    },

    // 查看租客详情
    queryTenants(row) {
      this.title = "租客基本信息详情";
      this.form = row;
      this.form.cardType = intCovString(row.cardType);
      this.form.sex = intCovString(row.sex);
      this.rules = {};
      this.isQuery = true;
      this.open = true;
    },

    /** 查询租客基本信息列表 */
    getList() {
      this.loading = true;
      listTenants(this.queryParams).then(response => {
        this.tenantsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
      this.$refs.ruleForm.resetFields()
    },

    // 表单重置
    reset() {
      this.form = {
        tenantsId: null,
        tenantsName: null,
        cardType: '0',
        cardNumber: null,
        registerAddress: null,
        officeAddress: null,
        phone: null,
        fax: null,
        email: null,
        contactPhone: null,
        contcatName: null,
        sex: '0',
        profession: null,
        communicationAddress: null,
        emergencyContactName: null,
        emergencyContactPhone: null,
        remark: null,
        delFlag: null,
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
      this.ids = selection.map(item => item.tenantsId)
      this.names = selection.map(item => item.tenantsName)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.title = "添加租客基本信息";
      this.isQuery = false;
      this.handleChangeCardType(this.form.cardType);
      this.open = true;
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const tenantsId = row.tenantsId || this.ids
      getTenants(tenantsId).then(response => {
        this.form = response.data;
        this.form.cardType = intCovString(this.form.cardType)
        this.form.sex = intCovString(this.form.sex)
        this.handleChangeCardType(this.form.cardType);
        this.title = "修改租客基本信息";
        this.isQuery = false;
        this.open = true;
      });
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["ruleForm"].validate(valid => {
        if (valid) {
          if (this.isQuery) {
            this.open = false;
          } else {
            if (this.form.tenantsId != null) {
              updateTenants(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addTenants(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
                this.$refs.ruleForm.resetFields()
              });
            }
          }
        }
      });
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const tenantsIds = row.tenantsId || this.ids;
      const tenantsNames = row.tenantsName || this.names;
      this.$modal.confirm('是否确认删除租客名称为"' + tenantsNames + '"的数据项？').then(function () {
        return delTenants(tenantsIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('tenants/info/export', {
        ...this.queryParams
      }, `tenants_${new Date().getTime()}.xlsx`)
    }

  }
};
</script>

<style scoped>
.el_form_search > .el-form-item {
  margin-bottom: 0px;
}

.div_search {
  float: right;
}

.el-form-item__content > .el-input, .el-textarea, .el-select, .el-radio-group {
  width: 75% !important;
  margin-left: 10px;
}

.el-dialog__body > .el-form {
  margin-left: 10%;
}


</style>
