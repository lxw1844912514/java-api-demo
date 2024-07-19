package com.yksj.monitor.controller;

import com.yksj.monitor.entity.Stu;
import com.yksj.monitor.entity.bo.StuBo;
import com.yksj.monitor.impl.StuServiceImpl;
import com.yksj.monitor.service.StuService;
import com.yksj.monitor.utils.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@Slf4j
@RequestMapping("stu")
public class StuController {

    private final Stu stu;

    public StuController(Stu stu) {
        this.stu = stu;
    }

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

    @Autowired
//    private StuService stuService;
    private StuServiceImpl stuService; //todo::区别

    @PostMapping("addStu")
    public JSONResult addStu() {
        String randName = UUID.randomUUID().toString();

        Stu stu = new Stu();
//        stu.setId(111);
        stu.setName(randName);
        stu.setAge(1);
        stuService.savaStu(stu);
        stuService.test();

        log.info(randName);
        return JSONResult.ok();
    }

    //    通过postman 传递数据
    @PostMapping("addStu2")
    public JSONResult addStu2(@Valid @RequestBody StuBo stuBo,
                              BindingResult result) {
        //  验证
        if (result.hasErrors()) {
            Map<String, String> map = getErrors(result);
            return JSONResult.errorMap(map);
        }

        Stu stu = new Stu();
        BeanUtils.copyProperties(stuBo, stu); // 将一个对象中的属性拷贝到另外一个对象里面，两个对象属性必须一致
        stuService.savaStu(stu);

        return JSONResult.ok();
    }

    //    获取错误
    public Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error : errorList) {
            String field = error.getField();
            String msg = error.getDefaultMessage();
            map.put(field, msg);
        }
        return map;
    }

    //    根据ID查询用户信息 {{host}}/stu/getStuById?stuId=111
    @GetMapping("getStuById")
    public JSONResult getStuById(String stuId) {
        /*//方法一：
        Stu stu = stuService.queryById(stuId);
        return JSONResult.ok(stu);*/

        // 方法二：通过自定义的SQL查询
        Stu stu1=stuService.queryByIdCustom(stuId);
        return JSONResult.ok(stu1);
    }

    // 根据条件查询结果集   {{host}}/stu/getStuByCondition?name=test&age=1
    @GetMapping("getStuByCondition")
    public JSONResult getStuByCondition(String name, Integer age) {
        List<Stu> stuList = stuService.queryByCondition(name, age);
//        List<Stu> stuList = stuService.queryByCondition("lxw",33);
        return JSONResult.ok(stuList);
    }

    //分页展示
    @GetMapping("pageList")
    public JSONResult pageList(String name, Integer age, Integer page, Integer pageSize) {
        List<Stu> stuList = stuService.queryByCondition(name, age, page, pageSize);
        return JSONResult.ok(stuList);
    }

    @PostMapping("updateStu")
    public JSONResult updateStu() {

        stu.setId(1); // 修改id为1的数据
        stu.setName("lxw-update");
        stu.setAge(1234);
        stu.setEmail("1234@qq.com");

        Integer num = stuService.updateStu(stu);
        String msg = (num > 0) ? "更改成功" : "更改失败";
        log.info("修改数量：{}{}", num, msg);
        return JSONResult.ok(msg);
    }

    /**
     * 根据 updateByExample 修改
     * {{host}}/stu/updateStu2?name=QQ
     * postBody:
     * {
     * "name":"qaz",
     * "age":22,
     * "email":"11234@qq.com"
     * }
     *
     * @param name
     * @param age
     * @param email
     * @param postStu
     * @return
     */
    @PostMapping("updateStu2")
    public JSONResult updateStu2(String name, Integer age, String email, @RequestBody Stu postStu
    ) {
        log.info("查询的名字：{}", name);
        log.info("查询的年龄：{}", age);

        //修改后的值
//        stu.setName("test1");
//        stu.setAge(11);
//        stu.setEmail("1@163.com");
//        log.info(map.toString());
//        UpStu upStu=new Stu();

//        Stu stu = new Stu();
//        log.info("修改前："+stu.getName());
//        log.info("postStu修改前："+postStu.getName());
//        BeanUtils.copyProperties(postStu,stu);
//
//        log.info("修改后："+stu.getName());
//        log.info("postStu修改后："+postStu.getName());
//        stu.setName(stu.getName());
//        stu.setAge(stu.getAge());
//        stu.setEmail(stu.getEmail());

        Integer num = stuService.updateStu(postStu, name, age, email);
        String msg = (num > 0) ? "更改成功" : "更改失败";
        log.info("修改数量：{}， 修改结果：{}", num, msg);
        return JSONResult.ok(msg);
    }

    /**
     * 删除用户
     * {{host}}/stu/delStu?stuId=121
     *
     * @param stu
     * @return
     */
    @PostMapping("delStu")
    public JSONResult deleteStu(Stu stu, Integer stuId, String name) {

        stu.setId(stuId);
        stu.setName(name);
        log.info("删除ID：" + stuId);
        log.info("删除name：" + name);
        log.info(stu.toString()); // Stu(id=null, name=ww, age=null, email=null) / Stu(id=121, name=ww, age=null, email=null)
        Integer res = stuService.deleteStu(stu);
        String msg = (res > 0) ? "删除成功" : "删除失败";
        log.info("删除条数：{} 删除结果：{}", res, msg);
        return JSONResult.ok(msg);
    }

    @PostMapping("testTrans")
    public JSONResult testTrans() {
        stuService.testTrans();
        return JSONResult.ok();
    }


    @Autowired
    private DataSource dataSource;
    //method如果不写的话，那么get，post方式都能进入方法，但是不安全
    @RequestMapping(value = {"/say"},method = RequestMethod.GET)
    public  String say(@RequestParam(value = "id",required = false,defaultValue = "0")String id){
        return dataSource.toString();
    }
}
