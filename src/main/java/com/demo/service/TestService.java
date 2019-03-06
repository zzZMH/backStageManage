package com.demo.service;

import com.demo.entity.Role;
import com.demo.entity.User;

import java.util.List;

public interface TestService {

    List<User> getUser(String userName);

    List<Role> getUserRole(Integer userId);
}
