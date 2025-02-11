package com.demo.backend.service;

import com.demo.backend.databaseJPA.Enum.Gender;
import com.demo.backend.databaseJPA.Enum.HouseType;
import com.demo.backend.databaseJPA.account.UserJPA;
import com.demo.backend.databaseJPA.address.AddressJPA;
import com.demo.backend.databaseJPA.house.HouseJPA;
import com.demo.backend.databaseJPA.house.HouseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseService {
    private final HouseRepo houseRepo;

    public HouseService(HouseRepo houseRepo) {
        this.houseRepo = houseRepo;
    }

    public void addHouse(HouseJPA newHouse) {

        houseRepo.save(newHouse);
    }
}
