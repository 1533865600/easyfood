package fun.cyhgraph.controller;

import fun.cyhgraph.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/admin/ai")
@RequiredArgsConstructor
public class AiChatController {

    private final AiService aiService;

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> stream(@RequestParam String message) {
        return aiService.streamChat(message)
                .map(text -> ServerSentEvent.<String>builder().data(text).build());
    }
}