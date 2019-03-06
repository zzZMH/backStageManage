package com.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.service.TestService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping({"/test"})
public class TestController {

    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping(value = {"/login"})
    public void doLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userName = request.getParameter("userName");
        String userPwd = request.getParameter("userPwd");
        UsernamePasswordToken token = new UsernamePasswordToken(userName, userPwd);
        redisTemplate.opsForValue().set("userId", "zmh");
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        JSONObject resultJson = new JSONObject();
        if (subject.isAuthenticated()) {
            resultJson.put("code", "seccess");
            resultJson.put("msg", "成功");
            resultJson.put("token", subject.getSession().getId());
        }else{
            resultJson.put("code", "error");
            resultJson.put("msg", "失败");
        }
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.getWriter().write(resultJson.toJSONString());
    }

    @RequestMapping(value = {"/getTest"})
    public void getTest() {
        redisTemplate.opsForValue().set("userId", "zmh");
        System.out.println(redisTemplate.opsForValue().get("userId"));
    }

    @RequestMapping(value = {"/show"})
    public void showUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userName = request.getParameter("userName");
        String userPwd = request.getParameter("userPwd");
        JSONObject resultJson = new JSONObject();
        resultJson.put("user", userName+"-"+userPwd);
        resultJson.put("method", "/test/show");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.getWriter().write(resultJson.toJSONString());
    }
}
