/** 系统操作日志 */
export interface SysOperLog {
    /** 日志ID */
    id?: number
    /** 操作模块 */
    title?: string
    /** 业务类型（0-新增，1-修改，2-删除） */
    businessType?: number
    /** 方法名称 */
    method?: string
    /** 请求方式（1-GET，2-POST，3-PUT，4-DELETE） */
    requestMethod?: number
    /** 操作人员 */
    operName?: string
    /** 部门名称 */
    deptName?: string
    /** 请求URL */
    operUrl?: string
    /** 请求参数 */
    operParam?: string
    /** 返回参数 */
    jsonResult?: string
    /** 操作状态（0-失败，1-成功） */
    status?: number
    /** 错误消息 */
    errorMsg?: string
    /** 操作时间 */
    operTime?: string
    /** 消耗时间（毫秒） */
    costTime?: number
    /** 创建时间 */
    createTime?: string
}
