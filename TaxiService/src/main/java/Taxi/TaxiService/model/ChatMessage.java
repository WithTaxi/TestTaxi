package Taxi.TaxiService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    public enum MessageType {
    ENTER, TALK, LEAVE
}

    private MessageType type;

    private String roomId;    //채팅방 ID

    private String sender;    //보내는 사람

    private String message;    //내용

}
