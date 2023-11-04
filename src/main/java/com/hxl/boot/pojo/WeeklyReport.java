package com.hxl.boot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @TableName weekly_report
 */
@TableName(value = "weekly_report")
@Data
public class WeeklyReport implements Serializable {
    /**
     * 周报告id
     */
    @TableId(type = IdType.AUTO)
    private Integer weeklyReportId;

    /**
     * 课题id
     */
    private Integer topicId;

    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 标题名字
     */
    private String title;

    /**
     * 正文
     */
    private String content;

    /**
     * 创建时间
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy/MM/dd HH:mm:ss")
    @JsonFormat(pattern ="yyyy/MM/dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 一周的签到
     */
    private String sign;

    /**
     * 关联的周
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy/MM/dd HH:mm:ss")
    @JsonFormat(pattern ="yyyy/MM/dd ",timezone = "GMT+8")
    private LocalDateTime associatedWeek;

    /**
     * 关联的周的周数(一年的第几周)
     */
    private Integer associatedWeekNum;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}