package com.hxl.boot.service;

import com.hxl.boot.pojo.LearningRecordFile;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author 21141
* @description 针对表【learning_record_file】的数据库操作Service
* @createDate 2023-08-04 20:41:18
*/
public interface LearningRecordFileService extends IService<LearningRecordFile> {
       int saveStudentReportFile(Integer weeklyReportId, MultipartFile file);
}
