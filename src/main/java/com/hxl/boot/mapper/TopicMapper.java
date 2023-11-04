package com.hxl.boot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxl.boot.pojo.Topic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxl.boot.vo.TopicAndTeacherNamesDTO;
import com.hxl.boot.vo.TopicDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 21141
 * @description 针对表【topic】的数据库操作Mapper
 * @createDate 2023-08-06 22:01:28
 * @Entity com.hxl.boot.pojo.Topic
 */
public interface TopicMapper extends BaseMapper<Topic> {
    Page<TopicAndTeacherNamesDTO> getTopicAllByPublisherId(Integer teacherId,@Param("page") Page<TopicAndTeacherNamesDTO> page);
    Page<TopicAndTeacherNamesDTO>  getTopicAllByTeacherId(Integer teacherId,@Param("page") Page<TopicAndTeacherNamesDTO> page);
    List<TopicAndTeacherNamesDTO> getTopicAllByTopicId(@Param("topicIds") List<Integer> topicIds);

     List<Topic> getTopicNames(Integer teacherId);

    TopicDTO getTopicIdAndName(Integer topicId);

    TopicAndTeacherNamesDTO  getTopicAndTeacherNamesByTopicId(Integer topicId);
}




