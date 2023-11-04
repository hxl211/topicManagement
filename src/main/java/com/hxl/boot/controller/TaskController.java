package com.hxl.boot.controller;
import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hxl.boot.annotation.StateEnum;
import com.hxl.boot.exception.StateException;
import com.hxl.boot.pojo.*;
import com.hxl.boot.service.*;
import com.hxl.boot.utils.AjaxR;
import com.hxl.boot.utils.FileMergeUtil;
import com.hxl.boot.utils.JwtUtil;
import com.hxl.boot.vo.WeeklyReportCountDTO;
import com.hxl.boot.vo.WeeklyReportDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Resource
    WeeklyReportService weeklyReportService;
    @Resource
    CacheFileService cacheFileService;
    @Resource
    TopicReportFileService topicReportFileService;
    @Resource
    TopicService topicService;
    @Resource
    LearningRecordFileService learningRecordFileService;
    @Resource
    FileDownloadService fileDownloadService;
    /**
     * 通过用户id跟课题id唯一确定一个周报
     * @param request
     * @param id 课题id
     * @return
     */
    @GetMapping("/{id}") AjaxR getTaskById(HttpServletRequest request,@PathVariable Integer id){
        Integer userId = JwtUtil.getUserId(request.getHeader("token"));
        if (userId == null) {
            throw new StateException(StateEnum.USER_NOT_LOGIN);
        }
        if (id==null) {
            throw new StateException(StateEnum.UNKNOWN_ERROR);
        }
        WeeklyReport data = weeklyReportService.getById(id);
        if (data==null) {
            throw new StateException(StateEnum.RESULT_EMPTY);
        }

        Long count = learningRecordFileService.lambdaQuery().eq(LearningRecordFile::getWeeklyReportId, id).count();
        return AjaxR.success(new WeeklyReportDTO(data,count));
    }

    @GetMapping("/count/{topicId}")
    public AjaxR getTaskCount(HttpServletRequest request,@PathVariable Integer topicId){

        Integer userId = JwtUtil.getUserId(request.getHeader("token"));
        if (userId == null) {
            throw new StateException(StateEnum.USER_NOT_LOGIN);
        }
        if (topicId==null){
            throw new StateException(StateEnum.PARAMETER_ERROR);
        }
        WeeklyReportCountDTO dto = new WeeklyReportCountDTO();

        Long current = weeklyReportService.lambdaQuery()
                .eq(WeeklyReport::getTopicId, topicId)
                .eq(WeeklyReport::getAssociatedWeekNum, DateTime.now().weekOfYear()).count();
            dto.setCurrent(current);
        Topic one = topicService.lambdaQuery().eq(Topic::getTopicId, topicId).select(Topic::getMemberNum).one();
        if (one!=null &&one.getMemberNum()!=null) {
            dto.setTotal(one.getMemberNum().longValue());
        }

        return AjaxR.success(dto);
    }

    /**
     * 查询学生当前所选课题的周报列表
     * @param request
     * @return
     */
    @GetMapping()
    public  AjaxR getStudentTaskList(HttpServletRequest request){
        Integer userId = JwtUtil.getUserId(request.getHeader("token"));
        if (userId == null) {
            throw new StateException(StateEnum.USER_NOT_LOGIN);
        }

        List<WeeklyReport> list = weeklyReportService.lambdaQuery().eq(WeeklyReport::getStudentId, userId).list();
        return AjaxR.success(list);
    }

    /**
     * 获取学生本周的 签到，周报以及课题报告信息
     * @param request
     * @return
     */
    @GetMapping("/info")
    public AjaxR getStudentTaskInfo(HttpServletRequest request){
        Integer userId = JwtUtil.getUserId(request.getHeader("token"));
        if (userId == null) {
            throw new StateException(StateEnum.USER_NOT_LOGIN);
        }
        return weeklyReportService.getStudentTaskInfo(userId);
    }

    @PostMapping("/topicReport")
    public AjaxR saveTopicReport(HttpServletRequest request,MultipartFile file){
        Integer userId = JwtUtil.getUserId(request.getHeader("token"));
        if (userId == null) {
            throw new StateException(StateEnum.USER_NOT_LOGIN);
        }
          return topicReportFileService.saveStudentTopicReportFile(file,userId);

    }

    /**
     * 通过 type 先来判断下载类型（1：课题报告，2：课题附件），再通过周报id 或 课题id 来下载文件
     * @param request
     * @param response
     * @param id 周报id 或 课题id
     * @param type 周报2 /课题1  （周报附件 跟 课题报告）
     * @throws IOException
     */
    @GetMapping("/download/{type}/{id}")
    public void downloadLocal( HttpServletRequest request,HttpServletResponse response,
                               @PathVariable Integer id,
                               @PathVariable Integer type
    ) throws IOException {
        Integer userId = JwtUtil.getUserId(request.getHeader("token"));
        if (userId == null) {
            throw new StateException(StateEnum.USER_NOT_LOGIN);
        }
        if (type==null||id==null){
            throw new StateException(StateEnum.PARAMETER_ERROR);
        }
        fileDownloadService.downLoadFile(type,id,response);

    }
    /**
     * 保存学生的周报信息，不需要分片的文件
     * @param oneFile 不需要分片的文件
     * @param weeklyReportJson 周报信息
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("")
    public AjaxR saveStudentTask(@RequestPart(value = "file", required = false) MultipartFile oneFile,
                                 @RequestParam("weeklyReport") String weeklyReportJson,

                                 HttpServletRequest request
    ) throws IOException {
        Integer userId = JwtUtil.getUserId(request.getHeader("token"));
        if (userId == null) {
            throw new StateException(StateEnum.USER_NOT_LOGIN);
        }
        WeeklyReport weeklyReport = JSONUtil.toBean(weeklyReportJson, WeeklyReport.class);
        try {
            weeklyReportService.saveStudentReport(userId, weeklyReport, oneFile);
            return AjaxR.success();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 大文件上传前的 周报信息保存，成功后 进行 文件分片上传，失败则报错，不上传文件
     * @param request
     * @param weeklyReport  周报信息
     * @param cacheFile 文件分片信息-为分片做准备
     * @return
     */
    @PostMapping("/upload/ack")
    public AjaxR uploadPrepare(HttpServletRequest request,WeeklyReport weeklyReport, CacheFile cacheFile){
        Integer userId = JwtUtil.getUserId(request.getHeader("token"));
        if (userId == null) {
            throw new StateException(StateEnum.USER_NOT_LOGIN);
        }
       return weeklyReportService.upload(weeklyReport,userId,cacheFile);
    }

    /**
     * 上传多个 单分片文件
     * @param file 文件
     * @param chunks  分片数量
     * @param chunk 当前第几片
     * @param request
     * @return
     */
    @PostMapping("/upload")
    public AjaxR uploadTaskFile( @RequestPart(value = "file") MultipartFile file,
                                 Integer chunks,
                                 Integer chunk,
                                 HttpServletRequest request) {
        Integer userId = JwtUtil.getUserId(request.getHeader("token"));
        if (userId == null) {
            throw new StateException(StateEnum.USER_NOT_LOGIN);
        }
        String cacheDirectory = FileMergeUtil.existsCacheDirectory(userId);
        if (cacheDirectory!=null) {
            try {
                file.transferTo(new File(cacheDirectory+file.getOriginalFilename()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //对分片文件先进行保存 ，当是最后一个文件的时候进行合并
        if (chunk==chunks) {
            return cacheFileService.uploadAck(userId,cacheDirectory);
        }
       return AjaxR.success();
    }

    @DeleteMapping("{id}")
    public AjaxR deleteWeeklyReport(HttpServletRequest request,@PathVariable Integer id){
        Integer userId = JwtUtil.getUserId(request.getHeader("token"));
        if (userId == null) {
            throw new StateException(StateEnum.USER_NOT_LOGIN);
        }
        if (id==null) {
            throw new StateException(StateEnum.UNKNOWN_ERROR);
        }
        boolean b = weeklyReportService.remove(new LambdaQueryWrapper<WeeklyReport>()
                .eq(WeeklyReport::getStudentId, userId).eq(WeeklyReport::getWeeklyReportId, id));
        if (!b) throw new StateException(StateEnum.UNKNOWN_ERROR);
        return AjaxR.success();


    }
}
