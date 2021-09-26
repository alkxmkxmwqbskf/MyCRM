package com.codefish.workbench.dao;/**
 * @author codefish
 * @date 9/25/2021
 * @apinote
 */

import com.codefish.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * @author: codefish
 * @discription:
 */
public interface ActivityDao {
    int save(Activity activity);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);
}
