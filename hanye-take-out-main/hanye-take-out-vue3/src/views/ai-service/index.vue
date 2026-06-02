<template>
  <div class="ai-service-page">
    <div class="chat-container">
      <!-- 消息列表 -->
      <div class="message-list" ref="messageListRef">
        <div
          v-for="(item, index) in messages"
          :key="index"
          :class="['message-item', item.role]"
        >
          <div v-if="item.role === 'ai'" class="avatar ai-avatar">AI</div>
          <div v-if="item.role === 'user'" class="avatar user-avatar">我</div>
          <div class="message-content" v-html="formatContent(item.content)"></div>
        </div>

        <div v-if="loading" class="message-item ai">
          <div class="avatar ai-avatar">AI</div>
          <div class="message-content loading">
            <span class="dot-flashing"></span>
          </div>
        </div>
      </div>

      <div class="input-bar">
        <el-input
          v-model="inputMsg"
          placeholder="请输入外卖问题..."
          @keyup.enter="sendMessage"
          clearable
          :disabled="loading"
          style="flex: 1; min-width: 0"
        />
        <el-button
          type="primary"
          @click="sendMessage"
          :loading="loading"
          :disabled="!inputMsg.trim()"
        >
          发送
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, nextTick } from 'vue'
import { useUserInfoStore } from '@/store'

interface Message {
  role: 'ai' | 'user'
  content: string
}

const messages = ref<Message[]>([
  { role: 'ai', content: '你好！我是外卖AI客服，可咨询订单、配送、菜品、退款~' }
])

const inputMsg = ref('')
const loading = ref(false)
const userInfoStore = useUserInfoStore()
const messageListRef = ref<HTMLElement | null>(null)
let sseBuffer = ''

const formatContent = (content: string) => {
  if (!content) return ''
  return content.replace(/\n/g, '<br>')
}

const normalizeSseData = (data: string) => {
  const trimmed = data.trim()
  if ((trimmed.startsWith('"') && trimmed.endsWith('"')) || (trimmed.startsWith('“') && trimmed.endsWith('”'))) {
    try {
      return JSON.parse(trimmed)
    } catch {
      return trimmed.substring(1, trimmed.length - 1)
    }
  }
  return trimmed
}

const parseSseChunk = (chunk: string, onData: (data: string) => void) => {
  sseBuffer += chunk
  const parts = sseBuffer.split(/\r?\n\r?\n/)
  sseBuffer = parts.pop() ?? ''

  for (const part of parts) {
    const lines = part.split(/\r?\n/)
    const dataLines = lines.filter(line => line.startsWith('data:'))
    if (!dataLines.length) continue

    const data = dataLines.map(line => line.replace(/^data:\s?/, '')).join('\n')
    const normalized = normalizeSseData(data)
    if (normalized) {
      onData(normalized)
    }
  }
}

const appendReply = (index: number, text: string) => {
  const current = messages.value[index]
  if (!current) return
  current.content += text
  nextTick(() => {
    if (messageListRef.value) {
      messageListRef.value.scrollTop = messageListRef.value.scrollHeight
    }
  })
}

const scrollToBottom = async () => {
  await nextTick()
  setTimeout(() => {
    if (messageListRef.value) {
      messageListRef.value.scrollTop = messageListRef.value.scrollHeight
    }
  }, 50)
}

const sendMessage = async () => {
  if (!inputMsg.value.trim()) return
  const userMsg = inputMsg.value
  messages.value.push({ role: 'user', content: userMsg })
  inputMsg.value = ''
  loading.value = true
  sseBuffer = ''

  messages.value.push({ role: 'ai', content: '' })
  const currentAiIndex = messages.value.length - 1

  try {
    const headers: Record<string, string> = {}
    if (userInfoStore.userInfo?.token) {
      headers.Authorization = userInfoStore.userInfo.token
    }

  
    const res = await fetch(`/api/admin/ai/stream?message=${encodeURIComponent(userMsg)}`, {
      method: 'GET',
      headers,
      credentials: 'include'
    })

    if (!res.ok || !res.body) {
      throw new Error('AI 流式服务不可用')
    }

    const reader = res.body.getReader()
    const decoder = new TextDecoder()
    let streamDone = false

    while (!streamDone) {
      const result = await reader.read()
      streamDone = result.done
      if (streamDone) break
      const text = decoder.decode(result.value, { stream: true })
      parseSseChunk(text, data => appendReply(currentAiIndex, data))
    }

    if (sseBuffer) {
      parseSseChunk('\n\n', data => appendReply(currentAiIndex, data))
    }
  } catch (err) {
    const current = messages.value[currentAiIndex]
    if (current) {
      current.content = 'AI 客服暂时不可用，请稍后再试'
    }
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  scrollToBottom()
})
</script>

<style scoped>
.ai-service-page {
  padding: 20px;
  height: calc(100vh - 120px);
  background: #f5f7fa;
}
.chat-container {
  height: 100%;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
.message-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
  flex: 1;
  padding: 20px;
  overflow-y: auto;
}
.message-item {
  display: flex;
  width: 100%;
  margin-bottom: 0;
  align-items: flex-start;
}
.message-item.user {
  flex-direction: row-reverse;
}
.avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 12px;
  flex-shrink: 0;
  margin: 0 10px;
}
.ai-avatar { background: #409eff; }
.user-avatar { background: #67c23a; }
.message-content {
  max-width: 70%;
  padding: 10px 14px;
  border-radius: 18px;
  line-height: 1.5;
  background: #f5f7fa;
  word-break: break-word;
}
.message-item.ai .message-content { background: #e8f3ff; }
.message-item.user .message-content { background: #e1f3e8; }
.input-bar {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 16px 20px;
  border-top: 1px solid #ebeef5;
  background: #ffffff;
}
.loading {
  display: flex;
  align-items: center;
  min-width: 60px;
}
.dot-flashing {
  position: relative;
  width: 6px;
  height: 6px;
  border-radius: 3px;
  background-color: #909399;
  animation: dot-flashing 1s infinite linear alternate;
  animation-delay: 0.5s;
}
.dot-flashing::before,
.dot-flashing::after {
  content: "";
  display: inline-block;
  position: absolute;
  top: 0;
}
.dot-flashing::before {
  left: -10px;
  width: 6px;
  height: 6px;
  border-radius: 3px;
}
.dot-flashing::after {
  right: -10px;
  width: 6px;
  height: 6px;
  border-radius: 3px;
}
@keyframes dot-flashing {
  0% { opacity: 0.2; }
  100% { opacity: 1; }
}
</style>
