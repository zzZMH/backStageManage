package com.demo.config;

import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.service.TestService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.List;

public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private TestService testService;

    /**
     * 角色授权
     * 验证用户是否拥有业务角色
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Object principal = principals.getPrimaryPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            if(user != null){
                List<Role> roleList = testService.getUserRole(user.getId());
                if(roleList != null && roleList.size() > 0){
                    for(Role role : roleList){
                        info.addRole(role.getName());
                    }
                }
            }
        }
        return info;
    }

    /**
     * 登录认证
     * 主要是用来进行身份认证的
     * 验证用户输入的账号和密码是否正确
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        String username = authToken.getPrincipal().toString();
        String userPwd = new String((char[]) authToken.getCredentials());
        userPwd = new Md5Hash(userPwd, username).toHex();
        List<User> userList = testService.getUser(username);
        if (userList != null && userList.size() > 0) {
            User user = userList.get(0);
            if (user.getState().equals("1")) {
                SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                        user,//用户
                        userPwd,//密码
                        ByteSource.Util.bytes(username),
                        getName()
                );
                return authenticationInfo;
            }
            throw new DisabledAccountException();
        }
        throw new UnknownAccountException();
    }
}
