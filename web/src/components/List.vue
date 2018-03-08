<template>
  <div id = 'list'>
    <div id = 'head'>
      航空公司：
      <input v-model="airline" value=""/>
      机号：
      <input v-model="reg" value=""/>
      <button type="button" class="btn-search" @click="customSearch">查询</button>
      <button type="button" class="btn-download" @click="download">下载</button>
      <button type="button" class="btn-add" @click="addRow">增加</button>
    </div>
    <div id = 'body'>
      <v-table
        :select-all="selectALL"
        :select-change="selectChange"
        :select-group-change="selectGroupChange"

        is-vertical-resize
        :vertical-resize-offset='60'
        is-horizontal-resize
        style="width:100%"
        :multiple-sort="false"
        :min-height="350"
        row-hover-color="#eee"
        row-click-color="#edf7ff"

        even-bg-color="#f2f2f2"
        @on-custom-comp="customCompFunc"
        :columns="columns"
        :table-data="tableData">
      </v-table>
      <div class="mt20 mb20 bold"></div>
      <v-pagination @page-change="pageChange" @page-size-change="pageSizeChange"
                    :total="totalElements" :page-size="pageSize"
                    :layout="['total', 'prev', 'pager', 'next', 'sizer', 'jumper']">
      </v-pagination>
      <edit-modal v-if="isEditModalVisible"
                  :editColumn="editColumn"
                  v-on:close="modalClose"
                  v-on:success="modalSuccess">
      </edit-modal>
      <delete-modal v-if="isDeleteModalVisible"
                    :deleteColumn="deleteColumn"
                    v-on:close="modalClose"
                    v-on:success="modalSuccess">
      </delete-modal>
      <add-modal v-if="isAddModalVisible"
                 v-on:close="modalClose"
                 v-on:success="modalSuccess">
      </add-modal>

      <download-modal v-if="isDownloadModalVisible"
                      :downloadColumn = "downloadColumn"
                      v-on:close="modalClose"></download-modal>
    </div>
  </div>
</template>


