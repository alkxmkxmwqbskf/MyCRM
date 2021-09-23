package com.codefish.util;/**
 * @author codefish
 * @date 9/23/2021
 * @apinote
 */

import java.util.UUID;

/**
 * @author: codefish
 * @discription:
 */
public class UUIDUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
