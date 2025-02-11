package com.demo.backend.databaseJPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListUserHouseRepo extends JpaRepository<ListUserHouseJPA, Integer> {
}
