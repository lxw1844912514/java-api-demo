package com.yksj.monitor.controller;

import com.yksj.monitor.entity.Record;
import com.yksj.monitor.mapper.RecordMapper;
import com.yksj.monitor.utils.JSONResult;
import com.yksj.monitor.vo.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.annotation.Resource;
import javax.sound.midi.MidiDevice;
import javax.sound.sampled.Line;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/record")
public class RecordController {
    private static final Logger log = LoggerFactory.getLogger(RecordController.class);
    private final Date currentTime = new Date();
    @Resource
    RecordMapper recordMapper;

    //    添加日志记录
    @PostMapping("addRecord")
    public JSONResult addRecord(@RequestBody Record record) {
        record.setCreated_at(currentTime);
        record.setUpdated_at(currentTime);
        log.info(record.toString());
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

}
