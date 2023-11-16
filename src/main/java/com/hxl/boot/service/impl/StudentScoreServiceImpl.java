package com.hxl.boot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxl.boot.annotation.StateEnum;
import com.hxl.boot.annotation.StaticEnum;
import com.hxl.boot.exception.StateException;
import com.hxl.boot.mapper.StudentMapper;
import com.hxl.boot.pojo.Student;
import com.hxl.boot.pojo.StudentScore;
import com.hxl.boot.service.StudentScoreService;
import com.hxl.boot.mapper.StudentScoreMapper;
import com.hxl.boot.utils.AjaxR;
import com.hxl.boot.vo.StudentScoreDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author hxl
* @description 针对表【student_score】的数据库操作Service实现
* @createDate 2023-09-06 16:13:40
*/
@Service
public class StudentScoreServiceImpl extends ServiceImpl<StudentScoreMapper, StudentScore>
    implements StudentScoreService{
    @Resource
    StudentScoreMapper studentScoreMapper;
    @Resource
    StudentMapper studentMapper;
    @Override
    public List<StudentScoreDTO> getStudentScoreInfoById(Integer studentId) {
        if (studentId==null) {
            return  null;
        }
           return studentScoreMapper.getScoreInfoAndTopicInfo(studentId);
    }

    @Override
    public Page<StudentScoreDTO> getStudentAllScoreByTopicId(Integer page, Integer selectTopicId) {

        Page<StudentScoreDTO> list = studentScoreMapper
                .getStudentAllScoreByTopicId(new Page<>(page, StaticEnum.PAGE_SIZE.getValInt()), selectTopicId);
        return list;
    }

    @Override
    public AjaxR saveStudentScore(StudentScore info) {

        //判断对象的字段是否有空字段
        boolean empty = BeanUtil.hasNullField(info,"remark","studentScoreId","finalAcademicRecordFileId");
        if (empty) throw new StateException(StateEnum.PARAMETER_ERROR);

        //是否已经存在
        boolean exists = studentScoreMapper.exists(new LambdaQueryWrapper<StudentScore>()
                .eq(StudentScore::getStudentId, info.getStudentId())
                .eq(StudentScore::getTopicId, info.getTopicId()));
        if (exists) throw new StateException(StateEnum.AGAIN_ADD);

        //是否存在该学生
        boolean exists1 = studentMapper.exists(new LambdaQueryWrapper<Student>()
                .eq(Student::getStudentId, info.getStudentId()).eq(Student::getTopicId, info.getTopicId()));
        if (!exists1) throw new StateException(StateEnum.PARAMETER_ERROR);

        //判断成绩是否合法
        Integer regularGrade = info.getRegularGrade();
        Integer finalGrade = info.getFinalGrade();
        Integer totalMark = info.getTotalMark();
        if (regularGrade>100||regularGrade<=0||finalGrade<=0||totalMark<=0||finalGrade>100||totalMark>100) {
                throw new StateException(StateEnum.PARAMETER_ERROR);
        }
        //大概对平时成绩占比 判断
        if (regularGrade/totalMark>=1&&regularGrade/totalMark<0) {
            throw new StateException(StateEnum.PARAMETER_ERROR);
        }
        //插入
        int i = studentScoreMapper.insert(info);
        if (i!=1) {
            throw new StateException(StateEnum.UNKNOWN_ERROR);
        }
        return AjaxR.success();
    }
}




