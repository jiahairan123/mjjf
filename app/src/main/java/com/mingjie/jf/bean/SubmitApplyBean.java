package com.mingjie.jf.bean;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 提交申请 参数bean
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-23 17:12
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class SubmitApplyBean
{
    public String realName;     // 姓名
    public String phoneNumber;  // 电话
    public int sexint;          // 性别
    public int ageint;          // 年龄
    public String usages;       // 借款用途
    public String undertakeCompany;  // 承接公司
    public Double borrowAmount;       // 借款金额
    public String reserve1;          //图片验证码
    public String from = "android";

    public String getRealName()
    {
        return realName;
    }

    public void setRealName(String realName)
    {
        this.realName = realName;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public int getSexint()
    {
        return sexint;
    }

    public void setSexint(int sexint)
    {
        this.sexint = sexint;
    }

    public int getAgeint()
    {
        return ageint;
    }

    public void setAgeint(int ageint)
    {
        this.ageint = ageint;
    }

    public String getUsages()
    {
        return usages;
    }

    public void setUsages(String usages)
    {
        this.usages = usages;
    }

    public String getUndertakeCompany()
    {
        return undertakeCompany;
    }

    public void setUndertakeCompany(String undertakeCompany)
    {
        this.undertakeCompany = undertakeCompany;
    }

    public double getBorrowAmount()
    {
        return borrowAmount;
    }

    public void setBorrowAmount(Double borrowAmount)
    {
        this.borrowAmount = borrowAmount;
    }

    public String getReserve1()
    {
        return reserve1;
    }

    public void setReserve1(String reserve1)
    {
        this.reserve1 = reserve1;
    }
}
