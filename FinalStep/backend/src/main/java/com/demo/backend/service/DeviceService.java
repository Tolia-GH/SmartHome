package com.demo.backend.service;

import com.demo.backend.databaseJPA.device.DeviceJPA;
import com.demo.backend.databaseJPA.device.DeviceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {
    @Autowired
    DeviceRepo deviceRepo;
    public void add(DeviceJPA deviceJPA) {
        deviceRepo.save(deviceJPA);
    }
}
