package fun.cyhgraph.service;

import reactor.core.publisher.Flux;

public interface AiService {
    String chat(String userMessage);

    // 这里必须写 Flux<String>
    Flux<String> streamChat(String message);
}