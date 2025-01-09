package web.board.service;

import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RedisSubscriber {
    @EventListener
    public void handleMessage(Message message) {
        String channel = Arrays.toString(message.getChannel());
        String body = new String(message.getBody());

    }
}
