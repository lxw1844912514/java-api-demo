package com.yksj.monitor.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javafx.util.converter.LocalDateTimeStringConverter;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ServiceType {
    private int id;
    private String name;
    private String listen_name;
    private int state;
    private int is_docker;
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created_at;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //private LocalDateTime updated_at;
    private Date updated_at;


    public ServiceType() {
        this.created_at = new Date();
        this.updated_at = new Date();
    }

    public void update() {
        this.updated_at = new Date();
    }
    public void insert(){
        this.created_at = new Date();
    }
}
