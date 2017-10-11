package com.htsec.controller;

import com.htsec.Student.beans.BankInfo;
import com.htsec.Student.beans.JGInfo;
import com.htsec.Student.beans.StudentMessage;
import com.htsec.Student.beans.FHinfo;
import com.htsec.Student.process.MessageManager;
import com.htsec.commons.utils.CodeHelper;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by bernard on 2017/9/26.
 */
@Controller
public class StudentQueryStateController {
    private static final Logger logger = Logger.getLogger(StudentQuerySCYCController.class);
    @RequestMapping(value = "/queryState", method = RequestMethod.GET)
    public void queryYYLL(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject queryObj = JSONObject.fromObject(CodeHelper.decode(request.getQueryString()));
        String code =queryObj.getString("code");
        JSONObject result = new JSONObject();
        ///
       /* FHinfo FHinfo = new FHinfo();
        FHinfo.setDepositGroupNum("1");
        FHinfo.setLoanGroupNum("2");
        FHinfo zhInfo1 = new FHinfo();
        zhInfo1.setDepositGroupNum("1");
        zhInfo1.setLoanGroupNum("2");
        ArrayList<FHinfo> zhInfos = new ArrayList<>();
        zhInfos.add(FHinfo);
        zhInfos.add(zhInfo1);



        BankInfo kk=new BankInfo();
        kk.setCash("10000");
        kk.setTime("第一年");
        kk.setTotalDeposit("10000");
        kk.setTotalLoan("dfdsfsdfsd");
        kk.setZhInfoList(zhInfos);

        JGInfo jgInfo =new JGInfo();
        jgInfo.setBbfgl("1");
        jgInfo.setBldkv("22");
        jgInfo.setCdb("22");
        jgInfo.setDkjzd("33");
        jgInfo.setHxzbczl("222");
        jgInfo.setZbczl("ddd");

        StudentMessage studentMessage = new StudentMessage();
        studentMessage.setType("1");
        studentMessage.setMessage("hello world ,你好 太阳");
        ArrayList<StudentMessage> messages = new ArrayList<>();
        messages.add(studentMessage);
        StudentMessage studentMessage1 = new StudentMessage();
        studentMessage1.setType("2");
        studentMessage.setFileName("1.xlsx");



        result.put("bankInfo",kk);
        result.put("jgInfo",jgInfo);
        result.put("message",messages);*/
        result.put("message", MessageManager.findByCode(code));
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
