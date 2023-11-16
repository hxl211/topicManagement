package com.hxl.boot.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hxl.boot.annotation.StateEnum;
import com.hxl.boot.exception.StateException;
import com.hxl.boot.pojo.Topic;
import com.hxl.boot.service.TopicService;
import com.hxl.boot.service.WeeklyReportService;
import com.hxl.boot.utils.AjaxR;
import com.hxl.boot.utils.JwtUtil;
import com.hxl.boot.utils.ThreadLocalUtil;
import com.hxl.boot.vo.SearchWeeklyReportInfoDTO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RequestMapping("/topic")
@RestController
public class TopicController {
    @Resource
    TopicService topicService;
    @Resource
    WeeklyReportService weeklyReportService;
    /**
     * 通过当前登录用户 获取 该用户发布的课题
     *
     * @param request 获取token
     * @return R
     */
    @GetMapping("/publish/page/{page}")
    public AjaxR queryTopicAllByPublisherId(HttpServletRequest request, @PathVariable Integer page) {

        Integer teacherId =  ThreadLocalUtil.getUser().getUserId();
        if (teacherId == null) throw new StateException(StateEnum.USER_NOT_LOGIN);

        return topicService.getTopicAllByPublisherId(teacherId, page);
    }

    /**
     * 通过当前登录用户获取教授的课题
     *
     * @param request 获取token
     * @return R
     */
    @GetMapping("/teach/page/{page}")
    public AjaxR queryTopicAllByTeacherId(HttpServletRequest request, @PathVariable Integer page) {
        Integer teacherId =  ThreadLocalUtil.getUser().getUserId();
        return topicService.getTopicAllByTeacherId(teacherId, page);
    }
    /**
     * 通过登录用户 ，获取该教师所教授的课题名字
     * @param request token
     * @return R
     */
    @GetMapping("/names")
    public AjaxR getTeachNames(HttpServletRequest request){
        //获取当前登录用户
        Integer userId = ThreadLocalUtil.getUser().getUserId();
        if (userId == null) throw new StateException(StateEnum.USER_NOT_FOUND);
       return    topicService.getTopicNames(userId);
    }


    @GetMapping("/student/search")
    public  AjaxR getTopicStudent(HttpServletRequest request, SearchWeeklyReportInfoDTO dto){
        Integer userId = ThreadLocalUtil.getUser().getUserId();
        if (userId == null) {
            throw new StateException(StateEnum.USER_NOT_LOGIN);
        }
        if (dto==null||dto.getTopicId()==null) {
            throw new StateException(StateEnum.PARAMETER_ERROR);
        }
      return   weeklyReportService.getTopicStudent(userId,dto);

    }
    /**
     * 保存当前登录用户 发布的课题
     *
     * @param request   获取token
     * @param topicInfo 课题信息
     * @return R
     */
    @PostMapping
    public AjaxR addTopic(HttpServletRequest request, Topic topicInfo, String topicName) {

        Integer teacherId = ThreadLocalUtil.getUser().getUserId();
        if (teacherId == null) throw new StateException(StateEnum.USER_NOT_LOGIN);
        return topicService.saveTopic(teacherId, topicInfo);

    }
    /**
     * 通过当前用户   删除课题 id
     *
     * @param request 获取token
     * @param id      课题id
     * @return R
     */
    @DeleteMapping("{id}")
    public AjaxR deleteTopic(HttpServletRequest request, @PathVariable Integer id) {
        Integer teacherId = ThreadLocalUtil.getUser().getUserId();
        if (teacherId == null) throw new StateException(StateEnum.USER_NOT_LOGIN);

        return topicService.deleteTopic(id);
    }
    /**
     * 通过课题对象修改课题 o
     * @param request 获取token
     * @param topic  课题对象
     * @return R
     */
    @PutMapping()
    public AjaxR ModifyTopicByEntity(HttpServletRequest request, Topic topic) {
        Integer teacherId = ThreadLocalUtil.getUser().getUserId();
        if (teacherId == null) throw new StateException(StateEnum.USER_NOT_LOGIN);

        //设置更新时间
        topic.setUpdateTime(LocalDateTime.now());
        //不更新 创建时间 当 createTime=null 的时候 通过实体类修改 的时候会忽略null,并不会修改这个字段
        boolean b = topicService.saveOrUpdate(topic,
                new LambdaUpdateWrapper<Topic>().eq(Topic::getTopicId, topic.getTopicId()));

        if (!b) throw new StateException( StateEnum.UNKNOWN_ERROR);
        return AjaxR.success();
    }




}
