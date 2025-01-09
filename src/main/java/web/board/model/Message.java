package web.board.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Message {
    private String sender;    // 메시지 발신자
    private String content;   // 메시지 내용
}