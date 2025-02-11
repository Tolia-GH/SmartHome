package com.demo.backend.databaseJPA.device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepo extends JpaRepository<DeviceJPA, Integer> {

}
