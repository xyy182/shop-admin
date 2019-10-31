package com.fh.shop.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil implements Serializable {
    public static final String  Y_M_D="yyyy-MM-dd";
    public static final String FULL_YEAR="yyyy-MM-dd HH:mm:ss";
    public static final String Y="yyyy";

    public static String data2str(Date date,String str){
        if (date==null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        String format = simpleDateFormat.format(date);
        return format;
    }

}
