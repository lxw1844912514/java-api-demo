package com.yksj.monitor.service;

import com.yksj.monitor.entity.Stu;

/**
 * stu 服务接口
 */
public interface StuService {
    /**
     * 新增stu 到数据库
     * @param stu
     */
    public void savaStu(Stu stu);
}
