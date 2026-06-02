// src/utils/request.ts
import axios from 'axios'
import router from '@/router'
import { ElMessage } from 'element-plus'
import { useUserInfoStore } from '@/store'

const baseURL = '/api'
const instance = axios.create({ baseURL })

const userInfoStore = useUserInfoStore()

// 请求拦截器
instance.interceptors.request.use(
  (config) => {
    const token = userInfoStore.userInfo ? userInfoStore.userInfo.token : null
    if (token) {
      config.headers.Authorization = token
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// 响应拦截器 - 统一返回完整响应对象
instance.interceptors.response.use(
  (response) => {
    console.log('响应拦截器:', response)
    
    // 直接返回完整的 response 对象，这样页面中的 res.data、res.data.data 等写法都能正常工作
    return response
  },
  (error) => {
    console.dir(error)
    if (error.response?.status === 401) {
      userInfoStore.userInfo = null
      ElMessage.error('用户身份已过期~')
      router.push('/login')
    } else {
      ElMessage.error(error.message || '网络错误')
    }
    return Promise.reject(error)
  }
)

export default instance