package com.hxl.boot.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileDownloadService {
    void downLoadFile(Integer type,Integer id, HttpServletResponse response) throws IOException;
}
