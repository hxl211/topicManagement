package com.hxl.boot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName topic_report_file
 */
@TableName(value ="topic_report_file")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicReportFile implements Serializable {
    /**
     * 文件id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 课题id(外键)
     */
    private Integer topicId;

    /**
     * 学生id(外键)
     */
    private Integer studentId;

    /**
     * 文件真实名字
     */
    private String fileRealName;

    /**
     * 文件存放名字
     */
    private String fileCodeName;

    /**
     * 文件大小，单位字节
     */
    private Long fileSize;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 文件存放路径
     */
    private String filePath;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}