package com.yksj.monitor.entity;

import lombok.Data;

@Data
public class Stu {
    private String name;
    private Integer age;


    public Stu(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
