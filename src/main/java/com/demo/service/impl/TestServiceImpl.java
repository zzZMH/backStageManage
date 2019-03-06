package com.demo.service.impl;

import com.demo.dao.TestDao;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Resource
    private TestDao testDao;

    @Override
    public List<User> getUser(String userName) {
        return testDao.getUser(userName);
    }

    @Override
    public List<Role> getUserRole(Integer userId) {
        return testDao.getUserRole(userId);
    }
}
