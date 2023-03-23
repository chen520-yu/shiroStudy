package com.ybc.shiro.Mapper;

import com.ybc.shiro.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Mapper
public interface UserMapper {

    @Select("select * from t_user where username = #{username}")
    @Results(id = "user", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "passwd", property = "password"),
            @Result(column = "status", property = "status"),
            @Result(column = "create_time", property = "date")
    })
    User findByUsername(String usernmae);
}
