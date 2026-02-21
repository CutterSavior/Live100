/**
 * Store 相关的类型定义
 */

// 布局模式类型
export type LayoutModelType = "vertical" | "classic" | "transverse" | "columns";

// 标签页类型定义
export interface TabItem {
    title: string;
    path: string;
    name: string;
    icon: string;
}
