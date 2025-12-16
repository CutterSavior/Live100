<template>
  <el-dialog
      :model-value="visible"
      @close="$emit('close')"
      :title="modalTitle"
      width="500px"
      :close-on-click-modal="false"
      append-to-body
      destroy-on-close
  >
    <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" v-loading="loading">
      <el-form-item label="上级菜单" prop="parentId">
        <el-tree-select
            v-model="form.parentId"
            :data="treeOptionsWithRoot"
            :props="{ label: 'name', value: 'id', children: 'children', disabled: (data:any) => data.id === form.id }"
            :disabled="type === 'view'"
            placeholder="请选择上级菜单"
            clearable
            filterable
            check-strictly
            node-key="id"
            :default-checked-keys="[form.id]"
            style="width: 100%"
        />
      </el-form-item>
      <el-form-item label="菜单名称" prop="name">
        <el-input v-model="form.name" :disabled="type === 'view'" placeholder="请输入菜单名称"/>
      </el-form-item>
      <el-form-item label="菜单类型" prop="type">
        <el-radio-group v-model="form.type" :disabled="type === 'view'">
          <el-radio v-for="item in menuTypeOptions" :key="item.value" :label="item.value">{{ item.label }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="form.type === 2" label="是否外链" prop="isExt">
        <el-radio-group v-model="form.isExt" :disabled="type === 'view'">
          <el-radio :label="0">否</el-radio>
          <el-radio :label="1">是</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="form.type === 2 && form.isExt === 1" label="打开方式" prop="openMode">
        <el-radio-group v-model="form.openMode" :disabled="type === 'view'">
          <el-radio :label="0">内嵌</el-radio>
          <el-radio :label="1">新窗口</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="图标" v-if="form.type !== 3" prop="icon">
        <IconSelector v-model="form.icon" :disabled="type === 'view'"/>
      </el-form-item>
      <el-form-item v-if="form.type === 2" prop="path">
        <template #label>
          <span style="display: inline-flex; align-items: center;">
            <span>路由地址</span>
            <el-tooltip
                content="路由地址为 Vue 路由路径（不含域名和端口）。若启用外链，则需填写完整的 HTTP 或 HTTPS 外链地址。"
                placement="top"
            >
              <el-icon style="margin-left: 4px; cursor: help; color: #909399; vertical-align: middle;">
                <QuestionFilled/>
              </el-icon>
            </el-tooltip>
          </span>
        </template>
        <el-input v-model="form.path" :disabled="type === 'view'" placeholder="请输入路由地址"/>
      </el-form-item>
      <el-form-item v-if="form.type === 2 && form.isExt !== 1" prop="component">
        <template #label>
          <span style="display: inline-flex; align-items: center;">
            <span>组件路径</span>
            <el-tooltip
                content="组件路径为 Vue 页面组件的相对路径，对应项目中 @/views/ 目录下的文件路径。"
                placement="top"
            >
              <el-icon style="margin-left: 4px; cursor: help; color: #909399; vertical-align: middle;">
                <QuestionFilled/>
              </el-icon>
            </el-tooltip>
          </span>
        </template>
        <el-input v-model="form.component" :disabled="type === 'view'" placeholder="请输入组件路径">
          <template #prepend>@/views/</template>
          <template #append>.vue</template>
        </el-input>
      </el-form-item>
      <el-form-item v-if="(form.type === 2 && form.isExt !== 1) || form.type === 3" prop="permission">
        <template #label>
          <span style="display: inline-flex; align-items: center;">
            <span>权限标识</span>
            <el-tooltip
                content="权限标识用于标识后端接口权限。未配置时，对应的操作按钮将不会显示，且无法获得相应的接口访问权限。"
                placement="top"
            >
              <el-icon style="margin-left: 4px; cursor: help; color: #909399; vertical-align: middle;">
                <QuestionFilled/>
              </el-icon>
            </el-tooltip>
          </span>
        </template>
        <el-input v-model="form.permission" :disabled="type === 'view'" placeholder="请输入权限标识"/>
      </el-form-item>
      <el-form-item label="排序" prop="sortOrder">
        <el-input-number v-model="form.sortOrder" :disabled="type === 'view'" :min="0" placeholder="请输入排序"
                         style="width: 100%"/>
      </el-form-item>
      <el-form-item label="状态" prop="isEnabled">
        <el-radio-group v-model="form.isEnabled" :disabled="type === 'view'">
          <el-radio v-for="item in isEnabledOptions" :key="item.value" :label="item.value">{{ item.label }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="可见" prop="isVisible">
        <el-radio-group v-model="form.isVisible" :disabled="type === 'view'">
          <el-radio :label="1">显示</el-radio>
          <el-radio :label="0">隐藏</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" :disabled="type === 'view'" type="textarea" placeholder="请输入备注"/>
      </el-form-item>
      <el-form-item v-if="type !== 'view'">
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
        <el-button @click="$emit('close')">取 消</el-button>
      </el-form-item>
      <el-form-item v-else>
        <el-button @click="$emit('close')">关闭</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script setup lang="ts">
import {computed, onMounted, ref} from 'vue';
import {menuTypeOptions, isEnabledOptions} from '@/constants';
import * as menuApi from '@/api/modules/monitor/menuApi';
import {getModalTitle} from '@/types/modal';
import type {ResponseData} from '@/api/http.ts';
import {IconSelector} from '@/components/IconPicker';
import {QuestionFilled} from '@element-plus/icons-vue';

const props = defineProps({
  visible: Boolean,
  type: {
    type: String,
    default: 'add' // add, edit, view
  },
  menuTreeOptions: {
    type: Array,
  },
  menuData: {
    type: Object,
    default: () => ({})
  }
});
const emit = defineEmits(['close', 'success']);

const formRef = ref();
const loading = ref(false);

const form = ref({
  id: undefined as number | undefined,
  name: '',
  type: 1,
  parentId: undefined as number | undefined,
  icon: '',
  path: '',
  component: '',
  permission: '',
  sortOrder: 0,
  isEnabled: 1,
  isEnabledLabel: '',
  isVisible: 1,
  isVisibleLabel: '',
  isExt: 0,
  openMode: 0,
  remark: ''
});

const rules = computed(() => ({
  name: [
    {required: true, message: '请输入菜单名称', trigger: 'blur'}
  ],
  sortOrder: [
    {required: true, message: '请输入排序', trigger: 'blur'}
  ],
  type: [
    {required: true, message: '请选择菜单类型', trigger: 'change'}
  ],
  parentId: [
    {required: true, message: '请选择上级菜单', trigger: 'change'}
  ],
  isEnabled: [
    {required: true, message: '请选择状态', trigger: 'change'}
  ],
  isVisible: [
    {required: true, message: '请选择可见性', trigger: 'change'}
  ],
  path: [
    {required: true, message: '请输入路由地址', trigger: 'blur'}
  ],
  component: [
    {
      required: form.value.type === 2 && form.value.isExt !== 1,
      validator: (rule: any, value: any, callback: any) => {
        if (form.value.type === 2 && form.value.isExt !== 1 && !value) {
          callback(new Error('请输入组件路径'));
        } else {
          callback();
        }
      },
      trigger: 'submit'
    }
  ],
  isExt: [
    {
      validator: (rule: any, value: any, callback: any) => {
        if (form.value.type === 2 && (value === undefined || value === null || value === '')) {
          callback(new Error('请选择是否外链'));
        } else {
          callback();
        }
      },
      trigger: 'change'
    }
  ],
  permission: [
    {
      required: (form.value.type === 2 && form.value.isExt !== 1) || form.value.type === 3,
      validator: (rule: any, value: any, callback: any) => {
        if (((form.value.type === 2 && form.value.isExt !== 1) || form.value.type === 3) && !value) {
          callback(new Error('请输入权限标识'));
        } else {
          callback();
        }
      },
      trigger: 'submit'
    }
  ]
}));

const modalTitle = computed(() => {
  return getModalTitle(props.type as any, '菜单');
});

const loadDetail = async (id?: number) => {
  if (!id) return;
  try {
    loading.value = true;
    const detail = await menuApi.getMenuById(id) as ResponseData;
    form.value = detail.data;
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  if (props.type === 'edit' || props.type === 'view') {
    loadDetail(props.menuData?.id);
  } else if (props.type === 'add') {
    // 新增时，如果传入了parentId，则设置为上级菜单
    if (props.menuData?.parentId) {
      form.value.parentId = props.menuData.parentId;
    }
  }
})

const treeOptionsWithRoot = computed(() => {
  const root = {id: 0, name: '根菜单', children: props.menuTreeOptions || []};
  return [root];
});

const handleSubmit = () => {
  formRef.value.validate(async (valid: boolean) => {
    if (!valid) return;

    if (props.type === 'add') {
      await menuApi.createMenu(form.value);
    } else if (props.type === 'edit') {
      await menuApi.updateMenu(form.value.id as number, form.value);
    }
    emit('success');
  });
};
</script> 