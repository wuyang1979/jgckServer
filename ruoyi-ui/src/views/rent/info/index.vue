<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
             label-width="68px">
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
          v-hasPermi="['rent:info:add']"
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
          v-hasPermi="['rent:info:edit']"
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
          v-hasPermi="['rent:info:remove']"
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
          v-hasPermi="['rent:info:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="rentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="合同租金" align="center" prop="contractRent"/>
      <el-table-column label="收款期数" align="center" prop="collectionPeriod"/>
      <el-table-column label="租金所属期" align="center" prop="rentalPeriod"/>
      <el-table-column label="应收金额" align="center" prop="receivableMoney"/>
      <el-table-column label="应收日期" align="center" prop="receivableDate"/>
      <el-table-column label="实收金额" align="center" prop="practicalMoney"/>
      <el-table-column label="实收日期" align="center" prop="practicalDate"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['rent:info:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['rent:info:remove']"
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

    <!-- 添加或修改租金管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-row>
          <el-col :span="12">
<!--            <el-form-item label="合同编号" prop="contractId">-->
<!--              <el-input v-model="form.contractId" placeholder="请输入合同编号"/>-->
<!--            </el-form-item>-->
          </el-col>
          <el-col :span="12">
            <el-form-item label="合同租金" prop="contractRent">
              <el-input v-model="form.contractRent" placeholder="请输入合同租金"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="收款期数" prop="collectionPeriod">
              <el-input v-model="form.collectionPeriod" placeholder="请输入收款期数"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="租金所属期" prop="rentalPeriod">
              <el-input v-model="form.rentalPeriod" placeholder="请输入租金所属期"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="应收金额" prop="receivableMoney">
              <el-input v-model="form.receivableMoney" placeholder="请输入应收金额"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应收日期" prop="receivableDate">
              <el-input v-model="form.receivableDate" placeholder="请输入应收日期"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="实收金额" prop="practicalMoney">
              <el-input v-model="form.practicalMoney" placeholder="请输入实收金额"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="实收日期" prop="practicalDate">
              <el-input v-model="form.practicalDate" placeholder="请输入实收日期"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
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
import {
  listRent,
  getRent,
  delRent,
  addRent,
  updateRent
} from "@/api/rent/rent";

const spaceId = sessionStorage.getItem("spaceId")

export default {
  name: "Rent",
  data() {
    return {
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
      // 租金管理表格数据
      rentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contractId: null,
        spaceId: spaceId,
        contractRent: null,
        collectionPeriod: null,
        rentalPeriod: null,
        receivableMoney: null,
        receivableDate: null,
        practicalMoney: null,
        practicalDate: null,
      },
      // 表单参数
      form: {}
      ,
      // 表单校验
      rules: {}
    }
      ;
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询租金管理列表 */
    getList() {
      this.loading = true;
      listRent(this.queryParams).then(response => {
        this.rentList = response.rows;
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
        rentId: null,
        contractId: null,
        spaceId: spaceId,
        contractRent: null,
        collectionPeriod: null,
        rentalPeriod: null,
        receivableMoney: null,
        receivableDate: null,
        practicalMoney: null,
        practicalDate: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      }
      ;
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
      this.ids = selection.map(item => item.rentId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加租金管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const rentId =
        row.rentId || this.ids
      getRent(rentId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改租金管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.rentId != null) {
            updateRent(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRent(this.form).then(response => {
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
      const rentIds = row.rentId || this.ids;
      this.$modal.confirm('是否确认删除租金管理编号为"' + rentIds + '"的数据项？').then(function () {
        return delRent(rentIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('rent/info/export', {
        ...this.queryParams
      }, `rent_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style scoped>
.el-form-item__content > .el-input {
  width: 80% !important;
}

.el-form-item__content > .el-textarea {
  width: 91.5% !important;
}
</style>
