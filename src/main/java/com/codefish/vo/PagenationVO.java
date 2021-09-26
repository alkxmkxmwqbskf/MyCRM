package com.codefish.vo;/**
 * @author codefish
 * @date 9/26/2021
 * @apinote
 */

import java.util.List;

/**
 * @author: codefish
 * @discription:
 */
public class PagenationVO<T>{
    private int total;
    private List<T> dataList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
