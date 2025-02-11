package com.demo.backend.response;

import lombok.Data;

import java.util.List;

@Data
public class RoomResponse {
    private Long id;
    private String roomType;
    private List<DeviceResponse> devices;
}
