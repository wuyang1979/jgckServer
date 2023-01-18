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
      <el-form-item label="应缴金额" prop="payableMoney">
        <el-input
          v-model="queryParams.payableMoney"
          placeholder="应缴金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="缴费状态" prop="payStatus">
        <el-select v-model="queryParams.payStatus" placeholder="请选择缴费状态"
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
          v-hasPermi="['pay:add']"
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
          v-hasPermi="['pay:edit']"
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
          v-hasPermi="['pay:remove']"
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
      <!--          v-hasPermi="['pay:export']"-->
      <!--        >导出-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="payList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="房源号" align="center" prop="roomName"/>
      <el-table-column label="月份" align="center" prop="month" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.month, '{y}-{m}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="缴费类型" align="center" prop="payType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.payment_type" :value="scope.row.payType"/>
        </template>
      </el-table-column>
      <!--      <el-table-column label="上月参数" align="center" prop="lastMonthParameter"/>-->
      <!--      <el-table-column label="本月参数" align="center" prop="thisMonthParameter"/>-->
      <!--      <el-table-column label="单价" align="center" prop="unitPrice"/>-->
      <!--      <el-table-column label="本月使用" align="center" prop="thisMonthUse"/>-->
      <el-table-column label="应缴金额" align="center" prop="payableMoney"/>
      <el-table-column label="缴费状态" align="center" prop="payStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pay_status" :value="scope.row.payStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="缴费时间" align="center" prop="paymentTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.paymentTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200px" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="changePayStatus(scope.row)"
            v-hasPermi="['pay:edit']"
          >缴费
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="queryPay(scope.row)"
            v-hasPermi="['pay:query']"
          >详情
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pay:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pay:remove']"
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

    <!-- 添加或修改缴费基本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="房源号" prop="roomId">
              <template>
                <el-select :disabled="isQuery"
                           v-model="form.roomId"
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
            <el-form-item label="缴费类型" prop="payType">
              <el-select :disabled="isQuery" v-model="form.payType" placeholder="请选择缴费类型">
                <el-option
                  v-for="dict in dict.type.payment_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="月份" prop="month">
              <el-date-picker :disabled="isQuery" clearable
                              v-model="form.month"
                              type="month"
                              value-format="yyyy-MM"
                              placeholder="请选择月份">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="缴费状态" prop="payStatus">
              <el-select :disabled="isQuery" v-model="form.payStatus" placeholder="请选择缴费状态">
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
            <el-form-item v-if="!isService" label="上月参数" prop="lastMonthParameter">
              <el-input :readonly="isQuery" type="number" v-model="form.lastMonthParameter"
                        placeholder="请输入上月参数"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="!isService" label="本月参数" prop="thisMonthParameter">
              <el-input :readonly="isQuery" type="number" v-model="form.thisMonthParameter"
                        placeholder="请输入本月参数"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item v-if="!isService" label="单价" prop="unitPrice">
              <el-input :readonly="isQuery" type="number" v-model="form.unitPrice" placeholder="请输入单价"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="!isService" label="本月使用" prop="thisMonthUse">
              <el-input :readonly="isQuery" type="number" v-model="form.thisMonthUse" placeholder="请输入本月使用"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="应缴金额" prop="payableMoney">
              <el-input :readonly="isQuery" v-model="form.payableMoney" placeholder="请输入应缴金额"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="isQuery" label="缴费时间" prop="paymentTime">
              <el-date-picker clearable
                              :disabled="isQuery"
                              v-model="form.paymentTime"
                              type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              placeholder="请选择月份">
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
  listPay,
  getPay,
  delPay,
  addPay,
  updatePay
} from "@/api/pay/pay";
import {listRoomNoScope} from "../../../api/room/info";
import {parseTime} from "../../../utils/ruoyi";
import {getNowDateTime} from "../../../api/common/common";

const spaceId = sessionStorage.getItem("spaceId")

