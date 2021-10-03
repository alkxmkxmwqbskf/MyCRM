package com.codefish.workbench.service.impl;
/**
 * @author codefish
 * @date 9/25/2021
 * @apinote
 */

import com.codefish.settings.dao.UserDao;
import com.codefish.settings.domain.User;
import com.codefish.util.SqlSessionUtil;
import com.codefish.vo.PagenationVO;
import com.codefish.workbench.dao.ActivityDao;
import com.codefish.workbench.dao.ActivityRemarkDao;
import com.codefish.workbench.domain.Activity;
import com.codefish.workbench.domain.ActivityRemark;
import com.codefish.workbench.service.ActivityService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: codefish
 * @discription:
 */
public class ActivityServiceImpl implements ActivityService {
    UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    @Override
    public boolean save(Activity activity) {

        boolean flag = true;
        int count = activityDao.save(activity);
        if (count != 1){
            //此处更好的方式应该是抛出一个自定义异常;
            flag = false;
        }
        return flag;
    }

    @Override
    public PagenationVO<Activity> getPageList(Map<String, Object> map) {
        //ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
        //获取total
        int total = activityDao.getTotalByCondition(map);
        //取得dataList
        List<Activity> activityList = activityDao.getActivityListByCondition(map);
        PagenationVO<Activity> vo = new PagenationVO<>();
        vo.setTotal(total);
        vo.setDataList(activityList);
        return vo;
    }

    @Override
    public boolean delete(String[] ids) {
        boolean flag = true;
        //ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

        //关联删除活动评论
        Integer count = activityRemarkDao.getCountByAids(ids);
        System.out.println(count);
        //删除备注返回受影响的条数
        //int count = 0;
        int count_delete = activityRemarkDao.deleteByAids(ids);
        if (count != count_delete){
            flag = false;
        }
        int count_success = activityDao.delete(ids);
        if (count_success != ids.length){
            flag = false;
        }
        System.out.println(flag);
        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        //取uList and activity
        //UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
        //ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
        List<User> uList = userDao.getUserList();
        //根据活动id获取单条数据
        Activity activity = activityDao.getActivityById(id);

        Map<String, Object> map = new HashMap<>();
        map.put("uList", uList);
        map.put("activity", activity);
        return map;
    }

    @Override
    public boolean update(Activity activity) {
        //ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
        boolean flag = true;
        int count = activityDao.update(activity);
        if (count != 1){
            //此处更好的方式应该是抛出一个自定义异常;
            flag = false;
        }
        return flag;
    }

    @Override
    public Activity getDetail(String id) {
        //ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
        Activity activity = activityDao.getDetail(id);
        return activity;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String activityId) {
        //ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
        List<ActivityRemark> activityRemark = activityRemarkDao.getRemarkListByAid(activityId);
        return activityRemark;
    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag = true;
        //ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
        int success = activityRemarkDao.deleteRemark(id);

        if (success != 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark activityRemark) {
        boolean flag = true;
        ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
        int count = activityRemarkDao.saveRemark(activityRemark);
        if (count != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateRemark(ActivityRemark activityRemark) {
        boolean flag = true;
        /**
         * 为什么这边如果不将全局变量activityRemarkDao提下来就会报错
         */
        ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
        int account = activityRemarkDao.updateRemark(activityRemark);
        if (account != 1){
            flag = false;
        }
        return flag;
    }
}
