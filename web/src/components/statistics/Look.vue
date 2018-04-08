<template>
  <div id = 'look'>
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
          <!--<el-col :span="3"><div ><el-button type="button" @click="addRow">增加</el-button></div></el-col>-->
        </el-row>
        <el-row>

        </el-row>
      </el-header>
      <el-main>
        <el-row>
          <v-table
            @on-custom-comp="editRecord"
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
            :columns="columns"
            :table-data="tableData">
          </v-table>
          <div class="mt20 mb20 bold"></div>
          <v-pagination @page-change="pageChange" @page-size-change="pageSizeChange"
                        :total="totalElements" :page-size="pageSize"
                        :layout="['total', 'prev', 'pager', 'next', 'sizer', 'jumper']">
          </v-pagination>
          <download-modal v-if="isDownloadModalVisible"
                          :downloadColumn = "downloadColumn"
                          v-on:close="modalClose">
          </download-modal>
          <el-alert title="alertMessage"
                    type="error" @close="alertClose"
                    v-if="alertMessage.length>1">
          </el-alert>
          <edit-record-modal v-if="isEditRecordModalVisible"
                             :editColumn = "editColumn"
                             v-on:close="modalClose">
          </edit-record-modal>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>


<script>
  import BaseUrl from '../../../config/url.config'
  import DownloadModal from '../modal/DownloadModal.vue'
  import EditRecordModal from '../modal/EditRecordModal.vue'

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
      DownloadModal,
      EditRecordModal
    },
    data() {
      return {
        airline:'',
        reg:'',
        totalElements:0,
        pageIndex:0,
        pageSize:10,
        isDownloadModalVisible: false,
        isEditRecordModalVisible: false,
        tableData: [],
        downloadColumn: [],
        tempDownloadColumn: [],
        editColumn:'',
        alertMessage:'',
        columns: [
          {width: 40, titleAlign: 'center',columnAlign:'center',type: 'selection'},
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
          {field: '',title:'查看',width: 80, titleAlign: 'center',columnAlign:'center',
            componentName:'file-look',isResize:true}
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
      },
      modalClose (type) {
        if (type === 'download') {
          this.isDownloadModalVisible = false;
          this.downloadColumn = [];
        }
        if (type === 'editRecord') {
          this.isEditRecordModalVisible = false;
          this.editColumn = [];
        }
      },
      editRecord(params) {
        this.isEditRecordModalVisible = true;
        this.editColumn = params['rowData'];
      }
    }
  }

  Vue.component('file-look',{
    template:`<span>
                <i class="el-icon-tickets" @click.stop.prevent="tickets(rowData,index)"></i>&nbsp;
                <i class="el-icon-search" @click.stop.prevent="search(rowData,index)"></i>
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
      tickets(){
        // 参数根据业务场景随意构造
        let params = this.rowData['PLANE_ID']+ '_'+this.rowData['PLANE_REG'];
        window.open('http://localhost:8081/file/download?FILE_NAME='+params);
      },
      search(){
        // 参数根据业务场景随意构造
        let params = {rowData:this.rowData};
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
    right: 45%;
    padding-right: 10px;
    padding-left: 10px;
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

  i {
    cursor: pointer
  }
</style>
