package web.board.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Message {
    private String sender;
    private String content;
    private LocalDateTime timestamp;
}
