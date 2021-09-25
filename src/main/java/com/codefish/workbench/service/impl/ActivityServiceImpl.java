package com.codefish.workbench.service.impl;/**
 * @author codefish
 * @date 9/25/2021
 * @apinote
 */

import com.codefish.util.SqlSessionUtil;
import com.codefish.workbench.dao.ActivityDao;
import com.codefish.workbench.service.ActivityService;

/**
 * @author: codefish
 * @discription:
 */
public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

}
