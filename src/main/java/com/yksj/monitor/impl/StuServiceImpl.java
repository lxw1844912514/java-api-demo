package com.yksj.monitor.impl;

import com.yksj.monitor.entity.Stu;
import com.yksj.monitor.mapper.StuMapper;
import com.yksj.monitor.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private StuMapper stuMapper;

    @Override
    public void savaStu(Stu stu) {
        stuMapper.insert(stu);
    }
}
