package com.demo.backend.service;

import com.demo.backend.databaseJPA.room.RoomJPA;
import com.demo.backend.databaseJPA.room.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    RoomRepo roomRepo;

    public void add(RoomJPA roomJPA) {
        roomRepo.save(roomJPA);
    }

    public RoomJPA findRoomById(Integer room_id) {
        return roomRepo.findById(room_id).orElse(null);
    }

    public void delete(RoomJPA roomJPA) {
        roomRepo.delete(roomJPA);
    }
}
