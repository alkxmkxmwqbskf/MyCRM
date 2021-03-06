package com.codefish.settings.dao;
/**
 * @author codefish
 * @date 9/23/2021
 * @apinote
 */

import com.codefish.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * @author: codefish
 * @discription:
 */
public interface UserDao {
    User login(Map<String, String> map);

    //获取UserList
    List<User> getUserList();
}
