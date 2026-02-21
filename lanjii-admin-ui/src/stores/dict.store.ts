import {defineStore} from 'pinia'
import type {SysDictData} from '@/types/sys/sysDictData'
import {getAllEnabledDictData, getEnabledDictDataByType} from '@/api/modules/sys/dictApi'

interface DictState {
    // 字典数据按类型分组存储
    dictMap: Record<string, SysDictData[]>
    // 是否已加载
    loaded: boolean
}

export const useDictStore = defineStore('dict', {
    state: (): DictState => ({
        dictMap: {},
        loaded: false
    }),

    getters: {
        /**
         * 根据字典类型获取字典数据
         */
        getDictByType: (state) => {
            return (dictType: string): SysDictData[] => {
                return state.dictMap[dictType] || []
            }
        },

        /**
         * 根据字典类型和值获取标签
         */
        getDictLabel: (state) => {
            return (dictType: string, value: string | number): string => {
                const dicts = state.dictMap[dictType] || []
                const dict = dicts.find((item) => String(item.dictValue) === String(value))
                return dict?.dictLabel || String(value)
            }
        }
    },

    actions: {
        /**
         * 加载所有启用的字典数据
         */
        async loadAllDicts() {
            try {
                const res = await getAllEnabledDictData()
                if (res.data) {
                    // 按字典类型分组
                    const groupedDict: Record<string, SysDictData[]> = {}
                    res.data.forEach((item) => {
                        const type = item.dictType
                        if (!type) return

                        if (!groupedDict[type]) {
                            groupedDict[type] = []
                        }
                        groupedDict[type].push(item)
                    })

                    this.dictMap = groupedDict
                    this.loaded = true
                }
            } catch (error) {
                console.error('[DictStore] 加载字典数据失败：', error)
                throw error
            }
        },

        /**
         * 清空字典数据
         */
        clearDicts() {
            this.dictMap = {}
            this.loaded = false
        },

        /**
         * 刷新字典数据
         */
        async refreshDicts() {
            this.clearDicts()
            await this.loadAllDicts()
        },

        /**
         * 加载指定类型的字典数据
         */
        async loadDictByType(dictType: string) {
            try {
                const res = await getEnabledDictDataByType(dictType)
                if (res.data) {
                    this.dictMap[dictType] = res.data
                }
            } catch (error) {
                console.error(`[DictStore] 加载字典类型 ${dictType} 失败：`, error)
                throw error
            }
        },

        /**
         * 刷新指定类型的字典数据
         */
        async refreshDictByType(dictType: string) {
            await this.loadDictByType(dictType)
        }
    },

    persist: {
        key: 'dict-store',
        storage: sessionStorage
    }
})
