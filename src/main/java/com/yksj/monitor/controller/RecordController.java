package com.yksj.monitor.controller;

import com.yksj.monitor.entity.Record;
import com.yksj.monitor.mapper.RecordMapper;
import com.yksj.monitor.vo.Page;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import javax.annotation.Resource;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {
    @Resource
    RecordMapper recordMapper;

    //    添加日志记录
    @PostMapping
    public String addRecord(@RequestBody Record record) {
        record.insert();
        recordMapper.save(record);
        return "success";
    }

    //    获取全部日志列表
    @GetMapping("/all")
    public List<Record> getRecords(Record record) {
//        record.select();
        return recordMapper.records();
    }

    @GetMapping("/unit")
    public Page<Record> recordPage(@RequestParam(defaultValue = "1") long type_id,
                                   @RequestParam(defaultValue = "today") String label,
                                   @RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        int offset = (pageNum - 1) * pageSize;
        List<Record> recordData = recordMapper.findByPage(offset, pageSize);
        Page<Record> page = new Page<>();
        page.setData(recordData);

        int total = recordMapper.countRecord();
        page.setTotal(total);
        page.setPageSize(pageSize);
        page.setPageNum(pageNum);
        return page;
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Shanghai"));

    @GetMapping("/time")
    public String getTime() {
        Instant createdAt = Instant.ofEpochSecond(1720170048); // 获取数据库中的created_at时间戳
        return formatter.format(createdAt);
    }

}
