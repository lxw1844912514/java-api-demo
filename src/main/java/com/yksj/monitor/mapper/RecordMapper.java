package com.yksj.monitor.mapper;

import com.yksj.monitor.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface RecordMapper {


    @Update("INSERT INTO `record`(`" +
            "type_id`,`container_id`,`image`,`command`,`created`,`status`,`ports`,`names`,`cpu`,`mem_usage`,`mem_limit`," +
            "`mem`,`net_input`,`net_out`,`block_input`,`block_out`,`pids`,`restart_count`,`created_at`,`updated_at`) " +
            "VALUES " +
            "(#{type_id},#{container_id},#{image},#{command},#{created},#{status},#{ports},#{names},#{cpu},#{mem_usage},#{mem_limit}," +
            "#{mem},#{net_input},#{net_out},#{block_input},#{block_out},#{pids},#{restart_count},#{created_at},#{updated_at})")
   @Transactional
    void save(Record record);

    //查询所有
    @Select("SELECT * FROM `record` order by `id` desc LIMIT 0,1000")
    List<Record> records();

    //分页展示
    @Select("select * from record order by `id` desc limit  #{offset},#{pageSize}")
    List<Record> findByPage(int offset, int pageSize);

    @Select("select count(id) from record  ")
    int countRecord();

}
