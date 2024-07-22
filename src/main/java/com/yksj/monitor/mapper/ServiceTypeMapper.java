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

    @Update("INSERT INTO `service_type`(`name`, `listen_name`, `state`,`is_docker`,`ip`,`created_at`,`updated_at`) VALUES (#{name},#{listen_name},#{state},#{is_docker},#{ip},#{created_at},#{updated_at})")
    @Transactional
    Integer save(ServiceType serviceType);

    @Update("UPDATE `service_type` SET `state` = #{state},`name`=#{name},`ip`=#{ip}, `listen_name`=#{listen_name} ,`updated_at`= #{updated_at} WHERE `id` = #{id}")
    @Transactional
    public Integer updateById(ServiceType serviceType);

    @Delete("delete from `service_type` where `id`=#{id}")
    @Transactional
    Integer deleteById(long id);

}
