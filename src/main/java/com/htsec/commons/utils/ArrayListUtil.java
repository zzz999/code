package com.htsec.commons.utils;

import java.util.ArrayList;

/**
 * Created by bernard on 2017/3/29.
 */
public class ArrayListUtil {
    public static ArrayList getDefinedList(int offset, int length, ArrayList list){
        ArrayList dist= new ArrayList();
        if(offset*length>list.size()-1){
            return dist;
        }
        for(int i=offset*length;(i<list.size()&&i<offset*length+length);i++){
            dist.add(list.get(i));
        }
        return dist;

    }

}
