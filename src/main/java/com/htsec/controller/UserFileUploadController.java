package com.htsec.controller;

import com.htsec.Student.beans.BankInfo;
import com.htsec.Student.beans.StudentMessage;
import com.htsec.Student.process.MessageManager;
import com.htsec.Student.process.StudentProcessManager;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * Created by bernard on 2017/9/25.
 */


@Controller
public class UserFileUploadController {
    /**
     * 文件上传功能
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value= "/fileupload",method= RequestMethod.POST)
    @ResponseBody
    public String upload(MultipartFile file, HttpServletRequest request) throws Exception {
        String path = request.getSession().getServletContext().getRealPath("upload");
        ;
        String fileName = "file_"+MessageManager.getRandomString(10)+"_"+file.getOriginalFilename();
        File dir = new File(path,fileName);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //MultipartFile自带的解析方法
        file.transferTo(dir);
        String code=request.getParameter("code");
        String sendCode=request.getParameter("sendCode");
        BankInfo bankInfo= StudentProcessManager.getBankInfoHashMap().get(code);
        if(bankInfo==null){
            throw new Exception("没有对应银行信息");
        }
        StudentMessage sm=new StudentMessage(code,sendCode,"2",file.getOriginalFilename(),fileName);
        sm.setMessage(bankInfo.getName());
        MessageManager.getList().add(sm);
        return "ok!";
    }

    /**
     * 文件下载功能
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/filedownload")
    public void down(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String file=request.getParameter("file");
        //模拟文件，myfile.txt为需要下载的文件
        String fileName = request.getSession().getServletContext().getRealPath("upload")+"/"+file;
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
        //假如以中文名下载的话
        String filename = file.substring(16);
        //转码，免得文件名中文乱码
        filename = URLEncoder.encode(filename,"UTF-8");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while((len = bis.read()) != -1){
            out.write(len);
            out.flush();
        }
        out.close();
    }
}