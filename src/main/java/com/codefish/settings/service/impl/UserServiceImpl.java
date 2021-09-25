package com.codefish.settings.service.impl;/**
 * @author codefish
 * @date 9/23/2021
 * @apinote
 */

import com.codefish.settings.dao.UserDao;
import com.codefish.settings.domain.User;
import com.codefish.exception.LoginException;
import com.codefish.settings.service.UserService;
import com.codefish.util.DateTimeUtil;
import com.codefish.util.SqlSessionUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: codefish
 * @discription:
 */
public class UserServiceImpl implements UserService {

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {

        UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

        Map<String, String> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);
        map.put("ip", ip);

        User user = userDao.login(map);
        if (user == null){
            throw new LoginException("账号密码错误!");
        }
        //执行到此, 账号密码正确, 继续向下验证
        //判定账号失效
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if (currentTime.compareTo(expireTime) > 0){
            throw new LoginException("账号已失效!");
        }

        //判定锁定状态
        String lockState = user.getLockState();
        if ("0".equals(lockState)){
            throw new LoginException("账号已锁定!");
        }

        //判断ip地址
        String allowIps = user.getAllowIps();
        if (allowIps != null && !"".equals(allowIps)){
            if (!allowIps.contains(ip)){
                throw new LoginException("IP地址受限!");
            }
        }
        return user;
    }
}
