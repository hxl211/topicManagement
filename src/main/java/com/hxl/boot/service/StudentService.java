package com.hxl.boot.service;

import com.hxl.boot.pojo.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxl.boot.utils.AjaxR;

/**
* @author 21141
* @description 针对表【student】的数据库操作Service
* @createDate 2023-08-01 09:29:06
*/
public interface StudentService extends IService<Student> {

    AjaxR getStudentAllByPage(Integer page,Integer selectTopic);

    AjaxR getStudentTopic(Integer studentId);


}
