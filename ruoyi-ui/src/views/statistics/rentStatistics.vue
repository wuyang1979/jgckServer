<template>
  <div class="app-container">
    <el-form class="el_form_search" ref="queryForm" :model="queryParams" size="mini" :inline="true"
             label-width="68px">
      <el-form-item label="年份">
        <el-select v-model="queryParams.year">
          <el-option v-for="item in yearList"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>

    <el-table :data="rentList" class="el-table">
      <el-table-column label="预收费用(万元)" align="center" prop="receiveAdvance">
        <template slot-scope="scope">
          <span> {{ (scope.row.receiveAdvance) != 0 ? (scope.row.receiveAdvance) / 10000 : 0}}</span>
        </template>
      </el-table-column>
      <el-table-column label="实收费用(万元)" align="center" prop="actualReceipt">
        <template slot-scope="scope">
          <span> {{ (scope.row.actualReceipt) != 0 ? (scope.row.actualReceipt) / 10000 : 0}}</span>
        </template>
      </el-table-column>
      <el-table-column label="累计收入(万元)" align="center" prop="accumulative">
        <template slot-scope="scope">
          <span> {{ (scope.row.accumulative) != 0 ? (scope.row.accumulative) / 10000 : 0}}</span>
        </template>
      </el-table-column>

    </el-table>
  </div>
</template>


<script>

import {getNowDateTime} from "../../api/common/common";
import {getRentStatistics} from "../../api/statistics/statistics";
import {Fixed} from "../../api/common/common";

export default {
  name: "rentStatistics",
  data() {
    return {
      rentList: [],
      queryParams: {
        year: '',
      },
      yearList: [
        {label: '2021年', value: 2021},
        {label: '2022年', value: 2022},
        {label: '2023年', value: 2023},
        {label: '2020年', value: 2020},
      ],
    }
  },
  created() {
    this.queryParams.year = getNowDateTime().slice(0, 4);
  },
  watch: {
    'queryParams.year': 'handleChange'
  },
  methods: {
    handleChange() {
      this.rentList=[];
      getRentStatistics(this.queryParams.year).then(res => {
        this.rentList.push(res.data);
      })
    },
  }
}
</script>

<style scoped>
.el-form-item__content > .el-input, .el-select {
  width: 50% !important;
}

.el-table {
  width: 35%;
  position: absolute;
  top: 40%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>
