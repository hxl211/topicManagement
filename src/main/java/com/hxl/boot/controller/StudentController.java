package com.hxl.boot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hxl.boot.annotation.StateEnum;
import com.hxl.boot.exception.StateException;
import com.hxl.boot.pojo.Student;
import com.hxl.boot.pojo.Topic;
import com.hxl.boot.service.StudentService;
import com.hxl.boot.service.TopicService;
import com.hxl.boot.utils.AjaxR;
import com.hxl.boot.utils.ThreadLocalUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    StudentService studentService;
    @Resource
    TopicService topicService;


    /**
     * 通过课题id 分页查询所有用户信息
     *
     * @param page 页数
     * @return 结果集
     */
    @GetMapping("/page/{page}/topic/{selectTopic}")
    public AjaxR getStudentAllByPage(@PathVariable Integer page, @PathVariable Integer selectTopic) {
        //参数错误
        if (page == null || selectTopic == null) {
            throw new StateException(StateEnum.PARAMETER_ERROR);
        }

        return studentService.getStudentAllByPage(page, selectTopic);
    }


    /**
     * 根据id查询某个学生的信息
     *
     * @param id 学生id
     * @return 结果集
     */
    @GetMapping("{id}")
    public AjaxR getStudentById(@PathVariable String id) {
        //判断是否为空
        boolean blank = StrUtil.isBlank(id);
        if (blank) throw new StateException(StateEnum.REQUIRE_NOT_NULL);


        Student student = studentService.getOne(new LambdaQueryWrapper<Student>()
                .select(Student::getStudentId, Student::getName, Student::getClassId).eq(Student::getStudentId, id));

        if (student == null) throw new StateException(StateEnum.USER_NOT_FOUND);
        return AjaxR.success(student);
    }


    /**
     * 查询学生所选的课题信息
     *
     * @param request
     * @return
     */
    @GetMapping("/topic")
    @CrossOrigin
    public AjaxR getStudentTopic(HttpServletRequest request) {
        Integer studentId = ThreadLocalUtil.getUser().getUserId();
        if (studentId == null) {
            throw new StateException(StateEnum.USER_NOT_LOGIN);
        }
        return studentService.getStudentTopic(studentId);
    }

    /**
     * 添加学生到课题中
     *
     * @param studentId 学生id
     * @return R
     */
    @PostMapping()
    public AjaxR addStudentById(Integer studentId, Integer topicId) {
        if (studentId == null || topicId == null) throw new StateException(StateEnum.PARAMETER_ERROR);

        //  boolean isEmail = ReUtil.isMatch(StaticEnum.PASSWORD_REG.getValString(), studentId);
        //throw new StateException(StateEnum.USER_EXIST);
        //查询用户是否已添加过课题
        boolean existsStudent = studentService.lambdaQuery()
                .eq(Student::getStudentId, studentId)
                .select(Student::getTopicId).exists();

        if (existsStudent) {
            throw new StateException(StateEnum.ALREADY_ADD_TOPIC);
        }
        //查询课题是否存在
        boolean existsTopic = topicService.lambdaQuery().eq(Topic::getTopicId, topicId).exists();
        if (!existsTopic) {
            throw new StateException(StateEnum.TOPIC_NOT_FOUND);
        }

        //将该id学生添加到课题中，课题人数加1
        boolean update = studentService.lambdaUpdate().eq(Student::getStudentId, studentId)
                .set(Student::getTopicId, topicId).update()
                && topicService.lambdaUpdate().setSql("member_num=member_num+1").update();
        if (!update) throw new StateException(StateEnum.PARAMETER_ERROR);

        return AjaxR.success();

    }


    /**
     * 删除学生
     *
     * @param id 学生id
     * @return R
     */
    @DeleteMapping("{id}")
    public AjaxR deleteStudentById(@PathVariable Integer id) {

        if (id == null) throw new StateException(StateEnum.PARAMETER_ERROR);
        //查询该学生是否存在
        if (studentService.getById(id) == null) {
            throw new StateException(StateEnum.USER_NOT_FOUND);
        }
        boolean b = studentService.update(new LambdaUpdateWrapper<Student>()
                .eq(Student::getStudentId, id).set(Student::getTopicId, null));

        if (!b) throw new StateException(StateEnum.UNKNOWN_ERROR);

        return AjaxR.success();
    }


}
