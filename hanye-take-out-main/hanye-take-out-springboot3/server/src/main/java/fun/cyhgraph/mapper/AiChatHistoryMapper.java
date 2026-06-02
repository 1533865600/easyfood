package fun.cyhgraph.mapper;

import fun.cyhgraph.entity.AiChatHistory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AiChatHistoryMapper {
    @Insert("INSERT INTO ai_chat_history(user_id, message, reply, create_time) VALUES (#{userId}, #{message}, #{reply}, #{createTime})")
    void insert(AiChatHistory history);
}