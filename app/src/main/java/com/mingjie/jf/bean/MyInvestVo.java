package com.mingjie.jf.bean;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 我的账户-我的投资-请求所带对象
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-09-19 14:20
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class MyInvestVo
{
    public int pageSize;//单页长度
    public int currentPage;//当前页数
    public String status;//状态，用来区分是还款中0、招标中1，还是已完结2
    public String reserve1;//借用的备用字段，区分最新1 利率2 期限3 金额4
    public String reserve2;//借用的备用字段，用来标注排序方式 升序0 降序1

    /**
     * @param pageSize 单页长度
     * @param reserve2 借用的备用字段，用来标注排序方式 升序 降序
     * @param reserve1 借用的备用字段，区分最新 利率 期限 金额
     * @param status 状态，用来区分是还款中、招标中，还是已完结
     * @param currentPage 当前页数
     */
    public MyInvestVo(int pageSize, String reserve2, String reserve1, String status, int currentPage)
    {
        this.pageSize = pageSize;
        this.reserve2 = reserve2;
        this.reserve1 = reserve1;
        this.status = status;
        this.currentPage = currentPage;
    }

    public MyInvestVo()
    {
    }

    @Override
    public String toString()
    {
        return "MyInvestVo{" +
                "pageSize=" + pageSize +
                ", currentPage=" + currentPage +
                ", status='" + status + '\'' +
                ", reserve1='" + reserve1 + '\'' +
                ", reserve2='" + reserve2 + '\'' +
                '}';
    }
}
