<template>
  <div class="app-container">
    <el-form class="el_form_search" :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
             label-width="68px">
      <el-form-item label="签约时间">
        <el-date-picker clearable
                        v-model="queryTime"
                        type="daterange"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        :default-time="['00:00:00', '23:59:59']">
        </el-date-picker>
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
          v-hasPermi="['contract:info:add']"
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
          v-hasPermi="['contract:info:edit']"
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
          v-hasPermi="['contract:info:remove']"
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
      <!--          v-hasPermi="['contract:info:export']"-->
      <!--        >导出-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="合同类型" align="center" prop="contractType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.contract_type" :value="scope.row.contractType"/>
        </template>
      </el-table-column>
      <el-table-column label="合同状态" align="center" prop="contractStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.contract_status" :value="scope.row.contractStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="房屋名称" align="center" prop="roomName"/>
      <el-table-column label="房屋面积" align="center" prop="roomArea"/>
      <el-table-column label="年租金" align="center" prop="yearMoney"/>
      <el-table-column label="租赁日期" align="center" width="200">
        <template slot-scope="scope">
          <span>{{
              parseTime(scope.row.leaseStartTime, '{y}-{m}-{d}') + ' ~ ' + parseTime(scope.row.leaseEndTime, '{y}-{m}-{d}')
            }}</span>
        </template>
      </el-table-column>
      <el-table-column label="签约日期" align="center" prop="signTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.signTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="left" class-name="small-padding fixed-width" width="260px">
        <template slot-scope="scope">
          <el-button
            :disabled="scope.row.contractStatus=='1'"
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="terminateContract(scope.row)"
            v-hasPermi="['contract:info:query']"
          >终止
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="queryContract(scope.row)"
            v-hasPermi="['contract:info:query']"
          >详情
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['contract:info:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['contract:info:remove']"
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

    <!-- 添加或修改合同信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body @close="dialogCancel">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="合同类型" prop="contractType">
              <el-select :disabled="isQuery" @change="handleChangeContractType" v-model="form.contractType"
                         placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.contract_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合同状态" prop="contractStatus">
              <el-select :disabled="isQuery" v-model="form.contractStatus"
                         placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.contract_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="房屋名称" prop="roomId">
              <el-select
                :disabled="isQuery"
                v-model="form.roomId"
                filterable
                remote
                clearable
                @change="handleChangeRoomName"
                placeholder="请输入关键词"
                :remote-method="getRoomList"
                :loading="loading">
                <el-option
                  v-for="item in roomOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="房源状态" prop="roomStatus">
              <el-select disabled v-model="form.roomStatus"
                         placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.room_status"
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
            <el-form-item label="开始日期" prop="leaseStartTime">
              <el-date-picker :readonly="isQuery" clearable
                              v-model="form.leaseStartTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择租赁开始日期">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="结束日期" prop="leaseEndTime">
              <el-date-picker :readonly="isQuery" clearable
                              v-model="form.leaseEndTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择租赁结束日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>

          <el-col :span="12">
            <el-form-item label="房屋地址" prop="roomAddress">
              <el-input readonly v-model="form.roomAddress" placeholder="请输入房屋地址"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="房屋面积" prop="area">
              <el-input readonly v-model="form.area" placeholder="请输入房屋面积"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="承租方" prop="tenantsId">
              <el-select
                :disabled="isQuery"
                v-model="form.tenantsId"
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
              <i @click="insertTenants" style="color: #00afff;padding-left: 5px;cursor:pointer"
                 class="el-icon-circle-plus"></i>
            </el-form-item>
          </el-col>

          <div v-if="isApartment">
            <el-col :span="12">
              <el-form-item label="同住人" prop="togetherPersonId">
                <el-select
                  :disabled="isQuery"
                  v-model="form.togetherPersonId"
                  multiple
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
          </div>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="年租金" prop="yearMoney">
              <el-input type="number" :readonly="isQuery" v-model="form.yearMoney" placeholder="请输入年租金"/>
            </el-form-item>

          </el-col>
          <el-col :span="12">
            <el-form-item label="支付周期" prop="paymentPeriod">
              <el-select :disabled="isQuery" v-model="form.paymentPeriod"
                         placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.pay_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value">
                </el-option>
              </el-select>
              <!--              <el-input :readonly="isQuery" v-model="form.paymentPeriod" placeholder="请输入支付周期"/>-->
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="首次支付时间" prop="paymentFirstTime">
              <el-date-picker :readonly="isQuery" clearable
                              v-model="form.paymentFirstTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择首次支付时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="首次支付金额" prop="paymentFirstMoney">
              <el-input type="number" :readonly="isQuery" v-model="form.paymentFirstMoney"
                        placeholder="请输入首次支付金额"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="保证金" prop="securityDeposit">
              <el-input type="number" :readonly="isQuery" v-model="form.securityDeposit" placeholder="请输入保证金"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="签约日期" prop="signTime">
              <el-date-picker :readonly="isQuery" clearable
                              v-model="form.signTime"
                              type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              placeholder="请选择签约日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <div v-if="isInsert">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
        <div v-if="isQuery">
          <el-button type="primary" @click="submitFormQuery">确 定</el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listContract, getContract, delContract, addContract, updateContract} from "@/api/contract/info";
