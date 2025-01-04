package web.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import web.board.model.Message;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public ChatController(KafkaTemplate<String, String> kafkaTemplate, RedisTemplate<String, Object> redisTemplate){
        this.kafkaTemplate = kafkaTemplate;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody Message message){
        kafkaTemplate.send("chat", message.getContent());
        redisTemplate.opsForList().rightPush("chat_log", message.getContent());
        return ResponseEntity.ok("Message sent");
    }

    @GetMapping("/history")
    public ResponseEntity<List<Object>> getChatHistory(){
        List<Object> chatLog = redisTemplate.opsForList().range("chat_log", 0, -1);
        return ResponseEntity.ok(chatLog);
    }
}
