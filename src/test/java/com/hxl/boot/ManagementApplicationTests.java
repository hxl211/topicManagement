package com.hxl.boot;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxl.boot.mapper.StudentMapper;
import com.hxl.boot.mapper.TeacherMapper;
import com.hxl.boot.utils.FileMergeUtil;
import com.hxl.boot.vo.StudentDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@SpringBootTest
class ManagementApplicationTests {

    @Resource
    StudentMapper studentMapper;

    @Test
    public void test1() throws IOException {


    }
}
