package com.hxl.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxl.boot.annotation.StateEnum;
import com.hxl.boot.annotation.StaticEnum;
import com.hxl.boot.exception.StateException;
import com.hxl.boot.mapper.TopicMapper;
import com.hxl.boot.pojo.Student;
import com.hxl.boot.service.StudentService;
import com.hxl.boot.mapper.StudentMapper;
import com.hxl.boot.utils.AjaxR;
import com.hxl.boot.vo.StudentDTO;
import com.hxl.boot.vo.TopicAndTeacherNamesDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 21141
 * @description 针对表【student】的数据库操作Service实现
 * @createDate 2023-08-01 09:29:06
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
        implements StudentService {
    @Resource
    StudentMapper studentMapper;
    @Resource
    TopicMapper topicMapper;


    /**
     * 通过页数 跟 课题名选择展示 学生
     *
     * @param page        页数
     * @param selectTopic 课题id （-1表示未选课学生，0表示所有学生）
     * @return
     */
    @Override
    public AjaxR getStudentAllByPage(Integer page, Integer selectTopic) {
        if (selectTopic == null) throw new StateException(StateEnum.PARAMETER_ERROR);
        //是否分类
        boolean isClassify = true;
        if (page == null) page = 1;
        //为空或者为0 代表不分类
        if (selectTopic == 0) isClassify = false;

        //查询选修了 某课题id的学生分页
        Page<StudentDTO> studentPage = studentMapper.
                getStudentAllAndTopicByTopicId(new Page<>(page, StaticEnum.PAGE_SIZE.getValInt()), selectTopic);

        return AjaxR.success(studentPage);
    }

    @Override
    public AjaxR getStudentTopic(Integer studentId) {
        //查询是否选了课题
        Student student = studentMapper.selectOne(new LambdaQueryWrapper<Student>()
                .select(Student::getTopicId).eq(Student::getStudentId, studentId));
        if (student == null) {
            throw new StateException(StateEnum.NOT_ADD_TOPIC);
        }
        //获取课题信息
        TopicAndTeacherNamesDTO o = topicMapper.getTopicAndTeacherNamesByTopicId(student.getTopicId());
        return AjaxR.success(o);
    }


}




