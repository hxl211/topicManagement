package com.hxl.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxl.boot.pojo.Teacher;
import com.hxl.boot.service.TeacherService;
import com.hxl.boot.mapper.TeacherMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 21141
* @description 针对表【teacher】的数据库操作Service实现
* @createDate 2023-07-31 18:50:49
*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService{
    @Resource
    TeacherMapper teacherMapper;

}




