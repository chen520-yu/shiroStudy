package com.ybc.shiro.Mapper;

import com.ybc.shiro.Entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    @Select("select r.* from t_user as u, t_role as r, t_user_role as ur where u.username = #{username} and u.id = ur.user_id and" +
            " ur.rid = r.id")
    List<Role> findRoleByUsername(String username);

}
