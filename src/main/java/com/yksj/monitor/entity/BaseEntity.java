package com.yksj.monitor.entity;

import java.util.Date;

public class BaseEntity {

    private Date created_at;
    private Date updated_at;
    private Date currentTime = new Date();

    // 实现创建时自动填充创建时间和创建者
    public void insert() {
        this.created_at = currentTime;
    }

    public void update() {
        this.updated_at =currentTime;
    }
}
