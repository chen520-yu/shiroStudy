package com.ybc.shiro.Mapper;

import com.ybc.shiro.Entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserPermissionMapper {

    @Select("select p.* from t_user as u, t_role as r, t_user_role as ur, t_permission as p, t_role_permission as rp " +
            "where u.username = #{username} and u.id = ur.user_id and " +
            "ur.rid = r.id and rp.rid = r.id and p.id = rp.pid")
    List<Permission> findPermissionByUsername(String username);
}
