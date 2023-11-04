package com.hxl.boot.service;

import com.hxl.boot.pojo.CacheFile;
import com.hxl.boot.pojo.WeeklyReport;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxl.boot.utils.AjaxR;
import com.hxl.boot.vo.SearchWeeklyReportInfoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

/**
* @author hxl
* @description 针对表【weekly_report】的数据库操作Service
* @createDate 2023-08-23 18:08:35
*/
public interface WeeklyReportService extends IService<WeeklyReport> {

    Map<String, Integer> saveStudentReport(Integer userId, WeeklyReport weeklyReport, MultipartFile oneFile) throws IOException;


    AjaxR upload(WeeklyReport weeklyReport, Integer userId, CacheFile cacheFile);


    AjaxR getStudentTaskInfo(Integer userId);

    AjaxR getTopicStudent(Integer userId, SearchWeeklyReportInfoDTO dto);
}
