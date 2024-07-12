package com.yksj.monitor;

import lombok.Data;

import java.util.Date;

@Data
public class Entity {
    private Date createTime;
    private Date updateTime;

    public Entity() {
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    public void update() {
        this.updateTime = new Date();
    }

    // Getters and setters
}