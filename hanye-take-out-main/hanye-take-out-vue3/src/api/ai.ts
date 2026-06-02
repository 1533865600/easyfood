import request from '@/utils/request'

// AI 对话接口
export function aiChat(message: string) {
  return request({
    url: '/admin/ai/chat',
    method: 'POST',
    data: { message }
  })
}