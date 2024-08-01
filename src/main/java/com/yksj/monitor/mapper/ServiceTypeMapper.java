package com.yksj.monitor.mapper;

import com.yksj.monitor.entity.ServiceType;
import com.yksj.monitor.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Mapper
public interface ServiceTypeMapper {

    @Select("SELECT * FROM `service_type` LIMIT 0,1000")
    List<ServiceType> types();

    @Update("INSERT INTO `service_type`(`name`, `listen_name`, `state`,`is_docker`,`ip`,`created_at`,`updated_at`) VALUES (#{name},#{listenName},#{state},#{isDocker},#{ip},#{createdAt},#{updatedAt})")
    @Transactional
    Integer save(ServiceType serviceType);

    @Update("UPDATE `service_type` SET `state` = #{state},`name`=#{name},`ip`=#{ip}, `listen_name`=#{listenName} ,`updated_at`= #{updatedAt} WHERE `id` = #{id}")
    @Transactional
     Integer updateById(ServiceType serviceType);

    @Update("UPDATE `service_type` SET `state` = #{state},`updated_at`= #{updatedAt} WHERE `id` = #{id}")
    @Transactional
    Integer updateStateById(ServiceType serviceType);


    @Delete("delete from `service_type` where `id`=#{id}")
    @Transactional
    Integer deleteById(long id);

}
