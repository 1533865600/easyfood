package fun.cyhgraph.service.serviceImpl;

import fun.cyhgraph.entity.AiChatHistory;
import fun.cyhgraph.mapper.AiChatHistoryMapper;
import fun.cyhgraph.service.AiService;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class AiServiceImpl implements AiService {

    private final ChatModel chatModel;
    private final AiChatHistoryMapper aiChatHistoryMapper;

    private static final String SYSTEM_PROMPT = """
            你是苍穹外卖的智能客服，只回答外卖相关问题。
            回答必须直接、简洁、专业，不要进行自我介绍、问候或无关描述。
            不要输出任何引号或英文符号，回答中只允许中文、逗号和句号。
            不要复述用户问题。
            如果你无法理解用户意图，请直接回复：请问有什么可以帮您的吗。
            """;

    public AiServiceImpl(ChatModel chatModel, AiChatHistoryMapper aiChatHistoryMapper) {
        this.chatModel = chatModel;
        this.aiChatHistoryMapper = aiChatHistoryMapper;
    }

    // 普通对话
    @Override
    public String chat(String userMessage) {
        SystemMessage systemMessage = new SystemMessage(SYSTEM_PROMPT);
        UserMessage userMsg = new UserMessage(userMessage);
        Prompt prompt = new Prompt(List.of(systemMessage, userMsg));

        String reply = chatModel.call(prompt)
                .getResult()
                .getOutput()
                .getText();

        reply = cleanReply(reply);
        saveHistory(userMessage, reply);
        return reply;
    }

    // 流式对话（给Controller用）
    @Override
    public Flux<String> streamChat(String userMessage) {
        SystemMessage systemMessage = new SystemMessage(SYSTEM_PROMPT);
        UserMessage userMsg = new UserMessage(userMessage);
        Prompt prompt = new Prompt(List.of(systemMessage, userMsg));

        StringBuilder fullReply = new StringBuilder();

        return chatModel.stream(prompt)
                .map(res -> {
                    if (res == null || res.getResult() == null) return "";
                    var output = res.getResult().getOutput();
                    if (output == null) return "";
                    String text = output.getText();
                    fullReply.append(text);

                    // 关键：这里必须彻底清理，不留任何引号！
                    return cleanReply(text);
                })
                .doOnComplete(() -> {
                    String finalReply = cleanReply(fullReply.toString());
                    saveHistory(userMessage, finalReply);
                });
    }

    // 统一清洗：彻底清理所有符号，只留中文、逗号、句号
    private String cleanReply(String text) {
        if (text == null || text.isBlank()) return "请问有什么可以帮您的吗。";

        // 1. 彻底删除所有引号（最关键！）
        text = text.replaceAll("\"", "");
        text = text.replaceAll("“", "");
        text = text.replaceAll("”", "");

        // 2. 只保留中文、逗号、句号
        text = text.replaceAll("[^\\u4e00-\\u9fa5，。]", "");

        // 3. 去掉多余前缀
        text = text.replaceFirst("^(你好|您好|我是.*?客服)", "");

        // 4. 去掉首尾标点
        text = text.replaceAll("^[，。]+", "").replaceAll("[，。]+$", "");

        // 5. 合并重复标点
        text = text.replaceAll("。+", "。").replaceAll("，+", "，");

        text = text.trim();
        if (text.isEmpty()) return "请问有什么可以帮您的吗。";
        if (!text.endsWith("。")) text += "。";
        return text;
    }

    // 统一保存聊天记录
    private void saveHistory(String message, String reply) {
        AiChatHistory history = new AiChatHistory();
        history.setUserId(null);
        history.setMessage(message);
        history.setReply(reply);
        history.setCreateTime(LocalDateTime.now());
        aiChatHistoryMapper.insert(history);
    }
}