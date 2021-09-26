package com.codefish.workbench.web.controller;/**
 * @author codefish
 * @date 9/25/2021
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
public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("You have entered the market activities controller!");
    }
}
