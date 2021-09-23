package com.codefish.util;/**
 * @author codefish
 * @date 9/23/2021
 * @apinote
 */

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: codefish
 * @discription:
 */
public class DateTimeUtil {
    public static String getSysTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateStr = sdf.format(date);
        return dateStr;
    }
}
