package com.example.demo;

import com.example.mybatis.mapper.UserMapper;
import com.example.mybatis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 测试同一个类事务调用是否生效
 */
@Service
public class TestTransactionalService implements TestService{

    @Autowired
    private UserMapper userMapper;

    /**
     * 同一个类
     * 1：事务方法调非事务方法，事务会生效
     * 2：非事务方法调用事务方法，事务会失效
     * @return
     */

    @Transactional
    public int add(){
        int count = doAdd();
        return count;
    }

    public int doAdd(){
        User user = new User();
        user.setName("Lucy");
        user.setAge("30");
        int count =  userMapper.insert(user);
        int i = 10/0;
        return count;
    }

}
