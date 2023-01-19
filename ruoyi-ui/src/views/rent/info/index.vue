<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
             label-width="68px">
      <el-form-item label="房源号" prop="roomName">
        <el-input
          v-model="queryParams.roomName"
          placeholder="请输入房源号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报修状态" prop="rentStatus">
        <el-select v-model="queryParams.rentStatus" placeholder="请选择报修状态"
                   clearable>
          <el-option
            v-for="dict in dict.type.pay_status"
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
      <el-table-column label="房源号" align="center" prop="roomName"/>
      <el-table-column label="缴费状态" align="center" prop="rentStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pay_status" :value="scope.row.rentStatus"/>
        </template>
      </el-table-column>
      <!--      <el-table-column label="合同租金(万元)" align="center" prop="contractRent">-->
      <!--        <template slot-scope="scope">-->
      <!--          <span>{{ scope.row.contractRent / 10000 }}</span>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <el-table-column label="收款期数" align="center" prop="collectionPeriod"/>
      <el-table-column label="租金所属期" align="center" prop="rentalPeriod" width="180"/>
      <el-table-column label="应收金额(万元)" align="center" prop="receivableMoney">
        <template slot-scope="scope">
          <span>{{ scope.row.receivableMoney / 10000 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="应收日期" align="center" prop="receivableDate"/>
      <!--      <el-table-column label="实收金额(万元)" align="center" prop="practicalMoney">-->
      <!--        <template slot-scope="scope">-->
      <!--          <span>{{ (scope.row.practicalMoney / 10000)==0?'':scope.row.practicalMoney / 10000 }}</span>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <!--      <el-table-column label="实收日期" align="center" prop="practicalDate"/>-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="queryRent(scope.row)"
            v-hasPermi="['rent:info:query']"
          >详情
          </el-button>
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
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="房源号" prop="roomId">
              <template>
                <el-select v-model="form.roomId"
                           :disabled="isQuery"
                           clearable
                           placeholder="请选择房源号">
                  <el-option
                    v-for="item in roomList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </template>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="缴费状态" prop="rentStatus">
              <el-select :disabled="isQuery" v-model="form.rentStatus" placeholder="请选择报修类型">
                <el-option
                  v-for="dict in dict.type.pay_status"
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
            <el-form-item label="合同租金(元)" prop="contractRent">
              <el-input type="number" oninput="if(value.length>9)value=value.slice(0,9)" :readonly="isQuery"
                        v-model="form.contractRent" placeholder="请输入合同租金"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="收款期数" prop="collectionPeriod">
              <el-input :readonly="isQuery" v-model="form.collectionPeriod" placeholder="请输入收款期数"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="租金所属期" prop="rentalPeriod">
              <el-input :readonly="isQuery" v-model="form.rentalPeriod" placeholder="请输入租金所属期"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应收金额(元)" prop="receivableMoney">
              <el-input type="number" oninput="if(value.length>9)value=value.slice(0,9)" :readonly="isQuery"
                        v-model="form.receivableMoney" placeholder="请输入应收金额"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="应收日期" prop="receivableDate">
              <el-input :readonly="isQuery" v-model="form.receivableDate" placeholder="请输入应收日期"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="实收金额(元)" prop="practicalMoney">
              <el-input type="number" oninput="if(value.length>9)value=value.slice(0,9)" :readonly="isQuery"
                        v-model="form.practicalMoney" placeholder="请输入实收金额"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="实收日期" prop="practicalDate">
              <el-input :readonly="isQuery" v-model="form.practicalDate" placeholder="请输入实收日期"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="备注" prop="remark">
              <el-input :readonly="isQuery" v-model="form.remark" type="textarea" placeholder="请输入内容"/>
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
  listRent,
  getRent,
  delRent,
  addRent,
  updateRent
} from "@/api/rent/rent";
import {listRoomNoScope} from "../../../api/room/info";

const spaceId = sessionStorage.getItem("spaceId")

export default {
  name: "Rent",
  dicts: ['pay_status'],
  data() {
    return {

      isQuery: false,

      roomQueryParams: {
        spaceId: spaceId
      },

      roomList: [],
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
        rentStatus: null,
        roonName:null
      },
      // 表单参数
      form: {}
      ,
      // 表单校验
      rules: {},
      noQueryRules: {
        roomId: [{required: true, message: '请选择房源号', trigger: 'change'}],
        rentStatus: [{required: true, message: '请选择缴费状态', trigger: 'change'}],
        contractRent: [{required: true, message: '请输入合同租金', trigger: 'blur'}],
        collectionPeriod: [{required: true, message: '请输入收款期数', trigger: 'blur'}],
        rentalPeriod: [{required: true, message: '请输入租金所属期', trigger: 'blur'}],
        receivableMoney: [{required: true, message: '请输入应收金额', trigger: 'blur'}],
        receivableDate: [{required: true, message: '请输入应收日期', trigger: 'blur'}],
      }
    }
      ;
  },
  created() {
    this.getList();
    this.initRoom();
  },
  methods: {

    queryRent(row) {
      getRent(row.rentId).then(res => {
        this.form = res.data;
        this.title = "房租详情";
        this.isQuery = true;
        this.rules = {};
        this.open = true;
      })
    },

    //初始化房源集合
    initRoom() {
      var list = [];
      listRoomNoScope(this.roomQueryParams).then(respone => {
        var rooms = respone.rows
        rooms.forEach(rs => {
          list.push({
              value: rs.roomId,
              label: rs.roomName,
            }
          )
        })
      })
      this.roomList = list
    },

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
        rentStatus: 0,
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
      this.names = selection.map(item => item.collectionPeriod)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.isQuery = false;
      this.rules = this.noQueryRules;
      this.title = "添加租金管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const rentId = row.rentId || this.ids
      getRent(rentId).then(response => {
        this.form = response.data;
        this.isQuery = false;
        this.rules = this.noQueryRules;
        this.open = true;
        this.title = "修改租金管理";
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
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const rentIds = row.rentId || this.ids;
      const names = row.collectionPeriod || this.names
      this.$modal.confirm('是否确认删除租金管理收款期数为"' + names + '"的数据项？').then(function () {
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
.el-form-item__content > .el-input, .el-select {
  width: 80% !important;
}

.el-form-item__content > .el-textarea {
  width: 91.5% !important;
}
</style>
