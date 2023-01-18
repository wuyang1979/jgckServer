<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
             label-width="68px">
      <el-form-item label="楼层" prop="floor">
        <el-select v-model="queryParams.floor" placeholder="请选择楼层"
                   clearable>
          <el-option
            v-for="dict in dict.type.room_floor"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="位置" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入具体位置"
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
          v-hasPermi="['fire:add']"
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
          v-hasPermi="['fire:edit']"
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
          v-hasPermi="['fire:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="fireList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" prop="number"/>
      <el-table-column label="楼层" align="center" prop="floor">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.room_floor" :value="scope.row.floor"/>
        </template>
      </el-table-column>
      <el-table-column label="位置" align="center" prop="address"/>
      <el-table-column label="型号" align="center" prop="size"/>
      <el-table-column label="生产日期" align="center" prop="productionTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.productionTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="queryFire(scope.row)"
            v-hasPermi="['fire:query']"
          >详情
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['fire:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['fire:remove']"
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

    <!-- 添加或修改消防安全对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="编号" prop="number">
              <el-input :readonly="isQuery" v-model="form.number" placeholder="请输入编号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="楼层" prop="floor">
              <el-select :disabled="isQuery" v-model="form.floor" placeholder="请选择楼层">
                <el-option
                  v-for="dict in dict.type.room_floor"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="位置" prop="address">
              <el-input :readonly="isQuery" v-model="form.address" placeholder="请输入具体位置"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="型号" prop="size">
              <el-input :readonly="isQuery" v-model="form.size" placeholder="请输入型号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="数量" prop="quantity">
              <el-input type="number" :readonly="isQuery" v-model="form.quantity" placeholder="请输入数量"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="采购人" prop="purchaser">
              <el-input :readonly="isQuery" v-model="form.purchaser" placeholder="请输入采购人"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="有效期" prop="validityPeriod">
              <el-input :readonly="isQuery" v-model="form.validityPeriod" placeholder="请输入有效期"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生产日期" prop="productionTime">
              <el-date-picker clearable
                              :disabled="isQuery"
                              v-model="form.productionTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择生产日期">
              </el-date-picker>
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
import {
  listFire,
  getFire,
  delFire,
  addFire,
  updateFire
} from "@/api/fire/fire";

const spaceId = sessionStorage.getItem("spaceId");

export default {
  name: "Fire",
  dicts: ['room_floor'],
  data() {
    return {

      isQuery: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],

      name: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 消防安全表格数据
      fireList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        floor: null,
        address: null,
        quantity: null,
        productionTime: null,
        validityPeriod: null,
        purchaser: null,
        size: null,
        number: null,
        spaceId: spaceId,
      },
      // 表单参数
      form: {}
      ,
      // 表单校验
      rules: {
        number: [{required: true, message: '请输入编号', trigger: 'blur'}],
        floor: [{required: true, message: '请输入编号', trigger: 'blur'}],
        address: [{required: true, message: '请输入编号', trigger: 'blur'}],
        size: [{required: true, message: '请输入编号', trigger: 'blur'}],
        quantity: [{required: true, message: '请输入编号', trigger: 'blur'}],
        purchaser: [{required: true, message: '请输入编号', trigger: 'blur'}],
        validityPeriod: [{required: true, message: '请输入编号', trigger: 'blur'}],
        productionTime: [{required: true, message: '请输入编号', trigger: 'blur'}],
      }
    }
      ;
  },
  created() {
    this.getList();
  },
  methods: {
    queryFire(row) {
      this.title = "消防安全详情";
      this.reset();
      getFire(row.fireId).then(res => {
        this.form = res.data;
        this.isQuery = true;
        this.open = true;
      })
    },

    /** 查询消防安全列表 */
    getList() {
      this.loading = true;
      listFire(this.queryParams).then(response => {
        this.fireList = response.rows;
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
        fireId: null,
        floor: null,
        address: null,
        quantity: null,
        productionTime: null,
        validityPeriod: null,
        purchaser: null,
        size: null,
        number: null,
        spaceId: spaceId,
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
      this.ids = selection.map(item => item.fireId)
      this.names = selection.map(item => item.number)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.isQuery = false;
      this.open = true;
      this.title = "添加消防安全";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const fireId = row.fireId || this.ids
      getFire(fireId).then(response => {
        this.form = response.data;
        this.isQuery = false;
        this.open = true;
        this.title = "修改消防安全";
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
            if (this.form.fireId != null) {
              updateFire(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addFire(this.form).then(response => {
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
      const fireIds = row.fireId || this.ids;
      const numbers = row.number || this.names;
      this.$modal.confirm('是否确认删除消防安全编号为"' + numbers + '"的数据项？').then(function () {
        return delFire(fireIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('fire/export', {
        ...this.queryParams
      }, `fire_${new Date().getTime()}.xlsx`)
    }
  }
}
;
</script>
<style scoped>
.el-form-item__content > .el-input, .el-select {
  width: 80% !important;
}
</style>
