package com.hxl.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxl.boot.pojo.Teach;
import com.hxl.boot.service.TeachService;
import com.hxl.boot.mapper.TeachMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author 21141
* @description 针对表【teach】的数据库操作Service实现
* @createDate 2023-08-06 21:59:51
*/
@Service
@Transactional
public class TeachServiceImpl extends ServiceImpl<TeachMapper, Teach>
    implements TeachService{

}




