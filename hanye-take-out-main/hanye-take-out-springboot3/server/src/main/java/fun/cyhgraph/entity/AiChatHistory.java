package fun.cyhgraph.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AiChatHistory {
    private Integer id;
    private Integer userId;
    private String message;
    private String reply;
    private LocalDateTime createTime;
}