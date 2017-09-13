package com.htsec.filter;

/**
 * Created by bernard on 2017/5/26.
 */

import com.htsec.commons.utils.CommonResponseWriter;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class TimeInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = Logger.getLogger(TimeInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        response.setHeader("id",request.getSession().getId());
        response.setHeader("Access-Control-Expose-Headers","id");
        String timestamp=request.getHeader("timestamp");
        logger.info("timestamp is:"+timestamp);
        if(timestamp==null){
            CommonResponseWriter.write("duplicate request",response);
            return false;
        }
        HttpSession session=request.getSession();
        logger.info("session is "+session.getId());
        if(session!=null) {
            ArrayList<String> array = (ArrayList<String>) session.getAttribute("array");
            if (array!=null){

                for(String a:array){
                    logger.info(a);
                }
                if(array.size()>10000||array.contains(timestamp)){
                    logger.info("timestamp already exist");
                    CommonResponseWriter.write("duplicate request",response);
                    return false;
                }else {
                    array.add(timestamp);
                    session.setAttribute("array",array);
                    return true;
                }
            }else {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(timestamp);
                session.setAttribute("array",arrayList);
                return true;
            }
        }else {
            logger.info("session is null");
            return true;
        }
    }









}

