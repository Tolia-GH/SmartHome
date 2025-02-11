package com.demo.backend.databaseJPA.house;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepo {

    @Query(value = "select A from HouseJPA A where A.id = ?1")
    HouseJPA getHouseById(String id);
}
