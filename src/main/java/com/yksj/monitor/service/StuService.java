package com.yksj.monitor.service;

import com.yksj.monitor.entity.Stu;

import java.util.List;

/**
 * stu 服务接口
 */
public interface StuService {
    /**
     * 新增stu 到数据库
     * @param stu
     */
    public void savaStu(Stu stu);

    /**
     * 根据ID查询
     * @param id
     */
    public Stu queryById(String id);

    /**
     * 根据条件查询stu的list结果集
     * @param id
     * @param age
     * @return
     */
    public List<Stu> queryByCondition(String id,Integer age);
}
