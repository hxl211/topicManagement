package com.hxl.boot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxl.boot.annotation.StateEnum;
import com.hxl.boot.exception.StateException;
import com.hxl.boot.pojo.StudentScore;
import com.hxl.boot.pojo.Teacher;
import com.hxl.boot.service.StudentScoreService;
import com.hxl.boot.service.TeacherService;
import com.hxl.boot.utils.AjaxR;
import com.hxl.boot.utils.JwtUtil;
import com.hxl.boot.utils.ThreadLocalUtil;
import com.hxl.boot.vo.StudentScoreDTO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/score")
public class StudentScoreController {

    @Resource
    StudentScoreService studentScoreService;
    @Resource
    TeacherService teacherService;

    //通过学生id查询学生所有所选课题的成绩信息
    @GetMapping("{id}")
    public AjaxR getStudentScoreInfoById(HttpServletRequest request, @PathVariable Integer id) {
        return null;
    }

    /**
     * 通过登录的学生查询所有所选课题的成绩信息
     *
     * @param request
     * @return
     */

    @GetMapping()
    public AjaxR getStudentScoreInfo(HttpServletRequest request) {
        Integer userId = ThreadLocalUtil.getUser().getUserId();
        if (userId == null) {
            throw new StateException(StateEnum.USER_NOT_LOGIN);
        }
        List<StudentScoreDTO> list1 = studentScoreService.getStudentScoreInfoById(userId);

        //  List<StudentScore> list = studentScoreService.list(new LambdaQueryWrapper<StudentScore>().eq(StudentScore::getStudentId, userId));
        return AjaxR.success(list1);
    }

    /**
     * 通过课题id 查询该课题下的学生 的成绩情况
     *
     * @param request
     * @param page          页数
     * @param selectTopicId 课题id
     * @return
     */

    @GetMapping("/page/{page}/{selectTopicId}")
    public AjaxR getStudentAllScoreByTopicId(HttpServletRequest request, @PathVariable Integer page, @PathVariable Integer selectTopicId) {
        Integer userId = ThreadLocalUtil.getUser().getUserId();
        if (userId == null) {
            throw new StateException(StateEnum.USER_NOT_LOGIN);
        }
        if (page == null || selectTopicId == null) {
            throw new StateException(StateEnum.PARAMETER_ERROR);
        }

        Page<StudentScoreDTO> page1 = studentScoreService.getStudentAllScoreByTopicId(page, selectTopicId);

        return AjaxR.success(page1);
    }

    /**
     * 通过教师id 添加学生成绩
     * @param request
     * @return
     */

    @PostMapping()
    public AjaxR saveStudentScore(HttpServletRequest request, StudentScore  info) {
        Integer userId = ThreadLocalUtil.getUser().getUserId();
        if (userId == null) {
            throw new StateException(StateEnum.USER_NOT_LOGIN);
        }
        //查询是否为老师
        boolean exists = teacherService.lambdaQuery().eq(Teacher::getTeacherId, userId).exists();
        if (!exists) throw new StateException(StateEnum.USER_NOT_LOGIN);
        studentScoreService.saveStudentScore(info);
        return AjaxR.success();
    }

}
