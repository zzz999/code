package com.htsec.commons.utils;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by bernard on 2017/3/30.
 */
public class CommonResponseFomatter {
    public static void  format( HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin","*");
        return;
    }
}
