package com.yksj.monitor.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yksj.monitor.entity.Stu;
import com.yksj.monitor.mapper.StuMapper;
import com.yksj.monitor.mapper.StuMapperCustom;
import com.yksj.monitor.service.StuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private Stu stu;

    @Autowired
    private StuMapperCustom stuMapperCustom;

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

    @Override
    public Stu queryByIdCustom(String id) {
        List<Stu> list=stuMapperCustom.getStuById(id);
        if (list!=null&& !list.isEmpty()){
            return list.get(0);
        }
        return null;
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

    public Integer deleteStu(Stu stu) {
        //删除对象三种方式
        //1.根据主键删除
        //  Integer res= stuMapper.deleteByPrimaryKey(stu.getId());

        //2.根据对象中的属性值匹配做条件删除
        // Integer res=  stuMapper.delete(stu);

        //3.根据构建的example 进行条件删除
        Example example = new Example(Stu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", stu.getId());     //等于
        criteria.andEqualTo("name", stu.getName());     //等于
        Integer res = stuMapper.deleteByExample(example);

        return res;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void testTrans() {
        //1.新增数据
        //2.修改数据
        //3.模拟发生异常
        //4.观察 1和2 步骤 发生的数据变动，有没有影响到数据库
        //5.处理事务实现事物的回滚，不然先前的事务回滚
        stu.setName("test");
        stu.setAge(22);
        stu.setEmail("22@qq.com");
        stuMapper.insert(stu);

        int y = 100 / 0; //报错抛出异常： / by zero ，开启事务后，后边的数据不在变更

        stu.setId(128);
        stuMapper.deleteByPrimaryKey(stu.getId());

    }
}
