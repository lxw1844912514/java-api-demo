package com.yksj.monitor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yksj.monitor.entity.Record;
import com.yksj.monitor.entity.ServiceType;
import com.yksj.monitor.mapper.RecordMapper;
import com.yksj.monitor.mapper.ServiceTypeMapper;
import com.yksj.monitor.service.ToolService;
import com.yksj.monitor.utils.JSONResult;
import com.yksj.monitor.vo.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.annotation.Resource;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/record")
public class RecordController {
    private static final Logger log = LoggerFactory.getLogger(RecordController.class);
    @Resource
    RecordMapper recordMapper;
    @Resource
    ServiceTypeMapper serviceTypeMapper;
    @Autowired
    private ToolService toolService;


    //    添加日志记录
    @PostMapping("addRecord")
    public JSONResult addRecord(@RequestBody String args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Date currentTime = new Date();
        Record record = new Record();
        log.info("接受参数：" + args);

        // 包含&符号的是docker服务，不包含的是curl或supervisorctl等服务
        if (args.contains("&")) { //docker服务
            String[] jsonInput = args.split("&");
         /*for (String item: jsonInput){
            System.out.println(item);
        }
        System.out.println(Arrays.toString(jsonInput));*/
            Map<String, String> map;
            // 将JSON字符串转换为Map对象
            map = mapper.readValue(jsonInput[0], new TypeReference<Map<String, String>>() {
            });
            Map<String, String> dockerStatMap = mapper.readValue(jsonInput[1], new TypeReference<Map<String, String>>() {
            });
            Map<String, String> restartCountMap = mapper.readValue(jsonInput[2], new TypeReference<Map<String, String>>() {
            });

            // 数据整理
            int type_id = toolService.array_search_string(map.get("Names")) + 1;
            record.setTypeId(type_id);
            record.setContainerId(map.get("ID"));
            record.setImage(map.get("Image"));
            record.setCommand(map.get("Command").trim());
            record.setImage(map.get("Image"));
            record.setCreated(String.format(map.get("CreatedAt"), "YYYY-MM-dd HH:mm:ss").substring(0, 19));
            record.setStatus(map.get("Status"));
            record.setState(map.get("State"));
            record.setPorts(map.get("Ports"));
            record.setNames(map.get("Names"));

            record.setBlockInputOut(dockerStatMap.get("BlockIO"));
            record.setCpu(dockerStatMap.get("CPUPerc"));
            record.setMemPerc(dockerStatMap.get("MemPerc"));
            record.setMemUsage(dockerStatMap.get("MemUsage"));
            record.setNetInputOut(dockerStatMap.get("NetIO"));
            record.setPids(dockerStatMap.get("PIDs"));
            record.setRestartCount(restartCountMap.get("restartCount"));
        } else if (args.contains("#")) { // 客户端服务器，管理端supervisorctl
            String[] curlInput = args.split("#");
            Map<String, String> superMap = mapper.readValue(curlInput[0], new TypeReference<Map<String, String>>() {
            });
            Map<String, String> typeMap = mapper.readValue(curlInput[1], new TypeReference<Map<String, String>>() {
            });

            //整理数据
            int type_id = toolService.array_search_string(typeMap.get("type")) + 1;
            record.setTypeId(type_id);
            record.setNames(superMap.get("name"));
            record.setPids(superMap.get("pid"));
            record.setState(superMap.get("status"));
            record.setStatus(superMap.get("time"));
        } else { // tts服务,NLP服务端/NLP客户端
            Map<String, String> curlMap = mapper.readValue(args, new TypeReference<Map<String, String>>() {
            });

            //整理数据
            int type_id = toolService.array_search_string(curlMap.get("name")) + 1;
            record.setTypeId(type_id);
            record.setNames(curlMap.get("name"));
            record.setHttpCode(curlMap.get("http_code"));
            record.setTimeTotal(curlMap.get("time_total"));
            record.setState(curlMap.get("http_code"));
        }

        record.setCreatedAt(currentTime);
        record.setUpdatedAt(currentTime);
        log.info("入表参数：" + record);

        // 如果运行状态停止，则同步更新服务类型表的状态
        if (!Objects.equals(record.getState(), "running") || !Objects.equals(record.getState(), "RUNNING")|| !Objects.equals(record.getState(), "200")) {
            ServiceType serviceType = new ServiceType();
            //获取容器非正常运行状态对应的key
            Integer state = toolService.getStatuMapKey(record.getState());
            serviceType.setUpdated_at(currentTime);
            serviceType.setState(state);
            serviceType.setId(record.getTypeId());
            //log.info("更新：" + serviceType);
            //log.info("state:" + state + ",typeId:" + record.getType_id());
            serviceTypeMapper.updateStateById(serviceType);
        }


        Integer res = recordMapper.save(record);
        String msg = (res > 0) ? "添加成功" : "添加失败";
        return JSONResult.ok(msg);
    }


    //获取全部日志列表
    @GetMapping("/all")
    public JSONResult getRecords() {
        List<Record> recordList = recordMapper.records();
        return JSONResult.ok(recordList);
    }

    //分类-分页-获取日志列表
    // {{host}}/record/unit?pageSize=10&pageNum=1&label=today&type_id=1
    @GetMapping("/unit")
    public Page<Record> recordPage(@RequestParam(defaultValue = "1") Integer typeId,
                                   @RequestParam(defaultValue = "today") String tag,
                                   @RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "10") Integer pageSize
    ) {

        LocalDateTime createTime;
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        if (Objects.equals(tag, "today")) {
            // 获取当天凌晨的时间
            createTime = now.withHour(0).withMinute(0).withSecond(0).withNano(0);
        } else {
            // 获取7天前的时间
            createTime = now.minusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
        }
        log.info("查询起始时间: {}", createTime);

        int offset = (pageNum - 1) * pageSize;
        List<Record> recordData = recordMapper.findByPage(typeId, createTime, offset, pageSize);
        Page<Record> page = new Page<>();
        page.setData(recordData);

        log.info(recordData.toString());

        int total = recordMapper.countRecord(typeId, createTime);
        page.setTotal(total);
        page.setPageSize(pageSize);
        page.setPageNum(pageNum);
        return page;
    }


    //测试用
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Shanghai"));

    @GetMapping("/time")
    public String getTime() {

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 获取当天凌晨的时间
        LocalDateTime startOfDay = now.withHour(0).withMinute(0).withSecond(0).withNano(0);
        // 获取7天前的时间
        LocalDateTime sevenDaysAgo = now.minusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
        // 打印结果
        System.out.println("当天凌晨: " + startOfDay);
        System.out.println("7天前: " + sevenDaysAgo);

        Instant createdAt = Instant.ofEpochSecond(1720170048); // 获取数据库中的created_at时间戳
        return formatter.format(createdAt);
    }

    //容器状态映射关系
    @GetMapping("getStatusMap")
    public JSONResult getStatus() {
        return JSONResult.ok(toolService.statuMap());
    }


    @GetMapping("test")
    public Integer test() {
        Integer res = toolService.getStatuMapKey("running");
        System.out.println("controller:" + res);
        return res;
    }
}
