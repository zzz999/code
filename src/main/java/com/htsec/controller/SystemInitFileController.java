package com.htsec.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by bernard on 2017/9/25.
 */

@Controller
public class SystemInitFileController {
    private static final Logger logger = Logger.getLogger(SystemInitFileController.class);

    public String upload(MultipartFile file, HttpServletRequest request) throws IOException {
        //String path = request.getSession().getServletContext().getRealPath("upload");
        file.
        String fileName = file.getOriginalFilename();
        File dir = new File(path,fileName);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //MultipartFile自带的解析方法
        file.transferTo(dir);
        return "ok!";
    }



}
