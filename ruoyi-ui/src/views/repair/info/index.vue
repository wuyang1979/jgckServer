<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
             label-width="68px">
      <el-form-item label="房源号" prop="repairName">
        <el-input
          v-model="queryParams.repairName"
          placeholder="请输入房源号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报修状态" prop="repairStatus">
        <el-select v-model="queryParams.repairStatus" placeholder="请选择报修状态"
                   clearable>
          <el-option
            v-for="dict in dict.type.repair_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="报修类型" prop="repairType">
        <el-select v-model="queryParams.repairType" placeholder="请选择报修类型"
                   clearable>
          <el-option
            v-for="dict in dict.type.repair_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="处理人" prop="handleName">
        <el-input
          v-model="queryParams.handleName"
          placeholder="处理人"
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
          v-hasPermi="['repair:add']"
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
          v-hasPermi="['repair:edit']"
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
          v-hasPermi="['repair:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="repairList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="房源号" align="center" prop="roomName"/>
      <el-table-column label="报修类型" align="center" prop="repairType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.repair_type" :value="scope.row.repairType"/>
        </template>
      </el-table-column>
      <el-table-column label="报修状态" align="center" prop="repairStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.repair_status" :value="scope.row.repairStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="报修人" align="center" prop="repairName"/>
      <el-table-column label="报修人电话" align="center" prop="repairMobile"/>
      <el-table-column label="处理人" align="center" prop="handleName"/>
      <el-table-column label="报修时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="处理时间" align="center" prop="handlerTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.handlerTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="queryRoom(scope.row)"
            v-hasPermi="['room:info:query']"
          >详情
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['repair:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['repair:remove']"
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

    <!-- 添加或修改报修反馈基本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="报修类型" prop="repairType">
              <el-select :disabled="isQuery" v-model="form.repairType" placeholder="请选择报修类型">
                <el-option
                  v-for="dict in dict.type.repair_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报修人" prop="repairHandleId">
              <el-select
                :disabled="isQuery"
                v-model="form.repairHandleId"
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

        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="房源号" prop="roomId">
              <template>
                <el-select
                  :disabled="isUser"
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
            <el-form-item label="报修状态" prop="repairStatus">
              <el-select :disabled="isQuery" v-model="form.repairStatus" placeholder="请选择报修状态">
                <el-option
                  v-for="dict in dict.type.repair_status"
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
            <el-form-item label="处理人" prop="handleId">
              <treeselect v-model="form.handleId" :options="treeData" :disable-branch-nodes="true" :show-count="true"
                          placeholder="请选择处理人"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="处理时间" prop="handlerTime">
              <el-date-picker :disabled="isQuery" clearable
                              v-model="form.handlerTime"
                              type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              placeholder="请选择处理时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="图片" prop="remark">
              <el-upload
                ref="upload"
                :file-list="fileList"
                :headers="headers"
                :limit="4"
                :auto-upload="true"
                :action="uploadUrl"
                list-type="picture-card"
                accept=".jpg,.png"
                :on-exceed="handleExceed"
                :on-preview="handlePreview"
                :on-change="handleChange"
                :on-remove="handleRemove"
                multiple>
                <i class="el-icon-plus"></i>
                <div class="el-upload__tip" slot="tip">只能上传jpg/png文件</div>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="描述" prop="remark">
              <el-input :readonly="isQuery" v-model="form.remark" type="textarea" placeholder="请输入内容"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="查看图片"
      :close-on-click-modal="false"
      width="850px"
      :visible.sync="dialogVisible"
      append-to-body
    >
      <el-image :src="url" style="width: 100%; height: 400px" fit="contain"></el-image>
    </el-dialog>
  </div>
</template>

<script>
import {listRepair, getRepair, addRepair, updateRepair, delRepair} from "../../../api/repair/repair";
import {getToken} from "../../../utils/auth";
import {editFileByBusinessId, listFile} from "../../../api/activity/relation";
import {listTenantsNoScope} from "../../../api/tenants/info";
import {getTree} from "../../../api/room/look";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {listRoomNoScope, listRoomByTenantsId} from "../../../api/room/info";
import {intCovString} from "../../../api/common/common";
import {listUser} from "../../../api/system/user";

const spaceId = sessionStorage.getItem("spaceId")

export default {
  name: "Repair",
  components: {
    Treeselect
  },
  dicts: ['repair_type', 'repair_status'],
  data() {
    return {

      userList:[],

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

      //文件集合
      fileList: [],

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
      repairList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        spaceId: spaceId,
        repairName: null,
        repairStatus: null,
        repairType: null,
        handleName: null,
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
    this.init();
    this.initRoom();
    this.getUserList();
  },
  methods: {

    getListRoom() {
      let params = {
        repairHandleId: this.form.repairHandleId,
        spaceId: spaceId
      }
      listRoomByTenantsId(params).then(res => {
        let list = [];
        let data = res.rows;
        data.forEach(d => {
          list.push({
            label: d.roomName,
            value: d.roomId,
          })
        })
        this.roomList = list;
      })
    },

    getUserList() {
      let userQueryParams = {};
      listUser(userQueryParams).then(res => {
        let data = res.rows;
        let list = [];
        data.forEach(d => {
          list.push({
            label: d.nickName,
            value: intCovString(d.userId)
          })
        })
        this.userList = list
      })
    },

    handlerepairHandleIdChange() {
      let type = this.form.repairType;
      let id = this.form.repairHandleId;
      if (type == 3) {
      } else {
        this.getListRoom();
      }

    },

    handleRepairTypeChange() {
      let type = this.form.repairType;
      if (type == 3) {
        this.isUser = true;
        this.tenantsOptions=this.userList;
      } else {
        if (!this.isQuery) {
          this.isUser = false;
        }
        this.tenantsFilterType(type);
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

    tenantsFilterType(type) {
      this.tenantsOptions = this.tenantsList.filter(item => {
        if (type != null) {
          if (type == 2) {
            return item.type.indexOf("0") > -1;
          } else {
            return item.type.indexOf("1") > -1;
          }
        }
      });
    },

    queryRoom(row) {
      this.title = '租客反馈详情';
      this.isQuery = true;
      this.isUser = true;
      getRepair(row.repairId).then(respone => {
        this.listFile(row.repairId);
        if (respone.data.repairType == 3) {
          this.tenantsOptions=this.userList;
        } else {
          this.tenantsOptions = this.tenantsList;
        }
        listFile(row.repairId)
        this.form = respone.data;
        this.initRoom();
      })
      this.open = true
    },

    init() {
      getTree().then(response => {
        this.treeData.push(response.data)
        this.delectChildren(this.treeData)
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
      })
    },

    //删除文件
    handleRemove(file, fileList) {
      this.fileIds = [];
      fileList.forEach(fl => {
        this.fileIds.push(fl.id);
      })
    },

    //获取文件list
    listFile(businessId) {
      listFile(businessId).then(respone => {
        var list = [];
        var rows = respone.rows;
        rows.forEach(r => {
          var rep = {};
          rep = r;
          list.push({
            name: r.originalFileName,
            id: r.fileId,
            url: r.url,
          })
        })
        this.fileList = list;
      })
    },

    //文件绑定业务id
    editFileByBusinessId(businessId) {
      editFileByBusinessId(businessId, this.fileIds).then(respone => {
      })
    },

    //上传文件变化
    handleChange(file, fileList) {
      var fileIds = [];
      if (fileList.length > 0) {
        fileList.forEach(fl => {
          if (fl.id) {
            fileIds.push(fl.id)
          } else if (fl.response) {
            fileIds.push(fl.response.id);
          }
        })
      } else {
        fileIds = [];
      }
      this.fileIds = fileIds
    },

    //查看图片
    handlePreview(file) {
      this.cover = file.url || file.response.src
      this.dialogVisible = true
    },

    //最多上传文件个数
    handleExceed() {
      this.$modal.msgError('最多上传4张图片！');
    },

    /** 查询报修反馈基本信息列表 */
    getList() {
      this.loading = true;
      listRepair(this.queryParams).then(response => {
        this.repairList = response.rows;
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
        repairId: null,
        repairHandleId: null,
        repairStatus: 0,
        repairType: 3,
        remark: null,
        handleId: null,
        handlerTime: null,
        spaceId: spaceId,
      };
      this.resetForm("form");
      this.fileList = [];
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
      this.ids = selection.map(item => item.repairId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加报修反馈基本信息";
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.isQuery = false;
      const repairId = row.repairId || this.ids
      getRepair(repairId).then(response => {
        this.tenantsOptions = this.tenantsList;
        this.listFile(repairId);
        this.form = response.data;
        this.open = true;
        this.title = "修改报修反馈基本信息";
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
            if (this.form.repairId != null) {
              updateRepair(this.form).then(response => {
                editFileByBusinessId(this.form.repairId, this.fileIds);
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addRepair(this.form).then(response => {
                editFileByBusinessId(response.data, this.fileIds);
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
      const repairIds = row.repairId || this.ids;
      this.$modal.confirm('是否确认删除报修反馈基本信息编号为"' + repairIds + '"的数据项？').then(function () {
        return delRepair(repairIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('repair/export', {
        ...this.queryParams
      }, `repair_${new Date().getTime()}.xlsx`)
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

    'form.repairType': 'handleRepairTypeChange',

    'form.repairHandleId': 'handlerepairHandleIdChange'
  }
};
</script>
<style scoped>

.el-form-item__content > .el-input, .el-select, .vue-treeselect {
  width: 80% !important;
}

.el-form-item__content > .el-textarea {
  width: 91% !important;
}

</style>
