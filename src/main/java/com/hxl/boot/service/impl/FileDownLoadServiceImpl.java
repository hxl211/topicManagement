package com.hxl.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hxl.boot.annotation.StateEnum;
import com.hxl.boot.exception.StateException;
import com.hxl.boot.mapper.LearningRecordFileMapper;
import com.hxl.boot.mapper.TopicReportFileMapper;
import com.hxl.boot.pojo.LearningRecordFile;
import com.hxl.boot.pojo.TopicReportFile;
import com.hxl.boot.service.FileDownloadService;
import com.hxl.boot.utils.FileMergeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class FileDownLoadServiceImpl implements FileDownloadService {
    @Resource
    LearningRecordFileMapper learningRecordFileMapper;
    @Resource
    TopicReportFileMapper topicReportFileMapper;
    @Override
    public void downLoadFile(Integer type, Integer id, HttpServletResponse response) throws IOException {

        if (type==1){
            LearningRecordFile one = learningRecordFileMapper.selectOne(new LambdaQueryWrapper<LearningRecordFile>()
                    .eq(LearningRecordFile::getWeeklyReportId, id).select(LearningRecordFile::getFilePath));

            if (one==null) throw new StateException(StateEnum.RESULT_EMPTY);
            if (one.getFilePath()!=null) {

                FileMergeUtil.downLoadFile(one.getFilePath(),response);
            }
        }

        else if(type==2){
            TopicReportFile one = topicReportFileMapper.selectOne(new LambdaQueryWrapper<TopicReportFile>()
                    .eq(TopicReportFile::getTopicId, id).select(TopicReportFile::getFilePath));

            if (one==null) throw new StateException(StateEnum.RESULT_EMPTY);
            if (one.getFilePath()!=null) {

                FileMergeUtil.downLoadFile(one.getFilePath(),response);
            }
        }
    }
}
