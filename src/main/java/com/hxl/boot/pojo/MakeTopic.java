package com.hxl.boot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName make_topic
 */
@TableName(value ="make_topic")
@Data
public class MakeTopic implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer makeTopicId;

    /**
     * 课题id
     */
    private Integer topicId;

    /**
     * 教师id
     */
    private Integer teacherId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 平时/总评 的成绩占比 为小数(0.5表示平时成绩占50%)
     */
    private Double gradeRatio;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}