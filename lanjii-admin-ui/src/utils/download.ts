/**
 * 文件下载工具函数
 */

/**
 * 下载文件
 * @param blob 文件blob数据
 * @param filename 文件名
 */
export function downloadFile(blob: any, filename: string) {
  // 检查是否为有效的Blob对象
  if (!blob || !(blob instanceof Blob)) {
    console.error('Invalid blob data:', blob)
    throw new Error('无效的文件数据')
  }

  try {
    // 创建下载链接
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = filename
    
    // 触发下载
    document.body.appendChild(link)
    link.click()
    
    // 清理
    document.body.removeChild(link)
    window.URL.revokeObjectURL(url)
  } catch (error) {
    console.error('Download failed:', error)
    throw new Error('文件下载失败')
  }
}

/**
 * 从响应头获取文件名
 * @param response 响应对象
 * @param defaultName 默认文件名
 */
export function getFilenameFromResponse(response: any, defaultName: string = 'download'): string {
  const contentDisposition = response.headers?.['content-disposition']
  if (contentDisposition) {
    const matches = contentDisposition.match(/filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/)
    if (matches && matches[1]) {
      return matches[1].replace(/['"]/g, '')
    }
  }
  return defaultName
}

/**
 * 生成带时间戳的文件名
 * @param prefix 文件名前缀
 * @param extension 文件扩展名
 */
export function generateTimestampFilename(prefix: string, extension: string): string {
  const now = new Date()
  const timestamp = now.toISOString().slice(0, 19).replace(/[-:]/g, '').replace('T', '_')
  return `${prefix}_${timestamp}.${extension}`
}
