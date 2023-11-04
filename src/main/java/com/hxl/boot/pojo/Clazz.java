package com.hxl.boot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 班级表
 * @TableName class
 */
@TableName(value ="class")
@Data
public class Clazz implements Serializable {
    /**
     * 班级id
     */
    @TableId(type = IdType.AUTO)
    private Integer classId;

    /**
     * 班级姓名
     */
    private String className;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}