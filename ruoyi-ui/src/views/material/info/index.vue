<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
             label-width="68px">
      <el-form-item label="物料名称" prop="materialName">
        <el-input
          v-model="queryParams.materialName"
          placeholder="请输入物料名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料状态" prop="materialStatus">
        <el-select v-model="queryParams.materialStatus" placeholder="请选择物料状态"
                   clearable>
          <el-option
            v-for="dict in dict.type.material_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="物料类型" prop="materialType">
        <el-select v-model="queryParams.materialType" placeholder="请选择物料类型"
                   clearable>
          <el-option
            v-for="dict in dict.type.material_type"
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
          v-hasPermi="['material:add']"
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
          v-hasPermi="['material:edit']"
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
          v-hasPermi="['material:remove']"
        >删除
        </el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--&lt;!&ndash;        <el-button&ndash;&gt;-->
<!--&lt;!&ndash;          type="warning"&ndash;&gt;-->
<!--&lt;!&ndash;          plain&ndash;&gt;-->
<!--&lt;!&ndash;          icon="el-icon-download"&ndash;&gt;-->
<!--&lt;!&ndash;          size="mini"&ndash;&gt;-->
<!--&lt;!&ndash;          @click="handleExport"&ndash;&gt;-->
<!--&lt;!&ndash;          v-hasPermi="['material:export']"&ndash;&gt;-->
<!--&lt;!&ndash;        >导出&ndash;&gt;-->
<!--&lt;!&ndash;        </el-button>&ndash;&gt;-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="materialList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="物料名称" align="center" prop="materialName"/>
      <el-table-column label="物料状态" align="center" prop="materialStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.material_status" :value="scope.row.materialStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="物料类型" align="center" prop="materialType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.material_type" :value="scope.row.materialType"/>
        </template>
      </el-table-column>
      <el-table-column label="物料型号" align="center" prop="materialSize"/>
      <!--      <el-table-column label="入库数量" align="center" prop="warehouseIn"/>-->
      <!--      <el-table-column label="出库数量" align="center" prop="warehouseOut"/>-->
      <!--      <el-table-column label="结存数量" align="center" prop="balance"/>-->
      <!--      <el-table-column label="入库经手人" align="center" prop="warehouseInUser"/>-->
      <!--      <el-table-column label="出库领用人" align="center" prop="warehouseOutUser"/>-->
      <!--      <el-table-column label="出库审核人" align="center" prop="warehouseOutAudit"/>-->
      <!--      <el-table-column label="入库时间" align="center" prop="warehouseInTime" width="180">-->
      <!--        <template slot-scope="scope">-->
      <!--          <span>{{ parseTime(scope.row.warehouseInTime, '{y}-{m}-{d}') }}</span>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <!--      <el-table-column label="出库时间" align="center" prop="warehouseOutTime" width="180">-->
      <!--        <template slot-scope="scope">-->
      <!--          <span>{{ parseTime(scope.row.warehouseOutTime, '{y}-{m}-{d}') }}</span>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="queryMaterial(scope.row)"
            v-hasPermi="['material:query']"
          >详情
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['material:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['material:remove']"
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

    <!-- 添加或修改物料管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" :validateOnRuleChange="true">
        <el-row>
          <el-col :span="12">
            <el-form-item label="物料名称" prop="materialName">
              <el-input :readonly="isQuery" v-model="form.materialName" placeholder="请输入物料名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="物料状态" prop="materialStatus">
              <el-select :disabled="isQuery" v-model="form.materialStatus" placeholder="请选择物料状态">
                <el-option
                  v-for="dict in dict.type.material_status"
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
            <el-form-item label="物料类型" prop="materialType">
              <el-select :disabled="isQuery" v-model="form.materialType" placeholder="请选择物料类型">
                <el-option
                  v-for="dict in dict.type.material_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="物料型号" prop="materialSize">
              <el-input :readonly="isQuery" v-model="form.materialSize" placeholder="请输入物料型号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <div class="warehouse_in" v-if="!isOut">
          <el-row>
            <el-col :span="12">
              <el-form-item label="入库数量" prop="warehouseIn">
                <el-input :readonly="isQuery" type="number" oninput="if(value.length>9)value=value.slice(0,9)" v-model="form.warehouseIn"
                          placeholder="请输入入库数量"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="入库经手人" prop="warehouseInUser">
                <treeselect :disabled="isQuery"  v-model="form.warehouseInUser" :options="treeData" :disable-branch-nodes="true"
                            :show-count="true" placeholder="请选择入库经手人"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="入库时间" prop="warehouseInTime">
                <el-date-picker :disabled="isQuery" clearable
                                v-model="form.warehouseInTime"
                                type="date"
                                value-format="yyyy-MM-dd"
                                placeholder="请选择入库时间">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="结存数量" prop="balance">
                <el-input :readonly="isQuery" type="number" oninput="if(value.length>9)value=value.slice(0,9)" v-model="form.balance"
                          placeholder="请输入结存数量"/>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <div class="warehouse_out" v-if="isOut">
          <el-row>
            <el-col :span="12">
              <el-form-item label="出库数量" prop="warehouseOut">
                <el-input :readonly="isQuery" type="number" oninput="if(value.length>9)value=value.slice(0,9)" v-model="form.warehouseOut"
                          placeholder="请输入出库数量"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="出库领用人" prop="warehouseOutUser">
                <treeselect :disabled="isQuery" v-model="form.warehouseOutUser" :options="treeData" :disable-branch-nodes="true"
                            :show-count="true" placeholder="请选择出库领用人"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="出库时间" prop="warehouseOutTime">
                <el-date-picker :disabled="isQuery" clearable
                                v-model="form.warehouseOutTime"
                                type="date"
                                value-format="yyyy-MM-dd"
                                placeholder="请选择出库时间">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="出库审核人" prop="warehouseOutAudit">
                <treeselect :disabled="isQuery" v-model="form.warehouseOutAudit" :options="treeData" :disable-branch-nodes="true"
                            :show-count="true" placeholder="请选择出库审核人"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="结存数量" prop="balance">
                <el-input :readonly="isQuery" type="number" oninput="if(value.length>9)value=value.slice(0,9)" v-model="form.balance"
                          placeholder="请输入结存数量"/>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
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
  listMaterial,
  getMaterial,
  delMaterial,
  addMaterial,
  updateMaterial
} from "@/api/material/material";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {getTree} from "../../../api/room/look";

