package com.yksj.monitor.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yksj.monitor.entity.Stu;
import com.yksj.monitor.mapper.StuMapper;
import com.yksj.monitor.service.StuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 实现类：去实现StuService中的方法
 */
@Service
public class StuServiceImpl implements StuService {

    private static final Logger log = LoggerFactory.getLogger(StuServiceImpl.class);
    @Autowired
    private StuMapper stuMapper;

    @Override
    public void savaStu(Stu stu) {
        stuMapper.insert(stu);
    }

    public void test() {
        System.out.println("我是实现类中test方法");
    }

    @Override
    public Stu queryById(String id) {
        Stu stu = stuMapper.selectByPrimaryKey(id);
        return stu;
    }

    //    方法一：适用于多种复杂查询的情况下，模糊查询，大于，小于等
    @Override
    public List<Stu> queryByCondition(String name, Integer age) {
        Example example = new Example(Stu.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("name", name);     //等于
//        criteria.andLike("name",name); // like模糊查询
        criteria.andBetween("age", 1, 12); // 年龄在1-12岁之间的，包含1和12岁
//        criteria.andEqualTo("age",age);

        return stuMapper.selectByExample(example);
//        return Collections.emptyList(); //返回空集合
    }

    /*// 方法二：仅适用于属性匹配，也就是说：只能在简单等于的情况下
    @Override
    public List<Stu> queryByCondition(String name, Integer age) {
        //条件对象
        Stu stu = new Stu();
        stu.setName(name);
        stu.setAge(age);
        return stuMapper.select(stu);
    }*/

    //    分页展示
    @Override
    public List<Stu> queryByCondition(String name, Integer age, Integer page, Integer pageSize) {

        PageHelper.startPage(page, pageSize);

        Stu stu = new Stu();
        List<Stu> stuList = stuMapper.select(stu);
        PageInfo<?> pageListInfo = new PageInfo<>(stuList);
        log.info(pageListInfo.toString());
        return stuList;
    }

    @Override
    public Integer updateStu(Stu stu) {
        return stuMapper.updateByPrimaryKey(stu);
    }

    @Override
    public Integer updateStu(Stu stu, String name, Integer age, String email) {


        //查询的条件
        Example example = new Example(Stu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", name);     //等于
//        criteria.andEqualTo("age", age);     //等于
//        criteria.andEqualTo("email", email);     //等于
        return stuMapper.updateByExample(stu, example);
//        return stuMapper.updateByExampleSelective(stu, example);
    }
}
