package com.demo.backend.controller;

import com.demo.backend.databaseJPA.Enum.*;
import com.demo.backend.databaseJPA.ListUserHouseJPA;
import com.demo.backend.databaseJPA.ListUserHouseRepo;
import com.demo.backend.databaseJPA.account.UserJPA;
import com.demo.backend.databaseJPA.address.AddressJPA;
import com.demo.backend.databaseJPA.device.DeviceJPA;
import com.demo.backend.databaseJPA.house.HouseJPA;
import com.demo.backend.databaseJPA.room.RoomJPA;
import com.demo.backend.requestDTO.HouseDTO;
import com.demo.backend.response.*;
import com.demo.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
public class HouseController {

//    @GetMapping("/houses")
//    public List<HouseResponse> getExampleHouses() {
//        // Sample data - typically, this would be fetched from the database
//        HouseResponse house1 = new HouseResponse();
//        house1.setId(1L);
//        house1.setHouseType("APARTMENTS");
//        house1.setAddress("Shanghai, 5th Avenue");
//
//        RoomResponse room1 = new RoomResponse();
//        room1.setId(1L);
//        room1.setRoomType("KITCHEN");
//
//        DeviceResponse device1 = new DeviceResponse();
//        device1.setId(1L);
//        device1.setDeviceType("AIR_CONDITION");
//        device1.setManufacture("Brand A");
//        device1.setAvailable(true);
//
//        DeviceResponse device2 = new DeviceResponse();
//        device2.setId(2L);
//        device2.setDeviceType("LIGHT");
//        device2.setManufacture("Brand B");
//        device2.setAvailable(false);
//
//        room1.setDevices(Arrays.asList(device1, device2));
//
//        RoomResponse room2 = new RoomResponse();
//        room2.setId(2L);
//        room2.setRoomType("LIVING");
//
//        DeviceResponse device3 = new DeviceResponse();
//        device3.setId(3L);
//        device3.setDeviceType("FAN");
//        device3.setManufacture("Brand C");
//        device3.setAvailable(true);
//
//        room2.setDevices(Arrays.asList(device3));
//
//        house1.setRooms(Arrays.asList(room1, room2));
//
//        HouseResponse house2 = new HouseResponse();
//        house2.setId(2L);
//        house2.setHouseType("VILLAS");
//        house2.setAddress("Beijing, 7th Avenue");
//
//        RoomResponse room3 = new RoomResponse();
//        room3.setId(3L);
//        room3.setRoomType("BEDROOM");
//
//        DeviceResponse device4 = new DeviceResponse();
//        device4.setId(4L);
//        device4.setDeviceType("CAMERA");
//        device4.setManufacture("Brand D");
//        device4.setAvailable(true);
//
//        room3.setDevices(Arrays.asList(device4));
//
//        house2.setRooms(Arrays.asList(room3));
//
//        HousesResponse houses = new HousesResponse();
//        houses.setSuccess(true);
//        houses.setMessage("Success");
//        houses.getHouseResponse().add(house1);
//        houses.getHouseResponse().add(house2);
//
//        return houses.getHouseResponse();
//    }

    @Autowired
    private AccountService accountService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private HouseService houseService;
    @Autowired
    private ListUserHouseRepo listUserHouseRepo;
    @Autowired
    private ListUserHouseService listUserHouseService;

