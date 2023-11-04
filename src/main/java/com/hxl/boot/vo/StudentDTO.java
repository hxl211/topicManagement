package com.hxl.boot.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;


@Data
public class StudentDTO implements Serializable {
    /**
     * 学生id
     */
    private Long studentId;

    /**
     * 班级id
     */
    private Integer classId;

    /**
     * 课题
     */
    private TopicDTO topic;

    /**
     * 名字
     */
    private String name;

    /**
     * 性别
     */
    private Integer age;

    /**
     * 年龄
     */
    private String sex;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    //private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户头像

     */
    private String userPhoto;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 昵称
     */
    private String nickname;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}