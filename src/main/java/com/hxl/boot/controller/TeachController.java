package com.hxl.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hxl.boot.annotation.StateEnum;
import com.hxl.boot.exception.StateException;
import com.hxl.boot.pojo.Teach;
import com.hxl.boot.service.TeachService;
import com.hxl.boot.service.TeacherService;
import com.hxl.boot.utils.AjaxR;
import com.hxl.boot.utils.JwtUtil;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/teach")
@Transactional
public class TeachController {

    @Resource
    TeachService teachService;
    @Resource
    TeacherService teacherService;



    /**
     * 添加教师到该课题
     *
     * @param request token
     * @param teach   教师id与课题id的对象
     * @return R
     */

    @PostMapping()
    public AjaxR addTeacherToTopic(HttpServletRequest request, Teach teach) {

        //对参数进行判断
        if (teach.getTeacherId() == null || teach.getTopicId() == null)
            throw new StateException(StateEnum.PARAMETER_ERROR);

        //教师不存在,则返回
        if (teacherService.getById(teach.getTeacherId()) == null) throw new StateException(StateEnum.USER_NOT_FOUND);
        //已在课题中 返回
        Teach one = teachService.getOne(new LambdaQueryWrapper<Teach>().eq(Teach::getTeacherId, teach.getTeacherId())
                .eq(Teach::getTopicId, teach.getTopicId()));
        if (one != null) throw new StateException(StateEnum.USER_EXIST);

        boolean save = teachService.save(teach);
        if (!save) throw new StateException(StateEnum.UNKNOWN_ERROR);
        return AjaxR.success();
    }

    /**
     * 将某课题的老师退出
     * @param request  token
     * @param teacherId 该教师id
     * @param topicId   课题id
     * @return R
     */
    @DeleteMapping("{teacherId}/topic/{topicId}")
    public AjaxR quitTeacherLeaveTopic(HttpServletRequest request,
                                       @PathVariable Integer teacherId,
                                       @PathVariable Integer topicId) {

        //对参数进行判断
        if (teacherId == null || topicId == null) throw new StateException(StateEnum.PARAMETER_ERROR);

        //直接删除 ，false说明不存在 或者内部错误
        boolean remove = teachService.remove(new LambdaUpdateWrapper<Teach>()
                .eq(Teach::getTopicId, topicId).eq(Teach::getTeacherId, teacherId));
        if (!remove) throw new StateException(StateEnum.USER_NOT_FOUND);
        return AjaxR.success();
    }

}
