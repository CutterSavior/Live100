export interface AiModelConfig {
  id?: number
  configName: string
  apiProvider: string
  modelId: string
  isEnabled: number
  isDefault: number
  roleId?: number | null
  apiKey: string
  apiEndpoint?: string
  description?: string
  temperature?: number
  topP?: number
  maxTokens?: number
  timeoutSeconds?: number
  frequencyPenalty?: number
  presencePenalty?: number
  stopSequences?: string
  retryCount?: number
  createTime?: string
  updateTime?: string
  createBy?: string
  updateBy?: string
}
