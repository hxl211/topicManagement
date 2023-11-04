package com.hxl.boot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName topic
 */
@TableName(value ="topic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class    Topic implements Serializable {
    /**
     * 课题id
     */
    @TableId(type = IdType.AUTO)
    private Integer topicId;

    /**
     * 课题名字
     */
    private String topicName;

    /**
     * 课题介绍
     */
        private String topicIntroduce;

    /**
     * 课题学分
     */
    private Integer topicCredit;

    /**
     * 课题学时
     */
    private Integer topicPeriod;

    /**
     * 课题人数
     */
    private Integer memberNum;

    /**
     * 课题最大人数
     */
    private Integer topicMaxPeople;

    /**
     * 发布者id （外键）
     */
    private Integer teacherId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern ="yyyy/MM/dd HH:mm:ss",timezone = "GMT+8")
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME,pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GMT+8")
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 平时/总评 的成绩占比 为小数(0.5表示平时成绩占50%)
     */
    private Double gradeRatio;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}