package com.yksj.monitor.entity;

import java.util.Date;

public class BaseEntity {

    private Date createdAt;
    private Date updatedAt;
    private Date currentTime = new Date();

    // 实现创建时自动填充创建时间和创建者
    public void insert() {
        this.createdAt = currentTime;
    }

    public void update() {
        this.updatedAt =currentTime;
    }
}
