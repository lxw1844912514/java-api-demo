package com.yksj.monitor.mapper;

import com.yksj.monitor.entity.User;
import com.yksj.monitor.vo.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAll();

    @Select("select * from user")
    List<User> Users();


    @Update("INSERT INTO `user`(`name`, `age`, `email`) VALUES (#{name},#{age},#{email})")
    @Transactional
    void save(User user);

    @Update("UPDATE `user` SET `name` =#{name} ,age=#{age},email=#{email} WHERE `id`=#{id}")
    @Transactional
    void updateById(User user);

    @Delete("delete  from `user` where id=#{id}")
    @Transactional
    void deleteById(long id);

    @Select("select * from user  where id=#{id}")
    User findByUserId(long id);

    @Select("select * from user limit  #{offset},#{pageSize}")
    List<User> findByPage(int offset, int pageSize);

    @Select("select count(id) from user ")
    int countUser();

}
