package com.example.springbootstage.config;


import com.example.springbootstage.entity.system.Permission;
import com.example.springbootstage.entity.system.Role;
import com.example.springbootstage.entity.system.User;
import com.example.springbootstage.service.system.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * shiro配置类
 * Created by WangHong on 2018/3/26.
 */
public class MyShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserService userService;

    //角色权限和对应权限添加
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("调用授权检测。。。。。。");
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        User user = (User) principals.getPrimaryPrincipal();
        if(user != null){
            //添加角色和权限
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            simpleAuthorizationInfo.setRoles(user.getRoleNames());
            simpleAuthorizationInfo.setStringPermissions(user.getPermissionNames());
            logger.info(String.format("用户[%s]登录成功,获取权限集合:%s", user.getUsername(), user.getPermissionNames().toString()));
            return simpleAuthorizationInfo;
        }
        return null;
    }

    //用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        logger.info(String.format("用户[%s]尝试登录,...", token.getUsername()));

        //获取用户信息
        String name = token.getUsername();
        User user = userService.findByUsername(name);
        if (user == null) {
            //这里返回后会报出对应异常
            return null;
        }
        //设置盐值
        ByteSource salt = ByteSource.Util.bytes(user.getCredentialsSalt());
        return new SimpleAuthenticationInfo(user, user.getPassword(), salt, getName());
    }


}
