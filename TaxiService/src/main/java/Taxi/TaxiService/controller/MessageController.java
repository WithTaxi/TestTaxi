package Taxi.TaxiService.controller;

import Taxi.TaxiService.model.ChatMessage;
import Taxi.TaxiService.service.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;


    @Autowired
    ChatRepository service;

    @MessageMapping("/chat/message")
    public void enterUser(ChatMessage chat, SimpMessageHeaderAccessor headerAccessor){


        if (ChatMessage.MessageType.ENTER.equals(chat.getType())) {
            service.plusUserCnt(chat.getRoomId());
            String userUUID = service.addUser(chat.getRoomId(), chat.getSender());

            headerAccessor.getSessionAttributes().put("userUUID",userUUID);
            headerAccessor.getSessionAttributes().put("roomId",chat.getRoomId());
            chat.setMessage(chat.getSender() + " 님 입장");
        }
        sendingOperations.convertAndSend("/topic/chat/room/"+chat.getRoomId(),chat);
    }

    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event){

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userUUID = (String) headerAccessor.getSessionAttributes().get("userUUID");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");

        service.minusUserCnt(roomId);

        service.delUser(roomId, userUUID);


    }

}
