package com.htsec.commons.utils;

import net.sf.json.JSONObject;

/**
 * Created by bernard on 2017/3/24.
 */
public class JSONUtil {

    public static String safeGetString(JSONObject obj,String key){
        if(obj.containsKey(key)==false){
            return "";
        }
        return obj.getString(key);
    }
}
