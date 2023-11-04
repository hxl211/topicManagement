package com.hxl.boot.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchWeeklyReportInfoDTO {
    private Integer topicId;
    private Integer page;
    private String name;
    private Integer associatedWeekNum;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME,pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime start;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME,pattern = "yyyy/MM/dd HH:mm:ss")
    private  LocalDateTime end;
}
