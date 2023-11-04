package com.hxl.boot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName cache_file
 */
@TableName(value ="cache_file")
@Data
public class CacheFile implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 关联的课题id
     */
    private Integer topicId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 分片文件的片数
     */
    private Integer chunks;

    /**
     * 关联的周报id
     */
    private Integer weeklyReportId;

    /**
     * 合并后的文件名字
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件的md5作为codeName
     */
    private String md5;

    /**
     * 文件的存放路径
     */
    private String path;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}