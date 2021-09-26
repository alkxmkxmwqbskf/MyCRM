<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 9/25/2021
  Time: 9:32 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
</head>
<body>

//创建时间: 当前系统时间
String createTime = DateTimeUtil.getSysTime();
//创建人: 当前登录用户
String createBy = ((User) request.getSession().getAttribute("user")).getName();





</body>
</html>