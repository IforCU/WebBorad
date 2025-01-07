package web.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.board.service.KafkaConsumerService;
import web.board.service.KafkaProducerService;

import java.util.Map;

@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;
    private final KafkaConsumerService kafkaConsumerService;

    @Operation(summary = "카프카 메세지 보내기", description = "메세지를 카프카에 보냅니다.")
    @PostMapping("/send")
    public String sendMessage(@RequestParam String topic, @RequestParam String message, @RequestParam int count) {
        long startTime = System.currentTimeMillis();

        // 메시지 전송
        kafkaProducerService.sendMessage(topic, message, count);

        // 모든 메시지가 소비될 때까지 대기
        try {
            kafkaProducerService.waitForConsumption();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Error waiting for message consumption";
        }

        long endTime = System.currentTimeMillis();
        long processingTime = endTime - startTime;

        // 각 파티션의 소비 메시지 수
        Map<Integer, Integer> partitionMessageCount = kafkaConsumerService.getPartitionMessageCount();

        // 응답에는 소비된 파티션 정보와 처리 시간 포함
        StringBuilder response = new StringBuilder();
        response.append(String.format("Messages sent and consumed in %d ms\n", processingTime));
        partitionMessageCount.forEach((partition, countMessages) ->
                response.append(String.format("Partition %d: %d messages consumed.\n", partition, countMessages))
        );

        kafkaConsumerService.resetPartitionMessageCount();
        return response.toString();
    }
}
