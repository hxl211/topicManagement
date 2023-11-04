package com.hxl.boot.vo;

import com.hxl.boot.pojo.WeeklyReport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeekReportInfo {
    private int sign;
    private int weeklyReport;
    private int isFinalAcademicRecordFile;

}
