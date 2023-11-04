package com.hxl.boot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxl.boot.pojo.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxl.boot.vo.StudentDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 21141
* @description 针对表【student】的数据库操作Mapper
* @createDate 2023-08-01 09:29:06
* @Entity com.hxl.boot.pojo.Student
*/
public interface StudentMapper extends BaseMapper<Student> {

   Page <StudentDTO> getStudentAllAndTopicByTopicId(@Param("page") Page<StudentDTO> page, @Param("topicId") Integer topicId);
   int clearDeletedTopic(int topicId);
}




