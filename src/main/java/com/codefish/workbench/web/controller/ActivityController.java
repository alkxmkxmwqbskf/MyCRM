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
import com.codefish.workbench.domain.ActivityRemark;
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
        }else if("/workbench/activity/delete.do".equals(path)){
            //删除市场活动
            delete(request, response);
        }else if ("/workbench/activity/getUserListAndActivity.do".equals(path)){
            //同步市场活动数据到修改模态窗口
            getUserListAndActivity(request, response);
        }else if("/workbench/activity/update.do".equals(path)){
            //修改市场活动
            update(request, response);
        }else if("/workbench/activity/detail.do".equals(path)){
            //跳转到详情页
            getDetail(request, response);
        }else if("/workbench/activity/getRemarkListByAid.do".equals(path)){
            //获取备注
            getRemarkListByAid(request, response);
        }
    }

    private void getRemarkListByAid(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Get remark list!");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        String activityId = request.getParameter("activityId");
        List<ActivityRemark> remarkList = activityService.getRemarkListByAid(activityId);
        System.out.println(remarkList);
        PrintJson.printJsonObj(response, remarkList);
    }

    private void getDetail(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Enter the detail page");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        try {
            String id = request.getParameter("id");
            Activity activity = activityService.getDetail(id);
            System.out.println(activity);
            request.setAttribute("activity", activity);
            request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Execute the modify of activity info!");
        String id = request.getParameter("id");
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        //修改时间: 当前系统时间
        String editTime = DateTimeUtil.getSysTime();
        //修改人: 当前登录用户
        String editBy = ((User) request.getSession().getAttribute("user")).getName();

        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean success = activityService.update(activity);
        PrintJson.printJsonFlag(response, success);
    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Entered the query list and according the activity's id query single record!");
        String id = request.getParameter("id");
        System.out.println("ID:"+id);
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        /*
            uList activity
            map
         */

        Map<String, Object> map = activityService.getUserListAndActivity(id);
        PrintJson.printJsonObj(response, map);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        System.out.println("delete activity");
        String[] ids = request.getParameterValues("id");
        boolean success = activityService.delete(ids);
        System.out.println("success:"+success);
        PrintJson.printJsonFlag(response, success);
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
