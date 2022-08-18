package com.example.demo.dataservice.mysql.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Team {

    private Integer id;

    private Integer cardId;

    private String name;

    private Integer age;

    private Integer height;

    private Integer weight;

    private String address;

    private Date createDate;

}