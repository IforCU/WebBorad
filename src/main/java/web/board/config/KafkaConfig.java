package web.board.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic boardTopic(){
        return new NewTopic("board-topic", 1, (short) 1);
    }
}
