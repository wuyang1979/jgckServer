<template>
  <div class="div_top">
    <div style="width: 100%;height: 28px;">
      <div class="div_button_right">
        <el-button @click="backRoom" size="mini" type="primary">返回</el-button>
      </div>
    </div>

    <div class="div_body">
      <div class="div_title">
        活动信息
      </div>
      <div class="div_room_info" style="height: 100px;padding-left: 10px">
        <el-row style="padding-bottom: 20px">
          <el-col :span="6">
            <span> 活动标题：</span>{{ activityForm.activityTitle }}
          </el-col>
          <el-col :span="6">
            <span> 活动地址：</span>{{ activityForm.activityAddress }}
          </el-col>
          <el-col :span="12">
            <span> 活动时间：</span>{{ activityForm.activityStartTime }} ~ {{ activityForm.activityEndTime }}
          </el-col>
        </el-row>
      </div>
      <div class="div_title">
        活动名单
      </div>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5" style="padding-left: 20px">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['activity:info:export']"
          >导出
          </el-button>
        </el-col>
      </el-row>
      <div class="div_history_table">
        <el-table v-loading="loading" :data="infoList" height="400">
          <el-table-column label="人员名称" align="center" prop="nickName"/>
          <el-table-column label="性别" align="center" prop="sex">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex"/>
            </template>
          </el-table-column>
          <el-table-column label="人员角色" align="center" prop="roleName"/>
          <el-table-column label="人员部门" align="center" prop="deptName"/>
          <el-table-column label="人员手机号" align="center" prop="phoneNumber"/>
          <el-table-column label="报名时间" align="center" prop="signTime">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.signTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

  </div>
</template>

<script>
import {listPerson} from "@/api/activity/relation";
import {getActivity} from "@/api/activity/info";

export default {
  name: "activity_person",
  dicts: ['sys_user_sex'],
  data() {
    return {

      activityForm: {},

      activityId: '',

      infoList: [],

      loading: false,

      queryParams: {
        activityId: null,
      }
    }
  },
  created() {
    this.activityId = this.$route.query.id;
    this.getActivity();
    this.getList();
  },
  methods: {

    handleExport() {
      this.queryParams.activityId = this.activityId;
      this.download('activity/relation/export', {
        ...this.queryParams
      }, `info_${new Date().getTime()}.xlsx`)
    },

    getList() {
      this.loading = true;
      listPerson(this.activityId).then(respone => {
        this.infoList = respone.rows;
        this.loading = false;
      })
    },


    getActivity() {
      getActivity(this.activityId).then(respone => {
        this.activityForm = respone.data;
      })
    },

    //返回房源列表
    backRoom() {
      this.$router.push({
        path: '/service/info'
      })
    }
  }
};
</script>

<style scoped>
.div_top {
  padding: 20px;
}

.div_button_right {
  float: right;
}


.div_title {
  font-size: 20px;
  font-weight: bold;
  padding: 10px;
}

.div_remark {
  width: 200px;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
}

</style>
