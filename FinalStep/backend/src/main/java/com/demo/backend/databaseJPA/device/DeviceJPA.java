package com.demo.backend.databaseJPA.device;


import com.demo.backend.databaseJPA.Enum.DeviceType;
import com.demo.backend.databaseJPA.Enum.PostgreSQLEnumType;
import com.demo.backend.databaseJPA.room.RoomJPA;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Data
@Entity
@Table(name = "device", schema = "smart_home")
public class DeviceJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "room_id")
    private Integer room_id;

    @Column(nullable = false, name = "manufacture")
    private String manufacture;

    @Column(nullable = false, name = "available")
    private Boolean available;

    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private DeviceType deviceType;
}

