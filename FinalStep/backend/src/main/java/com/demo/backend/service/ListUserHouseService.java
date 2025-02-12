package com.demo.backend.service;

import com.demo.backend.databaseJPA.ListUserHouseJPA;
import com.demo.backend.databaseJPA.ListUserHouseRepo;
import com.demo.backend.databaseJPA.house.HouseJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListUserHouseService {
    @Autowired
    ListUserHouseRepo listUserHouseRepo;

    public void addUserHouse(ListUserHouseJPA newUserHouse) {
        listUserHouseRepo.save(newUserHouse);
    }

    public void delete(ListUserHouseJPA listUserHouseJPA) {
        listUserHouseRepo.delete(listUserHouseJPA);
    }
}
