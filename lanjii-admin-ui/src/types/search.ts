/**
 * 搜索项接口定义
 */
export interface SearchItem {
    field: string // 字段名，对应model中的属性
    label: string // 标签名
    type: 'input' | 'select' | 'date' | 'daterange' // 输入类型
    placeholder?: string // 占位文本
    options?: Array<{ label: string, value: string | number }> | string
    width?: string // 宽度
    clearable?: boolean // 是否可清除
} 
