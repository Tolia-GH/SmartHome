package com.demo.backend.response;

import lombok.Data;

@Data
public class ProfileResponse {
    private boolean success;
    private String message;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private Integer age;
    private String gender;
}
