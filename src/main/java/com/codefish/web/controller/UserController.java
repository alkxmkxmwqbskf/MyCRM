package com.codefish.web.controller;/**
 * @author codefish
 * @date 9/23/2021
 * @apinote
 */

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: codefish
 * @discription:
 */
public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到用户控制界面!");
        String path = request.getServletPath();
        if ("/user/login.do".equals(path)){
            System.out.println(path);
        }
    }
}
