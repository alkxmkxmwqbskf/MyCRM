package com.codefish.settings.service;/**
 * @author codefish
 * @date 10/6/2021
 * @apinote
 */

import com.codefish.settings.domain.DictValue;

import java.util.List;
import java.util.Map;

/**
 * @author: codefish
 * @discription:
 */
public interface DictService {
    Map<String, List<DictValue>> getAll();
}
