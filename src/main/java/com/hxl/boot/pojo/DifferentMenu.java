package com.hxl.boot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName different_menu
 */
@TableName(value ="different_menu")
@Data
public class DifferentMenu implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 身份
     */
    private String identity;

    /**
     * 菜单数据
     */
    private String menu;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}