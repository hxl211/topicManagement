package com.hxl.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxl.boot.annotation.StateEnum;
import com.hxl.boot.exception.StateException;
import com.hxl.boot.mapper.LearningRecordFileMapper;
import com.hxl.boot.pojo.CacheFile;
import com.hxl.boot.pojo.LearningRecordFile;
import com.hxl.boot.service.CacheFileService;
import com.hxl.boot.mapper.CacheFileMapper;
import com.hxl.boot.utils.AjaxR;
import com.hxl.boot.utils.FileMergeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
* @author hxl
* @description 针对表【cache_file】的数据库操作Service实现
* @createDate 2023-08-27 20:59:04
*/
@Service
public class CacheFileServiceImpl extends ServiceImpl<CacheFileMapper, CacheFile>
    implements CacheFileService{
    @Resource
    CacheFileMapper cacheFileMapper;
    @Resource
    LearningRecordFileMapper learningRecordFileMapper;
    @Override
    @Transactional
    public AjaxR uploadAck(Integer userId, String cacheDirectory) {
        CacheFile cacheFile = cacheFileMapper.selectOne(new LambdaQueryWrapper<CacheFile>().eq(CacheFile::getUserId, userId));
        if (cacheFile==null) {
            throw new StateException(StateEnum.UNKNOWN_ERROR);
        }
        String suffix=cacheFile.getFileName().substring(cacheFile.getFileName().lastIndexOf("."));
        //合并文件
        String path = FileMergeUtil.mergeFile(userId, cacheFile.getWeeklyReportId(),cacheFile.getTopicId() ,suffix);
        if (path==null) {
            throw new StateException(StateEnum.UNKNOWN_ERROR);
        }
        //保存文件信息
        int iFile = learningRecordFileMapper.insert(new LearningRecordFile(null,
                cacheFile.getWeeklyReportId(),
                cacheFile.getFileName(),
                cacheFile.getMd5()+suffix,
                cacheFile.getFileSize(),
                LocalDateTime.now(), path));
        if (iFile!=1) {
            throw new StateException(StateEnum.UNKNOWN_ERROR);
        }
        //删除缓存
        int i = cacheFileMapper.deleteById(cacheFile.getId());
        if (i!=1) {
            throw new StateException(StateEnum.UNKNOWN_ERROR);
        }

        return AjaxR.success();

    }
}




