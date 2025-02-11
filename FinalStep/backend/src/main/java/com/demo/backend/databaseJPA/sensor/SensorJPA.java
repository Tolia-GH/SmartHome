package com.demo.backend.databaseJPA.sensor;

import com.demo.backend.databaseJPA.Enum.SensorType;
import com.demo.backend.databaseJPA.room.RoomJPA;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "sensor", schema = "smart_home")
public class SensorJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
    private RoomJPA room;

    @Column(nullable = false, name = "manufacture")
    private String manufacture;

    @Column(nullable = false, name = "available")
    private Boolean available;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "sensor_type")
    private SensorType sensorType;
}

