package com.nee.spring.boot.jdbc.controller;

import com.nee.spring.boot.jdbc.domain.User;
import com.nee.spring.boot.jdbc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/web/mvc/com.nee.user/save")
    public boolean save(@RequestBody User user) {

        return userRepository.save(user);
    }
}
