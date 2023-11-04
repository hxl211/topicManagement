package com.hxl.boot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxl.boot.pojo.WeeklyReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxl.boot.vo.SearchWeeklyReportInfoDTO;
import com.hxl.boot.vo.WeeklyReportAndStudentDTO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
* @author hxl
* @description 针对表【weekly_report】的数据库操作Mapper
* @createDate 2023-08-30 13:02:07
* @Entity com.hxl.boot.pojo.WeeklyReport
*/
public interface WeeklyReportMapper extends BaseMapper<WeeklyReport> {
    Page<WeeklyReportAndStudentDTO> getTopicStudentsPage(@Param("page") Page<WeeklyReportAndStudentDTO> page,
                                                         SearchWeeklyReportInfoDTO dto);
}




