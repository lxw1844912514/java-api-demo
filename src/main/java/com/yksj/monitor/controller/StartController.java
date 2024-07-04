package com.yksj.monitor.controller;

import com.yksj.monitor.entity.User;
import com.yksj.monitor.mapper.UserMapper;
import com.yksj.monitor.service.UserService;
import com.yksj.monitor.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//@ResponseBody
@RestController
@RequestMapping("/start")
public class StartController {
    @Resource
    UserMapper userMapper;

    @Autowired
    private UserService userService;

    @RequestMapping("/abc")
    public Animal getName() {
        return new Animal("dog", 5);
    }

    @GetMapping("/hello")
    public List<String> hello() {
        List<String> strings = new java.util.ArrayList<>();
        strings.add("hello");
        strings.add("world");
        return strings;
    }

    @RequestMapping("/userList")
    public List<User> getUserList() {
        return userService.searchAll();
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userMapper.Users();
    }

    @PostMapping
    public String addUser(@RequestBody User user) {
        userMapper.save(user);
        return "success";
    }

    @PutMapping
    public String updateUser(@RequestBody User user) {
        userMapper.updateById(user);
        return "success";
    }

    @DeleteMapping("/{id}")
    public String deleteUserbyId(@PathVariable("id") long id) {
        userMapper.deleteById(id);
        return "success";
    }

    @GetMapping("/user/{id}")
    public User findById(@PathVariable("id") long id) {
        return userMapper.findByUserId(id);
    }

    // pagehelper 插件
    @GetMapping("/page")
    public Page<User> findByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize) {

        int offset = (pageNum - 1) * pageSize;
        List<User> userData = userMapper.findByPage(offset, pageSize);
        Page<User> page = new Page<>();
        page.setData(userData);

        int total = userMapper.countUser();
        page.setTotal(total);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        return page;
    }

}