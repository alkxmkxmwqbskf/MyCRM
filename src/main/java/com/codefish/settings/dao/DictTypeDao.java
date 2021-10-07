package com.codefish.settings.dao;/**
 * @author codefish
 * @date 10/6/2021
 * @apinote
 */

import com.codefish.settings.domain.DictType;

import java.util.List;

/**
 * @author: codefish
 * @discription:
 */
public interface DictTypeDao {
    List<DictType> getTypeList();
}
