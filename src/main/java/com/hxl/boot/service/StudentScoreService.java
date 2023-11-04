package com.hxl.boot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxl.boot.pojo.StudentScore;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxl.boot.utils.AjaxR;
import com.hxl.boot.vo.ScoreInfoDTO;
import com.hxl.boot.vo.StudentScoreDTO;

import java.util.List;

/**
* @author hxl
* @description 针对表【student_score】的数据库操作Service
* @createDate 2023-09-06 16:13:40
*/
public interface StudentScoreService extends IService<StudentScore> {

  List<StudentScoreDTO> getStudentScoreInfoById(Integer studentId);

  Page<StudentScoreDTO> getStudentAllScoreByTopicId(Integer page, Integer selectTopicId);

  AjaxR saveStudentScore(StudentScore info);
}
