package com.mingjie.jf.bean;

import java.util.List;

/**
 * Created by jiahairan on 2017/8/7 09.
 * 新的第二层bean
 */

public class NewBaseListBean <T>
{

    public int pageCurrent;//当前页码
    public int pageSize;//单页条数
    public int pageTotal;//总条数
    public int pages;

    public List<T> list;//数据

    public NewBaseListBean(int pageCurrent, int pageSize, int pageTotal, int pages, List<T> list) {
        this.pageCurrent = pageCurrent;
        this.pageSize = pageSize;
        this.pageTotal = pageTotal;
        this.pages = pages;
        this.list = list;
    }

    @Override
    public String toString() {
        return "NewBaseListBean{" +
                "pageCurrent=" + pageCurrent +
                ", pageSize=" + pageSize +
                ", pageTotal=" + pageTotal +
                ", pages=" + pages +
                ", list=" + list +
                '}';
    }

    public int getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(int pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}

