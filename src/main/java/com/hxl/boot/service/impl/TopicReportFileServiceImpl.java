package com.hxl.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxl.boot.annotation.StateEnum;
import com.hxl.boot.exception.StateException;
import com.hxl.boot.mapper.StudentMapper;
import com.hxl.boot.pojo.Student;
import com.hxl.boot.pojo.TopicReportFile;
import com.hxl.boot.service.TopicReportFileService;
import com.hxl.boot.mapper.TopicReportFileMapper;
import com.hxl.boot.utils.AjaxR;
import com.hxl.boot.utils.FileMergeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author hxl
 * @description 针对表【topic_report_file】的数据库操作Service实现
 * @createDate 2023-09-06 14:58:53
 */
@Service
@Slf4j
public class TopicReportFileServiceImpl extends ServiceImpl<TopicReportFileMapper, TopicReportFile>
        implements TopicReportFileService {
    @Resource
    TopicReportFileMapper topicReportFileMapper;
    @Resource
    StudentMapper studentMapper;

    @Override
    @Transactional
    public AjaxR saveStudentTopicReportFile(MultipartFile file, Integer studentId) {
        //查询学生

        Student student = studentMapper.selectOne(new LambdaQueryWrapper<Student>().eq(Student::getStudentId, studentId));
        if (student == null) {
            throw new StateException(StateEnum.UNKNOWN_ERROR);
        }
        if (student.getTopicId() == null) {
            throw new StateException(StateEnum.NOT_ADD_TOPIC);
        }
        int topicId = student.getTopicId();
        //获取文件的名字 带拓展名后缀
        String originalFilename = file.getOriginalFilename();

        String basePath = FileMergeUtil.existsCacheDirectory(studentId, topicId);
        if (basePath == null) {
            throw new StateException(StateEnum.UNKNOWN_ERROR);
        }
        //文件路径
        String path = basePath + originalFilename;
        //创建对象
        TopicReportFile topicReportFile = new TopicReportFile(null, topicId,
                studentId, originalFilename, originalFilename, file.getSize(), LocalDateTime.now(), path);
        //插入数据
        int i = topicReportFileMapper.insert(topicReportFile);
        if (i != 1)
            throw new StateException(StateEnum.UNKNOWN_ERROR);

        try {
            //保存文件
            file.transferTo(new File(path));
        } catch (IOException e) {
            log.info(e.getLocalizedMessage());
        }
        return AjaxR.success();
    }
}





