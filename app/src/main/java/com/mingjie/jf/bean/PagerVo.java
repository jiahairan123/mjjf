package com.mingjie.jf.bean;

import java.util.List;

/**
 * Created by zhangjin on 2016/8/22.
 */
public class PagerVo<T>
{
    public List<T> list;

    public int pageCurrent = 1; // 当前页数

    public int pageSize = 10; // 每页显示条数

    public long pageTotal; // 总页数

    @Override
    public String toString()
    {
        return "PagerVo{" +
                " pageCurrent=" + pageCurrent +
                ", pageSize=" + pageSize +
                ", pageTotal=" + pageTotal +
                '}';
    }
}
