package com.codefish.service;/**
 * @author codefish
 * @date 9/23/2021
 * @apinote
 */

import com.codefish.domain.User;
import com.codefish.exception.LoginException;

/**
 * @author: codefish
 * @discription:
 */
public interface UserService {
    User login(String userName, String passWord, String ip) throws LoginException;
}
