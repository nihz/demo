package com.nee.user.service;

import com.nee.user.domain.User;

import java.util.Collection;

public interface UserService {

    boolean save(User user);

    Collection<User> findAll();
}
