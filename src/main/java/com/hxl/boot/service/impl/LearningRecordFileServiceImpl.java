package com.hxl.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxl.boot.annotation.StaticEnum;
import com.hxl.boot.pojo.LearningRecordFile;
import com.hxl.boot.service.LearningRecordFileService;
import com.hxl.boot.mapper.LearningRecordFileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author 21141
 * @description 针对表【learning_record_file】的数据库操作Service实现
 * @createDate 2023-08-04 20:41:18
 */
@Service
@Slf4j
public class LearningRecordFileServiceImpl extends ServiceImpl<LearningRecordFileMapper, LearningRecordFile>
        implements LearningRecordFileService {
    @Resource
    LearningRecordFileMapper learningRecordFileMapper;

    @Override
    @Transactional
    public int saveStudentReportFile(Integer weeklyReportId, MultipartFile file ) {
        try {
            //获取文件的名字 带拓展名后缀
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            //文件路径
            String path= StaticEnum.WEEKLY_REPORT_FILE_PATH.getValString1() +weeklyReportId+suffix;

            //创建对象
            LearningRecordFile recordFile = new LearningRecordFile(null, weeklyReportId,
                    originalFilename, weeklyReportId + suffix, file.getSize(),
                    LocalDateTime.now(), path);
            //插入数据
            int i = learningRecordFileMapper.insert(recordFile);
            //保存文件
            file.transferTo(new File(path));
            return i;
        } catch (IOException e) {
            log.info(e.getLocalizedMessage());
        }
        return 0;
    }
}




