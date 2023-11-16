package com.hxl.boot.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxl.boot.annotation.StateEnum;
import com.hxl.boot.annotation.StaticEnum;
import com.hxl.boot.exception.StateException;
import com.hxl.boot.mapper.*;
import com.hxl.boot.pojo.*;
import com.hxl.boot.service.WeeklyReportService;
import com.hxl.boot.utils.AjaxR;
import com.hxl.boot.vo.SearchWeeklyReportInfoDTO;
import com.hxl.boot.vo.WeekReportInfo;
import com.hxl.boot.vo.WeeklyReportAndStudentDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hxl
 * @description 针对表【weekly_report】的数据库操作Service实现
 * @createDate 2023-08-23 18:08:35
 */
@Service
public class WeeklyReportServiceImpl extends ServiceImpl<WeeklyReportMapper, WeeklyReport>
        implements WeeklyReportService {
    @Resource
    WeeklyReportMapper weeklyReportMapper;
    @Resource
    LearningRecordFileMapper learningRecordFileMapper;
    @Resource
    CacheFileMapper cacheFileMapper;
    @Resource
    StudentMapper studentMapper;
   @Resource
   TopicReportFileMapper topicReportFileMapper;

    @Override
    @Transactional
    public Map<String, Integer> saveStudentReport(Integer userId, WeeklyReport weeklyReport, MultipartFile oneFile) {
        Map<String, Integer> idMap = new HashMap<>();
        weeklyReport.setStudentId(userId);
        weeklyReport.setCreateTime(LocalDateTime.now());
        weeklyReport.setUpdateTime(LocalDateTime.now());
        weeklyReport.setAssociatedWeekNum(new DateTime(weeklyReport.getAssociatedWeek()).weekOfYear());
        //查询是否选课

        Student student = studentMapper.selectOne(new LambdaQueryWrapper<Student>()
                .select(Student::getTopicId, Student::getStudentId).eq(Student::getStudentId, userId));
        if (student == null) {
            throw new StateException(StateEnum.NOT_ADD_TOPIC);
        }
        //查询是否重复添加，根据关联时间 ,一周内只有一个 ，不能重复添加
        boolean exists =
                weeklyReportMapper.exists(new LambdaQueryWrapper<WeeklyReport>()
                        .eq(WeeklyReport::getTopicId, student.getTopicId())
                        .eq(WeeklyReport::getStudentId, student.getStudentId())
                        .eq(WeeklyReport::getAssociatedWeekNum, weeklyReport.getAssociatedWeekNum()));

        if (exists) throw new StateException(StateEnum.AGAIN_ADD);
        weeklyReport.setTopicId(student.getTopicId());
        //保存周志
        int i = weeklyReportMapper.insert(weeklyReport);
        if (i != 1) {
            throw new StateException(StateEnum.UNKNOWN_ERROR);
        }

        //返回数据
        idMap.put("topicId", student.getTopicId());
        idMap.put("weeklyReportId", weeklyReport.getWeeklyReportId());
        //是否有附件
        if (oneFile == null) {
            return idMap;
        }
        //是否上传附件
        String originalFileName = oneFile.getOriginalFilename();//获取文件的名字 带拓展名
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
        String codeName = weeklyReport.getWeeklyReportId() + suffix;

        String storePath=StaticEnum.WEEKLY_REPORT_FILE_PATH.getValString1() +userId+"\\"+idMap.get("topicId")+"\\"+idMap.get("weeklyReportId");

        new File(storePath).mkdirs();
        //路径
        String path = storePath+"\\"+ codeName;
        //保存文件信息
        int iFile = learningRecordFileMapper.insert(new LearningRecordFile(null,
                weeklyReport.getWeeklyReportId(),
                originalFileName,
                codeName,
                oneFile.getSize(),
                LocalDateTime.now(), path));

        if (iFile != 1) {
            throw new StateException(StateEnum.UNKNOWN_ERROR);
        }

        //保存文件
        try {
            oneFile.transferTo(new File(path));
            return idMap;
        } catch (IOException e) {
            log.error("io错误",e);
            throw new StateException(StateEnum.UNKNOWN_ERROR);
        }

    }

    @Override
    @Transactional
    public AjaxR upload(WeeklyReport weeklyReport, Integer userId, CacheFile cacheFile) {
        weeklyReport.setStudentId(userId);
        weeklyReport.setCreateTime(LocalDateTime.now());
        weeklyReport.setUpdateTime(LocalDateTime.now());
        //获取周一的时间，将周二时间相减
        weeklyReport.setAssociatedWeek(weeklyReport.getAssociatedWeek().minusDays(1));
        //保存周志
        Map<String, Integer> idMap = saveStudentReport(userId, weeklyReport, null);

        cacheFile.setWeeklyReportId(idMap.get("weeklyReportId"));
        cacheFile.setTopicId(idMap.get("topicId"));
        //设置用户id
        cacheFile.setUserId(userId);
        //保存缓存文件信息
        int i = cacheFileMapper.insert(cacheFile);
        if (i != 1) {
            throw new StateException(StateEnum.UNKNOWN_ERROR);
        }
        return AjaxR.success();
    }

    @Override
    public AjaxR getStudentTaskInfo(Integer userId) {

        WeekReportInfo info = new WeekReportInfo();
        //获取周报
        WeeklyReport weeklyReport = weeklyReportMapper.selectOne(new LambdaQueryWrapper<WeeklyReport>()
                .eq(WeeklyReport::getAssociatedWeekNum, DateTime.now().weekOfYear()));
        //为空则设为0
        if (weeklyReport == null) {
            info.setWeeklyReport(0);

        }
        else if (weeklyReport.getSign() != null) {
            info.setSign(1);
        } else {
            info.setWeeklyReport(1);
        }
        //获取学生的选修的课题id 不能通过周报对象获取课题id，因为 有可能学生未发布任何课题（就会有空指针异常），但是学生发布了课题报告
        Student student = studentMapper.selectOne(new LambdaQueryWrapper<Student>().eq(Student::getStudentId, userId));
        if (student!=null&&student.getTopicId()!=null){
            boolean exists=topicReportFileMapper.exists(new LambdaQueryWrapper<TopicReportFile>()
                    .eq(TopicReportFile::getStudentId,userId).eq(TopicReportFile::getTopicId,student.getTopicId()));

            if (exists) {
                info.setIsFinalAcademicRecordFile(1);
            }
        }

        return AjaxR.success(info);
    }

    @Override
    public AjaxR getTopicStudent(Integer userId, SearchWeeklyReportInfoDTO dto) {
        //第一次 没有选择时， 默认展示本周的周报
        if (dto.getStart()==null||dto.getEnd()==null) {
                dto.setAssociatedWeekNum(DateTime.now().weekOfYear());
        }
        Page<WeeklyReportAndStudentDTO> data = weeklyReportMapper.getTopicStudentsPage(new Page<>(dto.getPage(), StaticEnum.PAGE_SIZE.getValInt()), dto);

        return AjaxR.success(data);
    }


}




