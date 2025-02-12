package com.demo.backend.requestDTO;

import com.demo.backend.databaseJPA.Enum.RoomType;
import lombok.Data;

@Data
public class RoomDTO {
    private String username;
    private Double area_size;
    private Double height;
    private Integer house_id;
    private Boolean is_filled;
    private RoomType room_type;
}
