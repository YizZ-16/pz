<template>
  <div id = 'statis'>
    <el-container>
      <el-header style="height: 20px">
        配载基础数据统计
      </el-header>
      <el-main>
        <el-row>
          <el-col :span="8">
            <el-date-picker
              v-model="startDate"
              type="date"
              value-format="timestamp"
              placeholder="选择开始日期">
            </el-date-picker>
          </el-col>
          <el-col :span="6">
            <el-date-picker
              v-model="endDate"
              type="date"
              value-format="timestamp"
              placeholder="选择结束日期">
            </el-date-picker>
          </el-col>
          <el-col :span="6">
            <el-button @click="submit" style="height: 40px">
              确认
            </el-button>
          </el-col>
        </el-row>
        <el-row class="table-body">
          <el-table
            :data="tableData"
            show-summary
            border
            style="border-bottom: hidden"
            :summary-method="getSummaries"
            align="center">
            <el-table-column
              width="60px"
            >
            </el-table-column>
            <el-table-column
              label="航空公司"
              prop="PLANE_AIRLINES"
              width="250px"
            >
            </el-table-column>
            <el-table-column type="expand">
              <template slot-scope="props">
                <el-table
                  :data="props.row.REG"
                  style="width: 241px; position: relative; left: 30%"
                  border
                  align="center">
                  <el-table-column
                    align="right"
                    width="120px"
                    label="机号"
                    prop="PLANE_REG">
                  </el-table-column>
                  <el-table-column
                    align="left"
                    width="120px"
                    label="机型"
                    prop="PLANE_TYPE"
                  >
                  </el-table-column>
                </el-table>
              </template>
            </el-table-column>
            <el-table-column
              label="机号数量"
              prop="PLANE_REG_NUM"
              width="250px"
            >
            </el-table-column>
            <el-table-column
              label="机型数量"
              prop="PLANE_TYPE_NUM"
              width="250px"
            >
              <template slot-scope="scope">
                <div @click="checkType(scope.row)"  style="cursor: pointer;">
                    {{scope.row.PLANE_TYPE_NUM}}
                </div>
              </template>
            </el-table-column>
            <el-table-column
              label="数据记录"
              prop="PLANE_RECORDS"
            >
            </el-table-column>
          </el-table>
        </el-row>
      </el-main>
    </el-container>
    <air-type-modal
      v-if="isAirTypeModalVisible"
      :airlines="airlines"
      @close="close"
    >
    </air-type-modal>
  </div>
</template>

<script>

  import ElContainer from "../../../node_modules/element-ui/packages/container/src/main.vue";
  import ElHeader from "../../../node_modules/element-ui/packages/header/src/main.vue";
  import ElMain from "../../../node_modules/element-ui/packages/main/src/main.vue";
  import ElRow from "element-ui/packages/row/src/row";
  import ElButton from "../../../node_modules/element-ui/packages/button/src/button.vue";
  import ElCol from "element-ui/packages/col/src/col";
  import ElTable from "../../../node_modules/element-ui/packages/table/src/table.vue";
  import ElFormItem from "../../../node_modules/element-ui/packages/form/src/form-item.vue";
  import AirTypeModal from "../modal/AirTypeModal.vue"

  export default {
    components: {
      ElFormItem,
      ElTable,
      ElCol,
      ElButton,
      ElRow,
      ElMain,
      ElHeader,
      ElContainer,
      AirTypeModal},
    name:'statis',
    data () {
      return {
        isAirTypeModalVisible:false,
        airlines:'',
        startDate:'',
        endDate: '',
        tableData:[
          {
            PLANE_AIRLINES:'MU',
            PLANE_REG_NUM:'13423',
            PLANE_TYPE_NUM:'234',
            REG:[
              {
                PLANE_REG: '13423',
                PLANE_TYPE: 'rweqr234',
              },
              {
                PLANE_REG: '13423',
                PLANE_TYPE: 'rweqr234',
              },
              {
                PLANE_REG: '13423',
                PLANE_TYPE: 'rweqr234',
              },
            ]
          },
          {
            PLANE_AIRLINES:'MU',
            PLANE_REG_NUM:'13423',
            PLANE_TYPE_NUM:'234',
            REG:[
              {
                PLANE_REG: '13423',
                PLANE_TYPE: 'rweqr234',

              },
              {
                PLANE_REG: '13423',
                PLANE_TYPE: 'rweqr234',
              },
              {
                PLANE_REG: '13423',
                PLANE_TYPE: 'rweqr234',
              },
            ]
          }
        ]
      }
    },
    mounted () {
      if (JSON.stringify(this.$store.state.user)==='{}'){
        this.$router.push('/login');
      }
      this.initTable();
    },
    methods : {
      checkType(value){
        this.airlines = value.PLANE_AIRLINES;
        this.isAirTypeModalVisible= true;
      },
      getSummaries(params) {
        const {columns, data} = params;
        const sums = [];
        columns.forEach((column, index) => {
          if (0 === index) {
            sums[index] = '合计';
            return
          }
          if (1 === index) {
            sums[index] = data.length;
            return
          }
          if (2 === index) {
            sums[index] = null;
            return
          }
          const values = data.map(item => Number(item[column.property]));
          if (!values.every(value => isNaN(value))) {
            sums[index] = values.reduce((prev, curr)=>{
              const value = Number(curr);
              if (!isNaN(value)) {
                return prev+curr;
              }else {
                return prev;
              }
            },0);
          }else{
            sums[index] = 'N/A';
          }
        });

        return sums;
      },
      initTable () {
        this.$axios.get('/api/statis/all')
          .then((res) => {
            this.tableData = res.data.DATA;
          })
      },
      submit() {
        let p = {};
        p['START_DATE'] = this.startDate;
        p['END_DATE'] = this.endDate;
        this.$axios.post('/api/statis/mod-date', p)
          .then((res) => {
            this.tableData = res.data.DATA;
          })
      },
      close () {
        this.isAirTypeModalVisible = false;
      }
    }
  }
</script>
<style>
  .el-table--border td{
   }
  .table-body {
    margin-top: 20px;
  }

  .el-table--border::after, .el-table--group::after, .el-table::before {
    content: '';
    position: absolute;
    background-color: #ebeef5;
    z-index: 1;
    display: none;
  }
</style>
