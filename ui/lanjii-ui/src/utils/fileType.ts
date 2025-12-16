// 文件分类枚举
export enum FileCategory {
  IMAGE = 'IMAGE',
  VIDEO = 'VIDEO',
  AUDIO = 'AUDIO',
  DOCUMENT = 'DOCUMENT',
  ARCHIVE = 'ARCHIVE',
  TEXT = 'TEXT',
  OTHER = 'OTHER'
}

// 文件扩展名到分类的映射
const EXTENSION_TYPE_MAP: Record<string, FileCategory> = {
  // 图片类型
  '.jpg': FileCategory.IMAGE,
  '.jpeg': FileCategory.IMAGE,
  '.png': FileCategory.IMAGE,
  '.gif': FileCategory.IMAGE,
  '.bmp': FileCategory.IMAGE,
  '.webp': FileCategory.IMAGE,
  '.svg': FileCategory.IMAGE,
  '.ico': FileCategory.IMAGE,
  '.tiff': FileCategory.IMAGE,
  '.tif': FileCategory.IMAGE,

  // 视频类型
  '.mp4': FileCategory.VIDEO,
  '.avi': FileCategory.VIDEO,
  '.mov': FileCategory.VIDEO,
  '.wmv': FileCategory.VIDEO,
  '.flv': FileCategory.VIDEO,
  '.mkv': FileCategory.VIDEO,
  '.webm': FileCategory.VIDEO,
  '.mpeg': FileCategory.VIDEO,
  '.mpg': FileCategory.VIDEO,
  '.m4v': FileCategory.VIDEO,

  // 音频类型
  '.mp3': FileCategory.AUDIO,
  '.wav': FileCategory.AUDIO,
  '.aac': FileCategory.AUDIO,
  '.ogg': FileCategory.AUDIO,
  '.flac': FileCategory.AUDIO,
  '.m4a': FileCategory.AUDIO,
  '.wma': FileCategory.AUDIO,
  '.ape': FileCategory.AUDIO,

  // 文档类型
  '.doc': FileCategory.DOCUMENT,
  '.docx': FileCategory.DOCUMENT,
  '.xls': FileCategory.DOCUMENT,
  '.xlsx': FileCategory.DOCUMENT,
  '.ppt': FileCategory.DOCUMENT,
  '.pptx': FileCategory.DOCUMENT,
  '.pdf': FileCategory.DOCUMENT,
  '.rtf': FileCategory.DOCUMENT,

  // 压缩文件
  '.zip': FileCategory.ARCHIVE,
  '.rar': FileCategory.ARCHIVE,
  '.7z': FileCategory.ARCHIVE,
  '.tar': FileCategory.ARCHIVE,
  '.gz': FileCategory.ARCHIVE,
  '.bz2': FileCategory.ARCHIVE,
  '.xz': FileCategory.ARCHIVE,

  // 文本类型
  '.txt': FileCategory.TEXT,
  '.csv': FileCategory.TEXT,
  '.json': FileCategory.TEXT,
  '.xml': FileCategory.TEXT,
  '.html': FileCategory.TEXT,
  '.htm': FileCategory.TEXT,
  '.css': FileCategory.TEXT,
  '.js': FileCategory.TEXT,
  '.java': FileCategory.TEXT,
  '.py': FileCategory.TEXT,
  '.cpp': FileCategory.TEXT,
  '.c': FileCategory.TEXT,
  '.md': FileCategory.TEXT,
  '.log': FileCategory.TEXT,
}

/**
 * 根据文件扩展名获取文件分类
 * @param extension 文件扩展名（如 .jpg 或 jpg）
 * @returns 文件分类
 */
export function getFileCategory(extension: string): FileCategory {
  if (!extension) {
    return FileCategory.OTHER
  }
  
  // 确保扩展名以点开头且为小写
  const normalizedExtension = extension.startsWith('.') ? extension.toLowerCase() : `.${extension.toLowerCase()}`
  
  return EXTENSION_TYPE_MAP[normalizedExtension] || FileCategory.OTHER
}

/**
 * 判断文件是否为图片
 * @param extension 文件扩展名
 * @returns 是否为图片
 */
export function isImage(extension: string): boolean {
  return getFileCategory(extension) === FileCategory.IMAGE
}

/**
 * 判断文件是否为视频
 * @param extension 文件扩展名
 * @returns 是否为视频
 */
export function isVideo(extension: string): boolean {
  return getFileCategory(extension) === FileCategory.VIDEO
}

/**
 * 判断文件是否为音频
 * @param extension 文件扩展名
 * @returns 是否为音频
 */
export function isAudio(extension: string): boolean {
  return getFileCategory(extension) === FileCategory.AUDIO
}

/**
 * 判断文件是否为文档
 * @param extension 文件扩展名
 * @returns 是否为文档
 */
export function isDocument(extension: string): boolean {
  return getFileCategory(extension) === FileCategory.DOCUMENT
}

/**
 * 判断文件是否为压缩包
 * @param extension 文件扩展名
 * @returns 是否为压缩包
 */
export function isArchive(extension: string): boolean {
  return getFileCategory(extension) === FileCategory.ARCHIVE
}

/**
 * 判断文件是否为文本
 * @param extension 文件扩展名
 * @returns 是否为文本
 */
export function isText(extension: string): boolean {
  return getFileCategory(extension) === FileCategory.TEXT
}

/**
 * 获取文件分类的中文名称
 * @param category 文件分类
 * @returns 中文名称
 */
export function getFileCategoryName(category: FileCategory): string {
  const nameMap: Record<FileCategory, string> = {
    [FileCategory.IMAGE]: '图片',
    [FileCategory.VIDEO]: '视频',
    [FileCategory.AUDIO]: '音频',
    [FileCategory.DOCUMENT]: '文档',
    [FileCategory.ARCHIVE]: '压缩包',
    [FileCategory.TEXT]: '文本',
    [FileCategory.OTHER]: '其他'
  }
  
  return nameMap[category] || '其他'
}
