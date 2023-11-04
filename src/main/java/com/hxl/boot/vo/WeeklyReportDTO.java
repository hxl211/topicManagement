package com.hxl.boot.vo;

import com.hxl.boot.pojo.LearningRecordFile;
import com.hxl.boot.pojo.WeeklyReport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyReportDTO {
    private WeeklyReport weeklyReport;
    private Long fileExists;
}
