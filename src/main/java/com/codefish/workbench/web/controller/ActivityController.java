package com.codefish.workbench.web.controller;/**
 * @author codefish
 * @date 9/25/2021
 * @apinote
 */

import com.codefish.settings.domain.User;
import com.codefish.settings.service.UserService;
import com.codefish.settings.service.impl.UserServiceImpl;
import com.codefish.util.PrintJson;
import com.codefish.util.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author: codefish
 * @discription:
 */
public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("You have entered the market activities controller!");
        String path = request.getServletPath();
        if ("/workbench/activity/getUserList.do".equals(path)){
            getUserList(request, response);
        }
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Get the user list!");
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> userList = userService.getUserList();
        PrintJson.printJsonObj(response, userList);
    }
}
