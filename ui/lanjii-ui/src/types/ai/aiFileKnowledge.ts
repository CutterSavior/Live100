/**
 * AI文件知识库类型定义
 */
export interface AiFileKnowledge {
    /**
     * 主键ID
     */
    id?: number

    /**
     * 文件名称
     */
    fileName: string

    /**
     * 文件类型
     */
    fileType?: string

    /**
     * 文件存储路径
     */
    filePath?: string

    /**
     * 文件大小（字节）
     */
    fileSize?: number

    /**
     * 文件大小标签
     */
    fileSizeLabel?: string

    /**
     * 元数据 JSON 字符串
     */
    metadataJson?: string

    /**
     * 前端使用的元数据对象
     */
    metadata?: Record<string, any>

    /**
     * 文件对象
     */
    file?: File

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
