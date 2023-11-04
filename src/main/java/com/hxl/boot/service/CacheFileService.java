package com.hxl.boot.service;

import com.hxl.boot.pojo.CacheFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxl.boot.utils.AjaxR;

/**
* @author hxl
* @description 针对表【cache_file】的数据库操作Service
* @createDate 2023-08-27 20:59:04
*/
public interface CacheFileService extends IService<CacheFile> {
    AjaxR uploadAck(Integer userId, String cacheDirectory);

}
