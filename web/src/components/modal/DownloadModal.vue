<template>
  <div>
    <div class="del-modal-backdrop">
      <div class="del-modal">
        <div class="modal-header">
          <slot name="header">
            导出前请确认下载的数据是否正确
          </slot>
        </div>
        <div class="modal-body">
          <slot name="body">
            <v-table
              even-bg-color="#f2f2f2"
              :columns="columnsDownload"
              :table-data="downloadColumn">
            </v-table>
          </slot>
        </div>
        <div class="modal-footer">
          <slot name="footer">
            <button type="button" class="btn-submit" @click="downloadSubmit">导出</button>
            <button type="button" class="btn-close" @click="close">关闭</button>
          </slot>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import BaseUrl from '../../../config/url.config'
  import { export_json_to_excel } from '../../vendor/Export2Excel'
  export default {
    name: 'EditModal',
    components: {

    },
    props:{
      downloadColumn:{
        default:[]
      }
    },
    mounted () {

    },
    data () {
      return {
        columnsDownload: [
          {field: 'PLANE_AIRLINES', title: '所属航空公司', width:80, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_REG', title:'机号', width: 80, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_TYPE', title: '机型', width: 80, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_BUJU', title: '飞机布局', width: 80, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_CND', title: 'CND表号', width: 80, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_BOW', title: '基本重量', width: 120, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_BOI', title: '基本指数', width: 120, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_MZFW', title: '最大无油重量', width: 80, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_MZDW', title: '最大起飞重量', width: 80, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_MTOW', title: '最大落地重量',width: 80, titleAlign: 'center',columnAlign:'left'},
          {field: 'PLANE_UPDATE_DATE', title: '更新时间', width: 180, titleAlign: 'center',columnAlign:'center'}
        ],

      }
    },
    methods: {
      close: function () {
        this.$emit('close', 'download');
      },
      formatJson(filterVal, jsonData){
        return jsonData.map(v => filterVal.map(j => v[j]))
      },
      downloadSubmit () {
         let tHeader = ['所属航空公司','机号','机型','飞机布局','CND表号',
           '基本重量','基本指数','最大无油重量','最大起飞重量','最大落地重量','更新时间'];
         let filterVal = ['PLANE_AIRLINES','PLANE_REG','PLANE_TYPE', 'PLANE_BUJU',
           'PLANE_CND','PLANE_BOW','PLANE_BOI','PLANE_MZFW', 'PLANE_MZDW',
           'PLANE_MTOW','PLANE_UPDATE_DATE'];
         let list = this.downloadColumn;
         let data = this.formatJson(filterVal, list);
         export_json_to_excel(tHeader, data, 'pz_list')
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

