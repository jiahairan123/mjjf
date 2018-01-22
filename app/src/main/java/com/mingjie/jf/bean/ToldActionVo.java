package com.mingjie.jf.bean;

/**
 * Created by QinSiYi on 2016-8-22.
 */
public class ToldActionVo {

    public int currentPage; // 当前页数
    public int pageSize ; // 每页显示条数

    public int getPageCurrent() {
        return currentPage;
    }

    public void setPageCurrent(int pageCurrent) {
        this.currentPage = pageCurrent;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString()
    {
        return "ToldActionVo{" +
                "currentPage='" + currentPage + '\'' +
                ", pageSize='" + pageSize + '\'' +
                '}';
    }
}
