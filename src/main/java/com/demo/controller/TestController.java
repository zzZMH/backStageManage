package com.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.entity.User;
import com.demo.service.TestService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/test"})
public class TestController {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private TestService testService;

    @RequestMapping(value = {"/login"})
    public JSONObject doLogin(@RequestBody Map params) {
        String userName = params.get("userName").toString();
        String userPwd = params.get("userPwd").toString();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, userPwd);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        JSONObject resultJson = new JSONObject();
        if (subject.isAuthenticated()) {
            resultJson.put("code", "success");
            resultJson.put("msg", "成功");
            resultJson.put("token", subject.getSession().getId());
        }else{
            resultJson.put("code", "error");
            resultJson.put("msg", "失败");
        }
        return resultJson;
    }

    @RequestMapping(value = {"/defaultLogin"})
    public JSONObject defaultLogin(@RequestBody Map params) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("msg", "defaultLogin--默认登陆跳转");
        return resultJson;
    }

    @RequestMapping(value = {"/successLogin"})
    public JSONObject successLogin(@RequestBody Map params) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("msg", "successLogin--登陆成功跳转");
        return resultJson;
    }

    @RequestMapping(value = {"/getTest"})
    public void getTest() {
        redisTemplate.opsForValue().set("userId", "zmh");
        System.out.println(redisTemplate.opsForValue().get("userId"));
    }

    @RequestMapping(value = {"/getUser"})
    public JSONObject getUser(@RequestBody Map params) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", "success");
        List<User> list = testService.getUser(params.get("userName").toString());
        User user = new User();
        if(list != null && list.size() > 0){
            resultJson.put("user", list.get(0));
        }
        return resultJson;
    }
}
