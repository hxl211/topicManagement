package com.hxl.boot.mapper;

import com.hxl.boot.pojo.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 21141
* @description 针对表【teacher】的数据库操作Mapper
* @createDate 2023-07-31 18:50:49
* @Entity com.hxl.boot.pojo.Teacher
*/
public interface TeacherMapper extends BaseMapper<Teacher> {
       Teacher selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}





