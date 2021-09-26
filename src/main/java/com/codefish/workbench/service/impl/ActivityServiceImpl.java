package com.codefish.workbench.service.impl;/**
 * @author codefish
 * @date 9/25/2021
 * @apinote
 */

import com.codefish.util.SqlSessionUtil;
import com.codefish.vo.PagenationVO;
import com.codefish.workbench.dao.ActivityDao;
import com.codefish.workbench.domain.Activity;
import com.codefish.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

/**
 * @author: codefish
 * @discription:
 */
public class ActivityServiceImpl implements ActivityService {

    @Override
    public boolean save(Activity activity) {
        ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
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
        ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
        //获取total
        int total = activityDao.getTotalByCondition(map);
        //取得dataList
        List<Activity> activityList = activityDao.getActivityListByCondition(map);
        PagenationVO<Activity> vo = new PagenationVO<>();
        vo.setTotal(total);
        vo.setDataList(activityList);
        return vo;
    }
}
