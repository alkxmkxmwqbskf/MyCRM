package com.codefish.workbench.service.impl;/**
 * @author codefish
 * @date 9/25/2021
 * @apinote
 */

import com.codefish.util.SqlSessionUtil;
import com.codefish.workbench.dao.ActivityDao;
import com.codefish.workbench.domain.Activity;
import com.codefish.workbench.service.ActivityService;

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
}
