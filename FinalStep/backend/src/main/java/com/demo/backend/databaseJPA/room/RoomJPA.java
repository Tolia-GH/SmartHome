package com.demo.backend.databaseJPA.room;

import com.demo.backend.databaseJPA.Enum.RoomType;
import com.demo.backend.databaseJPA.device.DeviceJPA;
import com.demo.backend.databaseJPA.house.HouseJPA;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "room", schema = "smart_home")
public class RoomJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "house_id")
    private Integer house_id;

    @Column(nullable = false, name = "area_size")
    private Double areaSize;

    @Column(nullable = false, name = "height")
    private Double height;

    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private RoomType roomType;

    @Column(nullable = false, name = "is_filled")
    private Boolean isFilled;

    @OneToMany(mappedBy = "room_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeviceJPA> deviceJPAList;
}

