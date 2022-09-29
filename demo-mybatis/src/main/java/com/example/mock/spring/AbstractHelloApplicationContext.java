package com.example.mock.spring;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cuihailong
 * @date 2022/9/29 14:53
 */
public abstract class AbstractHelloApplicationContext {

    protected Class clazz;

    protected Map<String,HelloBeanDefinition> beanDefinitionMap = new HashMap<>();

    protected Map<String,Object> singletonMap = new HashMap<>();

    public AbstractHelloApplicationContext(Class clazz){
        this.clazz = clazz;
    }
}
