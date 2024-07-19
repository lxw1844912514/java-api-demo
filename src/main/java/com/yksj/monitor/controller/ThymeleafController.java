package com.yksj.monitor.controller;

import com.yksj.monitor.entity.Stu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("thyme")
public class ThymeleafController {

    @GetMapping("view")
    public String view(Model model) {
        // 1.分开传递
        // model.addAttribute("name", "lxw");
        // model.addAttribute("age", 11);

        // 2.传递对象
//        Stu stu = new Stu();
//        stu.setName("test");
//        stu.setAge(22);
//        model.addAttribute(stu);

//        3.传递map
        Map<String,String> map = new HashMap<>();
        map.put("name","test22");
        model.addAllAttributes(map);

        return "teacher"; //模版文件名
    }
}
