package com.hxl.boot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxl.boot.annotation.StateEnum;
import com.hxl.boot.annotation.StaticEnum;
import com.hxl.boot.exception.StateException;
import com.hxl.boot.mapper.DifferentMenuMapper;
import com.hxl.boot.mapper.StudentMapper;
import com.hxl.boot.mapper.TeacherMapper;
import com.hxl.boot.pojo.DifferentMenu;
import com.hxl.boot.pojo.Student;
import com.hxl.boot.pojo.Teacher;
import com.hxl.boot.service.DifferentMenuService;
import com.hxl.boot.service.UserLoginService;
import com.hxl.boot.utils.AjaxR;
import com.hxl.boot.utils.JwtUtil;
import com.hxl.boot.vo.TeacherDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Resource
    TeacherMapper teacherMapper;
    @Resource
    StudentMapper studentMapper;
    @Resource
    DifferentMenuMapper differentMenuMapper;
    @Override
    public AjaxR login(String username, String password, String identityType) {
        if (StrUtil.hasBlank(username, password)) {
            throw new StateException(StateEnum.REQUIRE_NOT_NULL);
        }
        //身份
        Integer id = null;

        //区分类型
        if (StaticEnum.USER_IDENTITY.getValString1().equals(identityType)) {
            // select * from teacher where (username=#{username} and password=#{password}) or (email=#{username} and password=#{password})

            Teacher teacher = teacherMapper.selectOne(new QueryWrapper<Teacher>()
                    .eq("username", username).eq("password", password)
                    .or(i -> i.eq("email", username).eq("password", password)).select("teacher_id"));
            if (teacher != null) {
                id = teacher.getTeacherId();
            }

        } else if (StaticEnum.USER_IDENTITY.getValString2().equals(identityType)) {
            // select * from teacher where (username=#{username} and password=#{password}) or (email=#{username} and password=#{password})
            Student student = studentMapper.selectOne(new QueryWrapper<Student>()
                    .eq("username", username).eq("password", password)
                    .or(i -> i.eq("email", username).eq("password", password))
            );
            if (student != null) {
                id = student.getStudentId();
            }

        }
        if (id == null) {
            throw new StateException(StateEnum.USER_NOT_FOUND);
        }

        //存入id跟身份
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", id + "");
        map.put("identity", identityType);
        //生成token
        String token = JwtUtil.createToken(StaticEnum.TOKEN_EXPIRE_TIME.getValInt(), map);

        //获取菜单
        DifferentMenu menu = differentMenuMapper.selectOne(new LambdaQueryWrapper<DifferentMenu>()
                .eq(DifferentMenu::getIdentity, identityType).select(DifferentMenu::getMenu));
        return AjaxR.success(token,menu);
    }
}
