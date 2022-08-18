package com.example.demo;

import com.example.mybatis.mapper.UserMapper;
import com.example.mybatis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestTransactionalService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public int add(){
        User user = new User();
        user.setName("Lucy");
        user.setAge("30");
        return userMapper.insert(user);
    }

}
