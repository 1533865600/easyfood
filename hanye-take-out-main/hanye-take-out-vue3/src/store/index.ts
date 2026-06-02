import { defineStore } from 'pinia'
import type { UserInfo } from '@/types/employee'

export const useUserInfoStore = defineStore('userInfo', {
  state: () => ({
    userInfo: null as UserInfo | null
  }),
  persist: true
})