package com.example.mybatis;

import com.example.mybatis.mapper.UserMapper;
import com.example.mybatis.model.Currency;
import com.example.mybatis.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class TestMybatis {

    public static void main(String[] args) throws IOException {
        // 将mybatis-config的配置文件读入内存，生成字符流对象
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");

        // 解析全局配置文件mybatis-config.xml
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = builder.build(reader);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        //       PageHelper.startPage(1,2);

        // 测试一级缓存：
        User user = new User();
        user.setName("hello105");
        user.setCurrency(Currency.usd);
        List<User> list = userMapper.listUsers(user);
        System.out.println("第一次查询结果：" + list.size());
        System.out.println("第一次查询结果：" + list);
    }

        /*
        List<User> list2 = userMapper.selectUser("'hello105'");
        System.out.println("第二次查询结果：" + list2.size());
        System.out.println("第一次查询结果：" + list2);

        // 只有sqlSession关闭时，数据才会缓存到二级缓存
        sqlSession.close();

        // 测试二级缓存：
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
        System.out.println("另外一个会话查询结果："+userMapper2.selectUser("'hello105'").size());
        */
//    }



    private void test(){
        /*
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setName("hello");
        user.setAge("30");
        userMapper.insert(user);
        sqlSession.commit();*/

        /*UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        for(int i=105;i>0;i--){
            User user = new User();
            user.setName("hello"+i);
            user.setAge(""+i);
            userMapper.insert(user);
            if(i%10==0){
                sqlSession.commit();
            }
        }
        sqlSession.commit();*/
    }

}




