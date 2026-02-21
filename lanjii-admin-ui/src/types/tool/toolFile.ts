/** 工具文件 */
export interface ToolFile {
  /** 文件ID */
  id?: number
  /** 文件名称（系统生成的文件名） */
  fileName?: string
  /** 原始文件名 */
  originalName?: string
  /** 文件路径 */
  filePath?: string
  /** 文件大小（字节） */
  fileSize?: number
  /** 文件大小标签（格式化后，如 "100 KB"） */
  fileSizeLabel?: string
  /** 文件类型 */
  fileType?: string
  /** 文件类型名称（如 "图片"） */
  fileTypeLabel?: string
  /** 文件扩展名 */
  fileExtension?: string
  /** 创建时间 */
  createTime?: string
  /** 更新时间 */
  updateTime?: string
}
