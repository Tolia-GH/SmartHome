package com.demo.backend.controller;

import com.demo.backend.databaseJPA.account.UserJPA;
import com.demo.backend.databaseJPA.device.DeviceJPA;
import com.demo.backend.databaseJPA.room.RoomJPA;
import com.demo.backend.requestDTO.DeviceDTO;
import com.demo.backend.requestDTO.RoomDTO;
import com.demo.backend.response.DashboardResponse;
import com.demo.backend.service.AccountService;
import com.demo.backend.service.DeviceService;
import com.demo.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@RestController
public class DeviceController {
    @Autowired
    AccountService accountService;
    @Autowired
    RoomService roomService;
    @Autowired
    private DeviceService deviceService;

    @PostMapping("rooms/{room_id}/devices")
    public DashboardResponse addDevice(@RequestBody DeviceDTO deviceDTO, @PathVariable int room_id) {
        DashboardResponse dashboardResponse = new DashboardResponse();

        String username = deviceDTO.getUsername();

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

                    DeviceJPA deviceJPA = new DeviceJPA();

                    deviceJPA.setRoom_id(room_id);
                    deviceJPA.setManufacture(deviceDTO.getManufacture());
                    deviceJPA.setAvailable(deviceDTO.getAvailable());
                    deviceJPA.setDeviceType(deviceDTO.getDevice_type());

                    deviceService.add(deviceJPA);

                } else {
                    dashboardResponse.setSuccess(false);
                    dashboardResponse.setMessage("User not found!");
                }

            } else if (Pattern.matches(regexEmail, username)) {
                userJPA = accountService.findAccountByEmail(username);

                if (userJPA != null) {
                    dashboardResponse.setSuccess(true);
                    dashboardResponse.setMessage("User found by phone number");

                    DeviceJPA deviceJPA = new DeviceJPA();

                    deviceJPA.setRoom_id(room_id);
                    deviceJPA.setManufacture(deviceDTO.getManufacture());
                    deviceJPA.setAvailable(deviceDTO.getAvailable());
                    deviceJPA.setDeviceType(deviceDTO.getDevice_type());

                    deviceService.add(deviceJPA);

                } else {
                    dashboardResponse.setSuccess(false);
                    dashboardResponse.setMessage("User not found!");
                }
            }
        }
        return dashboardResponse;
    }

    @DeleteMapping("/houses/{houseId}/rooms/{roomId}/devices/{device_id}")
    public DashboardResponse delete(HttpServletRequest request, @PathVariable int houseId, @PathVariable int roomId) {
        DashboardResponse dashboardResponse = new DashboardResponse();

        String username = request.getParameter("username");

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

                    RoomJPA roomJPA = roomService.findRoomById(roomId);
                    roomService.delete(roomJPA);
                } else {
                    dashboardResponse.setSuccess(false);
                    dashboardResponse.setMessage("User not found!");
                }

            } else if (Pattern.matches(regexEmail, username)) {
                userJPA = accountService.findAccountByEmail(username);

                if (userJPA != null) {
                    dashboardResponse.setSuccess(true);
                    dashboardResponse.setMessage("User found by phone number");

                    RoomJPA roomJPA = roomService.findRoomById(roomId);
                    roomService.delete(roomJPA);

                } else {
                    dashboardResponse.setSuccess(false);
                    dashboardResponse.setMessage("User not found!");
                }
            }
        }
        return new DashboardResponse();
    }
}
