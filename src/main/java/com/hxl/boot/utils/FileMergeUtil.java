package com.hxl.boot.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.hxl.boot.annotation.StateEnum;
import com.hxl.boot.exception.StateException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class FileMergeUtil {

    //用户家目录
    public static String userHomeDirectory = "D:\\HTML\\后台管理系统\\file\\";



    /**拆分的文件 放到学生id目录下的cache目录,以完整文件的md5作为目录，里面是md5+分片序号的文件
     * 合并后以学生id作为目录，再以周报id作为目录 ，里面为以 MD5作为文件名 并记录到数据库 真实的名字
     * 确保合并的文件不存在 ，再进行合并
     * @param userId 用户id作为目录
     * @param weeklyReportId  课题id作为二级目录
     * @param mergeFileSuffix  合并的文件后缀
     */
    public static String   mergeFile(Integer userId, Integer weeklyReportId, Integer topicId, String mergeFileSuffix) {
        if (userId == null || weeklyReportId == null||topicId==null) {
            throw new RuntimeException();
        }
        //获取缓存目录
        String cacheDirectory = existsCacheDirectory(userId);
        File file = new File(cacheDirectory);
        //获取缓存文件
        File[] files = file.listFiles();
        //获取合并文件名字,不带后缀
        String mergeFileName = files[0].getName().substring(0, files[0].getName().lastIndexOf("-"));
        String s = existsCacheDirectory(userId, weeklyReportId,topicId);
        if (s==null) {
            return null;
        }

        //合并文件的目录
        FileOutputStream fos = null;
        FileInputStream fis=null;
        try {
            //合并文件具体路径（一定存在）
            String path = s+"\\"+ mergeFileName + mergeFileSuffix;
            fos = new FileOutputStream(path,true);
            //读写文件
            for (File f : files) {
                byte[] cache = new byte[1024 * 1024];
                 fis= new FileInputStream(f);
                int readCount = 0;
                while ((readCount = fis.read(cache)) != -1) {
                    fos.write(cache, 0, readCount);
                    fos.flush();
                }
                fis.close();
            }
            return path;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (fis!=null){
                    fis.close();
                }
                //删除文件
                deleteCache(files);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public static  void downLoadFile(String path, HttpServletResponse response) throws IOException {


        // 读到流中
        InputStream inputStream = new FileInputStream(path);// 文件的存放路径
        response.reset();
        response.setContentType("application/octet-stream");
        String filename = new File(path).getName();
        response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] b = new byte[1024];
        int len;
        //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
        while ((len = inputStream.read(b)) > 0) {
            outputStream.write(b, 0, len);
        }
        inputStream.close();
    }
    /**
     * 创建用户的缓存目录
     * @param userId 用户的id 用来创建用户的目录
     * @return 路径（可能为空）
     */
    public static String existsCacheDirectory(Integer userId) {
        String path = userHomeDirectory + userId + "\\cache\\";
        File file = new File(path);
        if (file.exists()) return path;
        if (file.mkdirs()) return path;
        return null;
    }

    /**
     * 创建用户的周报目录
     * @param userId 用户的id 用来创建用户的目录
     * @param weeklyReportId 用户选题的课题id ，用来创建课题目录
     * @return 路径（可能为空）
     */
    public static String existsCacheDirectory(Integer userId,Integer weeklyReportId,Integer topicId) {
        String path = userHomeDirectory + userId + "\\"+topicId+"\\"+ weeklyReportId;
        File file = new File(path);
        if (file.exists()) return path;
        if (file.mkdirs()) return path;
        return null;
    }

    /**
     * 创建用户的课题目录
     * @param userId
     * @param topicId
     * @return
     */
    public static String existsCacheDirectory(Integer userId,Integer topicId) {
        String path = userHomeDirectory + userId + "\\"+topicId;
        File file = new File(path);
        if (file.exists()) return path;
        if (file.mkdirs()) return path;
        return null;
    }
    public static void deleteCache(File[] files){
        if (files.length == 0||files==null) {
            return;
        }

        for (File file: files){
            if (file.exists()) {
               boolean a= file.delete();
                System.out.println(a);
            }

        }
    }

}
