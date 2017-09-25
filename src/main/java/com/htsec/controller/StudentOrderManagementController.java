package com.htsec.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by bernard on 2017/9/25.
 */

@Controller
public class StudentOrderManagementController {
    private static final Logger logger = Logger.getLogger(StudentQueryJYGLController.class);

    @RequestMapping(value = "/queryStudentOrder", method = RequestMethod.GET)
    public void queryStudentOrderManagement(HttpServletRequest request, HttpServletResponse response){



    }
}
