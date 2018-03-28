<template>
  <div id = 'maintain'>
    <el-container>
      <el-header style="height: 40px">
        <el-row>
          <el-col :span="6" style="text-align: left">
              <a>航空公司:</a>
              <input v-model="airline"/>
          </el-col>
          <el-col :span="6">
            <a>机号:</a>
            <input v-model="reg"/>
          </el-col>
          <el-col :span="3"><div><el-button type="button" @click="customSearch">查询</el-button></div></el-col>
          <el-col :span="3"><div><el-button type="button"  @click="download">下载</el-button></div></el-col>
          <el-col :span="3"><div ><el-button type="button" @click="addRow">增加</el-button></div></el-col>
          <!--<el-col id="one" :span="240">-->
            <!--航空公司：-->
            <!--<el-input id='air-input' v-model="airline" placeholder="请输入内容" clearable/>-->
          <!--</el-col>-->
          <!--<el-col id="two">-->
            <!--机号：-->
            <!--<el-input id='reg-input' v-model="reg" placeholder="请输入内容" clearable/>-->
          <!--</el-col>-->
          <!--<el-col id="four">-->
            <!--<el-button type="button" class="btn-search" @click="customSearch">查询</el-button>-->
          <!--</el-col>-->
          <!--<el-col id="five">-->
            <!--<el-button type="button" class="btn-download" @click="download">下载</el-button>-->
          <!--</el-col>-->
          <!--<el-col id="six">-->
            <!--<el-button type="button" class="btn-add" @click="addRow">增加</el-button>-->
          <!--</el-col>-->
        </el-row>
        <el-row>

        </el-row>
      </el-header>
      <el-main>
        <el-row>
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
          <upload-modal v-if="isUploadModalVisible"
                        :uploadRowData="uploadModalRowData"
                        v-on:close="modalClose">

          </upload-modal>
          <el-alert title="alertMessage"
                    type="error" @close="alertClose"
                    v-if="alertMessage.length>1">

          </el-alert>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>


<script>
  import BaseUrl from '../../../config/url.config'
  import EditModal from '../modal/EditModal.vue'
  import DeleteModal from '../modal/DelModal.vue'
  import AddModal from '../modal/AddModel.vue'
  import DownloadModal from '../modal/DownloadModal.vue'
  import UploadModal from '../modal/UploadModal.vue'

  import Vue from 'vue'
  import VTable from "../../../node_modules/vue-easytable/libs/v-table/src/table.vue";
  import ElContainer from "../../../node_modules/element-ui/packages/container/src/main.vue";
  import ElHeader from "../../../node_modules/element-ui/packages/header/src/main.vue";
  import ElInput from "../../../node_modules/element-ui/packages/input/src/input.vue";
  import ElRow from "element-ui/packages/row/src/row";
  import ElCol from "element-ui/packages/col/src/col";
  import ElAlert from "../../../node_modules/element-ui/packages/alert/src/main.vue";
  export default{
    components: {
      ElAlert,
      ElCol,
      ElRow,
      ElInput,
      ElHeader,
      ElContainer,
      VTable,
      EditModal,
      DeleteModal,
      AddModal,
      DownloadModal,
      UploadModal
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
        isUploadModalVisible: false,
        editColumn:[],
        deleteColumn: [],
        tableData: [],
        downloadColumn: [],
        tempDownloadColumn: [],
        uploadModalRowData: [],
        alertMessage:'',
        columns: [
          {width: 60, titleAlign: 'center',columnAlign:'center',type: 'selection'},
          {field: 'PLANE_AIRLINES', title: '所属航空公司', width:50, titleAlign: 'center',columnAlign:'center'},
          {field: 'PLANE_REG', title:'机号', width: 80, titleAlign: 'center',columnAlign:'center',
          style:'cursor:pointer'},
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
      if (JSON.stringify(this.$store.state.user)==='{}'){
        this.$router.push('/login');
      }
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
          this.uploadModalRowData = params.rowData;
          this.isUploadModalVisible = true;
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
          //this.alertMessage = '请输入需要查询的条件内容';
        }
      },
      searchByAirlines(params){
        this.$axios.post('/api/list/airlines',params)
          .then((res)=>{
            //console.log(JSON.stringify(data));
            this.tableData = res.data.DATA.DATA_LIST;
            this.totalElements = res.data.DATA.TOTAL_ELEMENTS;
            this.checkDownloadColumn();
          });
      },
      searchByAirlinesAndReg(params){
        this.$http.post('/api/list/airlines-reg',params)
          .then((res)=>{
            this.tableData = res.data.DATA.DATA_LIST;
            this.totalElements = res.data.DATA.TOTAL_ELEMENTS;
            this.checkDownloadColumn();
          });
      },
      searchByReg(params){
        this.$http.post('/api/list/reg',params)
          .then((res)=>{
            this.tableData = res.data.DATA.DATA_LIST;
            this.totalElements = res.data.DATA.TOTAL_ELEMENTS;
            this.checkDownloadColumn();
          });
      },
      searchAll(params){
        this.$axios.post('/api/list/all',params)
          .then((res)=>{
            this.tableData = res.data.DATA.DATA_LIST;
            this.totalElements = res.data.DATA.TOTAL_ELEMENTS;
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
        if (type === 'upload') {
          this.isUploadModalVisible = false;
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
    template:`<span>
                <i class="el-icon-edit" @click.stop.prevent="updateRow(rowData,index)"></i>&nbsp;
                <i class="el-icon-delete" @click.stop.prevent="deleteRow(rowData,index)"></i>&nbsp;
                <i class="el-icon-upload" @click.stop.prevent="uploadFile(rowData,index)"></i>
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
    height: 36px;
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
    height: 36px;
  }

  .btn-search{
    border: hidden;
    cursor: pointer;
    font-size: 15px;
    color: #fff;
    background-color: #4aae9b;
    position: absolute;
    right: 45%;
    padding-right: 10px;
    padding-left: 10px;
    height: 36px;
  }
  /*.el-input {*/
    /*width:200px;*/
    /*heigth: 30px;*/
  /*}*/
  input{
    width: 170px;
    height: 36px;
    border: 1px solid #ccc;
    border-radius: 4px
  }
  .el-button {
    height: 36px
  }
</style>
