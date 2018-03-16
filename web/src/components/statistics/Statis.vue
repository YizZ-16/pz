<template>
  <div id = 'statis'>
    <el-container>
      <el-header>
        配载基础数据统计
      </el-header>
      <el-main>
        <el-row>
          起始时间：
          <el-date-picker
            v-model="startDate"
            type="date"
            placeholder="选择开始日期">
          </el-date-picker>
          结束时间：
          <el-date-picker
            v-model="endDate"
            type="date"
            placeholder="选择结束日期">
          </el-date-picker>
          <el-button>
            确认
          </el-button>
        </el-row>
        <el-row>
          <a>配载各航空公司数据统计</a>
          <div id="chartOne" :style="{width: '600px', height: '400px'}"></div>
          <!--<div id="chartTwo"></div>-->
        </el-row>
        <el-row>
          <a style="text-align: left">数据汇总</a>
          <el-table
            :data="tableData"
          >
            <el-table-column
              prop="records"
              label="记录"
              width="150px">
            </el-table-column>
            <el-table-column
              prop="flyNum"
              label="机号"
              width="150px">
            </el-table-column>
            <el-table-column
              prop="flySize"
              label="机型"
              width="150px"

            >
            </el-table-column>
          </el-table>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script>

  import ElContainer from "../../../node_modules/element-ui/packages/container/src/main.vue";
  import ElHeader from "../../../node_modules/element-ui/packages/header/src/main.vue";
  import ElMain from "../../../node_modules/element-ui/packages/main/src/main.vue";
  import ElRow from "element-ui/packages/row/src/row";
  import ElButton from "../../../node_modules/element-ui/packages/button/src/button.vue";

  export default {
    components: {
      ElButton,
      ElRow,
      ElMain,
      ElHeader,
      ElContainer},
    name:'statis',
    data () {
      return {
        startDate:'',
        endDate: '',
        tableData:[{
          records:18,
          flyNum:23,
          flySize:34
        }]
      }
    },
    mounted () {
      this.drawBar();
    },
    methods : {
      drawBar () {
        let myChart = this.$echarts.init(document.getElementById('chartOne'))
        let labelOption = {

        }
        // 绘制图表
        myChart.setOption({
          color: ['#003366', '#006699', '#4cabce'],
         // title: {text: '各航空公司配载数据统计'},
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          legend: {
            data: ['记录', '机号', '机型']
          },
          toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
              mark: {show: true},
              dataView: {show: true, readOnly: false},
              magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
              restore: {show: true},
              saveAsImage: {show: true}
            }
          },
          calculable: true,
          xAxis: {
            type: 'category',
            axisTick: {show: false},
            data: ["KK", "MU", "KU", "GZ", "YL", "MY"]
          },
          yAxis: {
//            type: {
//              type:'value'
//            }
          },
          series: [
            {
              name: '记录',
              type: 'bar',
              barGap: 0,
             // label: labelOption,
              data: [320, 332, 301, 334, 390, 332]
            },
            {
              name: '机号',
              type: 'bar',
              //label: labelOption,
              data: [220, 182, 191, 234, 290,12]
            },
            {
              name: '机型',
              type: 'bar',
              //label: labelOption,
              data: [150, 232, 201, 154, 190, 212]
            },
//            {
//              name: 'Wetland',
//              type: 'bar',
//              //label: labelOption,
//              data: [98, 77, 101, 99, 40]
//            }
          ]
        })
      }
    }
  }
</script>
<style>
  .el-table .cell, .el-table th div {
    padding-right: 10px;
    overflow: hidden;
    text-overflow: ellipsis;
    text-align: right;
  }

</style>
