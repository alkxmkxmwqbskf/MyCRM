package com.codefish.workbench.dao;/**
 * @author codefish
 * @date 9/26/2021
 * @apinote
 */

/**
 * @author: codefish
 * @discription:
 */
public interface ActivityRemarkDao {
    Integer getCountByAids(String[] ids);

    Integer deleteByAids(String[] ids);
}
