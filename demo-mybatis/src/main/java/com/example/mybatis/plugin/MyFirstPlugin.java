package com.example.mybatis.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Statement;
import java.util.Properties;

/**
 * 告诉MyBatis当前插件用来拦截哪个对象的哪个方法
 */
@Intercepts({@Signature(
            type = StatementHandler.class,
            method = "query",
            args = {Statement.class,ResultHandler.class}
        )
    })
public class MyFirstPlugin implements Interceptor {

    /**
     *
     * 拦截目标对象的目标方法的执行
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("MyFirstPlugin...intercept:"+invocation.getMethod());
        Object target = invocation.getTarget();
        System.out.println("当前拦截到的对象："+target);
        //拿到target的元数据
        MetaObject metaObject = SystemMetaObject.forObject(target);
        Object value = metaObject.getValue("parameterHandler.parameterObject");
        System.out.println("sql语句用的参数是："+value);
        //执行目标方法
        Object proceed = invocation.proceed();
        //返回执行后的返回值
        return proceed;
    }

    /**
     *
     *包装目标对象的：为目标对象创建一个代理对象
     */
    @Override
    public Object plugin(Object target) {
        //我们可以借助Plugin的wrap方法来使用当前Interceptor包装我们目标对象
        System.out.println("MyFirstPlugin...plugin:mybatis将要包装的对象"+target);
        Object wrap = Plugin.wrap(target, this);
        if(target != wrap){
            System.out.println("已经被代理："+target);
        }
        //返回为当前target创建的动态代理
        return wrap;
    }

    /**
     *
     *将插件注册时 的property属性设置进来
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("插件配置的信息："+properties);
    }

}
