package com.yksj.monitor.controller;

import com.yksj.monitor.entity.MyConfig;
import com.yksj.monitor.entity.Stu;
import com.yksj.monitor.entity.Student;
import com.yksj.monitor.utils.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.SQLOutput;

//@Controller
@RestController
@Slf4j
public class HelloController {

    //    @ResponseBody
    @RequestMapping("hello")
    public String Hello() {
        return "hello world-123";
    }

    @Autowired  //实现依赖注入 DI
    Stu stu;

    @GetMapping("getStu")
    public Object getStu() {
        return stu;  // 控制反转 IOC
    }

    //
    @Autowired
    MyConfig myConfig;

    @GetMapping("myConfig")
    public Object getMyCOnfig() {
        return myConfig;
    }


    //在yml中实现自定义配置与表达式

    @Value("${self.custom.config.sdkSecret}")
    private String sdkSecret;
    @Value("${self.custom.config.host}")
    private String host;
    @Value("${self.custom.config.port}")
    private int port;

    @GetMapping("myYmlConfig")
    public Object myYmlConfig() {
        return sdkSecret + "\t" + host + ":" + port;
    }

    @GetMapping("getStudent")
    public JSONResult getStudent() {
        // 用的是 NoArgsConstructor 生成的无参数的构造函数
        Student student = new Student();
        student.setName("lxw-123");
        student.setAge(20);
        System.out.println(student);//Student(name=lxw-123, age=20)

        //用的是 AllArgsConstructor,生成的有参数的构造函数
        Student student1 = new Student("test-456", 23);
        log.info(student1.toString());

        log.debug("log-debug");
        log.info("log-info");
        log.warn("log-warn");
        log.error("log-error");
        return JSONResult.ok(student1);
    }


    //    上传文件
    @PostMapping("upload")
    public String uploadFile(MultipartFile image) throws Exception {
        image.transferTo(new File("/tmp/" + image.getOriginalFilename()));
        return "上传成功";
    }

    @PostMapping("upload2")
    public String uploadFile2(MultipartFile image) throws Exception {
        image.transferTo(new File("/tmp/" + image.getOriginalFilename()));
        return "上传成功";
    }
}
