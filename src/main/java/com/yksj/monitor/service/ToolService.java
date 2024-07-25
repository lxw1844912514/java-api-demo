package com.yksj.monitor.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ToolService {
    /**
     * 查询字符串在数组中的键值
     *
     * @return
     */
    public int array_search_string(String value) {
        String[] array = {"mysql", "redis", "rabbitmq", "mongo", "elasticsearch", "node_grpc", "ttl", "nlp", "client", "asr"};
        return Arrays.asList(array).indexOf(value);
    }

    public int array_search_int(long[] array, long value) {
        return Arrays.binarySearch(array, value);
    }


    public Map<Integer, String> statuMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "created"); //已创建
        map.put(2, "restarting"); //重启中
        map.put(3, "running"); //运行中
        map.put(4, "removing"); //迁移中
        map.put(5, "paused"); //暂停
        map.put(6, "exited"); //停止
        map.put(7, "dead"); //死亡

        return map;
    }

    public Integer getStatuMapKey(String status) {
        Map<Integer, String> map = this.statuMap();
        for (Integer key : map.keySet()) {
            if (map.get(key).equalsIgnoreCase(status)) {
                return key;
            }
            //System.out.println("Key = " + key + ", Value = " + map.get(key));
        }
        return 0;
    }


}
