package com.yksj.monitor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

@Controller
@Slf4j
@RequestMapping("thyme")
public class ThymeleafController {

    @Autowired
    private ServletContext servletContext;

    @GetMapping("view")
    public String view(Model model, HttpServletRequest request) {
        // 1.分开传递
        // model.addAttribute("name", "lxw");
        // model.addAttribute("age", 11);

        // 2.传递对象
//        Stu stu = new Stu();
//        stu.setName("test");
//        stu.setAge(22);
//        model.addAttribute(stu);

//        3.传递map
        Map<String, String> map = new HashMap<>();
        map.put("name", "test22");
        model.addAllAttributes(map);


        Date birthday = new Date();
        model.addAttribute("birthday", birthday);

        Integer sex = 1;
        model.addAttribute("sex", sex);

        List<String> myList = new ArrayList();
        myList.add("java");
        myList.add("html");
        myList.add("php");
        myList.add("go");

        Map<String, Object> myMap = new HashMap<>();
        myMap.put("name", "lxw");
        myMap.put("sex", 1);
        myMap.put("list", myList);

        model.addAttribute("myMap", myMap);
        model.addAttribute("myList", myList);

        request.setAttribute("englishName", "xd");
        request.getSession().setAttribute("token", "token-121212");

        return "teacher"; //模版文件名
    }


    @Autowired
    TemplateEngine templateEngine;

    @GetMapping("createHtml")
    @ResponseBody
    public String createHtml(Model model, HttpServletRequest request) throws Throwable {
//        3.传递map
        Map<String, String> map = new HashMap<>();
        map.put("name", "test22");
        model.addAllAttributes(map);


        Date birthday = new Date();
        model.addAttribute("birthday", birthday);

        Integer sex = 1;
        model.addAttribute("sex", sex);

        List<String> myList = new ArrayList();
        myList.add("java");
        myList.add("html");
        myList.add("php");
        myList.add("go");

        Map<String, Object> myMap = new HashMap<>();
        myMap.put("name", "lxw");
        myMap.put("sex", 1);
        myMap.put("list", myList);

        model.addAttribute("myMap", myMap);
        model.addAttribute("myList", myList);

        //生成静态页面 开始静态化
        Context context = new Context();
        context.setVariable("myMap", myMap);
        context.setVariable("myList", myList);
        context.setVariable("birthday", birthday);
        context.setVariable("sex", sex);
//        context.setVariable("map", map);
        context.setVariable("name", map);

        //定义保存的目录
        String path = "./";
        Writer out = new FileWriter(path + "/teacher.html");
        templateEngine.process("teacher", context, out);
        out.close();
        return "ok"; //模版文件名
    }
}
