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
     * @param name
     * @param age
     * @return
     */
    public List<Stu> queryByCondition(String name,Integer age);

    /**
     * 根据条件查询stu的list结果集-分页展示
     * @param name
     * @param age
     * @param page
     * @param pageSize
     * @return
     */
    public List<Stu> queryByCondition(String name,Integer age,Integer page,Integer pageSize);

    /**
     * 修改stu到数据库
     * @param stu
     * @return
     */
    public Integer updateStu(Stu stu);

   public Integer updateStu(Stu stu, String name,Integer age,String email);

    /**
     * 删除用户
     * @param stu
     * @return
     */
   public Integer deleteStu(Stu stu);

    /**
     * 测试事务
     */
   public void testTrans();
}
