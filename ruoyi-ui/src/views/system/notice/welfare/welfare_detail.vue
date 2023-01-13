<template>
  <div class="div_top">
    <div style="width: 100%;height: 28px;">
      <div class="div_button_right">
        <el-button @click="backRoom" size="mini" type="primary">返回</el-button>
      </div>
    </div>

    <div class="div_body">
      <div class="div_title">
        {{ noticeForm.noticeTitle }}
      </div>
      <span>发布人：{{noticeForm.createBy}}</span>
      <span>发布时间：{{parseTime(noticeForm.releaseTime, '{y}-{m}-{d} {h}:{i}:{s}')}}</span>


      <div v-html="noticeForm.noticeContent">

      </div>
    </div>

  </div>
</template>

<script>
import {getRoom} from "@/api/room/info";
import {intCovString} from "@/api/common/common";
import {listTenantsHistory} from "@/api/tenants/info";
import {getNotice} from "@/api/system/notice";

export default {
  name: "notice_detail",
  dicts: ['room_floor'],
  data() {
    return {

      noticeForm: {},

      noticeId: '',

    }
  },
  created() {
    this.noticeId = this.$route.query.id;
    this.getNotice();
  },
  methods: {

    getNotice() {
      getNotice(this.noticeId).then(respone => {
        this.noticeForm = respone.data;
      })
    },


    //返回房源列表
    backRoom() {
      this.$router.push({
        path: '/service/program/welfare'
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
}

.div_body {
  text-align: center;
}

.div_body>span{
  padding-left: 10px;
  font-size: 14px;
  color: #D3D3D3;
}

</style>
