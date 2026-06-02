import request from '@/utils/request'

// 订单管理
export const getOrderDataAPI = () => {
  return request({
    url: `/admin/workspace/overviewOrders`,  // 加 /admin
    method: 'get'
  })
}

// 菜品总览
export const getOverviewDishesAPI = () => {
  return request({
    url: `/admin/workspace/overviewDishes`,  // 加 /admin
    method: 'get'
  })
}

// 套餐总览
export const getSetMealStatisticsAPI = () => {
  return request({
    url: `/admin/workspace/overviewSetmeals`,  // 加 /admin
    method: 'get'
  })
}

// 营业数据
export const getBusinessDataAPI = () => {
  return request({
    url: `/admin/workspace/businessData`,  // 加 /admin
    method: 'get'
  })
}