package com.yksj.monitor.controller;

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
    private final Date currentTime = new Date();
    @Resource
    RecordMapper recordMapper;
    @Resource
    ServiceTypeMapper serviceTypeMapper;

    @Autowired
    private ToolService toolService;


    //    添加日志记录
    @PostMapping("addRecord")
    public JSONResult addRecord(@RequestBody String args) {
        Record record = new Record();
        String[] jsonInput = args.split("&");
        log.info("接受参数：" + args);

//         for (String item: jsonInput){
//            System.out.println(item);
//        }
//        System.out.println(Arrays.toString(jsonInput));

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = null;
        try {
            // 将JSON字符串转换为Map对象
            map = mapper.readValue(jsonInput[0], new TypeReference<Map<String, String>>() {
            });
            Map<String, String> dockerStatMap = mapper.readValue(jsonInput[1], new TypeReference<Map<String, String>>() {
            });
            Map<String, String> restartCountMap = mapper.readValue(jsonInput[2], new TypeReference<Map<String, String>>() {
            });

            // 数据整理
            int type_id = toolService.array_search_string(map.get("Names")) + 1;

//            log.info(String.valueOf(type_id));
//            log.info("Names:"+map.get("Names"));
            record.setType_id(type_id);
            record.setContainer_id(map.get("ID"));
            record.setImage(map.get("Image"));
            record.setCommand(map.get("Command").trim());
            record.setImage(map.get("Image"));
            record.setCreated(String.format(map.get("CreatedAt"), "YYYY-MM-dd HH:mm:ss").substring(0, 19));
            record.setStatus(map.get("Status"));
            record.setState(map.get("State"));
            record.setPorts(map.get("Ports"));
            record.setNames(map.get("Names"));

            record.setBlock_input_out(dockerStatMap.get("BlockIO"));
            record.setCpu(dockerStatMap.get("CPUPerc"));
            record.setMem_perc(dockerStatMap.get("MemPerc"));
            record.setMem_usage(dockerStatMap.get("MemUsage"));
            record.setNet_input_out(dockerStatMap.get("NetIO"));
            record.setPids(dockerStatMap.get("PIDs"));
            record.setRestart_count(restartCountMap.get("restartCount"));

        } catch (Exception e) {
            log.error("数据接受处理失败：" + args);
            e.printStackTrace();
        }


        record.setCreated_at(currentTime);
        record.setUpdated_at(currentTime);
        log.info("入表参数：" + record.toString());

        // 如果运行状态停止，则同步更新服务类型表的状态
        if (!Objects.equals(record.getState(), "running")) {
            ServiceType serviceType = new ServiceType();
            //获取容器非正常运行状态对应的key
            Integer state = toolService.getStatuMapKey(record.getState());
            serviceType.setUpdated_at(currentTime);
            serviceType.setState(state);
            serviceType.setId(record.getType_id());
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
