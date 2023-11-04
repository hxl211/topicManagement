package com.hxl.boot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName student_score
 */
@TableName(value ="student_score")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentScore implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer studentScoreId;

    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 课题id
     */
    private Integer topicId;

    /**
     * 平时成绩
     */
    private Integer regularGrade;

    /**
     * 期末成绩
     */
    private Integer finalGrade;

    /**
     * 总评成绩
     */
    private Integer totalMark;

    /**
     * 学生最终学习资料记录-总报id
     */
    private Integer finalAcademicRecordFileId;

    /**
     * 老师备注
     */
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}