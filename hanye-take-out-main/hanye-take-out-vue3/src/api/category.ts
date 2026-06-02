import request from '@/utils/request'

/**
 * 添加分类
 */
export const addCategoryAPI = (params: any) => {
  return request({
    url: '/admin/category',
    method: 'post',
    data: { ...params }
  })
}

/**
 * 获取分类分页列表
 */
export const getCategoryPageListAPI = (params: any) => {
  console.log('type呢！！！', params)
  return request({
    url: '/admin/category/page',
    method: 'get',
    params
  })
}

/**
 * 根据id获取分类信息
 */
export const getCategoryByIdAPI = (id: number) => {
  return request({
    url: `/admin/category/${id}`,
    method: 'get'
  })
}

/**
 * 修改分类信息
 */
export const updateCategoryAPI = (params: any) => {
  return request({
    url: '/admin/category',
    method: 'put',
    data: { ...params }
  })
}

/**
 * 修改分类状态
 */
export const updateCategoryStatusAPI = (id: number) => {
  console.log('发请求啊！', id)
  return request({
    url: `/admin/category/status/${id}`,
    method: 'put'
  })
}

/**
 * 根据id删除分类
 */
export const deleteCategoryAPI = (id: number) => {
  return request({
    url: `/admin/category/${id}`,
    method: 'delete'
  })
}