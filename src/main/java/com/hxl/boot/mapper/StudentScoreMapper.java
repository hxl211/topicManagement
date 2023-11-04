package com.hxl.boot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxl.boot.pojo.StudentScore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxl.boot.vo.StudentScoreDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author hxl
* @description 针对表【student_score】的数据库操作Mapper
* @createDate 2023-09-06 16:13:40
* @Entity com.hxl.boot.pojo.StudentScore
*/
public interface StudentScoreMapper extends BaseMapper<StudentScore> {

   List<StudentScoreDTO> getScoreInfoAndTopicInfo(int studentId);

   Page<StudentScoreDTO> getStudentAllScoreByTopicId(@Param("page") Page<StudentScoreDTO> page,@Param("topicId") Integer topicId);
}




