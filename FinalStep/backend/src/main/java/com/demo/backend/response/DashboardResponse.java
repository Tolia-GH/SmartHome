package com.demo.backend.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DashboardResponse {
    private boolean success;
    private String message;
    private List<HouseResponse> houseResponse = new ArrayList<>();
}
