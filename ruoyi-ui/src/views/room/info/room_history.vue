<template>
  <div class="div_top">
    <div style="width: 100%;height: 28px;">
      <div class="div_button_right">
        <el-button @click="backRoom" size="mini" type="primary">返回</el-button>
      </div>
    </div>

    <div class="div_body">
      <div class="div_title">
        房源信息
      </div>
      <div class="div_room_info" style="height: 100px;padding-left: 10px">
        <el-form :model="roomInfo" ref="roomInfo" size="small" :inline="true" label-width="120px">
          <el-row>
            <el-col :span="6">
              <el-form-item label="房源号：">
                {{ roomInfo.roomName }}
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="状态：">
                <dict-tag :options="dict.type.room_status" :value="roomInfo.roomStatus"/>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="楼层：">
                <dict-tag :options="dict.type.room_floor" :value="roomInfo.floor"/>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="面积：">
                {{ roomInfo.area }}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="价格（元/月）：">
                {{ roomInfo.price }}
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="底价（元/月）：">
                {{ roomInfo.bottomPrice }}
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="地址：">
                {{ roomInfo.roomAddress }}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="18">
              <el-tooltip effect="dark"
                          :content="roomInfo.remark"
                          placement="top">
                <el-form-item class="form-remark" label="备注：">
                  {{ roomInfo.remark }}
                </el-form-item>
              </el-tooltip>
            </el-col>
          </el-row>

        </el-form>
      </div>

      <div class="div_title top20">
        历史租户
      </div>
      <div class="div_history_table">
                <el-table v-loading="loading" :data="infoList" height="400">
                  <el-table-column label="租客名称" align="center" prop="tenantsName" width="200"/>
                  <el-table-column label="证件号码" align="center" prop="cardNumber"/>
                  <el-table-column label="联系人名称" align="center" prop="contactName"/>
                  <el-table-column label="联系人电话" align="center" prop="contactPhone"/>
                  <el-table-column label="年租金" align="center" prop="yearMoney"/>
                  <el-table-column label="租赁日期" align="center">
                    <template slot-scope="scope">
                  <span>{{
                      parseTime(scope.row.leaseStartTime, '{y}-{m}-{d}') + '~' + parseTime(scope.row.leaseEndTime, '{y}-{m}-{d}')
                    }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column label="签约日期" align="center" prop="signTime" width="180">
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
import {getRoom} from "@/api/room/info";
import {intCovString} from "@/api/common/common";
import {listTenantsHistory} from "@/api/tenants/info";

export default {
  name: "room_history",
  dicts: ['room_floor', 'room_status'],
  data() {
    return {

      roomInfo: {},

      roomId: '',

      infoList: [],

      loading: false,

      queryParams: {
        pageNum: 1,
        pageSize: 999,
        roomId: this.roomId,
      }
    }
  },
  created() {
    this.roomId = this.$route.query.id;
    console.info("history=>", this.$route.query.id)
    this.getRoom();
    this.getList();
  },
  methods: {

    getList() {
      this.loading = true
      this.queryParams.roomId = this.roomId;
      listTenantsHistory(this.queryParams).then(respone => {
        this.infoList = respone.rows;
        this.loading = false;
        console.info("this.infoList=>", respone)
        console.info("this.infoList=>", this.infoList)
      })
    },

    getRoom() {
      getRoom(this.roomId).then(respone => {
        this.roomInfo = respone.data;
        this.roomInfo.floor = intCovString(this.roomInfo.floor)
        console.info("this.roomInfo=>", this.roomInfo)
      })
    },

    //返回房源列表
    backRoom() {
      this.$router.push({
        path: '/business/room/info'
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
  margin: 10px;
}

/deep/ .form-remark .el-form-item__content {
  color: red;
  width: 200px;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
}

.top20 {
  margin-top: 50px;
}

</style>
