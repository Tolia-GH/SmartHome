package com.demo.backend.response;

import lombok.Data;

@Data
public class DeviceResponse {
    private Long id;
    private String deviceType;
    private String manufacture;
    private boolean available;
}

