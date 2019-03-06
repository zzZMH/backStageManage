package com.demo.dao;

import com.demo.entity.Role;
import com.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestDao {
    List<User> getUser(String userName);
    List<Role> getUserRole(Integer userId);
}
