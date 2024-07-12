package com.yksj.monitor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data  //生成getter 和setter
@ToString //生成toString 方法
@AllArgsConstructor //生成全参的构造函数
@NoArgsConstructor //生成默认的构造函数，不带参数
public class Student {
    private String name;
    private Integer age;

//1.与 @ToString 效果相同，不能同时存在
    /*@Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }*/


// 2.  与 @NoArgsConstructor 效果相同，不能同时存在
   /* public Student() {
    }*/


// 3.与 @AllArgsConstructor 效果相同，不能同时存在
    /*public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }*/

}
