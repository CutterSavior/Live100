/**
 * 日期时间工具函数
 * @description 提供日期时间格式化、转换等相关工具函数
 */

/**
 * 格式化时间为相对时间（多久前）
 * @param time 时间字符串
 * @returns 相对时间字符串（如：刚刚、5分钟前、2小时前、3天前等）
 * @example
 * formatRelativeTime('2024-11-27 10:00:00') // '2小时前'
 */
export const formatRelativeTime = (time: string): string => {
  const now = new Date().getTime()
  const publishTime = new Date(time).getTime()
  const diff = now - publishTime
  
  const minute = 60 * 1000
  const hour = 60 * minute
  const day = 24 * hour
  
  if (diff < minute) {
    return '刚刚'
  } else if (diff < hour) {
    return `${Math.floor(diff / minute)}分钟前`
  } else if (diff < day) {
    return `${Math.floor(diff / hour)}小时前`
  } else if (diff < 7 * day) {
    return `${Math.floor(diff / day)}天前`
  } else {
    return time.substring(0, 10)
  }
}

/**
 * 格式化日期时间
 * @param date 日期对象或时间字符串
 * @param format 格式化模板，默认 'YYYY-MM-DD HH:mm:ss'
 * @returns 格式化后的日期字符串
 * @example
 * formatDateTime(new Date(), 'YYYY-MM-DD') // '2024-11-27'
 * formatDateTime('2024-11-27 10:00:00', 'HH:mm') // '10:00'
 */
export const formatDateTime = (
  date: Date | string, 
  format: string = 'YYYY-MM-DD HH:mm:ss'
): string => {
  const d = typeof date === 'string' ? new Date(date) : date
  
  const year = d.getFullYear()
  const month = String(d.getMonth() + 1).padStart(2, '0')
  const day = String(d.getDate()).padStart(2, '0')
  const hours = String(d.getHours()).padStart(2, '0')
  const minutes = String(d.getMinutes()).padStart(2, '0')
  const seconds = String(d.getSeconds()).padStart(2, '0')
  
  return format
    .replace('YYYY', String(year))
    .replace('MM', month)
    .replace('DD', day)
    .replace('HH', hours)
    .replace('mm', minutes)
    .replace('ss', seconds)
}

/**
 * 获取当前时间戳（毫秒）
 * @returns 当前时间戳
 */
export const getTimestamp = (): number => {
  return new Date().getTime()
}

/**
 * 判断是否为今天
 * @param date 日期对象或时间字符串
 * @returns 是否为今天
 */
export const isToday = (date: Date | string): boolean => {
  const d = typeof date === 'string' ? new Date(date) : date
  const today = new Date()
  
  return d.getFullYear() === today.getFullYear() &&
         d.getMonth() === today.getMonth() &&
         d.getDate() === today.getDate()
}

/**
 * 判断是否为本周
 * @param date 日期对象或时间字符串
 * @returns 是否为本周
 */
export const isThisWeek = (date: Date | string): boolean => {
  const d = typeof date === 'string' ? new Date(date) : date
  const now = new Date()
  const dayOfWeek = now.getDay() || 7 // 将周日从0改为7
  const startOfWeek = new Date(now.getFullYear(), now.getMonth(), now.getDate() - dayOfWeek + 1)
  const endOfWeek = new Date(now.getFullYear(), now.getMonth(), now.getDate() + (7 - dayOfWeek))
  
  return d >= startOfWeek && d <= endOfWeek
}
