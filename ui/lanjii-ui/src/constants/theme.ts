/**
 * 主题配置
 * @description 基于实际使用的颜色配置，主要用于主题切换功能
 */

// 默认主色
export const DEFAULT_PRIMARY = '#409eff'

// 表格颜色配置（用于特殊模式）
export const TABLE_COLORS = {
  normal: '#222',    // 正常模式下的表格标题颜色
  special: '#666'    // 灰色/色弱模式下的表格标题颜色
} as const

// 主题配置
export const THEME_CONFIG = {
  // 默认主色
  defaultPrimary: DEFAULT_PRIMARY,
  
  // 表格颜色
  tableColors: TABLE_COLORS,
  
  // CSS 变量名称（用于动态设置主题）
  cssVars: {
    primary: '--el-color-primary',
    primaryLight: '--el-color-primary-light-',  // 后面会拼接数字 1-9
    tableThColor: '--el-table-th-color'
  }
} as const
