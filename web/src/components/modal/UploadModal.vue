<template>
  <div class="upload-modal-backdrop">
    <div class="upload-modal">
      <div class="modal-header">
        <slot name="header">
          上传平衡图文件
        </slot>
      </div>
      <div class="modal-body">
        <slot name="body">
          <uploader :options="options"
                    :file-status-text="statusText"
                    @file-added="fileAdded">
            <uploader-unsupport></uploader-unsupport>
            <uploader-drop>
              <!--<p>Drop files here to upload or</p>-->
              <uploader-btn>选择文件</uploader-btn>
              <!--<uploader-btn :attrs="attrs">select images</uploader-btn>-->
              <!--<uploader-btn :directory="true">select folder</uploader-btn>-->
            </uploader-drop>
            <uploader-list></uploader-list>
          </uploader>
        </slot>
      </div>
      <div class="modal-footer">
        <slot name="footer">
          <!--<button type="button" class="btn-submit" @click="">提交</button>-->
          <el-button type="button" class="btn-close" @click="close">关闭</el-button>
        </slot>
      </div>
    </div>
  </div>

</template>
<script>
  import BaseUrl from '../../../config/url.config'
  export default {
    name: 'UploadModal',
    components: {
    },
    props:{
      uploadRowData:{
        default: []
      }
    },
    mounted () {
    },
    data () {
      return {
        options: {
          target: '/api/file/upload',
          testChunks: false,
          singleFile:true,
          withCredentials:true,
          uploadMethod:'POST',
          method: 'multipart',
          prioritizeFirstAndLastChunk:true,
          chunkSize:10*1024*1024
        },
//        attrs: {
//          accept: 'image/*'
//        }
        statusText: {
          success: '成功了',
          error: '出错了',
          uploading: '上传中',
          paused: '暂停中',
          waiting: '等待中'
        }
      }
    },
    methods: {
      close: function () {
        this.$emit('close', 'upload');
      },
      fileAdded(file, event) {
        let type = file.getExtension();
        if (type !== 'pdf' && type !== 'doc' && type !== 'docx') {
          file.cancel();
          alert('请上传正确的文件类型！')
        }
        let name = this.uploadRowData['PLANE_ID']
          +'_'+this.uploadRowData['PLANE_REG'];
        file.name = name+'.'+type;
      }
    }
  }
</script>
<style scoped>
  .upload-modal-backdrop {
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
  .upload-modal {
    background-color: #fff;
    box-shadow: 2px 2px 20px 1px;
    overflow-x:auto;
    display: flex;
    flex-direction: column;
    width:500px;
    height:200px;
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

