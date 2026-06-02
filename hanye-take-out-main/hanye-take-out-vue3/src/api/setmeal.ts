import request from '@/utils/request'

/**
 * 添加套餐
 */
export const addSetmealAPI = (params: any) => {
  return request({
    url: '/admin/setmeal',
    method: 'post',
    data: { ...params }
  })
}

/**
 * 获取套餐分页列表
 */
export const getSetmealPageListAPI = (params: any) => {
  console.log('Setmeal-params', params)
  return request({
    url: '/admin/setmeal/page',
    method: 'get',
    params
  })
}

/**
 * 根据id获取套餐信息
 */
export const getSetmealByIdAPI = (id: number) => {
  return request({
    url: `/admin/setmeal/${id}`,
    method: 'get'
  })
}

/**
 * 修改套餐信息
 */
export const updateSetmealAPI = (params: any) => {
  return request({
    url: '/admin/setmeal',
    method: 'put',
    data: { ...params }
  })
}

/**
 * 修改套餐状态
 */
export const updateSetmealStatusAPI = (id: number) => {
  console.log('套餐id', id)
  return request({
    url: `/admin/setmeal/status/${id}`,
    method: 'put'
  })
}

/**
 * 根据ids批量删除套餐
 */
export const deleteSetmealsAPI = (ids: string) => {
  return request({
    url: '/admin/setmeal',
    method: 'delete',
    params: { ids }
  })
}