package web.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaCompletionService kafkaCompletionService;

    public void sendMessageToKafka(String topic, String message) {
        // 메시지 카프카로 전송
        kafkaTemplate.send(topic, message);
    }

    // 메시지를 카프카로 전송하고, 소비 완료까지 기다림
    public void sendMessageToKafka(String topic, String message, int count) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        // 메시지 카프카로 전송
        for (int i = 0; i < count; i++) {
            int index = i;
            executor.submit(() -> kafkaTemplate.send(topic, message + " #" + index));
        }
        executor.shutdown();
    }

    // 메시지가 모두 소비될 때까지 대기
    public void waitForConsumption() throws InterruptedException {
        kafkaCompletionService.waitForCompletion(); // 소비 완료 대기
    }
}
