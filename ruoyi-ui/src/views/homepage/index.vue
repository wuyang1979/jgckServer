<template>
  <div class="div_body">
    <div class="div_top">
      <div class="shopStatistics yellow">
        <div class="div_title">
          商铺统计
        </div>
        <div id="shopStatistics" style="width: 100%; height: 200px"></div>
      </div>
      <div class="officeStatistics yellow">
        <div class="div_title">
          办公室统计
        </div>
        <div id="officeStatistics" style="width: 100%; height: 200px"></div>
      </div>
      <div class="apartmentStatistics yellow">
        <div class="div_title">
          公寓统计
        </div>
        <div id="apartmentStatistics" style="width: 100%; height: 200px"></div>
      </div>

    </div>
    <div class="div_middle">
      <div class="half">
        <div class="div_title left">
          租金到期
          <el-button type="text">更多</el-button>
          <el-button @click="handleRent" size="mini">提醒设置</el-button>
        </div>
        <el-table
          :data="tableData"
          height="220"
          stripe
          style="width: 100%">
          <el-table-column prop="date" align="center" label="房源号">
          </el-table-column>
          <el-table-column prop="name" align="center" label="房源类型">
          </el-table-column>
          <el-table-column  prop="address" align="center" label="到期时间">
            <template slot-scope="scope">
              <span style="color: red">{{scope.row.address}}</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="half">
        <div class="div_title left">
          合同到期
          <el-button @click="goContractTable" type="text">更多</el-button>
          <el-button @click="handleContract" size="mini">提醒设置</el-button>
        </div>
        <el-table
          :data="contractData"
          height="220"
          stripe
          style="width: 100%">
          <el-table-column prop="roomName" label="房源号" align="center"/>
          <el-table-column prop="roomType" label="房源类型" align="center">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.room_type" :value="scope.row.roomType"/>
            </template>
          </el-table-column>
          <el-table-column prop="leaseEndTime" label="到期时间" align="center">
            <template slot-scope="scope">
              <span style="color: red">{{ parseTime(scope.row.leaseEndTime, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <div class="div_bottom">
      <div class="half">
        <div class="div_title left">
          报修提醒
          <!--          <el-button type="text">更多</el-button>-->
        </div>
        <el-table
          :data="tableDatas"
          height="220"
          stripe
          style="width: 100%">
          <el-table-column prop="date" align="center" label="房源号"/>
          <el-table-column prop="name" align="center" label="报修人名称"/>
          <el-table-column prop="address" align="center" label="报修日期">
            <template slot-scope="scope">
              <span style="color: red">{{scope.row.address}}</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="half">
        <div class="div_title left">
          证件提醒
          <!--          <el-button type="text">更多</el-button>-->
        </div>
        <el-table
          :data="credentialData"
          height="220"
          stripe
          style="width: 100%">
          <el-table-column prop="credentialType" label="证件名称" align="center">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.credential_type" :value="scope.row.credentialType"/>
            </template>
          </el-table-column>
          <el-table-column prop="userName" label="姓名" align="center">
          </el-table-column>
          <el-table-column prop="credentialExpireTime" align="center" label="到期日期">
            <template slot-scope="scope">
              <span style="color: red">
                {{ parseTime(scope.row.credentialExpireTime, '{y}-{m}-{d}') }}
              </span>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <el-dialog :title="title" :visible.sync="setRent" width="450px" :show-close="false" append-to-body>
      <el-form ref="rent" :model="rentForm" label-width="80px">
        <el-form-item label="提醒天数">
          <el-input type="number" v-model="rentForm.name"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitRent">保 存</el-button>
        <el-button @click="cancelRent">取 消</el-button>
      </div>
    </el-dialog>


    <el-dialog :title="title" :visible.sync="setContract" width="450px" :show-close="false" append-to-body>
      <el-form ref="contract" :model="contractForm" label-width="80px">
        <el-form-item label="公寓">
          <el-input type="number" v-model="contractForm.name"></el-input>
        </el-form-item>
        <el-form-item label="办公/商铺">
          <el-input type="number" v-model="contractForm.name"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitContract">保 存</el-button>
        <el-button @click="cancelContract">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

import {getContract, getCredential, getSettle} from "@/api/statistics/statistics";

export default {
  name: "Index",
  dicts:['room_type','credential_type'],
  data() {
    return {

      // 租金form表单
      rentForm: {},

      // 合同form表单
      contractForm: {},

      // 弹框标题
      title: '',

      // 租金设置弹框显示
      setRent: false,

      // 合同设置弹框显示
      setContract: false,

      contractData: [],

      credentialData: [],
      tableData: [{
        date: '7F',
        name: '办公',
        address: '2023-01-09'
      }, {
        date: '6F',
        name: '办公',
        address: '2023-01-15'
      }, {
        date: '8F',
        name: '办公',
        address: '2023-01-17'
      }],
      tableDatas: [{
        date: '102-1',
        name: '张三',
        address: '2023-01-01'
      }, {
        date: '103',
        name: '李萌',
        address: '2022-12-29'
      }, {
        date: '108',
        name: '沈七',
        address: '2022-12-27'
      }],
      shopForm: [],
      officeForm: [],
      apartmentForm: [],
    };
  },
  methods: {

    getCredential(){
      getCredential().then(respone=>{
        this.credentialData=respone.rows;
      })
    },

    goContractTable(){
      this.$router.push({
        path:'/business/contract/info',
      })
    },


    cancelContract() {
      this.setContract = false;
      this.contractForm = {};
    },

    submitContract() {
      this.$refs["contract"].validate(valid => {
        if (valid) {
          this.setContract = false;
          this.contractForm = {};
        }
      });
    },

    handleContract() {
      this.title = "合同到期提醒设置";
      this.setContract = true;
    },

    cancelRent() {
      this.setRent = false;
      this.rentForm = {};
    },

    submitRent() {
      this.$refs["rent"].validate(valid => {
        if (valid) {
          this.setRent = false;
          this.rentForm = {};
        }
      });
    },

    handleRent() {
      this.title = "租金到期提醒设置";
      this.setRent = true;
    },

    getContract() {
      getContract().then(respone => {
        this.contractData = respone.rows;
      })
    },

    //入驻统计
    getSettle() {
      getSettle().then(respone => {
        this.shopForm = respone.shop;
        this.officeForm = respone.office;
        this.apartmentForm = respone.apartment;
        this.$nextTick(this.shopStatisticsChart());
        this.$nextTick(this.officeStatistics());
        this.$nextTick(this.apartmentStatistics());
      })
    },

    shopStatisticsChart() {
      // 基于准备好的dom，初始化echarts实例  这个和上面的main对应
      let myChart = this.$echarts.init(document.getElementById("shopStatistics"));
      // 指定图表的配置项和数据
      let option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '80%',
          left: 'center',
          formatter:function (name){
            let data=option.series[0].data
            for (let i = 0; i < data.length; i++) {
              return `${name}\t\t${data[i].value}`
            }
          },
        },
        series: [
          {
            name: '商铺统计',
            type: 'pie',
            radius: ['35%', '50%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 40,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 16,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: this.shopForm,
            // 颜色
            color: [
              '#794af0', '#d6c221'
            ]
          }
        ]
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },
    officeStatistics() {
      // 基于准备好的dom，初始化echarts实例  这个和上面的main对应
      let myChart = this.$echarts.init(document.getElementById("officeStatistics"));
      // 指定图表的配置项和数据
      let option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '80%',
          left: 'center',
          formatter:function (name){
            let data=option.series[0].data
            for (let i = 0; i < data.length; i++) {
              return `${name}\t\t${data[i].value}`
            }
          },
        },
        series: [
          {
            name: '商铺统计',
            type: 'pie',
            radius: ['35%', '50%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 40,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 16,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: this.officeForm,
            // 颜色
            color: [
              '#794af0', '#d6c221'
            ]
          }
        ]
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },
    apartmentStatistics() {
      // 基于准备好的dom，初始化echarts实例  这个和上面的main对应
      let myChart = this.$echarts.init(document.getElementById("apartmentStatistics"));
      // 指定图表的配置项和数据
      let option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '80%',
          left: 'center',
          formatter:function (name){
            let data=option.series[0].data
            for (let i = 0; i < data.length; i++) {
              return `${name}\t\t${data[i].value}`
            }
          },
        },
        series: [
          {
            name: '商铺统计',
            type: 'pie',
            radius: ['35%', '50%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 40,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 16,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data:
            this.apartmentForm,
            // 颜色
            color: [
              '#794af0', '#d6c221'
            ]
          }
        ]
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },
  },
  mounted() {
    this.getSettle();
    this.getContract();
    this.getCredential();
  }
};
</script>

<style scoped lang="scss">
.div_top, .div_middle, .div_bottom {
  width: 100%;
  overflow: hidden;
}

.div_title {
  font-size: 18px;
  font-weight: bold;
  text-align: center;
  padding-top: 20px;
}

.div_top > div {
  float: left;
  width: 33%;
  margin: 0.15%;
}

.half {
  float: left;
  width: 49.6%;
  margin: 0.2%;
  background-color: whitesmoke;
}

//.div_middle {
//  padding-top: 20px;
//}

.left {
  text-align: left;
  padding-left: 20px;
  padding-bottom: 10px;
}

.div_title > .el-button {
  float: right !important;
  margin-left: 10px;
}

.div_body {
  margin: 10px;
  background-color: snow;
}

.yellow {
  background-color: whitesmoke;
}

</style>

