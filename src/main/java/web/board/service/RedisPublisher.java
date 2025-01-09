package web.board.service;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publisherMessage(String channel, String message){
        redisTemplate.convertAndSend(channel, message);
    }
}
