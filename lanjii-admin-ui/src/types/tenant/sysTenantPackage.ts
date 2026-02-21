/**
 * 租户套餐实体
 */
export interface SysTenantPackage {
    /** 套餐ID */
    id?: number
    /** 套餐名称 */
    packageName?: string
    /** 关联的菜单ID（逗号分隔字符串） */
    menuIds?: string
    /** 关联的菜单ID列表 */
    menuIdList?: number[]
    /** 状态（1-正常，0-停用） */
    status?: number
    /** 备注 */
    remark?: string
    /** 创建时间 */
    createTime?: string
    /** 更新时间 */
    updateTime?: string
}
