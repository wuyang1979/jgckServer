<template>
  <div class="div_body">
    <div class="div_top">
      <div class="shopStatistics">
        <div id="shopStatistics" style="width: 100%; height: 200px"></div>
      </div>
      <div class="officeStatistics">
        <div id="officeStatistics" style="width: 100%; height: 200px"></div>
      </div>
      <div class="apartmentStatistics">
        <div id="apartmentStatistics" style="width: 100%; height: 200px"></div>
      </div>
    </div>
    <div class="div_top">
      <div>
        <div id="maintainStatistics" style="width: 100%;height: 300px">
        </div>
      </div>
      <div>
        <div id="rectificationStatistics" style="width: 100%;height: 300px"></div>
      </div>
      <div>
        <div id="serviceQualityStatistics" style="width: 100%;height: 270px"></div>
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
          <el-table-column prop="address" align="center" label="到期时间">
            <template slot-scope="scope">
              <span style="color: red">{{ scope.row.address }}</span>
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
    <div class="div_middle">
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
          <el-table-column prop="name" align="center" label="报修人"/>
          <el-table-column prop="date" align="center" label="房源号"/>
          <el-table-column prop="name" align="center" label="描述"/>
          <el-table-column prop="address" align="center" label="报修时间">
            <template slot-scope="scope">
              <span style="color: red">{{ scope.row.address }}</span>
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
      <el-form ref="rent" :model="rentForm" :rules="rentRules" label-width="80px">
        <el-form-item label="提醒天数" prop="rentRemind">
          <el-input oninput="if(value.length>3) value=value.slice(0,3)" type="number"
                    v-model="rentForm.rentRemind"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitRent">保 存</el-button>
        <el-button @click="cancelRent">取 消</el-button>
      </div>
    </el-dialog>


    <el-dialog :title="title" :visible.sync="setContract" width="450px" :show-close="false" append-to-body>
      <el-form ref="contract" :model="contractForm" :rules="contractRules" label-width="90px">
        <el-form-item label="公寓" prop="apartmentRemind">
          <el-input oninput="if(value.length>3) value=value.slice(0,3)" type="number"
                    v-model="contractForm.apartmentRemind"></el-input>
        </el-form-item>
        <el-form-item label="办公/商铺" prop="officeShopRemind">
          <el-input oninput="if(value.length>3) value=value.slice(0,3)" type="number"
                    v-model="contractForm.officeShopRemind"></el-input>
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
import {setRemindConfig} from "../../api/statistics/statistics";
import {getConfigKey} from "../../api/system/config";
import echarts from "echarts";

const spaceId = sessionStorage.getItem("spaceId")


export default {
  name: "Index",
  dicts: ['room_type', 'credential_type'],
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
      rentRules: {
        rentRemind: [{required: true, message: '提醒天数不能为空', trigger: 'blur'}],
      },
      contractRules: {
        apartmentRemind: [{required: true, message: '公寓提醒天数不能为空', trigger: 'blur'}],
        officeShopRemind: [{required: true, message: '办公/商铺提醒天数不能为空', trigger: 'blur'}],
      },
    };
  },
  methods: {


    // 获取提醒设置参数
    async getRemindConfig(key) {
      var result = null;
      await getConfigKey(key).then(respone => {
        result = JSON.parse(respone.msg);
      })
      return result;
    },

    // 获取证件列表
    getCredential() {
      getCredential(spaceId).then(respone => {
        this.credentialData = respone.rows;
      })
    },

    // 跳转合同列表页面
    goContractTable() {
      this.$router.push({
        path: '/business/contract/info',
      })
    },

    // 关闭合同提醒设置弹窗
    cancelContract() {
      this.setContract = false;
      this.contractForm = {};
    },

    // 合同到期时间提交
    submitContract() {
      this.$refs["contract"].validate(valid => {
        if (valid) {
          this.setContract = false;
          var params = {
            configKey: "hpContractRemindDays",
            configValue: {
              apartmentRemind: this.contractForm.apartmentRemind,
              officeShopRemind: this.contractForm.officeShopRemind
            }
          }
          setRemindConfig(params).then(respone => {
            if (respone.code == 200) {
              this.$modal.msgSuccess("保存成功")
            } else {
              this.$modal.msgError("保存失败")
            }
          }).catch()
        }
      });
    },

    // 设置合同到期提醒时间
    handleContract() {
      this.title = "合同到期提醒设置";
      this.getRemindConfig("hpContractRemindDays").then(res => {
        this.contractForm = res
      })
      this.setContract = true;
    },

    // 关闭租金到期时间弹框
    cancelRent() {
      this.setRent = false;
      this.rentForm = {};
    },

    // 租金到期时间提交
    submitRent() {
      this.$refs["rent"].validate(valid => {
        if (valid) {
          this.setRent = false;
          var params = {
            configKey: "hpRentRemindDays",
            configValue: {
              rentRemind: this.rentForm.rentRemind
            }
          }
          setRemindConfig(params).then(respone => {
            if (respone.code == 200) {
              this.$modal.msgSuccess("保存成功")
            } else {
              this.$modal.msgError("保存失败")
            }
          }).catch()
        }
      });
    },

    // 设置租金到期提醒时间
    handleRent() {
      this.title = "租金到期提醒设置";
      this.getRemindConfig("hpRentRemindDays").then(res => {
        this.rentForm = res;
      });
      this.setRent = true;
    },

    // 获取合同列表
    getContract() {
      getContract(spaceId).then(respone => {
        this.contractData = respone.rows;
      })
    },

    // 入驻统计
    getSettle() {
      getSettle(spaceId).then(respone => {
        this.shopForm = respone.shop;
        this.officeForm = respone.office;
        this.apartmentForm = respone.apartment;
        this.$nextTick(this.shopStatisticsChart());
        this.$nextTick(this.officeStatistics());
        this.$nextTick(this.apartmentStatistics());
      })
    },

    // 商铺饼图
    shopStatisticsChart() {
      // 基于准备好的dom，初始化echarts实例  这个和上面的main对应
      let myChart = this.$echarts.init(document.getElementById("shopStatistics"));
      // 指定图表的配置项和数据
      let option = {
        title: {
          text: '商铺统计',
          x: 'center',
          y: 15
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '80%',
          left: 'center',
          formatter: function (name) {
            let data = option.series[0].data
            let value;
            data.forEach(d => {
              if (name === d.name) {
                value = d.value
              }
            })
            return `${name}\t\t ${value}`
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
              borderColor: 'whitesmoke',
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
              '#EDEEF0', '#1A79FF'
            ]
          }
        ]
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },

    //维修柱状图
    maintainStatistics() {
      // 基于准备好的dom，初始化echarts实例  这个和上面的main对应
      let myChart = this.$echarts.init(document.getElementById("maintainStatistics"));

      let option;


      let data = [220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149];
      let yMax = 500;
      let dataShadow = [];
      for (let i = 0; i < data.length; i++) {
        dataShadow.push(yMax);
      }
      option = {
        title: {
          text: '维修数据统计',
          x: 'center',
          y: 15
        },
        xAxis: {
          data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
          axisLabel: {
            inside: true,
            color: '#fff',
            fontSize: 7
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false,
          },
          z: 10,
        },
        yAxis: {
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            color: '#999'
          }
        },
        dataZoom: [
          {
            type: 'inside'
          }
        ],
        series: [
          {
            type: 'bar',
            showBackground: true,
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {offset: 0, color: '#a5ebf3'},
                {offset: 0.5, color: '#57d1e0'},
                {offset: 1, color: '#1992a0'}
              ])
            },
            emphasis: {
              itemStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {offset: 0, color: '#1992a0'},
                  {offset: 0.7, color: '#57d1e0'},
                  {offset: 1, color: '#a5ebf3'}
                ])
              }
            },
            data: data
          }
        ]
      };
