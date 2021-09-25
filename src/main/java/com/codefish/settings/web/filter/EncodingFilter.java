package com.codefish.settings.web.filter;/**
 * @author codefish
 * @date 9/25/2021
 * @apinote
 */

import javax.servlet.*;
import java.io.IOException;

/**
 * @author: codefish
 * @discription:
 */
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入到字符编码过滤器");
        //过滤post请求中文乱码
        request.setCharacterEncoding("UTF-8");
        //过滤响应流中文乱码
        response.setContentType("text/html;charset=utf-8");

        //将请求放行
        filterChain.doFilter(request, response);
    }
}
