package com.mingjie.jf.bean;

import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： ${TODO}
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-12-15 19:52
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class BaseListBean<T>
{

    public int pageCurrent;//当前页码
    public int pageSize;//单页条数
    public int pageTotal;//总条数
    public int pages;

    String usablePoint; //可用积分
    String frozePoint;  //冻结积分
    String convertedPoint; //已用积分

    public List<T> list;//数据

    public BaseListBean(int pageCurrent, int pageSize, int pageTotal, int pages, List<T> list)
    {
        this.pageCurrent = pageCurrent;
        this.pageSize = pageSize;
        this.pageTotal = pageTotal;
        this.pages = pages;
        this.list = list;
    }

    @Override
    public String toString() {
        return "BaseListBean{" +
                "pageCurrent=" + pageCurrent +
                ", pageSize=" + pageSize +
                ", pageTotal=" + pageTotal +
                ", pages=" + pages +
                ", usablePoint='" + usablePoint + '\'' +
                ", frozePoint='" + frozePoint + '\'' +
                ", convertedPoint='" + convertedPoint + '\'' +
                ", list=" + list +
                '}';
    }

    public BaseListBean(int pageCurrent, int pageSize, int pageTotal, int pages, String usablePoint, String frozePoint, String convertedPoint, List<T> list) {
        this.pageCurrent = pageCurrent;
        this.pageSize = pageSize;
        this.pageTotal = pageTotal;
        this.pages = pages;
        this.usablePoint = usablePoint;
        this.frozePoint = frozePoint;
        this.convertedPoint = convertedPoint;
        this.list = list;
    }

    public String getFrozePoint() {
        return frozePoint;
    }

    public void setFrozePoint(String frozePoint) {
        this.frozePoint = frozePoint;
    }

    public String getConvertedPoint() {
        return convertedPoint;
    }

    public void setConvertedPoint(String convertedPoint) {
        this.convertedPoint = convertedPoint;
    }

    public BaseListBean()
    {
    }

    public String getUsablePoint() {
        return usablePoint;
    }

    public void setUsablePoint(String usablePoint) {
        this.usablePoint = usablePoint;
    }

    public int getPageCurrent()
    {
        return pageCurrent;
    }

    public void setPageCurrent(int pageCurrent)
    {
        this.pageCurrent = pageCurrent;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public int getPageTotal()
    {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal)
    {
        this.pageTotal = pageTotal;
    }

    public int getPages()
    {
        return pages;
    }

    public void setPages(int pages)
    {
        this.pages = pages;
    }

    public List<T> getList()
    {
        return list;
    }

    public void setList(List<T> list)
    {
        this.list = list;
    }

}
