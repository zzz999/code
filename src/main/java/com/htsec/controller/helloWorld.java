package com.htsec.controller;

import com.htsec.mysql.service.UserDataBaseService;
import com.htsec.mysql.service.UserHqInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bernard on 2017/3/13.
 */

@Controller
public class helloWorld {
    @Autowired
    private UserDataBaseService userDataBaseService;
    @RequestMapping("/hello")
    public  void play(HttpServletRequest request, HttpServletResponse response){

        try {
            response.getWriter().write(this.userDataBaseService.getswbyaccount(1212).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;

    }
}
