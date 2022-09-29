package com.example.mock.spring;

/**
 * @author cuihailong
 * @date 2022/9/29 14:56
 */
public class HelloBeanDefinition {

    private Class clazz;

    private boolean isLazy;

    private String scope;

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public boolean isLazy() {
        return isLazy;
    }

    public void setLazy(boolean lazy) {
        isLazy = lazy;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
