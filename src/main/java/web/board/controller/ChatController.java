package web.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import web.board.model.Message;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RedisTemplate<String, Object> redisTemplate;


    @Operation(summary = "레디스에 메세지를 보내기", description = "레디스에 메세지를 저장합니다")
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody Message message){
        kafkaTemplate.send("chat", message.getContent());
        redisTemplate.opsForList().rightPush("chat_log", message.getContent());
        return ResponseEntity.ok("Message sent");
    }

    @Operation(summary = "레디스 메세지 로그", description = "레디스 메세지 로그를 확인합니다.")
    @GetMapping("/history")
    public ResponseEntity<List<Object>> getChatHistory(){
        List<Object> chatLog = redisTemplate.opsForList().range("chat_log", 0, -1);
        return ResponseEntity.ok(chatLog);
    }
}
