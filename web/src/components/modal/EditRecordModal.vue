<template>
  <div>
    <div class="del-modal-backdrop">
      <div class="del-modal">
        <div class="modal-header">
          <slot name="header">
            数据修改历史记录
          </slot>
        </div>
        <div class="modal-body">
          <slot name="body">
            <v-table
              is-vertical-resize
              even-bg-color="#f2f2f2"
              :columns="columnsER"
              :table-data="editColumnRecord">
            </v-table>
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
  export default {
    name: 'EditRecordModal',
    components: {

    },
    props:{
      editColumn:{
        default:[]
      }
    },
    mounted () {
      this.initTable();
    },
    data () {
      return {
        columnsER: [
          {field: 'PLANE_AIRLINES', title: '所属航空公司', width:50, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_REG', title:'机号', width: 120, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_TYPE', title: '机型', width: 100, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_BUJU', title: '飞机布局', width: 100, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_CND', title: 'CND表号', width: 80, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_BOW', title: '基本重量', width: 100, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_BOI', title: '基本指数', width: 80, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_MZFW', title: '最大无油重量', width: 90, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_MZDW', title: '最大起飞重量', width: 90, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_MTOW', title: '最大落地重量',width: 90, titleAlign: 'center',columnAlign:'left'},
          {field: 'PLANE_UPDATE_DATE', title: '更新时间', width: 100, titleAlign: 'center',columnAlign:'center'}
        ],
        editColumnRecord:[]

      }
    },
    methods: {
      close: function () {
        this.$emit('close', 'editRecord');
      },
      initTable () {
        this.$axios.post('/api/pzm/plane-id',{PLANE_ID: this.editColumn.PLANE_ID})
          .then((res)=>{
            if (res.data.DATA === '') {
              this.editColumnRecord=[];
              this.editColumnRecord.push(this.editColumn);
            }else{
              this.editColumnRecord=res.data.DATA;
              this.editColumnRecord.push(this.editColumn);
            }
          })
      }
    }
  }
</script>
<style scoped>
  .del-modal-backdrop {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-color: rgba(0,0,0,.3);
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .del-modal {
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