    @GetMapping("/houses")
    public List<HouseResponse> getHouses(HttpServletRequest request) {

        HousesResponse housesResponse = new HousesResponse();

        if (request.getParameter("username") == null ||
                request.getParameter("username").isEmpty()) {
            housesResponse.setSuccess(false);
            housesResponse.setMessage("Username can't be empty!");
            return housesResponse.getHouseResponse();
        }

        String username = request.getParameter("username");

        System.out.println("username=" + username);

        String regexPhone = "\\+[1-9]+[0-9]*";
        String regexEmail = ".*@.+\\.com";

        UserJPA userJPA;

        if (Pattern.matches(regexPhone, username)) {
            userJPA = accountService.findAccountByPhone(username);
            if (userJPA != null) {
                housesResponse.setSuccess(true);
                housesResponse.setMessage("User found by phone number");

                List<ListUserHouseJPA> userHouseJPAList = userJPA.getListUserHouseJPAS();

                List<HouseJPA> houseJPAList = new ArrayList<>();
                for (ListUserHouseJPA listUserHouseJPA : userHouseJPAList) {

                    HouseJPA houseJPA = listUserHouseJPA.getHouse();
                    houseJPAList.add(houseJPA);
                }

                for (HouseJPA house : houseJPAList) {
                    HouseResponse houseResponse = new HouseResponse();

                    houseResponse.setId(house.getId().longValue());
                    houseResponse.setHouseType(house.getHouseType().toString());
                    houseResponse.setCountry(house.getAddressJPA().getCountry().toString());
                    houseResponse.setCity(house.getAddressJPA().getCity().toString());
                    houseResponse.setStreet(house.getAddressJPA().getStreet());

                    List<RoomResponse> roomResponseList = new ArrayList<>();
                    for (RoomJPA room: house.getRoomJPAList()) {
                        RoomResponse roomResponse = new RoomResponse();

                        roomResponse.setId(room.getId().longValue());
                        roomResponse.setRoomType(room.getRoomType().toString());

                        List<DeviceResponse> deviceResponseList = new ArrayList<>();

                        for (DeviceJPA device: room.getDeviceJPAList()) {
                            DeviceResponse deviceResponse = new DeviceResponse();
                            deviceResponse.setId(device.getId().longValue());
                            deviceResponse.setDeviceType(device.getDeviceType().toString());
                            deviceResponse.setManufacture(device.getManufacture());
                            deviceResponse.setAvailable(device.getAvailable());

                            deviceResponseList.add(deviceResponse);
                        }
                        roomResponse.setDevices(deviceResponseList);

                    }
                    houseResponse.setRooms(roomResponseList);

                    housesResponse.getHouseResponse().add(houseResponse);
                }

                return housesResponse.getHouseResponse();

            } else {
                housesResponse.setSuccess(false);
                housesResponse.setMessage("User not found!");

                return housesResponse.getHouseResponse();
            }
        } else if (Pattern.matches(regexEmail, username)) {
            userJPA = accountService.findAccountByEmail(username);
            if (userJPA != null) {
                housesResponse.setSuccess(true);
                housesResponse.setMessage("User found by phone number");

                List<ListUserHouseJPA> userHouseJPAList = userJPA.getListUserHouseJPAS();

                List<HouseJPA> houseJPAList = new ArrayList<>();
                for (ListUserHouseJPA listUserHouseJPA : userHouseJPAList) {

                    HouseJPA houseJPA = listUserHouseJPA.getHouse();
                    houseJPAList.add(houseJPA);
                }

                for (HouseJPA house : houseJPAList ) {
                    HouseResponse houseResponse = new HouseResponse();

                    houseResponse.setId(house.getId().longValue());
                    houseResponse.setHouseType(house.getHouseType().toString());
                    houseResponse.setCountry(house.getAddressJPA().getCountry().toString());
                    houseResponse.setCity(house.getAddressJPA().getCity().toString());
                    houseResponse.setStreet(house.getAddressJPA().getStreet());
                    List<RoomResponse> roomResponseList = new ArrayList<>();
                    for (RoomJPA room: house.getRoomJPAList()) {
                        RoomResponse roomResponse = new RoomResponse();

                        roomResponse.setId(room.getId().longValue());
                        roomResponse.setRoomType(room.getRoomType().toString());

                        List<DeviceResponse> deviceResponseList = new ArrayList<>();

                        for (DeviceJPA device: room.getDeviceJPAList()) {
                            DeviceResponse deviceResponse = new DeviceResponse();
                            deviceResponse.setId(device.getId().longValue());
                            deviceResponse.setDeviceType(device.getDeviceType().toString());
                            deviceResponse.setManufacture(device.getManufacture());
                            deviceResponse.setAvailable(device.getAvailable());

                            deviceResponseList.add(deviceResponse);
                        }
                        roomResponse.setDevices(deviceResponseList);

                        roomResponseList.add(roomResponse);

                    }
                    houseResponse.setRooms(roomResponseList);

                    housesResponse.getHouseResponse().add(houseResponse);
                }

                return housesResponse.getHouseResponse();


            } else {
                housesResponse.setSuccess(false);
                housesResponse.setMessage("User not found!");

                return housesResponse.getHouseResponse();
            }

        }

        return housesResponse.getHouseResponse();
    }

    @PostMapping("/houses")
    public HousesResponse addHouse(@RequestBody HouseDTO houseDTO) {
        HousesResponse housesResponse = new HousesResponse();

        String username = houseDTO.getUsername();

        String regexPhone = "\\+[1-9]+[0-9]*";
        String regexEmail = ".*@.+\\.com";

        if (username == null || username.isEmpty()) {
            housesResponse.setSuccess(false);
            housesResponse.setMessage("Username can't be empty!");
            return housesResponse;
        } else {

            UserJPA userJPA;

            if (Pattern.matches(regexPhone, username)) {
                userJPA = accountService.findAccountByPhone(username);
                if (userJPA != null) {
                    housesResponse.setSuccess(true);
                    housesResponse.setMessage("User found by phone number");

                    HouseJPA newHouse = new HouseJPA();

                    newHouse.setHouseType(HouseType.valueOf(houseDTO.getHouseType()));

                    AddressJPA newAddress = new AddressJPA();

                    newAddress.setCountry(Country.valueOf(houseDTO.getCountry()));
                    newAddress.setCity(City.valueOf(houseDTO.getCity()));
                    newAddress.setStreet(houseDTO.getStreet());

                    newHouse.setAddressJPA(newAddress);

                    addressService.addAddress(newAddress);
                    houseService.addHouse(newHouse);

                    ListUserHouseJPA listUserHouseJPA = new ListUserHouseJPA();

                    listUserHouseJPA.setHouse(newHouse);
                    listUserHouseJPA.setUser_id(userJPA.getId());

                    listUserHouseService.addUserHouse(listUserHouseJPA);

                } else {
                    housesResponse.setSuccess(false);
                    housesResponse.setMessage("User not found!");
                }

            } else if (Pattern.matches(regexEmail, username)) {
                userJPA = accountService.findAccountByEmail(username);

                if (userJPA != null) {
                    housesResponse.setSuccess(true);
                    housesResponse.setMessage("User found by phone number");

                    AddressJPA newAddress = new AddressJPA();
                    newAddress.setCountry(Country.valueOf(houseDTO.getCountry()));
                    newAddress.setCity(City.CHENGDU);
                    newAddress.setStreet(houseDTO.getStreet());
                    addressService.addAddress(newAddress);

                    HouseJPA newHouse = new HouseJPA();
                    newHouse.setHouseType(HouseType.valueOf(houseDTO.getHouseType()));
                    newHouse.setAddressJPA(newAddress);

                    houseService.addHouse(newHouse);

                    ListUserHouseJPA listUserHouseJPA = new ListUserHouseJPA();

                    listUserHouseJPA.setHouse(newHouse);
                    listUserHouseJPA.setUser_id(userJPA.getId());

                    listUserHouseService.addUserHouse(listUserHouseJPA);

                } else {
                    housesResponse.setSuccess(false);
                    housesResponse.setMessage("User not found!");
                }
            }
        }

        return new HousesResponse();
    }
}

