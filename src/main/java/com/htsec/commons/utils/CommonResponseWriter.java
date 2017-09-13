package com.htsec.commons.utils;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bernard on 2017/4/6.
 */
public class CommonResponseWriter {
    private static final Logger logger = Logger.getLogger(CommonResponseWriter.class);
    public static void write(String result,HttpServletResponse response){
        logger.error("send response:"+result);
        try {
            response.getWriter().write(CodeHelper.encode(result));
        } catch (IOException e) {
            logger.error("send response failed",e);
        }

    }

}