<script>
  import BaseUrl from '../../config/url.config'
  import EditModal from './EditModal.vue'
  import DeleteModal from './DelModal.vue'
  import AddModal from './AddModel.vue'
  import DownloadModal from './DownLoad.vue'
  import Vue from 'vue'
  import VTable from "../../node_modules/vue-easytable/libs/v-table/src/table.vue";
  export default{
    components: {
      VTable,
      EditModal,
      DeleteModal,
      AddModal,
      DownloadModal,
    },
    data() {
      return {
        airline:'',
        reg:'',
        totalElements:0,
        pageIndex:0,
        pageSize:10,
        isEditModalVisible:false,
        isDeleteModalVisible:false,
        isAddModalVisible:false,
        isDownloadModalVisible: false,
        editColumn:[],
        deleteColumn: [],
        tableData: [],
        downloadColumn: [],
        tempDownloadColumn: [],
        columns: [
          {width: 60, titleAlign: 'center',columnAlign:'center',type: 'selection'},
          {field: 'PLANE_AIRLINES', title: '所属航空公司', width:50, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_REG', title:'机号', width: 80, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_TYPE', title: '机型', width: 120, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_BUJU', title: '飞机布局', width: 120, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_CND', title: 'CND表号', width: 80, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_BOW', title: '基本重量', width: 100, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_BOI', title: '基本指数', width: 100, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_MZFW', title: '最大无油重量', width: 90, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_MZDW', title: '最大起飞重量', width: 90, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_MTOW', title: '最大落地重量',width: 90, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_UPDATE_DATE', title: '更新时间', width: 120, titleAlign: 'center',columnAlign:'center'},
          {field: '',title:'操作',width: 120, titleAlign: 'center',columnAlign:'center',
            componentName:'table-operation',isResize:true}
        ],

      }
    },
    mounted () {
      let params = {};
      params['PAGE_INDEX']=0;
      params['PAGE_SIZE']= 10;
      this.searchAll(params);
    },
    methods: {
      customCompFunc(params){
        if (params.type === 'delete'){ // do delete operation
          //this.$delete(this.tableData,params.index);
          this.isDeleteModalVisible = true;
          this.deleteColumn[0] = this.tableData[params.index];
        }else if (params.type === 'edit'){ // do edit operation
          this.isEditModalVisible = true;
          this.editColumn[0] = params.rowData;
        }else if (params.type === 'upload'){
          this.isAddModalVisible = true;
        }
      },
      customSearch() {
        let params = {};
        params['PAGE_INDEX']= 0;
        params['PAGE_SIZE']= 10;
        if (this.airline!=='' && this.reg!=='') {
          params['PLANE_AIRLINES'] = this.airline;
          params['PLANE_REG'] = this.reg;
          this.searchByAirlinesAndReg(params);
        }else if (this.airline!=='' && this.reg===''){
          params['PLANE_AIRLINES'] = this.airline;
          this.searchByAirlines(params);
        }else if (this.airline==='' && this.reg!=='') {
          params['PLANE_REG'] = this.reg;
          this.searchByReg(params);
        }else{
          alert('请输入需要查询的条件内容')
        }
      },
      searchByAirlines(params){
        this.$http.post(BaseUrl.url+'/list/airlines',params)
          .then((data)=>{
            //console.log(JSON.stringify(data));
            this.tableData = data.body.DATA_LIST;
            this.totalElements = data.body.TOTAL_ELEMENTS;
          });
      },
      searchByAirlinesAndReg(params){
        this.$http.post(BaseUrl.url+'/list/airlines-reg',params)
          .then((data)=>{
            this.tableData = data.body.DATA_LIST;
            this.totalElements = data.body.TOTAL_ELEMENTS;
            this.checkDownloadColumn();
          });
      },
      searchByReg(params){
        this.$http.post(BaseUrl.url+'/list/reg',params)
          .then((data)=>{
            this.tableData = data.body.DATA_LIST;
            this.totalElements = data.body.TOTAL_ELEMENTS;
            this.checkDownloadColumn();
          });
      },
      searchAll(params){
        this.$http.post(BaseUrl.url+'/list/all',params)
          .then((data)=>{
            this.tableData = data.body.DATA_LIST;
            this.totalElements = data.body.TOTAL_ELEMENTS;
            this.checkDownloadColumn();
          });
      },
      pageChange(pageIndex){
        this.pageIndex = pageIndex;
        let params = {};
        params['PAGE_INDEX']=this.pageIndex - 1;
        params['PAGE_SIZE']= this.pageSize;
        if (this.airline!=='' && this.reg!=='') {
          params['PLANE_AIRLINES'] = this.airline;
          params['PLANE_REG'] = this.reg;
          this.searchByAirlinesAndReg(params);
        }else if (this.airline!=='' && this.reg===''){
          params['PLANE_AIRLINES'] = this.airline;
          this.searchByAirlines(params);
        }else if (this.airline==='' && this.reg!=='') {
          params['PLANE_REG'] = this.reg;
          this.searchByReg(params);
        }else{
          this.searchAll(params);
        }
        this.addToDownloadColumn();
      },
      pageSizeChange(pageSize){
        this.pageSize = pageSize;
        let params = {};
        params['PAGE_INDEX']= 0;
        params['PAGE_SIZE']= this.pageSize;
        if (this.airline!=='' && this.reg!=='') {
          params['PLANE_AIRLINES'] = this.airline;
          params['PLANE_REG'] = this.reg;
          this.searchByAirlinesAndReg(params);
        }else if (this.airline!=='' && this.reg===''){
          params['PLANE_AIRLINES'] = this.airline;
          this.searchByAirlines(params);
        }else if (this.airline==='' && this.reg!=='') {
          params['PLANE_REG'] = this.reg;
          this.searchByReg(params);
        }else {
          this.searchAll(params);
        }

      },
      modalClose(type) {
        if (type === 'edit') {
          this.isEditModalVisible = false;
        }
        if (type === 'delete') {
          this.isDeleteModalVisible = false;
        }
        if (type === 'add') {
          this.isAddModalVisible = false;
        }
        if (type === 'download') {
          this.isDownloadModalVisible = false;
          this.downloadColumn= [];
        }
      },
      modalSuccess(params) {
        if (params.modal === 'edit') {
          this.modalClose('edit');
          for (let i = 0;  i < this.tableData.length; i++) {
             if (this.tableData[i]['PLANE_ID'] === params.data['PLANE_ID']) {
               this.tableData[i] = params.data;
             }
             break;
          }
        }

        if (params.modal === 'delete') {
          this.modalClose('delete');
          let temp = [];
          for (let i = 0;  i < this.tableData.length; i++) {
            if (this.tableData[i]['PLANE_ID'] !== params.data['PLANE_ID']) {
              temp.push(this.tableData[i]);
            }
          }
          this.tableData = temp;
        }
      },

      addRow () {
        this.isAddModalVisible = true;
      },

      selectALL(selection){
        this.tempDownloadColumn = selection;
        console.log('select-aLL',selection);
      },

      selectChange(selection,rowData){
        console.log('select-change',selection,rowData);
      },

      selectGroupChange(selection){
        this.tempDownloadColumn = selection;
        console.log('select-group-change',selection);
      },

      checkDownloadColumn() {
        for (let j = 0;  j<this.downloadColumn.length; j++) {
          for (let i =0; i<this.tableData.length; i++) {
            if (this.downloadColumn[j]['PLANE_ID'] === this.tableData[i]['PLANE_ID']) {
              this.tableData[i]._checked=true;
            }
          }
        }
      },

      addToDownloadColumn() {
        for (let i = 0; i < this.tempDownloadColumn.length; i++) {
          this.downloadColumn.push(this.tempDownloadColumn[i]);
        }
        this.tempDownloadColumn = [];
        console.log(this.downloadColumn)
      },

      download() {
        for (let i = 0; i < this.tempDownloadColumn.length; i++) {
          this.downloadColumn.push(this.tempDownloadColumn[i]);
        }
        this.isDownloadModalVisible = true;
      }

    }
  }

  // 自定义列组件
  Vue.component('table-operation',{
    template:`<span><a href="" @click.stop.prevent="updateRow(rowData,index)">编辑</a>&nbsp;
              <a href="" @click.stop.prevent="deleteRow(rowData,index)">删除</a>&nbsp;
              <a href="" @click.stop.prevent="uploadFile(rowData,index)">上传</a>
              </span>`,
    props:{
      rowData:{
        type:Object
      },
      field:{
        type:String
      },
      index:{
        type:Number
      }
    },
    methods:{
      updateRow(){
        // 参数根据业务场景随意构造
        let params = {type:'edit',index:this.index,rowData:this.rowData};
        this.$emit('on-custom-comp',params);
      },
      deleteRow(){
        // 参数根据业务场景随意构造
        let params = {type:'delete',index:this.index};
        this.$emit('on-custom-comp',params);
      },
      uploadFile(){
        let params = {type:'upload',index:this.index,rowData:this.rowData};
        this.$emit('on-custom-comp',params);
      }
    }
  })
</script>

<style>
  .btn-add {
    border: hidden;
    cursor: pointer;
    font-size: 15px;
    color: #fff;
    background-color: #4aae9b;
    position: absolute;
    right: 13%;
    padding-right: 10px;
    padding-left: 10px;
  }

  .btn-download {
    border: hidden;
    cursor: pointer;
    font-size: 15px;
    color: #fff;
    background-color: #4aae9b;
    position: absolute;
    right: 20%;
    padding-right: 10px;
    padding-left: 10px;
  }

  .btn-search{
    border: hidden;
    cursor: pointer;
    font-size: 15px;
    color: #fff;
    background-color: #4aae9b;
    position: absolute;
    right: 55%;
    padding-right: 10px;
    padding-left: 10px;
  }

  #head {
    height: 50px;
    text-align: left;
  }
</style>
