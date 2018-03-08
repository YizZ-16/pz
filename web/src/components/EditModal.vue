<template>
  <div>
    <div class="edit-modal-backdrop">
      <div class="edit-modal">
        <div class="modal-header">
          <slot name="header">
            点击数据进行修改,提交前请确认数据是否正确
          </slot>
        </div>
        <div class="modal-body">
          <slot name="body">
            <v-table :columns="columnsEdit"
                     :table-data="editColumn"
                     :cell-edit-done="cellEditDone">
            </v-table>
          </slot>
        </div>
        <div class="modal-footer">
          <slot name="footer">
            <button type="button" class="btn-submit" @click="editSubmit">提交</button>
            <button type="button" class="btn-close" @click="close">关闭</button>
          </slot>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import BaseUrl from '../../config/url.config'
  export default {
    name: 'EditModal',
    components: {

    },
    props:{
      editColumn:{
        default:[]
      }
    },
    mounted () {

    },
    data () {
      return {
        columnsEdit: [
          {field: 'PLANE_AIRLINES', title: '所属航空公司', width:80, titleAlign: 'center',columnAlign:'center',
            isEdit:true},
          {field: 'PLANE_REG', title:'机号', width: 80, titleAlign: 'center',columnAlign:'center', isEdit:true},
          {field: 'PLANE_TYPE', title: '机型', width: 80, titleAlign: 'center',columnAlign:'center', isEdit:true},
          {field: 'PLANE_BUJU', title: '飞机布局', width: 80, titleAlign: 'center',columnAlign:'center', isEdit:true},
          {field: 'PLANE_CND', title: 'CND表号', width: 80, titleAlign: 'center',columnAlign:'center', isEdit:true},
          {field: 'PLANE_BOW', title: '基本重量', width: 120, titleAlign: 'center',columnAlign:'center', isEdit:true},
          {field: 'PLANE_BOI', title: '基本指数', width: 120, titleAlign: 'center',columnAlign:'center', isEdit:true},
          {field: 'PLANE_MZFW', title: '最大无油重量', width: 80, titleAlign: 'center',columnAlign:'center', isEdit:true},
          {field: 'PLANE_MZDW', title: '最大起飞重量', width: 80, titleAlign: 'center',columnAlign:'center', isEdit:true},
          {field: 'PLANE_MTOW', title: '最大落地重量',width: 80, titleAlign: 'center',columnAlign:'left', isEdit:true},
          {field: 'PLANE_UPDATE_DATE', title: '更新时间', width: 180, titleAlign: 'center',columnAlign:'center', isEdit:true}
        ],
        loading: false,
      }
      },
    methods: {
      close: function () {
        this.$emit('close', 'edit');
      },
      cellEditDone: function (newValue,oldValue,rowIndex,rowData,field) {
        this.editColumn[0][field] = newValue;
      },
      editSubmit: function () {
        this.$http.post(BaseUrl.url+'/pz/edit', this.editColumn[0])
          .then((data)=>{
              if (data.body) {
                let params = {};
                params['modal'] = 'edit';
                params['data'] = this.editColumn[0];
                this.$emit('success', params);
              }else{
                alert("数据更新失败！")
              }
          },(error)=>{
              alert("数据更新失败！")
              console.log(error)
          });
      }
    }
  }
</script>
<style scoped>
  .edit-modal-backdrop {
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
  .edit-modal {
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

