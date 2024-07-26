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
        String[] array = {"mysql", "redis", "rabbitmq", "mongo", "elasticsearch", "asr", "node_grpc", "supervisorctl_server", "ttl", "nlp_server", "nlp_client", "supervisorctl_client"};
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

        //supervisorctrl status
        map.put(8, "STOPPED");      //STOPPED：进程因为停止请求而停止，或从未启动过
        map.put(9, "STARTING");     //STARTING：过程因为启动请求，正在启动中
        map.put(10, "RUNNING");     //RUNNING：进程正常运行中
        map.put(11, "BACKOFF");     //BACKOFF：进程进入了STARTING状态，但随后退出太快而无法转移到RUNNING状态。
        map.put(12, "STOPPING");    //STOPPING：进程由于停止请求正在停止中
        map.put(13, "EXITED");      //EXITED：进程从RUNNING状态退出（可能正常，也可能异常）
        map.put(14, "FATAL");       //FATAL：进程无法成功启动
        map.put(15, "UNKNOWN");     //UNKNOWN：未知状态（考虑supervisor出错）

        map.put(16, "200");     //curl正常响应状态码
        map.put(17, "500");     //
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
