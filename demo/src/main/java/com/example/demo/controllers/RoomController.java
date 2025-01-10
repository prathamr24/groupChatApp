package com.example.demo.controllers;

import com.example.demo.entities.Message;
import com.example.demo.entities.Room;
import com.example.demo.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rooms")
@CrossOrigin("http://localhost:5173")
public class RoomController {
    @Autowired
    private RoomService roomService;

    //creating the room

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody String roomId){

        if(roomService.findBYRoomId(roomId)!=null){
            return ResponseEntity.badRequest().body("room already exists!");
        }

        Room room=new Room();
        room.setRoomId(roomId);
        Room savedRoom=roomService.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);

    }


    //get the room
    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId){
        Room room=roomService.findBYRoomId(roomId);

        if(room==null){
            return ResponseEntity.badRequest().body("Room Not Found!");
        }
        return ResponseEntity.ok(room);
    }


    //get messages
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String roomId,
                                                     @RequestParam(value="page",defaultValue="0",required = false) int page,
                                                     @RequestParam (value="size",defaultValue = "20",required = false)int size){
        Room room=roomService.findBYRoomId(roomId);
        if(room==null){
            return ResponseEntity.badRequest().build();
        }

        List<Message> messages=room.getMessages();
        int start=Math.max(0,messages.size()-(page+1)*size);
        int end=Math.min(messages.size(),start+size);

        List<Message> paginatedMessages=messages.subList(start,end);

        return ResponseEntity.ok(paginatedMessages);
    }



}
