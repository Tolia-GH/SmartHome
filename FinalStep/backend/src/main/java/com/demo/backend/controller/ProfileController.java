package com.demo.backend.controller;

import com.demo.backend.databaseJPA.account.UserJPA;
import com.demo.backend.response.ProfileResponse;
import com.demo.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@Controller
public class ProfileController {
    @Autowired
    AccountService accountService;

    public void setProfile(ProfileResponse profileResponse, UserJPA userJPA) {

        String firstName = userJPA.getUsername().split(" ")[0];
        String lastName = userJPA.getUsername().split(" ")[1];

        profileResponse.setFirstName(firstName);
        profileResponse.setLastName(lastName);
        profileResponse.setEmail(userJPA.getContactJPA().getEmail());
        profileResponse.setPhone(userJPA.getContactJPA().getPhone_num());
        profileResponse.setPassword(userJPA.getPassword());
        profileResponse.setAge(userJPA.getAge());
        profileResponse.setGender(userJPA.getGender().toString());

    }

    @GetMapping("/profile")
    @ResponseBody
    public ProfileResponse getProfile(HttpServletRequest request) {
        ProfileResponse profileResponse = new ProfileResponse();

        if (request.getParameter("username") == null ||
                request.getParameter("username").isEmpty()) {
            profileResponse.setSuccess(false);
            profileResponse.setMessage("Username can't be empty!");
            return profileResponse;
        }

        String username = request.getParameter("username");

        System.out.println("username=" + username);

        String regexPhone = "\\+[1-9]+[0-9]*";
        String regexEmail = ".*@.+\\.com";

        UserJPA userJPA;

        if (Pattern.matches(regexPhone, username)) {
            userJPA = accountService.findAccountByPhone(username);
            if (userJPA != null) {
                profileResponse.setSuccess(true);
                profileResponse.setMessage("Profile found by phone number");

                setProfile(profileResponse, userJPA);

            } else {
                profileResponse.setSuccess(false);
                profileResponse.setMessage("Profile not found!");

                return profileResponse;
            }
        } else if (Pattern.matches(regexEmail, username)) {
            userJPA = accountService.findAccountByEmail(username);
            if (userJPA != null) {
                profileResponse.setSuccess(true);
                profileResponse.setMessage("Profile found by email address");

                setProfile(profileResponse, userJPA);


            } else {
                profileResponse.setSuccess(false);
                profileResponse.setMessage("Profile not found!");

                return profileResponse;
            }

        }

        return profileResponse;
    }
}
