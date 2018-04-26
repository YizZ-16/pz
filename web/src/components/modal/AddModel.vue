<template>
  <div>
    <div class="add-modal-backdrop">
      <div class="add-modal">
        <div class="modal-header">
          <slot name="header">
            提交前请确认数据是否正确
          </slot>
        </div>
        <div class="modal-body">
          <slot name="body">
            <v-table :table-data="addColumn"
                     :columns="columnsEdit"
                     :cell-edit-done="cellEditDone">
            </v-table>
          </slot>
        </div>
        <div class="modal-footer">
          <slot name="footer">
            <button type="button" class="btn-submit" @click="addSubmit">提交</button>
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
    name: 'AddModal',
    components: {
    },
    props:{
    },
    mounted () {
    },
    data () {
      return {
        columnsEdit: [
          {field: 'PLANE_AIRLINES', title: '所属航空公司', width:50, titleAlign: 'center',columnAlign:'center',
            isEdit:true},
          {field: 'PLANE_REG', title:'机号', width: 120, titleAlign: 'center',columnAlign:'center', isEdit:true},
          {field: 'PLANE_TYPE', title: '机型', width: 100, titleAlign: 'center',columnAlign:'center', isEdit:true},
          {field: 'PLANE_BUJU', title: '飞机布局', width: 100, titleAlign: 'center',columnAlign:'center', isEdit:true},
          {field: 'PLANE_CND', title: 'CND表号', width: 80, titleAlign: 'center',columnAlign:'center', isEdit:true},
          {field: 'PLANE_BOW', title: '基本重量', width: 100, titleAlign: 'center',columnAlign:'center', isEdit:true},
          {field: 'PLANE_BOI', title: '基本指数', width: 80, titleAlign: 'center',columnAlign:'center', isEdit:true},
          {field: 'PLANE_MZFW', title: '最大无油重量', width: 90, titleAlign: 'center',columnAlign:'center', isEdit:true},
          {field: 'PLANE_MZDW', title: '最大起飞重量', width: 90, titleAlign: 'center',columnAlign:'center', isEdit:true},
          {field: 'PLANE_MTOW', title: '最大落地重量',width: 90, titleAlign: 'center',columnAlign:'left', isEdit:true},
//          {field: 'PLANE_UPDATE_DATE', title: '更新时间', width: 180, titleAlign: 'center',columnAlign:'center', isEdit:true}
        ],
        addColumn:[{'PLANE_AIRLINES':'','PLANE_REG':'','PLANE_TYPE':'','PLANE_BUJU':'',
          'PLANE_CND':'','PLANE_BOW':'','PLANE_BOI':'','PLANE_MZFW':'','PLANE_MZDW':'',
          'PLANE_MTOW':'','PLANE_UPDATE_DATE':''
        }]
      }
    },
    methods: {
      close: function () {
        this.$emit('close', 'add');
      },
      cellEditDone: function (newValue,oldValue,rowIndex,rowData,field) {
        this.addColumn[0][field] = newValue;
      },
      addSubmit: function () {
        this.$axios.post('/api/pz/add', this.addColumn[0])
          .then((res)=>{
            if (res.data.CODE === 0) {
              let params = {};
              params['modal'] = 'add';
              params['data'] = this.addColumn[0];
              //this.$emit('success', params);
              this.$emit('close', 'add');

            }else{
              alert("数据添加失败！")
            }
          },(error)=>{
            alert("数据添加失败！")
            console.log(error)
          });
      }
    }
  }
</script>
<style scoped>
  .add-modal-backdrop {
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
  .add-modal {
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

