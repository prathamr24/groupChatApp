package com.example.demo.controllers;

import com.example.demo.entities.Message;
import com.example.demo.entities.Room;
import com.example.demo.payload.Messagerequest;
import com.example.demo.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Controller
@CrossOrigin("http://localhost:5173")
public class ChatController {
   @Autowired
    private RoomService roomService;

   @MessageMapping("/sendMessage/{roomId}")
   @SendTo("/topic/room/{roomId}")
   public Message sendMessage(
           @DestinationVariable String roomId,
       @RequestBody Messagerequest messageRequest
   ){
      Room room=roomService.findBYRoomId(messageRequest.getRoomId());
      Message message=new Message();
      message.setContent(messageRequest.getContent());
      message.setSender(messageRequest.getSender());
      message.setTimeStamp(LocalDateTime.now());

      if(room!=null){
          room.getMessages().add(message);
          roomService.save(room);
      }else{
          throw new RuntimeException("room not found!");
      }

      return message;


   }


}
