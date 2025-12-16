export interface TableColumn {
    prop?: string
    label: string
    type?: 'index' | 'selection' | 'expand'
    width?: number | string
    minWidth?: number | string
    align?: 'left' | 'center' | 'right'
    fixed?: 'left' | 'right'
    slot?: string
    sortable?: string
    showOverflowTooltip?: boolean
}