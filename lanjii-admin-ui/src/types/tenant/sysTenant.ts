/**
 * 租户实体
 */
export interface SysTenant {
    /** 租户ID */
    id?: number
    /** 租户编码 */
    tenantCode?: string
    /** 租户名称 */
    tenantName?: string
    /** 套餐ID */
    packageId?: number
    /** 套餐名称 */
    packageName?: string
    /** 联系人 */
    contactName?: string
    /** 联系电话 */
    contactPhone?: string
    /** 状态（1-正常，0-停用） */
    status?: number
    /** 过期时间 */
    expireTime?: string
    /** 创建时间 */
    createTime?: string
    /** 更新时间 */
    updateTime?: string
}
