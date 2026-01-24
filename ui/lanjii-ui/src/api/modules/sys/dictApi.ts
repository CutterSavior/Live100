import {del, get, post, put} from '@/api/http'
import type {SysDictType} from '@/types/sys/sysDictType.ts'
import type {SysDictData} from '@/types/sys/sysDictData.ts'
import type {PageResponse} from '@/types/common'

// 获取字典类型分页列表
export function getDictTypeList(params: any) {
    return get<PageResponse<SysDictType>>('/admin/sys/dict-types', params)
}

// 获取字典类型详情
export function getDictTypeById(id: number) {
    return get<SysDictType>(`/admin/sys/dict-types/${id}`)
}

// 新增字典类型
export function createDictType(data: SysDictType) {
    return post('/admin/sys/dict-types', data)
}

// 更新字典类型
export function updateDictType(id: number, data: SysDictType) {
    return put(`/admin/sys/dict-types/${id}`, data)
}

// 删除字典类型
export function deleteDictType(id: number) {
    return del(`/admin/sys/dict-types/${id}`)
}

// 按类型编码查询字典数据
export function getDictDataByTypeCode(typeCode: string, params?: SysDictData) {
    return get<PageResponse<SysDictData>>(`/admin/sys/dict-types/${typeCode}/data`, params)
}

// 获取字典数据分页列表
export function getDictDataList(params: any) {
    return get<PageResponse<SysDictData>>('/admin/sys/dict-data', params)
}

// 获取字典数据详情
export function getDictDataById(id: number) {
    return get<SysDictData>(`/admin/sys/dict-data/${id}`)
}

// 新增字典数据
export function createDictData(data: SysDictData) {
    return post('/admin/sys/dict-data', data)
}

// 更新字典数据
export function updateDictData(id: number, data: SysDictData) {
    return put(`/admin/sys/dict-data/${id}`, data)
}

// 删除字典数据
export function deleteDictData(id: number) {
    return del(`/admin/sys/dict-data/${id}`)
}

// 清除字典数据缓存
export function clearDictDataCache() {
    return post('/admin/sys/dict-data/cache/clear', {})
}

// 获取所有启用的字典数据
export function getAllEnabledDictData() {
    return get<SysDictData[]>('/admin/sys/dict-types/enabled-data')
}

// 获取指定类型的所有启用字典数据
export function getEnabledDictDataByType(typeCode: string) {
    return get<SysDictData[]>(`/admin/sys/dict-types/${typeCode}/enabled-data`)
}
