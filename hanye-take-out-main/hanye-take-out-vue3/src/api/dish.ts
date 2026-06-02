import request from '@/utils/request'

/**
 * 添加菜品
 */
export const addDishAPI = (params: any) => {
  return request({
    url: '/admin/dish',
    method: 'post',
    data: { ...params }
  })
}

/**
 * 获取菜品分页列表
 */
export const getDishPageListAPI = (params: any) => {
  console.log('dish-params', params)
  return request({
    url: '/admin/dish/page',
    method: 'get',
    params
  })
}

/**
 * 根据id获取菜品信息
 */
export const getDishByIdAPI = (id: number) => {
  return request({
    url: `/admin/dish/${id}`,
    method: 'get'
  })
}

/**
 * 修改菜品信息
 */
export const updateDishAPI = (params: any) => {
  return request({
    url: '/admin/dish',
    method: 'put',
    data: { ...params }
  })
}

/**
 * 修改菜品状态
 */
export const updateDishStatusAPI = (id: number) => {
  console.log('发请求啊！', id)
  return request({
    url: `/admin/dish/status/${id}`,
    method: 'put'
  })
}

/**
 * 根据ids批量删除菜品
 */
export const deleteDishesAPI = (ids: string) => {
  return request({
    url: '/admin/dish',
    method: 'delete',
    params: { ids }
  })
}