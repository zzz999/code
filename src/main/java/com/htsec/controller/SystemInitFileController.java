package com.htsec.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

/**
 * Created by bernard on 2017/9/25.
 */

@Controller
public class SystemInitFileController {
    private static final Logger logger = Logger.getLogger(SystemInitFileController.class);

    @RequestMapping(value= "/init",method= RequestMethod.POST)
    @ResponseBody
    public void init(MultipartFile file, HttpServletRequest request) throws IOException {
        //String path = request.getSession().getServletContext().getRealPath("upload");
        String result="";
        BufferedReader in=new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result +=line+"\n";
        }
       System.out.println(result);
        return;
    }



}
