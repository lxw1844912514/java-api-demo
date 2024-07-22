package com.yksj.monitor.mapper;

import com.yksj.monitor.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface RecordMapper {


    // 添加记录
    @Update("INSERT INTO `record`(`" +
            "type_id`,`container_id`,`image`,`command`,`created`,`status`,`ports`,`names`,`cpu`,`mem_usage`,`mem_limit`," +
            "`mem`,`net_input`,`net_out`,`block_input`,`block_out`,`pids`,`restart_count`,`created_at`,`updated_at`) " +
            "VALUES " +
            "(#{type_id},#{container_id},#{image},#{command},#{created},#{status},#{ports},#{names},#{cpu},#{mem_usage},#{mem_limit}," +
            "#{mem},#{net_input},#{net_out},#{block_input},#{block_out},#{pids},#{restart_count},#{created_at},#{updated_at})")
    @Transactional
    Integer save(Record record);

    //查询所有
    @Select("SELECT * FROM `record` order by `id` desc LIMIT 0,1000")
    List<Record> records();

    //分页展示
    @Select("select * from record WHERE `type_id`=#{typeId} AND created_at >= #{createTime}  order by `id` desc limit  #{offset},#{pageSize}")
    List<Record> findByPage(Integer typeId, LocalDateTime createTime, int offset, int pageSize);

    // 统计记录总数
    @Select("select count(id) from record  WHERE `type_id`=#{typeId} AND created_at >= #{createTime}  ")
    int countRecord(Integer typeId, LocalDateTime createTime);

}
