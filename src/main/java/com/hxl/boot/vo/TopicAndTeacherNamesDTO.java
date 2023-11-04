package com.hxl.boot.vo;

import com.hxl.boot.pojo.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicAndTeacherNamesDTO {
    //课题
    private Topic topic;
    //该课题的授课老师们
    private List<String> teacherNames;
    private String  publisher;
}
