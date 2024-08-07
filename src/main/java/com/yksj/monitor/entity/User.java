package com.yksj.monitor.entity;

import lombok.Data;

@Data
//@JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler"})
public class User {
    private Integer id;
    private String name;
    private Integer age;
    private String email;

    //构造函数
    public User(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;  //字段顺序不能错乱，否则报错
        this.age = age;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
