package com.codefish.web.filter;/**
 * @author codefish
 * @date 9/25/2021
 * @apinote
 */

import com.codefish.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author: codefish
 * @discription:
 */
public class LoginFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("进入到登录验证过滤器");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //放行登录界面
        //String path = request
        //if ("/login.jsp".equals(path))


        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        //如果user不为空, 说明登录过
        if (user != null) {
            chain.doFilter(req, resp);
        }else{
            /*
                重定向路径如何写?
                转发和重定向:

                转发:/login.jsp
                    请求转发使用的是一种特殊的绝对路径方式, 这种绝对路径前面不加/项目名, 这种路径也被成为内部路径;
                重定向:/crm/login.jsp
                    重定向使用的是传统绝对路径写法, 前面必须以/项目名开头, 后面跟具体资源路径;

                为什么使用重定向, 使用转发不行吗?
                    请求转发之后, 路径会停留在老路径上, 而不是跳转之后资源新路径, 应该在跳转之后, 将地址栏改为最新资源的路径;

             */
            //重定向到登录页
            String path = request.getContextPath()+"/index.jsp";
            System.out.println(path);
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }
    }
}
