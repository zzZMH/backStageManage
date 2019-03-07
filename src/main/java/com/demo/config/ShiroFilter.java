package com.demo.config;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ShiroFilter extends FormAuthenticationFilter {

    public ShiroFilter() {
        super();
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            String urlStr = req.getRequestURL().toString().substring(req.getRequestURL().toString().indexOf(req.getContextPath())+req.getContextPath().length());
            if (req.getMethod().toUpperCase().equals("OPTIONS")) {
                return true;
            }
            if(urlStr.indexOf("/druid") == 0 || urlStr.indexOf("/test/login") == 0){
                return true;
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setCharacterEncoding("utf-8");
        Map<String, Object> map = new HashMap<>();
        map.put("code", "error");
        map.put("msg", "未登录");
        res.getWriter().write(JSON.toJSONString(map));
        return false;
    }
}
