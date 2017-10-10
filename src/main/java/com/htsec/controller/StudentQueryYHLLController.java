package com.htsec.controller;

import com.htsec.Student.init.bean.BaseRate;
import com.htsec.Student.init.bean.PersonalDeposit;
import com.htsec.Student.init.bean.PersonalDepositBean;
import com.htsec.Student.process.TeacherInitManager;
import com.htsec.commons.utils.CodeHelper;
import com.htsec.commons.utils.CommonKeys;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by bernard on 2017/9/25.
 */
@Controller
public class StudentQueryYHLLController {
    private static final Logger logger = Logger.getLogger(StudentQueryYHLLController.class);
    @RequestMapping(value = "/queryYYLL", method = RequestMethod.GET)
    public void queryYYLL(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject queryObj = JSONObject.fromObject(CodeHelper.decode(request.getQueryString()));
        JSONObject result = new JSONObject();
        String time =queryObj.getString("time");
        BaseRate baseRate=TeacherInitManager.getBaseRate();
        PersonalDeposit personalDeposit =TeacherInitManager.getPersonalDeposit();
        for(PersonalDepositBean personalDepositBean:personalDeposit.getPersonalDepositBeanList()){

        }
        result.put("baseRate",baseRate.getHashMap().get(time));
        //result.put(,);




    }


}
