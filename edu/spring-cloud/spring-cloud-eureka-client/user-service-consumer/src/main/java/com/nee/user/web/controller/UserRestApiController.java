package com.nee.user.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nee.user.domain.User;
import com.nee.user.service.UserService;

@RestController
public class UserRestApiController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/save")
    public User saveUser(@RequestParam String name ) {
        User user = new User();
        user.setName(name);
        if (userService.save(user)) {
            return user;
        } else {
            return null;
        }
    }

}
