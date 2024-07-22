package com.yksj.monitor.entity;

import com.yksj.monitor.service.StuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.UUID;

@SpringBootTest //表示当前会被sprint boot 加载为测试类
class StudentTest {
    @Autowired
    StuService stuService;

    @Test
    public void testStu() {

        Stu stu = new Stu();
        stu.setName(UUID.randomUUID().toString());
        stu.setAge(22);
        stu.setEmail("22@qq.com");
        stuService.savaStu(stu);
    }


}