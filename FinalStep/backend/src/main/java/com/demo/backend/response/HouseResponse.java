package com.demo.backend.response;


import lombok.Data;
import java.util.List;

@Data
public class HouseResponse {
    private Long id;
    private String houseType;
    private String address;
    private List<RoomResponse> rooms;
}

