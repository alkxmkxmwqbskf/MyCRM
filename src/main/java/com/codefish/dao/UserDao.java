package com.codefish.dao;
/**
 * @author codefish
 * @date 9/23/2021
 * @apinote
 */

import com.codefish.domain.User;

/**
 * @author: codefish
 * @discription:
 */
public interface UserDao {
    User selectUser(User user);
}
