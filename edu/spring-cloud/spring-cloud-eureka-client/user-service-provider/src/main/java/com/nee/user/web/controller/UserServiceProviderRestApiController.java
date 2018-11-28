package com.nee.user.web.controller;

import com.nee.user.domain.User;
import com.nee.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserServiceProviderRestApiController {


    @Autowired
    private UserService userService;

    @PostMapping("/user/save")
    public User saveUser(@RequestBody User user) {
        if (userService.save(user)) {
            return user;
        } else {
            return null;
        }
    }
}
