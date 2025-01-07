package web.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaCompletionService kafkaCompletionService;

    // 메시지를 카프카로 전송하고, 소비 완료까지 기다림
    public void sendMessage(String topic, String message, int count) {
        // 메시지 카프카로 전송
        for (int i = 0; i < count; i++) {
            kafkaTemplate.send(topic, message + " #" + i);
        }
    }

    // 메시지가 모두 소비될 때까지 대기
    public void waitForConsumption() throws InterruptedException {
        kafkaCompletionService.waitForCompletion(); // 소비 완료 대기
    }
}
