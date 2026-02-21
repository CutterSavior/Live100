/**
 * 模态框类型定义
 */

/**
 * 模态框操作类型
 */
export type ModalType = 'add' | 'edit' | 'view'

/**
 * 模态框状态接口
 */
export interface ModalState {
  /** 是否显示模态框 */
  visible: boolean
  /** 模态框类型 */
  type: ModalType
  /** 当前操作的行数据 */
  currentRow?: any
  /** 模态框标题 */
  title?: string
}

/**
 * 模态框配置接口
 */
export interface ModalConfig {
  /** 模态框宽度 */
  width?: string
  /** 是否可拖拽 */
  draggable?: boolean
  /** 是否显示关闭按钮 */
  showClose?: boolean
  /** 是否点击遮罩层关闭 */
  closeOnClickModal?: boolean
  /** 是否在关闭时销毁子元素 */
  destroyOnClose?: boolean
}

/**
 * 模态框事件接口
 */
export interface ModalEvents {
  /** 关闭事件 */
  onClose: () => void
  /** 成功事件 */
  onSuccess: () => void
  /** 取消事件 */
  onCancel?: () => void
}

/**
 * 模态框标题映射
 */
export const MODAL_TITLES: Record<ModalType, string> = {
  add: '新增',
  edit: '编辑',
  view: '查看'
}

/**
 * 获取模态框标题的辅助函数
 * @param type 模态框类型
 * @param entityName 实体名称，如 '用户'、'角色' 等
 * @returns 完整的模态框标题
 */
export function getModalTitle(type: ModalType, entityName: string = ''): string {
  const baseTitle = MODAL_TITLES[type]
  return entityName ? `${baseTitle}${entityName}` : baseTitle
}

/**
 * 模态框类型守卫
 * @param value 要检查的值
 * @returns 是否为有效的模态框类型
 */
export function isModalType(value: any): value is ModalType {
  return ['add', 'edit', 'view'].includes(value)
}
