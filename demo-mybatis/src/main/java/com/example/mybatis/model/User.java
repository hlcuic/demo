package com.example.mybatis.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private int id;
    private String name;
    private String age;

    private Currency currency;

}
