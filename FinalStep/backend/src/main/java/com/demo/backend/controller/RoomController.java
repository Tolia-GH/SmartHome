package com.demo.backend.controller;

import com.demo.backend.databaseJPA.ListUserHouseJPA;
import com.demo.backend.databaseJPA.account.UserJPA;
import com.demo.backend.databaseJPA.address.AddressJPA;
import com.demo.backend.databaseJPA.house.HouseJPA;
import com.demo.backend.databaseJPA.room.RoomJPA;
import com.demo.backend.requestDTO.RoomDTO;
import com.demo.backend.response.DashboardResponse;
import com.demo.backend.service.AccountService;
import com.demo.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
public class RoomController {
    @Autowired
    AccountService accountService;
    @Autowired
    RoomService roomService;

    @PostMapping("houses/{house_id}/rooms")
    public DashboardResponse addRoom(@RequestBody RoomDTO roomDTO, @PathVariable int house_id) {
        DashboardResponse dashboardResponse = new DashboardResponse();

        String username = roomDTO.getUsername();

        String regexPhone = "\\+[1-9]+[0-9]*";
        String regexEmail = ".*@.+\\.com";

        UserJPA userJPA;

        if (username == null || username.isEmpty()) {
            dashboardResponse.setSuccess(false);
            dashboardResponse.setMessage("Username can't be empty!");
            return dashboardResponse;
        } else {
            if (Pattern.matches(regexPhone, username)) {
                userJPA = accountService.findAccountByPhone(username);
                if (userJPA != null) {
                    dashboardResponse.setSuccess(true);
                    dashboardResponse.setMessage("User found by phone number");

                    RoomJPA roomJPA = new RoomJPA();

                    roomJPA.setRoomType(roomDTO.getRoom_type());
                    roomJPA.setAreaSize(roomDTO.getArea_size());
                    roomJPA.setHeight(roomDTO.getHeight());
                    roomJPA.setIsFilled(roomDTO.getIs_filled());
                    roomJPA.setHouse_id(house_id);

                    roomService.add(roomJPA);

                } else {
                    dashboardResponse.setSuccess(false);
                    dashboardResponse.setMessage("User not found!");
                }

            } else if (Pattern.matches(regexEmail, username)) {
                userJPA = accountService.findAccountByEmail(username);

                if (userJPA != null) {
                    dashboardResponse.setSuccess(true);
                    dashboardResponse.setMessage("User found by phone number");

                    RoomJPA roomJPA = new RoomJPA();

                    roomJPA.setRoomType(roomDTO.getRoom_type());
                    roomJPA.setAreaSize(roomDTO.getArea_size());
                    roomJPA.setHeight(roomDTO.getHeight());
                    roomJPA.setIsFilled(roomDTO.getIs_filled());
                    roomJPA.setHouse_id(house_id);

                    roomService.add(roomJPA);

                } else {
                    dashboardResponse.setSuccess(false);
                    dashboardResponse.setMessage("User not found!");
                }
            }
        }
        return dashboardResponse;
    }

//    @PostMapping("/")
//    public DashboardResponse example(@RequestBody RoomDTO roomDTO, @PathVariable int house_id) {
//        DashboardResponse dashboardResponse = new DashboardResponse();
//
//        String username = roomDTO.getUsername();
//
//        String regexPhone = "\\+[1-9]+[0-9]*";
//        String regexEmail = ".*@.+\\.com";
//
//        UserJPA userJPA;
//
//        if (username == null || username.isEmpty()) {
//            dashboardResponse.setSuccess(false);
//            dashboardResponse.setMessage("Username can't be empty!");
//            return dashboardResponse;
//        } else {
//            if (Pattern.matches(regexPhone, username)) {
//                userJPA = accountService.findAccountByPhone(username);
//                if (userJPA != null) {
//                    dashboardResponse.setSuccess(true);
//                    dashboardResponse.setMessage("User found by phone number");
//
//                } else {
//                    dashboardResponse.setSuccess(false);
//                    dashboardResponse.setMessage("User not found!");
//                }
//
//            } else if (Pattern.matches(regexEmail, username)) {
//                userJPA = accountService.findAccountByEmail(username);
//
//                if (userJPA != null) {
//                    dashboardResponse.setSuccess(true);
//                    dashboardResponse.setMessage("User found by phone number");
//
//                } else {
//                    dashboardResponse.setSuccess(false);
//                    dashboardResponse.setMessage("User not found!");
//                }
//            }
//        }
//        return new DashboardResponse();
//    }
}
