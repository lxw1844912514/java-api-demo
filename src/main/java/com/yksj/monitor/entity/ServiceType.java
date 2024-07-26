package com.yksj.monitor.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javafx.util.converter.LocalDateTimeStringConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class ServiceType {
    private int id;
    private String name;
    private String listenName;
    private int state;
    private int isDocker;
    private String ip;
    private Date createdAt;
    private Date updatedAt;

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime updated_at;

}
