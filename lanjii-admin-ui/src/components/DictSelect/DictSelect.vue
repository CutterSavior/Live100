<template>
  <el-select v-bind="$attrs">
    <el-option
        v-for="item in dictList"
        :key="item.dictValue"
        :label="item.dictLabel"
        :value="item.dictValue"
    />
  </el-select>
</template>

<script setup lang="ts">
import {computed} from 'vue'
import {useDictStore} from '@/stores/dict.store'

interface Props {
  dictType: string
}

const props = defineProps<Props>()

const dictStore = useDictStore()

const dictList = computed(() => {
  return dictStore.getDictByType(props.dictType)
})

</script>

<style scoped>
</style>
