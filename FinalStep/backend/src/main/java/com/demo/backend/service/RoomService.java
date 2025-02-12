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
}
