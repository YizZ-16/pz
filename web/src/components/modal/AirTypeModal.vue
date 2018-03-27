
<template>
  <div id="air-type-modal">
    <div class="air-type-modal-backdrop">
      <div class="air-type-modal">
        <div class="modal-header">
          <slot name="header">
            航空公司：{{airlines}}
          </slot>
        </div>
        <div class="modal-body">
          <slot name="body">
            <el-table
              :data="tableData">
              <el-table-column
                width="60px"
              >
              </el-table-column>
              <el-table-column
                label="机型"
                prop="PLANE_TYPE"
                width="250px"
              >
              </el-table-column>
              <el-table-column
                label="机号数量"
                prop="PLANE_REG_NUM"
                width="250px"
              >
              </el-table-column>
              <el-table-column type="expand" >
                <template slot-scope="props">
                  <el-table :data="props.row.REG" align="center">
                    <el-table-column align="center" width="150px"
                                     label="机号"
                                     prop="PLANE_REG">
                    </el-table-column>
                  </el-table>
                </template>
              </el-table-column>
            </el-table>
          </slot>
        </div>
        <div class="modal-footer">
          <slot name="footer">
            <button type="button" class="btn-close" @click="close">关闭</button>
          </slot>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import BaseUrl from '../../../config/url.config'
  import ElTable from "../../../node_modules/element-ui/packages/table/src/table.vue";

  export default {
    components: {ElTable},
    name: 'EditModal',
    props:{
      airlines: {
        default: 'unknown'
      }
    },
    mounted () {
      this.initTable(this.airlines);
    },
    data () {
      return {
        tableData:[
          {
            PLANE_TYPE:123,
            PLANE_REG_NUM:12,
            REG:[
              {
                PLANE_REG:'12dfd'
              },
              {
                PLANE_REG:'12dfd'
              },
              {
                PLANE_REG:'12dfd'
              },
              {
                PLANE_REG:'12dfd'
              },
              {
                PLANE_REG:'12dfd'
              }
            ]
          },{}]
      }
    },
    methods: {
      close: function () {
        this.$emit('close', 'delete');
      },
      initTable(airlines) {
        this.$axios.post('/api/statis/airlines',{
          PLANE_AIRLINES:airlines
        })
          .then((res)=>{
            this.tableData = res.data.DATA
          });
      }
    }
  }
</script>
<style scoped>
  .air-type-modal-backdrop {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-color: rgba(0,0,0,1);
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .air-type-modal {
    background-color: #fff;
    box-shadow: 2px 2px 20px 1px;
    overflow-x:auto;
    display: flex;
    flex-direction: column;
  }
  .modal-header,
  .modal-footer {
    padding: 15px;
    display: flex;
  }
  .modal-header {
    border-bottom: 1px solid #eee;
    color: #4aae9b;
    justify-content: space-between;
  }
  .modal-footer {
    border-top: 1px solid #eee;
    justify-content: flex-end;
  }
  .modal-body {
    position: relative;
    padding: 20px 10px;
  }
  .btn-submit {
    border: hidden;
    cursor: pointer;
    font-size: 10px;
    padding: 10px;
    color: #fff;
    background-color: #4aae9b;
    border-radius: 2px;
    margin-right: 20px;
  }
  .btn-close {
    border: hidden;
    cursor: pointer;
    font-size: 10px;
    padding: 10px;
    color: #fff;
    background-color: #4aae9b;
    border-radius: 2px;
  }
</style>

