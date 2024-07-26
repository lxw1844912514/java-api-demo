package com.yksj.monitor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Record extends BaseEntity {
    private int id;
    private int typeId;
    private String names;
    private String image;
    private String command;
    private String containerId;
    private String created;
    private String status;
    private String state;
    private String ports;
    private String cpu;
    private String memUsage;
    private String memPerc;
    private String netInputOut;
    private String blockInputOut;
    private String pids;
    private String restartCount;
    private String httpCode;
    private String timeTotal;
    private Date createdAt;
    private Date updatedAt;


    public Record() {

    }
}
