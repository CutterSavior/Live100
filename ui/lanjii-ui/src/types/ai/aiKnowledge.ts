/**
 * AI知识库类型定义
 */
export interface AiKnowledge {
    /**
     * 主键ID
     */
    id?: number

    /**
     * 知识库标题
     */
    title: string

    /**
     * 知识库内容
     */
    content: string

    /**
     * 创建时间
     */
    createTime?: string

    /**
     * 更新时间
     */
    updateTime?: string

    /**
     * 创建人
     */
    createBy?: string

    /**
     * 更新人
     */
    updateBy?: string
}
