package com.codefish.web.controller;/**
 * @author codefish
 * @date 9/23/2021
 * @apinote
 */

import com.codefish.domain.User;
import com.codefish.service.UserService;
import com.codefish.service.impl.UserServiceImpl;
import com.codefish.util.MD5Util;
import com.codefish.util.PrintJson;
import com.codefish.util.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: codefish
 * @discription:
 */
public class UserController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Welcome to The Control System!");
        System.out.println("错误");
        String path = request.getServletPath();
        if ("/user/login.do".equals(path)){
            System.out.println(path);
            login(request, response);
            //request.getRequestDispatcher("/workbench/index.jsp").forward(request, response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("entered the login checking sys!");
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        System.out.println(userService);

        //获取前端请求参数
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");

        //将明文密码转换为MD5
        loginPwd = MD5Util.getMD5(loginPwd);

        System.out.println(loginPwd);

        //获取远程ip
        String ip = request.getRemoteAddr();
        System.out.println(ip);
        User user = new User();
        user.setName(loginAct);
        user.setLoginPwd(loginPwd);

        try{
            User userQuery = userService.login(loginAct, loginPwd, ip);
            System.out.println(userQuery);
            request.getSession().setAttribute("user", userQuery);
            PrintJson.printJsonFlag(response, true);
        }catch (Exception e){
            e.printStackTrace();
            String msg = e.getMessage();
            /*
                controller
                    (1)将信息打包成map, 并解析成json;
                    (2)创建一个vo
                如果传递的信息大量使用,则创建vo, 少量就使用map
             */
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("msg", msg);
            PrintJson.printJsonObj(response, map);
        }
    }
}
