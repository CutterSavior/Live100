import {useGlobalSettingStore} from "@/stores/globalSetting.store";
import {getDarkColor, getLightColor} from "@/utils/color.ts";
import {DEFAULT_PRIMARY, THEME_CONFIG, TABLE_COLORS} from "@/constants";
import {ElMessage} from "element-plus";

export const switchTheme = () => {
    const globalSettingStore = useGlobalSettingStore();

    const switchDark = () => {
        let html = document.documentElement  //获取html根元素
        globalSettingStore.isDark ? html.className = 'dark' : html.className = ''
        changePrimary(globalSettingStore.primary)
    }

    // 切换灰色模式
    const switchGrayscale = () => {
        if (globalSettingStore.isColorBlind) {
            globalSettingStore.isColorBlind = false
        }
        if (globalSettingStore.isGrayscale) {
            document.body.setAttribute("style", "filter: grayscale(1);")
            document.documentElement.classList.add('special-mode-active');
            document.documentElement.style.setProperty(THEME_CONFIG.cssVars.tableThColor, TABLE_COLORS.special);
        } else {
            document.body.removeAttribute("style")
            document.documentElement.classList.remove('special-mode-active');
            document.documentElement.style.setProperty(THEME_CONFIG.cssVars.tableThColor, TABLE_COLORS.normal);
        }
    }

    //切换色弱模式
    const switchColorBlind = () => {
        if (globalSettingStore.isGrayscale) {
            globalSettingStore.isGrayscale = false
        }
        if (globalSettingStore.isColorBlind) {
            document.body.setAttribute("style", "filter: invert(80%);")
            document.documentElement.classList.add('special-mode-active');
            document.documentElement.style.setProperty(THEME_CONFIG.cssVars.tableThColor, TABLE_COLORS.special);
        } else {
            document.body.removeAttribute("style")
            document.documentElement.classList.remove('special-mode-active');
            document.documentElement.style.setProperty(THEME_CONFIG.cssVars.tableThColor, TABLE_COLORS.normal);
        }
    }

    const changePrimary = (v: string) => {
        if (!v) {
            v = DEFAULT_PRIMARY
            ElMessage.warning("已重置主题颜色")
        }
        document.documentElement.style.setProperty(THEME_CONFIG.cssVars.primary, v);
        for (let i = 1; i <= 9; i++) {
            const primaryColor = globalSettingStore.isDark ? `${getDarkColor(v, i / 10)}` : `${getLightColor(v, i / 10)}`;
            document.documentElement.style.setProperty(`${THEME_CONFIG.cssVars.primaryLight}${i}`, primaryColor);
        }
        globalSettingStore.primary = v;
    }

    return {changePrimary, switchDark, switchGrayscale, switchColorBlind}
}