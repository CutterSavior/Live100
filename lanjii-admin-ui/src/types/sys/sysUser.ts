/**
 * 系统用户实体
 */
export interface SysUser {
    /** 租户ID */
    tenantId?: number
    /** 用户ID */
    id?: number
    /** 用户名 */
    username?: string
    /** 密码 */
    password?: string
    /** 昵称 */
    nickname?: string
    /** 邮箱 */
    email?: string
    /** 手机号 */
    phone?: string
    /** 头像URL */
    avatar?: string
    /** 是否启用（1启用 0禁用） */
    isEnabled?: number
    /** 所属部门 ID */
    deptId?: number
    /** 最后登录时间 */
    lastLoginTime?: string
    /** 最后登录IP */
    lastLoginIp?: string
    /** 是否系统管理员（0-否，1-是） */
    isAdmin?: number
    /** 创建时间 */
    createTime?: string
    /** 更新时间 */
    updateTime?: string
    /** 创建人 */
    createBy?: string
    /** 更新人 */
    updateBy?: string
    /** 删除标志（0-未删除，1-已删除） */
    deleted?: number
}
