package com.codefish.service.impl;/**
 * @author codefish
 * @date 9/23/2021
 * @apinote
 */

import com.codefish.dao.UserDao;
import com.codefish.domain.User;
import com.codefish.service.UserService;
import com.codefish.util.SqlSessionUtil;

/**
 * @author: codefish
 * @discription:
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
}
