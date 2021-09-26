package com.codefish.workbench.web.controller;/**
 * @author codefish
 * @date 9/25/2021
 * @apinote
 */

import com.codefish.settings.domain.User;
import com.codefish.settings.service.UserService;
import com.codefish.settings.service.impl.UserServiceImpl;
import com.codefish.util.DateTimeUtil;
import com.codefish.util.PrintJson;
import com.codefish.util.ServiceFactory;
import com.codefish.util.UUIDUtil;
import com.codefish.vo.PagenationVO;
import com.codefish.workbench.domain.Activity;
import com.codefish.workbench.service.ActivityService;
import com.codefish.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        }else if ("/workbench/activity/save.do".equals(path)){
            save(request, response);
        }else if ("/workbench/activity/getPageList.do".equals(path)){
            //分页查询
            getPageList(request, response);
        }
    }

    private void getPageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Welcome to page query controller!");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String pageNoStr = request.getParameter("pageNo");
        int pageNo = Integer.parseInt(pageNoStr);
        String pageSizeStr = request.getParameter("pageSize");
        int pageSize = Integer.parseInt(pageSizeStr);
        int skipCount = (pageNo - 1)*pageSize;

        //查询数据封装到map
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("skipCount", skipCount);
        map.put("pageSize", pageSize);

        //查询数据
        /*
            map:
            vo:
                private int total;
                private List<T> dataList;
         */
        PagenationVO<Activity> vo = activityService.getPageList(map);
        PrintJson.printJsonObj(response, vo);
    }

    //市场活动表单数据保存
    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Market activity form saving!");
        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        //创建时间: 当前系统时间
        String createTime = DateTimeUtil.getSysTime();
        //创建人: 当前登录用户
        String createBy = ((User) request.getSession().getAttribute("user")).getName();

        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean success = activityService.save(activity);
        PrintJson.printJsonFlag(response, success);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Get the user list!");
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> userList = userService.getUserList();
        PrintJson.printJsonObj(response, userList);
    }
}
