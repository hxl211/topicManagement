package com.hxl.boot.vo;

import com.hxl.boot.pojo.Student;
import com.hxl.boot.pojo.WeeklyReport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class WeeklyReportAndStudentDTO {
    private WeeklyReportDTO weeklyReportDTO;
    private Student student;


}
