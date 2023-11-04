package com.hxl.boot.service;

import com.hxl.boot.pojo.Topic;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxl.boot.utils.AjaxR;

/**
* @author 21141
* @description 针对表【topic】的数据库操作Service
* @createDate 2023-08-06 22:01:28
*/
public interface TopicService extends IService<Topic> {

    AjaxR saveTopic(Integer teacherId, Topic topicInfo);
    AjaxR getTopicAllByPublisherId(Integer teacherId,Integer page);
    AjaxR getTopicAllByTeacherId(Integer teacherId,Integer page);
    AjaxR getTopicNames(Integer teacherId);
    AjaxR deleteTopic(Integer id);
}
