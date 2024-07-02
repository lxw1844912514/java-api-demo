package com.yksj.monitor.service;

import com.yksj.monitor.entity.User;
import com.yksj.monitor.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> searchAll(){
        return userMapper.findAll();
    }
}
