package com.codefish.settings.service.impl;/**
 * @author codefish
 * @date 10/6/2021
 * @apinote
 */

import com.codefish.settings.dao.DictTypeDao;
import com.codefish.settings.dao.DictValueDao;
import com.codefish.settings.domain.DictType;
import com.codefish.settings.domain.DictValue;
import com.codefish.settings.service.DictService;
import com.codefish.util.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: codefish
 * @discription:
 */
public class DictServiceImpl implements DictService {
    private DictTypeDao dictTypeDao = SqlSessionUtil.getSqlSession().getMapper(DictTypeDao.class);
    private DictValueDao dictValueDao = SqlSessionUtil.getSqlSession().getMapper(DictValueDao.class);

    @Override
    public Map<String, List<DictValue>> getAll() {
        Map<String, List<DictValue>> valueListMap = new HashMap<>();
        //取出字典类型
        List<DictType> dictTypeList = dictTypeDao.getTypeList();
        for (DictType dt: dictTypeList) {
            //取得每一种类型的字典类型的编码
            String code = dt.getCode();
            //根据每一个字典类型取得字典值列表
            List<DictValue> dictValueList = dictValueDao.getListByCode(code);
            valueListMap.put(code+"List", dictValueList);
        }
        System.out.println(valueListMap);
        return valueListMap;
    }
}
