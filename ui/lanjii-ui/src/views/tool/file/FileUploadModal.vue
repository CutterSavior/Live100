<template>
  <el-dialog
      :model-value="visible"
      @update:model-value="$emit('close')"
      title="文件上传"
      width="500px"
      :close-on-click-modal="false"
      append-to-body
      destroy-on-close
  >
    <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="选择文件" prop="file">
        <el-upload
            ref="uploadRef"
            class="upload-demo"
            drag
            :auto-upload="false"
            :on-change="handleFileChange"
            :on-remove="handleFileRemove"
            :before-upload="beforeUpload"
            :limit="1"
            :file-list="fileList"
            accept="*/*"
        >
          <el-icon class="el-icon--upload">
            <upload-filled/>
          </el-icon>
          <div class="el-upload__text">
            将文件拖到此处，或<em>点击上传</em>
          </div>
          <template #tip>
            <div class="el-upload__tip">
              支持任意格式文件，建议文件大小不超过100MB
            </div>
          </template>
        </el-upload>
      </el-form-item>
    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" :icon="Upload" @click="handleSubmit" :loading="uploading">
          {{ uploading ? '上传中...' : '上传文件' }}
        </el-button>
        <el-button @click="$emit('close')">取消</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import {reactive, ref} from 'vue';
import {ElMessage} from 'element-plus';
import {Upload, UploadFilled} from '@element-plus/icons-vue';
import * as fileApi from '@/api/modules/tool/fileApi';

const props = defineProps({
  visible: Boolean
});

const emit = defineEmits(['close', 'success']);

const formRef = ref();
const uploadRef = ref();
const uploading = ref(false);
const fileList = ref([]);
const selectedFile = ref<File | null>(null);

const form = reactive({
  file: null as File | null
});

const rules = {
  file: [
    {required: true, message: '请选择要上传的文件', trigger: 'change'}
  ]
};

const handleFileChange = (file: any, fileList: any[]) => {
  selectedFile.value = file.raw;
  form.file = file.raw;
};

const handleFileRemove = () => {
  selectedFile.value = null;
  form.file = null;
};

const beforeUpload = (file: File) => {
  // 检查文件大小（100MB限制）
  const maxSize = 100 * 1024 * 1024;
  if (file.size > maxSize) {
    ElMessage.error('文件大小不能超过100MB');
    return false;
  }
  return true;
};

const handleSubmit = () => {
  formRef.value.validate(async (valid: boolean) => {
    if (!valid) return;

    if (!form.file) {
      ElMessage.error('请选择要上传的文件');
      return;
    }

    uploading.value = true;

    try {
      const formData = new FormData();
      formData.append('file', form.file);

      await fileApi.uploadFile(formData);
      ElMessage.success('文件上传成功');

      form.file = null;
      selectedFile.value = null;
      fileList.value = [];
      uploadRef.value?.clearFiles();

    } catch (error: any) {
      ElMessage.error(error.message || '文件上传失败');
    } finally {
      uploading.value = false;
    }
  });
};
</script>

<style scoped>
.upload-demo {
  width: 100%;
}

.dialog-footer {
  text-align: right;
}

:deep(.el-upload-dragger) {
  width: 100%;
  height: 180px;
}

:deep(.el-upload__tip) {
  color: #909399;
  font-size: 12px;
  margin-top: 7px;
}
</style>
