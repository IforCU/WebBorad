package web.board.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import web.board.repository.ChatMessageRepository;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final KafkaCompletionService kafkaCompletionService;

    // 각 파티션별 소비된 메시지 수 리턴
    // 파티션별 메시지 소비 개수를 기록할 맵
    @Getter
    private final Map<Integer, Integer> partitionMessageCount = new HashMap<>();
    private final RedisTemplate redisTemplate;
    private final ChatMessageRepository chatMessageRepository;

    @KafkaListener(topics = "chat-topic", groupId = "chat-group")
    public void listenAndSave(ConsumerRecord<String, String> record) {
        int partition = record.partition();

        // 파티션별로 소비된 메시지 수 증가
        partitionMessageCount.put(partition, partitionMessageCount.getOrDefault(partition, 0) + 1);

        // 메시지가 모두 소비되었으면 카프카 완료 서비스로 알림
        kafkaCompletionService.messageConsumed();

    }

    @KafkaListener(topics = "chat-room-1", groupId = "chat-group")
    public void comsumeMessage(String message){
        redisTemplate.opsForList().rightPush("chat-room-1",message);
    }

    public void resetPartitionMessageCount() {
        if (!partitionMessageCount.isEmpty()) {
            System.out.println("messageConsumed");
            partitionMessageCount.clear();
        }
    }
}
