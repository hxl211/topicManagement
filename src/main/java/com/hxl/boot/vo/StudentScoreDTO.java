package com.hxl.boot.vo;

import com.hxl.boot.pojo.Student;
import com.hxl.boot.pojo.StudentScore;
import com.hxl.boot.pojo.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentScoreDTO {
    private StudentScore studentScore;
    private Topic topic;
    private Student student;

}