const spaceId = sessionStorage.getItem("spaceId")

export default {
  name: "Material",
  dicts: ['material_status', 'material_type'],
  components: {
    Treeselect
  },
  data() {
    return {

      treeData: [],

      isQuery: false,

      isOut: false,
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
      // 物料管理表格数据
      materialList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialName: null,
        materialStatus: null,
        materialType: null,
        materialSize: null,
        warehouseIn: null,
        warehouseOut: null,
        balance: null,
        warehouseInUser: null,
        warehouseOutUser: null,
        warehouseOutAudit: null,
        warehouseInTime: null,
        warehouseOutTime: null,
        spaceId: spaceId,
      },
      // 表单参数
      form: {}
      ,
      // 表单校验
      rules: {},

      inRules: {
        materialName: [{required: true, message: '请输入物料名称', trigger: 'blur'}],
        materialStatus: [{required: true, message: '请输入物料名称', trigger: 'blur'}],
        materialType: [{required: true, message: '请输入物料名称', trigger: 'blur'}],
        materialSize: [{required: true, message: '请输入物料名称', trigger: 'blur'}],
        warehouseIn: [{required: true, message: '请输入物料名称', trigger: 'blur'}],
        warehouseInUser: [{required: true, message: '请输入物料名称', trigger: 'blur'}],
        warehouseInTime: [{required: true, message: '请输入物料名称', trigger: 'blur'}],
        balance: [{required: true, message: '请输入物料名称', trigger: 'blur'}],
      },

      outRules: {
        materialName: [{required: true, message: '请输入物料名称', trigger: 'blur'}],
        materialStatus: [{required: true, message: '请输入物料名称', trigger: 'blur'}],
        materialType: [{required: true, message: '请输入物料名称', trigger: 'blur'}],
        materialSize: [{required: true, message: '请输入物料名称', trigger: 'blur'}],
        warehouseOut: [{required: true, message: '请输入物料名称', trigger: 'blur'}],
        warehouseOutUser: [{required: true, message: '请输入物料名称', trigger: 'blur'}],
        warehouseOutTime: [{required: true, message: '请输入物料名称', trigger: 'blur'}],
        warehouseOutAudit: [{required: true, message: '请输入物料名称', trigger: 'blur'}],
        balance: [{required: true, message: '请输入物料名称', trigger: 'blur'}],
      },
    }
      ;
  },
  created() {
    this.getList();
    this.init();
  },
  methods: {

    queryMaterial(row) {
      getMaterial(row.materialId).then(res => {
        this.form = res.data;
        this.title = "物料信息详情"
        this.isQuery = true;
        this.rules={};
        this.open = true;
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

    init() {
      getTree().then(response => {
        this.treeData.push(response.data)
        this.delectChildren(this.treeData)
      })
    },

    handleMaterialStatusChange() {
      if (this.form.materialStatus == 0) {
        this.restOut();
        this.isOut = false;
        if (!this.isQuery) {
          this.rules = this.inRules;
        }
      } else {
        this.restIn();
        this.isOut = true;
        if (!this.isQuery) {
          this.rules = this.outRules;
        }
      }
    },
    /** 查询物料管理列表 */
    getList() {
      this.loading = true;
      listMaterial(this.queryParams).then(response => {
        this.materialList = response.rows;
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
        materialId: null,
        materialName: null,
        materialStatus: 0,
        materialType: null,
        materialSize: null,
        warehouseIn: null,
        warehouseOut: null,
        balance: null,
        remark: null,
        warehouseInUser: null,
        warehouseOutUser: null,
        warehouseOutAudit: null,
        warehouseInTime: null,
        warehouseOutTime: null,
        spaceId: spaceId,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    restIn() {
      this.form.warehouseIn = null,
        this.form.warehouseInTime = null
    },
    restOut() {
      this.form.warehouseOut = null,
        this.form.warehouseInUser = null,
        this.form.warehouseOutUser = null,
        this.form.warehouseOutAudit = null,
        this.form.warehouseOutTime = null
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
      this.ids = selection.map(item => item.materialId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.isQuery=false;
      this.open = true;
      this.title = "添加物料管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.isQuery=false;
      const materialId = row.materialId || this.ids
      getMaterial(materialId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改物料管理";
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
            if (this.form.materialId != null) {
              updateMaterial(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addMaterial(this.form).then(response => {
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
      const materialIds = row.materialId || this.ids;
      this.$modal.confirm('是否确认删除物料管理编号为"' + materialIds + '"的数据项？').then(function () {
        return delMaterial(materialIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('material/export', {
        ...this.queryParams
      }, `material_${new Date().getTime()}.xlsx`)
    }
  },
  watch: {
    'form.materialStatus': 'handleMaterialStatusChange'
  }
}
;
</script>

<style scoped>
.el-form-item__content > .el-input, .el-select, .vue-treeselect {
  width: 80% !important;
}

.el-form-item__content > .el-textarea {
  width: 91.5% !important;
}
</style>
