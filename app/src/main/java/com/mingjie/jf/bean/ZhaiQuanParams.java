package com.mingjie.jf.bean;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 债权转让 所需参数
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-12 18:01
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ZhaiQuanParams
{

    private int pageSize;  //显示数量

    private int currentPage;  // 当前页数

    private int productType;  // 页面类型

    private String reserve1;  //升序降序类型

    public String getReserve1()
    {
        return reserve1;
    }

    public void setReserve1(String reserve1)
    {
        this.reserve1 = reserve1;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public int getCurrentPage()
    {
        return currentPage;
    }

    public void setCurrentPage(int currentPage)
    {
        this.currentPage = currentPage;
    }

    public int getProductType()
    {
        return productType;
    }

    public void setProductType(int productType)
    {
        this.productType = productType;
    }

    @Override
    public String toString()
    {
        return "ZhaiQuanParams{" +
                "pageSize=" + pageSize +
                ", currentPage=" + currentPage +
                ", productType=" + productType +
                ", reserve1='" + reserve1 + '\'' +
                '}';
    }


}
