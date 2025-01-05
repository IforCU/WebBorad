package web.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.board.service.KafkaProducer;

@RestController
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaProducer kafkaProducer;

    @Operation(summary = "카프카 메세지 보내기", description = "메세지를 카프카에 보냅니다.")
    @PostMapping("/send")
    public String sendMessage(@RequestParam String message){
        kafkaProducer.sendMessage("topic1", message);
        return "Message sent" + message;
    }
}