export default {
  name: "Pay",
  dicts: ['pay_status', 'payment_type'],
  data() {
    return {

      isAdd: false,

      // 是否详情
      isQuery: false,

      // 是否服务费
      isService: true,

      roomQueryParams: {
        spaceId: spaceId,
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
      // 缴费基本信息表格数据
      payList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        payableMoney: null,
        payStatus: null,
        roomName: null,
        spaceId: spaceId,
      },
      // 表单参数
      form: {}
      ,
      // 表单校验
      rules: {},
      serviceRules: {
        roomId: [{required: true, message: '请选择房源号', trigger: 'change'}],
        payType: [{required: true, message: '请选择支付类型', trigger: 'change'}],
        month: [{required: true, message: '请选择月份', trigger: 'change'}],
        payStatus: [{required: true, message: '请选择缴费状态', trigger: 'change'}],
        payableMoney: [{required: true, message: '请输入应缴金额', trigger: 'blur'}],
      },
      otherRules: {
        roomId: [{required: true, message: '请选择房源号', trigger: 'change'}],
        payType: [{required: true, message: '请选择支付类型', trigger: 'change'}],
        month: [{required: true, message: '请选择月份', trigger: 'blur'}],
        lastMonthParameter: [{required: true, message: '请输入上月参数', trigger: 'blur'}],
        thisMonthParameter: [{required: true, message: '请输入本月参数', trigger: 'blur'}],
        unitPrice: [{required: true, message: '请输入单价', trigger: 'blur'}],
        thisMonthUse: [{required: true, message: '请输入本月使用量', trigger: 'blur'}],
        payableMoney: [{required: true, message: '请输入应缴金额', trigger: 'blur  '}],
        payStatus: [{required: true, message: '请选择缴费状态', trigger: 'change'}],
      },
    }
      ;
  },
  created() {
    this.getList();
    this.initRoom();
  },
  methods: {

    changePayStatus(row) {
      let params = {};
      getPay(row.payId).then(res => {
        params = res.data;
        params.payStatus = 1;
      });
      this.$modal.confirm('是否确认将房源号为' + row.roomName + ',' + parseTime(row.month, '{y}-{m}') + '的缴费状态改为已缴费？').then(function () {
        return updatePay(params);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("修改成功");
      }).catch(() => {
      });
    },
    handleMonthParameterChange() {
      if (this.isAdd) {
        if (this.form.lastMonthParameter != null && this.form.thisMonthParameter != null) {
          this.form.thisMonthUse = (parseFloat(this.form.thisMonthParameter) - parseFloat(this.form.lastMonthParameter)).toFixed(2);
        } else {
          this.form.thisMonthUse
        }
      }
    },

    handleunitPriceChange() {
      if (this.isAdd) {
        if (this.form.unitPrice != null && this.form.thisMonthUse != null) {
          this.form.payableMoney = (parseFloat(this.form.unitPrice) * parseFloat(this.form.thisMonthUse)).toFixed(2);
        } else {
          this.form.payableMoney = ''
        }
      }
    },

    queryPay(row) {
      this.title = '缴费详情';
      this.rules = {};
      this.form = row;
      this.isQuery = true;
      this.isAdd = false;
      this.open = true;
    },

    handlePayTypeChange() {
      this.rules = {};
      if (this.form.payType == 2) {
        this.isService = true;
        this.rules = this.serviceRules;
      } else {
        this.isService = false;
        this.rules = this.otherRules;
      }
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
    /** 查询缴费基本信息列表 */
    getList() {
      this.loading = true;
      listPay(this.queryParams).then(response => {
        this.payList = response.rows;
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
        payId: null,
        month: null,
        lastMonthParameter: null,
        thisMonthParameter: null,
        unitPrice: null,
        thisMonthUse: null,
        payableMoney: null,
        payStatus: 0,
        payType: 0,
        spaceId: spaceId,
      };
      this.resetForm("form");
      this.isQuery = false;
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
      this.ids = selection.map(item => item.payId)
      this.names = selection.map(item => item.roomName)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.isAdd = true;
      this.open = true;
      this.title = "添加缴费基本信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const payId = row.payId || this.ids
      getPay(payId).then(response => {
        this.form = response.data;
        this.form.payStatus = this.form.payStatus;
        this.isAdd = false;
        this.open = true;
        this.title = "修改缴费基本信息";
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
            if (this.form.payId != null) {
              updatePay(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addPay(this.form).then(response => {
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
      const payIds = row.payId || this.ids;
      const names = row.roomName || this.names;
      this.$modal.confirm('是否确认删除缴费基本信息房源号为"' + names + '"的数据项？').then(function () {
        return delPay(payIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pay/export', {
        ...this.queryParams
      }, `pay_${new Date().getTime()}.xlsx`)
    }
  },
  watch: {
    'form.payType': 'handlePayTypeChange',
    'form.lastMonthParameter': 'handleMonthParameterChange',
    'form.thisMonthParameter': 'handleMonthParameterChange',
    'form.unitPrice': 'handleunitPriceChange',
    'form.thisMonthUse': 'handleunitPriceChange',
  }
};
</script>

<style scoped>
.el-form-item__content > .el-input, .el-select {
  width: 80% !important;
}
</style>
