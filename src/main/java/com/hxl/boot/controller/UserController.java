package com.hxl.boot.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hxl.boot.annotation.StateEnum;
import com.hxl.boot.annotation.StaticEnum;
import com.hxl.boot.exception.StateException;
import com.hxl.boot.pojo.Teacher;
import com.hxl.boot.service.StudentService;
import com.hxl.boot.service.TeacherService;
import com.hxl.boot.utils.AjaxR;
import com.hxl.boot.utils.ThreadLocalUtil;
import com.hxl.boot.vo.StudentDTO;
import com.hxl.boot.vo.TeacherDTO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private TeacherService teacherService;
    @Resource
    private StudentService studentService;

    /**
     * 查询当前登录用户信息
     *
     * @return R
     */
    @GetMapping("")
    public AjaxR queryUser(HttpServletRequest request, HttpServletResponse response) {

        TeacherDTO teacher = null;
        StudentDTO student = null;
        //获取当前登录用户id跟身份
        Integer userId = ThreadLocalUtil.getUser().getUserId();
        String identity = ThreadLocalUtil.getUser().getIdentity();
        if (userId == null || identity == null) {
            throw new StateException(StateEnum.USER_NOT_FOUND);
        }

        if (StaticEnum.USER_IDENTITY.getValString1().equals(identity)) {
            //查询当前用户封装DTO
            teacher = BeanUtil.copyProperties(teacherService.getById(userId), TeacherDTO.class);
        } else if (StaticEnum.USER_IDENTITY.getValString2().equals(identity)) {
            //查询当前用户封装DTO
            student = BeanUtil.copyProperties(studentService.getById(userId), StudentDTO.class);
        }

        if (teacher == null && student == null) {
            throw new StateException(StateEnum.USER_NOT_FOUND);
        }

        return teacher != null ? AjaxR.success(teacher) : AjaxR.success(student);
    }


    /**
     * 根据用户id查询用户信息
     *
     * @param userId
     * @return R
     */
    @GetMapping("/{userId}")
    public AjaxR queryUser(@PathVariable Long userId) {

        TeacherDTO teacher = BeanUtil.copyProperties(teacherService.getById(userId), TeacherDTO.class);

        if (teacher == null) {
            throw new StateException(StateEnum.USER_NOT_FOUND);
        }
        return AjaxR.success(teacher);
    }

    /**
     * 根据类型进行修改
     *
     * @param type 修改的字段
     * @param info 值
     * @return R
     */
    @PutMapping("/psw")
    public AjaxR updateUserInfo(HttpServletRequest request, String type, String info) {
        //获取当前登录用户
        Integer userId = ThreadLocalUtil.getUser().getUserId();
        if (userId == null) throw new StateException(StateEnum.USER_NOT_FOUND);

        //info不合法则返回
        if (StrUtil.hasBlank(info)) throw new StateException(StateEnum.REQUIRE_NOT_NULL);

        boolean isSuccess = false;
        if ("1".equals(type)) {

            //密码正则验证
            boolean match = ReUtil.isMatch(StaticEnum.PASSWORD_REG.getValString1(), info);

            if (match == false) throw new StateException(StateEnum.PASSWORD_ILLEGAL);
            // 更新密码 和时间
            isSuccess = teacherService
                    .update(new LambdaUpdateWrapper<Teacher>().eq(Teacher::getTeacherId, userId)
                            .set(Teacher::getPassword, info)
                            .set(Teacher::getUpdateTime, Instant.now()));


        }
        if ("2".equals(type)) {
            // 更新昵称 和时间
            isSuccess = teacherService
                    .update(new LambdaUpdateWrapper<Teacher>().eq(Teacher::getTeacherId, userId)
                            .set(Teacher::getNickname, info)
                            .set(Teacher::getUpdateTime, Instant.now()));

        }
        if ("3".equals(type)) {
            // 更新昵称 和时间
            isSuccess = teacherService
                    .update(new LambdaUpdateWrapper<Teacher>().eq(Teacher::getTeacherId, userId)
                            .set(Teacher::getEmail, info)
                            .set(Teacher::getUpdateTime, Instant.now()));

        }
        //失败则错误
        if (!isSuccess) throw new StateException(StateEnum.UNKNOWN_ERROR);
        return AjaxR.success();

    }


}