// Enable data zoom when user click bar.
      const zoomSize = 6;
      myChart.on('click', function (params) {
        console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)]);
        myChart.dispatchAction({
          type: 'dataZoom',
          startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
          endValue:
            dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
        });
      });

      option && myChart.setOption(option);
    },

    //服务质量柱状图
    serviceQualityStatistics() {
      // 基于准备好的dom，初始化echarts实例  这个和上面的main对应
      let myChart = this.$echarts.init(document.getElementById("serviceQualityStatistics"));

      let option = {
        title: {
          text: '服务质量统计',
          x: 'center',
          y: 15
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            axisTick: {
              alignWithLabel: true
            },
            axisLabel: {
              fontSize: 7
            }
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            // name: 'Direct',
            type: 'bar',
            barWidth: '60%',
            data: [10, 52, 200, 334, 390, 330, 220, 120, 155, 136, 200, 210]
          }
        ],
        color: ['#5eabe0', '#91cc75', '#fac858', '#ee6666', '#73c0de', '#3ba272', '#fc8452', '#9a60b4', '#ea7ccc'],
      };

      option && myChart.setOption(option);
    },

    //整改柱状图
    rectificationStatistics() {
      // 基于准备好的dom，初始化echarts实例  这个和上面的main对应
      let myChart = this.$echarts.init(document.getElementById("rectificationStatistics"));

      let option = {
        title: {
          text: '整改数据统计',
          x: 'center',
          y: 15
        },
        xAxis: {
          type: 'category',
          data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
          axisLabel: {
            fontSize: 7,
          },
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [120, 200, 150, 80, 70, 110, 130, 90, 80, 71, 40, 101],
            type: 'bar',
            showBackground: true,
            backgroundStyle: {
              color: 'rgba(180, 180, 180, 0.2)'
            }
          }
        ],
        color: ['#acf6f0', '#91cc75', '#fac858', '#ee6666', '#73c0de', '#3ba272', '#fc8452', '#9a60b4', '#ea7ccc'],
      };

      option && myChart.setOption(option);
    },

    // 办公饼图
    officeStatistics() {
      // 基于准备好的dom，初始化echarts实例  这个和上面的main对应
      let myChart = this.$echarts.init(document.getElementById("officeStatistics"));
      // 指定图表的配置项和数据
      let option = {
        title: {
          text: '办公室统计',
          x: 'center',
          y: 15
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '80%',
          left: 'center',
          formatter: function (name) {
            let data = option.series[0].data
            let value;
            data.forEach(d => {
              if (name === d.name) {
                value = d.value
              }
            })
            return `${name}\t\t ${value}`
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
              borderColor: 'whitesmoke',
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
              '#EDEEF0', '#1A79FF'
            ]
          }
        ]
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },

    // 公寓饼图
    apartmentStatistics() {
      // 基于准备好的dom，初始化echarts实例  这个和上面的main对应
      let myChart = this.$echarts.init(document.getElementById("apartmentStatistics"));
      // 指定图表的配置项和数据
      let option = {
        title: {
          text: '公寓统计',
          x: 'center',
          y: 15
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '80%',
          left: 'center',
          formatter: function (name) {
            let data = option.series[0].data
            let value;
            data.forEach(d => {
              if (name === d.name) {
                value = d.value
              }
            })
            return `${name}\t\t ${value}`
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
              borderColor: 'whitesmoke',
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
              '#EDEEF0', '#1A79FF'
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
    this.maintainStatistics();
    this.rectificationStatistics();
    this.serviceQualityStatistics();
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
  //background-color: snow;
}

.yellow {
  background-color: whitesmoke;
}

</style>

