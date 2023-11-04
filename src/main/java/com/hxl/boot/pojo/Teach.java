package com.hxl.boot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName teach
 */
@TableName(value ="teach")
@Data
public class Teach implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 教师id（外键）
     */
    private Integer teacherId;

    /**
     * 课程id（外键）
     */
    private Integer topicId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Teach() {
    }

    public Teach(Integer teacherId, Integer topicId) {
        this.teacherId = teacherId;
        this.topicId = topicId;
    }
}