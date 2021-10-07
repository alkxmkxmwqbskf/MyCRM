package com.codefish.workbench.service.impl;/**
 * @author codefish
 * @date 10/6/2021
 * @apinote
 */

import com.codefish.util.SqlSessionUtil;
import com.codefish.workbench.dao.ClueDao;
import com.codefish.workbench.service.ClueService;
import org.apache.ibatis.session.SqlSession;

/**
 * @author: codefish
 * @discription:
 */
public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);

}
