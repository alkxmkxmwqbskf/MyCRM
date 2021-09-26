package com.codefish.workbench.service;/**
 * @author codefish
 * @date 9/25/2021
 * @apinote
 */

import com.codefish.vo.PagenationVO;
import com.codefish.workbench.domain.Activity;

import java.util.Map;

/**
 * @author: codefish
 * @discription:
 */
public interface ActivityService {
    boolean save(Activity activity);

    PagenationVO<Activity> getPageList(Map<String, Object> map);
}
