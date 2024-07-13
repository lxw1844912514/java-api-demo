package com.yksj.monitor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("stu")
public class StuController {

    //测试url: http://localhost:8001/stu/get/9/11?id=1&age=22&name=w44
    @GetMapping("get/{stuId}/{page}")  //这里定义几个变量，请求时必须传递几个变量，可以不使用但必须传，否则报错
    public String getStudents(@PathVariable("stuId") String stuId, //
//                              Integer page,  //@PathVariable  获取路径中的变量， //page=null
                              @PathVariable("page") Integer page, //page=11
                              @RequestParam Integer id,
                              @RequestParam String name,
                              Integer age) {
        // @RequestParam: 用于获得url中的请求参数 key=>value形式，如果参数变量名保持一致，该注解可省略

        log.info("id={}", id);//id=1
        log.info("name={}", name);//name=w44
        log.info("stuId=" + stuId);//stuId=9
        log.info("age=" + age); //age=22
        log.info("page=" + page);   //page=null
        return "查询学生";
    }

    @PostMapping("create")
    public String createStu(@RequestBody Map<String, Object> map,
                            @RequestHeader("token") String token,
                            @CookieValue("clientId") String clientId,
                            HttpServletRequest request) {
        log.info("token=" + token);
        log.info("clientId={}", clientId);
        log.info("map={}", map.toString());

        String HeaderToken = request.getHeader("token");
        log.info("HeaderToken={}", HeaderToken);
        return "添加学生";
    }
}
