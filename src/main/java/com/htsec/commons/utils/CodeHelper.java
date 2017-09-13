package com.htsec.commons.utils;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by bernard on 2017/3/29.
 */
public class CodeHelper {
    private static final Logger logger = Logger.getLogger(CodeHelper.class);

    public static String encode(String input){
        String result ="";
        try {
            result=  URLEncoder.encode(input,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("URLencode failed",e);
        }
        return result;
    }

    public static String decode(String input){
        String result ="";
        try {
            result=  URLDecoder.decode(input,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("URLdecode failed",e);
        }
        return result;
    }
}