import {getRoom, updateRoom} from "@/api/room/info";
import {intCovString} from "@/api/common/common";
import {listTenantsNoScope} from "../../../api/tenants/info";
import {listRoomNoScope} from "../../../api/room/info";

export default {
  name: "Info",
  dicts: ['payment_type', 'room_type', 'contract_type', 'pay_type', 'contract_status','room_status'],
  data() {
    return {

      queryTime: [],

      isInsert: true,

      isQuery: false,

      isApartment: false,

      roomOptions: [],

      roomList: [],

      tenantsOptions: [],

      tenantsList: [],

      query: false,
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
      // 合同信息表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,

      roomQueryParams: {
        pageSize: 99999
      },

      tenantsQueryParams: {
        pageSize: 99999,
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contractNumber: null,
        signTimeStart: null,
        signTimeEnd: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        contractType: [{required: true, message: '请选择合同类型', trigger: 'blur'}],
        contractStatus: [{required: true, message: '请选择合同状态', trigger: 'blur'}],
        contractNumber: [{required: true, message: '请输入合同编号', trigger: 'blur'}],
        leaseStartTime: [{required: true, message: '请选择租赁开始日期', trigger: 'blur'}],
        leaseEndTime: [{required: true, message: '请选择租赁结束日期', trigger: 'blur'}],
        roomId: [{required: true, message: '请选择房屋名称', trigger: 'blur'}],
        tenantsId: [{required: true, message: '请选择承租方', trigger: 'blur'}],
        yearMoney: [{max: 10, message: '年租金不能超过10字符', trigger: 'blur'}],
        paymentPeriod: [{max: 4, message: '支付周期不能超过4字符', trigger: 'blur'}],
        paymentFirstMoney: [{max: 20, message: '首次支付金额不能超过20字符', trigger: 'blur'}],
        securityDeposit: [{max: 20, message: '保证金不能超过20字符', trigger: 'blur'}],
      },
    };
  },
  created() {
    this.getList();
    this.initTenants();
    this.initRoom()
    this.roomOptions = this.roomList;
    this.tenantsOptions = this.tenantsList;

  },
  methods: {

    changeRoomStatus(roomId, status) {
      getRoom(roomId).then(response => {
        var roomInfo = response.data;
        roomInfo.roomStatus = status;
        updateRoom(roomInfo);
      })
    },

    // 终止合同
    terminateContract(row) {
      this.$modal.confirm('是否确认终止已选择合同的数据项？').then(function () {
        row.contractStatus = 1;
        return updateContract(row)
      }).then(() => {
        //todo 将房源状态改为闲置
        this.changeRoomStatus(row.roomId, 0)
        this.getList();
        this.$modal.msgSuccess("终止成功");
      }).catch(() => {
      });
    },

    //筛选房源类型
    filterRooms() {
      this.roomOptions = this.roomList.filter(item => {
        if (this.form.contractType != null) {
          if (this.form.contractType == '0') {
            return item.type.indexOf('0') > -1;
          } else if (this.form.contractType == '1') {
            return item.type.indexOf('1') > -1;
          } else {
            return item.type.indexOf('2') > -1
          }
        }
      });
    },

    //筛选租客类型
    filterTenants() {
      this.tenantsOptions = this.tenantsList.filter(item => {
        if (this.form.contractType != null) {
          if (this.form.contractType == '2') {
            return item.type.indexOf('0') > -1;
          } else {
            return item.type.indexOf('1') > -1;
          }
        }
      });
    },

    //将同住人id转数组
    initTogetherPersonIdForm(val) {
      if (val = null && val.length > 0) {
        val = val.split(',')
      }
      return val;
    },

    //将同住人数组转字符串以，隔开
    initTogetherPersonIdParams() {
      var personIds = this.form.togetherPersonId;
      if (personIds != null && personIds.length > 0) {
        var str = '';
        for (var i = 0; i < personIds.length; i++) {
          str += personIds[i] + ','
        }
        this.form.togetherPersonId = str.substring(0, str.length - 1)
      } else {
        this.form.togetherPersonId = "";
      }

    },

    //添加租客跳转按钮
    insertTenants() {
      this.open=false;
      this.form={};
      this.$router.push("/business/tenants/info");
    },

    //处理预约时间参数
    initTimeParams() {
      var signTime = this.queryTime;
      if (signTime.length > 0) {
        this.queryParams.signTimeStart = signTime[0];
        this.queryParams.signTimeEnd = signTime[1];
      } else {
        this.queryParams.signTimeStart = null;
        this.queryParams.signTimeEnd = null;
      }
    },

    //关闭弹窗
    dialogCancel() {
      this.isInsert = true;
      this.isQuery = false;
    },

    //详情弹窗确定按钮
    submitFormQuery() {
      this.open = false;
      this.isInsert = true;
      this.isQuery = false;
      this.getList()
    },
    resetRoomForm(){
      this.form.roomId=null;
      this.form.roomStatus=null;
      this.form.roomAddress=null;
      this.form.area=null;
    },

    //合同类型选择框change方法
    handleChangeContractType(val) {
      this.resetRoomForm();
      this.filterTenants();
      this.filterRooms();
      if (val === '2') {
        this.isApartment = true;
      } else {
        this.isApartment = false;
        this.form.togetherPersonId = null
      }
    },

    //房源名选择框change方法
    handleChangeRoomName(val) {
      if (val.length > 0) {
        getRoom(val).then(respone => {
          if (respone.code === 200) {
            this.$set(this.form, 'roomAddress', respone.data.roomAddress);
            this.$set(this.form, 'area', respone.data.area);
            this.$set(this.form, 'spaceId', respone.data.spaceId);
            this.$set(this.form, 'roomStatus', intCovString(respone.data.roomStatus));
          }
        })
      }
    },

    //初始化roomList
    initRoom() {
      var list = [];
      listRoomNoScope(this.roomQueryParams).then(respone => {
        var rooms = respone.rows
        rooms.forEach(rs => {
          list.push({
              value: rs.roomId,
              label: rs.roomName,
              type: intCovString(rs.roomType),
            }
          )
        })
      })
      this.roomList = list
    },

    getRoomList(query) {
      this.loading = true;
      setTimeout(() => {
        this.loading = false;
        this.roomOptions = this.roomList.filter(item => {
          if (query != null) {
            return item.label.indexOf(query) > -1;
          }
        });
      }, 200);
    },

    initTenants() {
      var list = [];
      listTenantsNoScope(this.tenantsQueryParams).then(respone => {
        var tenants = respone.rows
        tenants.forEach(ts => {
          list.push({
              value: ts.tenantsId,
              label: ts.tenantsName,
              type: intCovString(ts.cardType),
            }
          )
        })
      })
      this.tenantsList = list
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


    //查询合同详情
    queryContract(row) {
      this.title = "合同基本信息详情";
      var roomId=this.form.roomId;
      this.form = row;
      this.isQuery = true;
      this.isInsert = false;
      this.open = true;
      this.form.contractType = intCovString(this.form.contractType);
      this.form.contractStatus = intCovString(this.form.contractStatus);
      this.form.togetherPersonId = this.initTogetherPersonIdForm(this.form.togetherPersonId);
      this.handleChangeRoomName(roomId);
      this.handleChangeContractType(this.form.contractType);
      this.form.roomId=roomId;
      console.info("contractForm=>",this.form)
      this.rules = {};
    },


    /** 查询合同信息列表 */
    getList() {
      this.initTimeParams();
      this.loading = true;
      listContract(this.queryParams).then(response => {
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
        contractId: null,
        contractNumber: null,
        contractType: '0',
        leaseStartTime: null,
        leaseEndTime: null,
        paymentType: null,
        paymentPeriod: null,
        paymentFirstTime: null,
        paymentFirstMoney: null,
        paymentDeadline: null,
        roomId: null,
        securityDeposit: null,
        tenantsId: null,
        signTime: null,
        yearMoney: null,
        contractStatus: '0'
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
      this.queryTime = [];
      this.queryParams.contractNumber = null;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.contractId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.filterTenants();
      this.filterRooms();
      this.open = true;
      this.title = "添加合同信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const contractId = row.contractId || this.ids
      getContract(contractId).then(response => {
        this.form = response.data;
        var roomId=this.form.roomId;
        this.form.contractType = intCovString(this.form.contractType);
        this.form.contractStatus = intCovString(this.form.contractStatus);
        this.form.togetherPersonId = this.initTogetherPersonIdForm(this.form.togetherPersonId);
        this.handleChangeRoomName(roomId);
        this.handleChangeContractType(this.form.contractType);
        this.form.roomId=roomId;
        this.open = true;
        this.title = "修改合同信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.initTogetherPersonIdParams();
          if (this.form.contractId != null) {
            updateContract(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.reset();
            });
          } else {
            addContract(this.form).then(response => {
              this.changeRoomStatus(this.form.roomId, 1);
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.reset();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const contractIds = row.contractId || this.ids;
      this.$modal.confirm('是否确认删除已选择合同的数据项？').then(function () {
        return delContract(contractIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('contract/info/export', {
        ...this.queryParams
      }, `info_${new Date().getTime()}.xlsx`)
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

.el-form-item__content > .el-input, .el-textarea, .el-select, .el-radio-group，.el-date-editor {
  width: 80% !important;
}

</style>
