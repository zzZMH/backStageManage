package com.demo.controller;

import com.demo.entity.User;
import com.demo.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping({"/test"})
public class TestController {

    @Resource
    private TestService testService;

    @RequestMapping(value = {"/getTest"})
    public List<User> getTest() {
        return testService.getUser();
    }
}
