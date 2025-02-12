package com.demo.backend.databaseJPA;

import com.demo.backend.databaseJPA.account.UserJPA;
import com.demo.backend.databaseJPA.house.HouseJPA;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.catalina.User;

import javax.persistence.*;

@Data
@Entity
@Table(name = "list_user_house", schema = "smart_home")
public class ListUserHouseJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private UserJPA userJPA;

    @ManyToOne
    @JoinColumn(name = "house_id", referencedColumnName = "id")
    @JsonIgnore
    private HouseJPA house;
}
