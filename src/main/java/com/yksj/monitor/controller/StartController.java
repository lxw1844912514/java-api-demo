package com.yksj.monitor.controller;

import com.yksj.monitor.entity.User;
import com.yksj.monitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/start")
public class StartController {
    @Autowired
    private UserService userService;

    @RequestMapping("/abc")
    public Animal getName(){
        return  new Animal("dog",5);
    }

    @RequestMapping("/userList")
    public List<User> getUserList(){
        return  userService.searchAll();
    }

}