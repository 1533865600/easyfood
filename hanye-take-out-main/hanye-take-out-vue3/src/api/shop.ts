import request from '@/utils/request'

/**
 * 获取店铺状态接口
 */
export const getStatusAPI = () => {
  return request({
    url: '/admin/shop/status',
    method: 'get'
  })
}

/**
 * 修改店铺状态接口
 */
export const fixStatusAPI = (status: number) => {
  console.log(status)
  return request({
    url: `/admin/shop/${status}`,
    method: 'put'
  })
}