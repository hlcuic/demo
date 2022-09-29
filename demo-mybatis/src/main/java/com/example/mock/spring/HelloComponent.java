package com.example.mock.spring;

/**
 * @author cuihailong
 * @date 2022/9/29 14:09
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HelloComponent {

    String value() default "";
}

