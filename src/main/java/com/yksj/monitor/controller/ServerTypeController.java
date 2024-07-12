package com.yksj.monitor.controller;

import com.yksj.monitor.entity.ServiceType;
import com.yksj.monitor.mapper.ServiceTypeMapper;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/service")
public class ServerTypeController {
    @Resource
    ServiceTypeMapper serviceTypeMapper;

    @GetMapping
    public List<ServiceType> getTypes() {
        return serviceTypeMapper.types();
    }

    @PostMapping
    public String addServiceType(@RequestBody ServiceType serviceType) {
        serviceTypeMapper.save(serviceType);
        return "success";
    }

    @PutMapping
    public String updateSerType(@RequestBody ServiceType serviceType){
        serviceTypeMapper.updateById(serviceType);
        return "success";
    }

    @DeleteMapping("/{id}")
    public  String deleteType(@PathVariable("id") long id){
        serviceTypeMapper.deleteById(id);
        return "success";
    }
}
