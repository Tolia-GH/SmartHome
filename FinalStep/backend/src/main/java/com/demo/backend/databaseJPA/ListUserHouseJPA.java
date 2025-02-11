package com.demo.backend.databaseJPA;

import com.demo.backend.databaseJPA.account.UserJPA;
import com.demo.backend.databaseJPA.house.HouseJPA;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "list_user_house", schema = "smart_home")
public class ListUserHouseJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,name="user_id")
    private Integer user_id;

    @ManyToOne
    @JoinColumn(name = "house_id", referencedColumnName = "id")
    @JsonIgnore
    private HouseJPA house;
}
