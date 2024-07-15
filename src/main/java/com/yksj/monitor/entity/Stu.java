package com.yksj.monitor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

//@AllArgsConstructor
@Data
@Table(name = "user")
public class Stu {
    @Id //声明主键
    private Integer id;
    private String name;
    private Integer age;

    public Stu() {

    }
}
