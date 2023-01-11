<template>
  <div class="app-container">
    <el-form class="el_form_search" :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
             label-width="68px">
      <el-form-item label="房源号" prop="roomName">
        <el-input
          v-model="queryParams.roomName"
          placeholder="请输入房源号"
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
          v-hasPermi="['room:info:add']"
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
          v-hasPermi="['room:info:edit']"
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
          v-hasPermi="['room:info:remove']"
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
      <!--          v-hasPermi="['room:info:export']"-->
      <!--        >导出-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="房源号" align="center" prop="roomName"/>
      <el-table-column label="房源状态" align="center" prop="roomStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.room_status" :value="scope.row.roomStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="房源类型" align="center" prop="roomType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.room_type" :value="scope.row.roomType"/>
        </template>
      </el-table-column>
      <el-table-column label="房源楼层" align="center" prop="floor">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.room_floor" :value="scope.row.floor"/>
        </template>
      </el-table-column>
      <el-table-column label="房源面积" align="center" prop="area"/>
      <el-table-column label="添加时间" align="center" prop="createTime"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
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
            v-hasPermi="['room:info:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['room:info:remove']"
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

    <!-- 添加或修改房源基本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="房源号" prop="roomName">
              <el-input v-model="form.roomName" placeholder="请输入房源号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="类型" prop="roomType">
              <el-select v-model="form.roomType" placeholder="请选择类型">
                <el-option
                  v-for="dict in dict.type.room_type"
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
            <el-form-item label="状态" prop="roomStatus">
              <el-select v-model="form.roomStatus" placeholder="请选择状态">
                <el-option
                  v-for="dict in dict.type.room_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="楼层" prop="floor">
              <el-select v-model="form.floor" placeholder="请选择楼层">
                <el-option
                  v-for="dict in dict.type.room_floor"
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
            <el-form-item label="面积" prop="area">
              <el-input oninput="if(value.length>4)value=value.slice(0,4)" type="number" v-model="form.area"
                        @keydown.native="channelInputLimit" placeholder="请输入面积"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="价格(元/月)" prop="price">
              <el-input oninput="if(value.length>9)value=value.slice(0,9)" type="number" v-model="form.price"
                        placeholder="请输入价格"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="底价(元/月)" prop="bottomPrice">
              <el-input oninput="if(value.length>9)value=value.slice(0,9)" type="number" v-model="form.bottomPrice"
                        placeholder="请输入底价"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="地址" prop="roomAddress">
              <el-input v-model="form.roomAddress" type="textarea" placeholder="请输入地址"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入备注"/>
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
import {listRoom, getRoom, delRoom, addRoom, updateRoom} from "@/api/room/info";
import {intCovString} from "@/api/common/common";
import {getToken} from "../../../utils/auth";
import {editFileByBusinessId, listFile} from "../../../api/activity/relation";
const spaceId=sessionStorage.getItem("spaceId")

export default {
  name: "Info",
  dicts: ['room_type', 'room_floor', 'room_status'],
  data() {
    return {

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

      //查询条件表单
      queryForm: {},

      // 遮罩层
      loading: true,

      // 选中数组
      ids: [],

      //删除文件名称集合
      names: [],

      // 非单个禁用
      single: true,

      // 非多个禁用
      multiple: true,

      // 显示搜索条件
      showSearch: true,

      // 总条数
      total: 0,

      // 房源基本信息表格数据
      infoList: [],

      // 弹出层标题
      title: "",

      // 是否显示弹出层
      open: false,

      //空间查询参数
      spaceQueryParams: {
        pageSize: 99999,
        spaceName: null,
        spaceAddress: null,
        delstatus: null,
      },

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roomId: null,
        spaceId: spaceId,
        roomType: null,
        roomName: null,
        floor: null,
        area: null,
        roomAddress: null,
        price: null,
        bottomPrice: null,
      },

      // 表单参数
      form: {},

      // 表单校验
      rules: {
        roomName: [{required: true, message: '请输入房源号', trigger: 'blur'}],
        roomType: [{required: true, message: '请选择类型', trigger: 'blur'}],
        roomStatus: [{required: true, message: '请选择状态', trigger: 'blur'}],
        floor: [{required: true, message: '请选择楼层', trigger: 'blur'}],
        area: [{required: true, message: '请输入面积', trigger: 'blur'}],
        price: [{required: true, message: '请输入价格', trigger: 'blur'}],
        roomAddress: [{max: 200, message: '字符长度不能超过200', trigger: 'blur'}],
        remark: [{max: 200, message: '字符长度不能超过200', trigger: 'blur'}],
      }
    };
  },

  created() {
    this.getList();
  },
  methods: {

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

    //不允许输入e和.
    channelInputLimit(e) {
      let key = e.key
      // 不允许输入'e'和'.'
      if (key === 'e' || key === '.') {
        e.returnValue = false
        return false
      }
      return true
    },

    //查看房源详情
    queryRoom(row) {
      this.$router.push({
        path: '/business/room/history',
        query: {
          id: row.roomId
        }
      })
    },

    /** 查询房源基本信息列表 */
    getList() {
      this.loading = true;
      listRoom(this.queryParams).then(response => {
        this.infoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.fileList=[];
      this.reset();
    },

    // 表单重置
    reset() {
      this.form = {
        roomId: null,
        spaceId: null,
        roomType: null,
        roomName: null,
        floor: null,
        area: null,
        remark: null,
        roomAddress: null,
        price: null,
        bottomPrice: null,
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
      this.ids = selection.map(item => item.roomId)
      this.names = selection.map(item => item.roomName)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加房源基本信息";
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const roomId = row.roomId || this.ids
      getRoom(roomId).then(response => {
        this.form = response.data;
        this.form.roomType = intCovString(this.form.roomType)//将数字类型转为字符串
        this.form.roomStatus = intCovString(this.form.roomStatus)//将数字类型转为字符串
        this.open = true;
        this.title = "修改房源基本信息";
      });
      this.listFile(roomId);
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.spaceId = spaceId;
          if (this.form.roomId != null) {
            updateRoom(this.form).then(response => {
              console.info("修改房源返回信息", response)
              this.editFileByBusinessId(this.form.roomId)
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.fileList=[];
            });
          } else {
            addRoom(this.form).then(response => {
              console.info("新增房源返回信息", response)
              this.editFileByBusinessId(response.data)
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.fileList=[];
            });
          }
        }
      });
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const roomIds = row.roomId || this.ids;
      const roomNames = row.roomName || this.names;
      this.$modal.confirm('是否确认删除房源号为"' + roomNames + '"的数据项？').then(function () {
        return delRoom(roomIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('room/info/export', {
        ...this.queryParams
      }, `info_${new Date().getTime()}.xlsx`)
    }
  },
  watch: {
    //监听查看图片
    cover: {
      handler(newVal, oldVal) {
        console.info("newVal+>", newVal);
        console.info("oldVal+>", oldVal);
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
}
;
</script>
<style scoped>


.el-form-item__content > .el-input, .el-textarea, .el-select {
  width: 90% !important;
}

.el-form-item__content > .el-textarea {
  width: 96% !important;
}


.el_form_search > .el-form-item {
  margin-bottom: 0px;
}

.div_search {
  float: right;
}

/deep/ .el-upload-dragger {
  width: 590px;
}

.el-row:nth-child(7) > .el-col > .el-form-item > .el-form-item__content {
  background-color: #00afff;
}

</style>
