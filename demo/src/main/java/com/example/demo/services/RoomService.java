package com.example.demo.services;

import com.example.demo.entities.Room;
import com.example.demo.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Room findBYRoomId(String roomId){
        return roomRepository.findByRoomId(roomId);

    }

    public Room save(Room room){
        return roomRepository.save(room);
    }



}
