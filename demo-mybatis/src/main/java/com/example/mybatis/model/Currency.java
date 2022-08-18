package com.example.mybatis.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Currency {

    cny("CNY","人民币"),
    usd("USD","美元");

    private String code;
    private String desc;

    Currency(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    private static Map<String,Currency> map = new ConcurrentHashMap<>();

    static{
        for(Currency currency:values()){
            map.put(currency.code,currency);
        }
    }

    public static Currency getCurrencyByCode(String code){
        return map.get(code);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
