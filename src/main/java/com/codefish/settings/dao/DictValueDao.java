package com.codefish.settings.dao;/**
 * @author codefish
 * @date 10/6/2021
 * @apinote
 */

import com.codefish.settings.domain.DictValue;

import java.util.List;

/**
 * @author: codefish
 * @discription:
 */
public interface DictValueDao {
    List<DictValue> getListByCode(String code);
}
