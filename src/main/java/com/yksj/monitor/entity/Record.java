package com.yksj.monitor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Record extends BaseEntity {
    private int id;
    private int type_id;
    private String names;
    private String image;
    private String command;
    private String container_id;
    private String created;
    private String status;
    private String ports;
    private String cpu;
    private String mem_usage;
    private String mem_limit;
    private String mem;
    private String net_input;
    private String net_out;
    private String block_input;
    private String block_out;
    private String pids;
    private int restart_count;
    private Date created_at;
    private Date updated_at;


}
