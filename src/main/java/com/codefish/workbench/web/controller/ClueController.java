package com.codefish.workbench.web.controller;/**
 * @author codefish
 * @date 10/6/2021
 * @apinote
 */

import com.codefish.settings.domain.User;
import com.codefish.settings.service.UserService;
import com.codefish.settings.service.impl.UserServiceImpl;
import com.codefish.util.PrintJson;
import com.codefish.util.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author: codefish
 * @discription:
 */
public class ClueController extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Welcome to the clue controller!");
        String path = request.getServletPath();
        if ("/workbench/clue/getUserList.do".equals(path)){
            getUserList(request, response);
        }else if ("/workbench/clue/xxx.do".equals(path)){

        }
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Welcome to user info list");
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> userList = userService.getUserList();
        PrintJson.printJsonObj(response, userList);
    }
}
