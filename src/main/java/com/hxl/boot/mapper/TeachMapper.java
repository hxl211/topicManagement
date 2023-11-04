package com.hxl.boot.mapper;

import com.hxl.boot.pojo.Teach;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxl.boot.vo.TopicAndTeacherNamesDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 21141
 * @description 针对表【teach】的数据库操作Mapper
 * @createDate 2023-08-06 21:59:51
 * @Entity com.hxl.boot.pojo.Teach
 */
public interface TeachMapper extends BaseMapper<Teach> {
            List<Integer> getTopicIdAllByTeacherId(Integer teacherId);
}




