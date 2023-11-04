package com.hxl.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hxl.boot.annotation.StateEnum;
import com.hxl.boot.annotation.StaticEnum;
import com.hxl.boot.exception.StateException;
import com.hxl.boot.mapper.StudentMapper;
import com.hxl.boot.mapper.TeachMapper;
import com.hxl.boot.pojo.Student;
import com.hxl.boot.pojo.Teach;
import com.hxl.boot.pojo.Topic;
import com.hxl.boot.service.TeachService;
import com.hxl.boot.service.TopicService;
import com.hxl.boot.mapper.TopicMapper;
import com.hxl.boot.utils.AjaxR;
import com.hxl.boot.vo.TopicAndTeacherNamesDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author 21141
 * @description 针对表【topic】的数据库操作Service实现
 * @createDate 2023-08-06 22:01:28
 */
@Service

public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic>
        implements TopicService {
    @Resource
    TopicMapper topicMapper;
    @Resource
    TeachMapper teachMapper;
    @Resource
    TeachService teachService;
    @Resource
    StudentMapper studentMapper;

    @Override
    public AjaxR getTopicNames(Integer teacherId) {
        List<Topic> names = topicMapper.getTopicNames(teacherId);

        if (names.size()==0) {
            throw new StateException(StateEnum.RESULT_EMPTY);
        }

        return AjaxR.success(names);
    }

    @Override
    public AjaxR getTopicAllByTeacherId(Integer teacherId, Integer page) {

        if (page == null) {
            throw new StateException(StateEnum.UNKNOWN_ERROR);
        }
        //获取分页对象
        Page<TopicAndTeacherNamesDTO> pageInfo = new Page<>(page, StaticEnum.PAGE_SIZE.getValInt());
        //查询数据
        Page<TopicAndTeacherNamesDTO> list = topicMapper.getTopicAllByTeacherId(teacherId, pageInfo);
        return AjaxR.success(list);
    }

    @Override
    public AjaxR getTopicAllByPublisherId(Integer teacherId, Integer page) {
        if (page == null) {
            throw new StateException(StateEnum.UNKNOWN_ERROR);
        }
        //获取分页对象
        Page<TopicAndTeacherNamesDTO> pageInfo = new Page<>(page, StaticEnum.PAGE_SIZE.getValInt());
        //查询数据
        Page<TopicAndTeacherNamesDTO> list = topicMapper.getTopicAllByPublisherId(teacherId, pageInfo);

        return AjaxR.success(list);
    }

    @Override
    @Transactional
    public AjaxR saveTopic(Integer teacherId, Topic topicInfo) {
        //判断课题名判断是否存在
        Long isOne = topicMapper.selectCount(new LambdaQueryWrapper<Topic>().eq(Topic::getTopicName, topicInfo.getTopicName()));
        if (isOne == null || isOne == 1) {
            throw new StateException(StateEnum.RESULT_NOT_EMPTY);
        }

        //设置发布者，创建时间和修改时间
        topicInfo.setTeacherId(teacherId);
        topicInfo.setCreateTime(LocalDateTime.now());
        topicInfo.setUpdateTime(LocalDateTime.now());
        int i = topicMapper.insert(topicInfo);
        if (i != 1) throw new StateException(StateEnum.UNKNOWN_ERROR);

        //保存授课表
        Teach teach = new Teach(teacherId, topicInfo.getTopicId());
        boolean save = teachService.save(teach);
        if (!save) throw new StateException(StateEnum.UNKNOWN_ERROR);

        //保存该课题
        return AjaxR.success();
    }

    @Override
    @Transactional
    public AjaxR deleteTopic(Integer topicId) {
        //查询是否存在
        boolean exists = topicMapper.exists(new LambdaQueryWrapper<Topic>().eq(Topic::getTopicId, topicId));
        //不存在返回
        if (!exists) {
            throw new StateException(StateEnum.RESULT_EMPTY);
        }

        int i = topicMapper.deleteById(topicId);
        i += teachMapper.delete(new LambdaQueryWrapper<Teach>().eq(Teach::getTopicId, topicId));
        i+=studentMapper.clearDeletedTopic(topicId);
        //i理论最低值为2(课题跟发布者) 小于2说明错误,回滚
        if (i <2) {
            throw new StateException(StateEnum.UNKNOWN_ERROR);
        }
        return AjaxR.success();
    }
}




