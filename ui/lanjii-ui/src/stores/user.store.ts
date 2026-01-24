import {defineStore} from 'pinia'
import type {SysMenu} from "@/types/sys/sysMenu.ts";
import type {SysUser} from "@/types/sys/sysUser.ts";

export const useUserStore = defineStore('user', {
    state: () => ({
        token: '',
        userInfo: {} as SysUser,
        menus: [] as SysMenu[],
        permissions: [] as string[],
        displayUuid: ''
    }),
    getters: {
        isLoggedIn: (state) => !!state.token,
        userMenus: (state) => state.menus,
        userPermissions: (state) => state.permissions
    },
    actions: {
        setToken(newToken: string) {
            this.token = newToken
        },
        clearToken() {
            this.token = ''
        },
        setUserInfo(info: SysUser) {
            this.userInfo = info
        },
        setMenus(menus: SysMenu[]) {
            this.menus = menus
        },
        setPermissions(permissions: string[]) {
            this.permissions = permissions
        },
        setDisplayUuid(displayUuid: string) {
            this.displayUuid = displayUuid
        },
        clearUserData() {
            this.token = ''
            this.userInfo = {} as SysUser
            this.menus = []
            this.permissions = []
            this.displayUuid = ''
        }
    },
    persist: {
        key: 'user-store',
        storage: localStorage
    }
}) 