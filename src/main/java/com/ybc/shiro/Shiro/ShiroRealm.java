package com.ybc.shiro.Shiro;

import com.ybc.shiro.Entity.Permission;
import com.ybc.shiro.Entity.Role;
import com.ybc.shiro.Entity.User;
import com.ybc.shiro.Mapper.UserMapper;
import com.ybc.shiro.Mapper.UserPermissionMapper;
import com.ybc.shiro.Mapper.UserRoleMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


public class ShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserPermissionMapper userPermissionMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();
        logger.info("获取方法为空");
        List<Role> roleList = userRoleMapper.findRoleByUsername(username);
        Set<String> roleSet = new HashSet<>();

        for(Role i : roleList){
            roleSet.add(i.getName());
            logger.info("获取角色:" + i.getName());
        }

        List<Permission> permissionList = userPermissionMapper.findPermissionByUsername(username);
        Set<String> permissionSet = new HashSet<>();
        for(Permission p : permissionList){
            permissionSet.add(p.getName());
            logger.info("获取权限:" + p.getName());
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        simpleAuthorizationInfo.setRoles(roleSet);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        User user = userMapper.findByUsername(username);

        logger.info("用户密码：" + user.getPassword());
        logger.info("用户名：" + user.getUsername());
        logger.info("登录密码：" + password);
        logger.info(String.valueOf(password.equals(user.getPassword())));


        if(! username.equals(user.getUsername())) throw new UnknownAccountException("用户名不存在");
        if(! password.equals(user.getPassword())){
            logger.info("抛出密码错误异常");
            throw new IncorrectCredentialsException("密码错误");
        }

        logger.info("success to here");
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, password, username);

        return simpleAuthenticationInfo;
    }
}
