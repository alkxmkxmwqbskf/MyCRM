package com.codefish.dao;
/**
 * @author codefish
 * @date 9/23/2021
 * @apinote
 */

import com.codefish.domain.User;

import java.util.Map;

/**
 * @author: codefish
 * @discription:
 */
public interface UserDao {
    User login(Map<String, String> map);
}
