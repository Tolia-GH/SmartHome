package com.demo.backend.response;


import lombok.Data;
import java.util.List;

@Data
public class HouseResponse {
    private Long id;
    private String houseType;
//    private String address;
    private String country;
    private String city;
    private String street;
    private List<RoomResponse> rooms;
}

