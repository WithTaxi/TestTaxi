package Taxi.TaxiService.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoom {

    private String roomId;
    private String roomName;
    private long userCount; // 채팅방 인원수
    private long maxUserCnt = 4; // 최대 인원

    private HashMap<String, String> userlist = new HashMap<String, String>();

    public ChatRoom create(String name) {
        ChatRoom room = new ChatRoom();
        room.roomId = UUID.randomUUID().toString();
        room.roomName = name;

        return room;
    }

}
