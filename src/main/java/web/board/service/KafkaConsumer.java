package web.board.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "topic1", groupId = "topic1")
    public void listen(String message){
        System.out.println("Received Message : " + message);
    }
}
