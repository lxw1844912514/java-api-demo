package com.yksj.monitor.entity;

import java.util.Date;

public class BaseEntity {

    private Date created_at;
    private Date updated_at;



    // 实现创建时自动填充创建时间和创建者
    public void insert() {
        this.created_at = new Date();
    }

    public void update() {
        this.updated_at = new Date();
    }
}
