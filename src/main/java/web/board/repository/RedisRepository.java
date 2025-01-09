package web.board.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;


    public void saveMessage(String key, String message) {
        redisTemplate.opsForList().rightPush(key, message);
    }

    // Pub/Sub을 통해 메시지 전송
    public void publishMessage(String channel, String message) {
        redisTemplate.convertAndSend(channel, message);
    }

    // 캐싱된 메시지 조회 (최신 n개 메시지)
    public List<String> getRecentMessages(String key, int count) {
        long start = Math.max(0, redisTemplate.opsForList().size(key) - count);
        return redisTemplate.opsForList().range(key, start, -1);
    }
}
