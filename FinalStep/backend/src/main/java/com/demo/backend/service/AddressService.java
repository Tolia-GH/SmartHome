package com.demo.backend.service;

import com.demo.backend.databaseJPA.Enum.City;
import com.demo.backend.databaseJPA.Enum.Country;
import com.demo.backend.databaseJPA.address.AddressJPA;
import com.demo.backend.databaseJPA.address.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    AddressRepo addressRepo;

    public void addAddress(AddressJPA newAddress) {

        addressRepo.save(newAddress);

    }
}
