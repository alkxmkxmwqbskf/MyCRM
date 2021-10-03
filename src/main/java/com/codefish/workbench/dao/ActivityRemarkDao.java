package com.codefish.workbench.dao;/**
 * @author codefish
 * @date 9/26/2021
 * @apinote
 */

import com.codefish.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * @author: codefish
 * @discription:
 */
public interface ActivityRemarkDao {
    Integer getCountByAids(String[] ids);

    Integer deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    int deleteRemark(String id);

    int saveRemark(ActivityRemark activityRemark);

    int updateRemark(ActivityRemark activityRemark);
}
