package com.yksj.monitor.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javafx.util.converter.LocalDateTimeStringConverter;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class ServiceType {
    private int id;
    private String name;
    private String listen_name;
    private int state;
    private int is_docker;
    private String ip;

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created_at;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime updated_at;
    private Date updated_at;

}
