<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="带看人" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入带看人名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户姓名" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="请输入客户姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户电话" prop="customerPhone">
        <el-input
          v-model="queryParams.customerPhone"
          placeholder="请输入客户电话"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="带看时间" prop="appointTime">
        <el-date-picker
          clearable
          v-model="appointTime"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd HH:mm:ss"
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
          v-hasPermi="['room:look:add']"
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
          v-hasPermi="['room:look:edit']"
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
          v-hasPermi="['room:look:remove']"
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
<!--          v-hasPermi="['look:look:export']"-->
<!--        >导出-->
<!--        </el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="lookList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="房源名称" align="center" prop="roomName"/>
      <el-table-column label="带看人" align="center" prop="nickName"/>
      <el-table-column label="客户姓名" align="center" prop="customerName"/>
      <el-table-column label="客户电话" align="center" prop="customerPhone"/>
      <el-table-column label="带看时间" align="center" prop="appointTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.appointTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="queryLook(scope.row)"
            v-hasPermi="['room:look:query']"
          >详情
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['room:look:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['room:look:remove']"
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

    <!-- 添加或修改房源带看基本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="房源名称" prop="roomId">
          <template>
            <el-select
              v-model="form.roomId"
              filterable
              remote
              clearable
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
          </template>
        </el-form-item>
        <el-form-item label="带看人" prop="userId">
          <treeselect v-model="form.userId" :options="treeData"  :disable-branch-nodes="true" :show-count="true" placeholder="请选择带看人" />
        </el-form-item>
        <el-form-item label="客户姓名" prop="customerName">
          <el-input v-model="form.customerName" placeholder="请输入客户姓名"/>
        </el-form-item>
        <el-form-item label="客户电话" prop="customerPhone">
          <el-input type="number" v-model="form.customerPhone" placeholder="请输入客户电话"/>
        </el-form-item>
        <el-form-item label="带看时间" prop="appointTime">
          <el-date-picker clearable
                          v-model="form.appointTime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          type="datetime"
                          placeholder="请选择带看时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


    <!--    房源带看详情-->
    <el-dialog :title="title" :visible.sync="query" width="700px" append-to-body>
      <el-form ref="queryForm" :model="queryForm" :rules="rules" label-width="80px">
        <el-form-item label="房源名称" prop="roomId">
          <template>
            <el-select disabled
                       v-model="queryForm.roomId"
                       filterable
                       remote
                       clearable
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
          </template>
        </el-form-item>
        <el-form-item label="带看人" prop="userId">
          <treeselect v-model="queryForm.userId" :options="treeData"  :disable-branch-nodes="true" :show-count="true" placeholder="请选择带看人" />
        </el-form-item>
        <el-form-item label="客户姓名" prop="customerName">
          <el-input readonly v-model="queryForm.customerName" placeholder="请输入客户姓名"/>
        </el-form-item>
        <el-form-item label="客户电话" prop="customerPhone">
          <el-input readonly v-model="queryForm.customerPhone" placeholder="请输入客户电话"/>
        </el-form-item>
        <el-form-item label="带看时间" prop="appointTime">
          <el-date-picker readonly clearable
                          v-model="queryForm.appointTime"
                          type="datetime"
                          placeholder="请选择带看时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormQuery">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listLook, getLook, delLook, addLook, updateLook, getTree} from "@/api/room/look";
import {listRoom} from "@/api/room/info";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Look",
  components:{
    Treeselect
  },
  data() {

    return {
      appointTime: [],
      roomQueryParams: {
        pageSize: 99999,
        spaceName: null,
        spaceAddress: null,
        delstatus: null,
      },
      roomOptions: [],
      roomList: [],
      treeData: [],


      queryForm: {},

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
      // 房源带看基本信息表格数据
      lookList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        customerName: null,
        customerPhone: null,
        appointTimeStart: null,
        appointTimeEnd: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [{required: true, message: '请选择带看人', trigger: 'change'}],
        customerName: [{required: true, message: '请输入租客名称', trigger: 'blur'}, {
          max: 50,
          message: "租客名称长度不能超过50字符",
          trigger: "blur"
        }],
        roomId: [{required: true, message: '请输入房源名称', trigger: 'blur'}, {
          max: 50,
          message: "房源名称长度不能超过50字符",
          trigger: "blur"
        }],
        customerPhone: [{required: true, message: '请输入手机号码', trigger: 'blur'}, {
          required: true,
          pattern: /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/,
          message: "请输入正确的手机号码",
          trigger: "blur"
        }],
      }
    };
  },
  created() {
    this.getList();
    this.init()
    this.initRoom()
  },
  methods: {
    //递归删除为0的children属性
    delectChildren(tree){
      tree.forEach(t=>{
        if (t.children.length>0){
          this.delectChildren(t.children)
        }else {
          delete t.children;
        }
      })
    },

    initRoom() {
      var list = [];
      listRoom(this.roomQueryParams).then(respone => {
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

    getRoomList(query) {
      this.loading = true;
      setTimeout(() => {
        this.loading = false;
        this.roomOptions = this.roomList.filter(item => {
          return item.label.indexOf(query) > -1;
        });
      }, 200);
    },

    init() {
      getTree().then(response => {
        this.treeData.push(response.data)
        console.info("treeData=>",this.treeData)
        this.delectChildren(this.treeData)
      })
    },

    // 空间详情确定按钮
    submitFormQuery() {
      this.query = false;
      this.queryForm = {};
    },
    // 查看空间详情
    queryLook(row) {
      this.query = true;
      this.title = "房源基本信息详情";
      this.queryForm = row;
      this.getRoomList("")
      var arr = [this.queryForm.userId];
      this.treeData.forEach(t => {
        this.$set(t, 'disabled', true)
      })
    },
    /** 查询房源带看基本信息列表 */
    getList() {
      this.loading = true;
      this.initAppointTime();
      listLook(this.queryParams).then(response => {
        this.lookList = response.rows;
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
        customerName: null,
        customerPhone: null,
        appointTime: null,
      };
      this.resetForm("form");
    },
    initAppointTime() {
      var time = this.appointTime;
      if (time.length > 0) {
        this.queryParams.appointTimeStart = time[0]
        this.queryParams.appointTimeEnd = time[1]
      }
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.appointTime = [];
      this.queryParams.appointTimeStart = null;
      this.queryParams.appointTimeEnd = null
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.lookId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加房源带看基本信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const lookId = row.lookId || this.ids
      getLook(lookId).then(response => {
        this.form = response.data;
        this.getRoomList("")
        var arr = [this.form.userId];
        console.info("this->", this.$refs)
        this.open = true;
        this.title = "修改房源带看基本信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.lookId != null) {
            updateLook(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLook(this.form).then(response => {
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
      const lookIds = row.lookId || this.ids;
      this.$modal.confirm('是否确认删除已选房源带看的数据项？').then(function () {
        return delLook(lookIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('room/look/export', {
        ...this.queryParams
      }, `look_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style scoped>
.el-form-item__content > .el-input, .el-textarea, .el-select, .el-tree ,.vue-treeselect {
  width: 75% !important;
}

.el-dialog__body > .el-form {
  margin-left: 10%;
}

.el_form_search >.el-form-item{
  margin-bottom: 0px;
}

.div_search{
  float: right;
}

</style>
