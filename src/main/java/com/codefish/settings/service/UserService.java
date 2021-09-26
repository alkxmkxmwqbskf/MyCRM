package com.codefish.settings.service;/**
 * @author codefish
 * @date 9/23/2021
 * @apinote
 */

import com.codefish.settings.domain.User;
import com.codefish.exception.LoginException;

import java.util.List;

/**
 * @author: codefish
 * @discription:
 */
public interface UserService {
    User login(String userName, String passWord, String ip) throws LoginException;

    List<User> getUserList();
}
