package com.yksj.monitor.mapper;

import com.yksj.monitor.entity.Stu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StuMapperCustom {
    public List<Stu> getStuById(String sid);
}
