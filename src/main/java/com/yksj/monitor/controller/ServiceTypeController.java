package com.yksj.monitor.controller;

import com.yksj.monitor.entity.ServiceType;
import com.yksj.monitor.mapper.ServiceTypeMapper;
import com.yksj.monitor.utils.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Slf4j
@RestController
@RequestMapping("/service")
public class ServiceTypeController {
    @Resource
    ServiceTypeMapper serviceTypeMapper;
    private Date currentTime = new Date();

    //服务类型列表概览
    @GetMapping("list")
    public JSONResult getTypes() {
        List<ServiceType> serviceTypeList = serviceTypeMapper.types();
        return JSONResult.ok(serviceTypeList);
    }

    //添加服务类型
    @PostMapping("addServiceType")
    public JSONResult addServiceType(@RequestBody ServiceType serviceType) {
        serviceType.setCreated_at(currentTime);
        serviceType.setUpdated_at(currentTime);
        Integer res = serviceTypeMapper.save(serviceType);
        String msg = (res > 0) ? "添加成功" : "添加失败";
        return JSONResult.ok(msg);
    }

    //修改服务类型
    @PostMapping("updateSerType")
    public JSONResult updateSerType(@RequestBody ServiceType serviceType) {
        serviceType.setUpdated_at(currentTime);
        Integer res = serviceTypeMapper.updateById(serviceType);
        log.info(serviceType.toString());
        String msg = (res > 0) ? "更新成功" : "更新失败";
        return JSONResult.ok(msg);
    }

    //根据ID删除服务类型
    @GetMapping("/{id}")
    public JSONResult deleteType(@PathVariable("id") long id) {
        Integer res = serviceTypeMapper.deleteById(id);
        String msg = (res > 0) ? "删除成功" : "删除失败";
        return JSONResult.ok(msg);
    }
}
