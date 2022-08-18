package com.example.mybatis.mapper;

import com.example.mybatis.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//@CacheNamespace(blocking = true)
public interface UserMapper {

    @Select({
        "select * from user where name = ${name}"
    })
    @ResultMap("com.UserMapper.test")
    List<User> selectUser(@Param("name") String name);

    int insert(User user);

    List<User> listUsers(User user);
}
