package com.demo.backend.requestDTO;

import com.demo.backend.databaseJPA.Enum.DeviceType;
import lombok.Data;

@Data
public class DeviceDTO {
    private String username;
    private String manufacture;
    private Boolean available;
    private DeviceType device_type;
}
