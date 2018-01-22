package com.mingjie.jf.bean;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： xxxx
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-11-24 19:22
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class CaseCardBean
{

    /**
     * cardNo : 1115
     * city : 长春市
     * createdDate : 1479890653000
     * currentPage : 1
     * deleteFlag : 0
     * id : a4b8b4f330f44b0b877e0ff489613050
     * isDefault : 1
     * pageSize : 6
     * province : 吉林省
     * realName : 孟**
     * sourceTerminal : PC
     * state : 1
     * userId : 8f113438b66244249e398300978149d1
     * version : 1479890653000
     */

    private String cardNo;

    public String getBankNameByNo()
    {
        return bankNameByNo;
    }

    public void setBankNameByNo(String bankNameByNo)
    {
        this.bankNameByNo = bankNameByNo;
    }

    private String bankNameByNo;
    private String city;
    private long createdDate;
    private int currentPage;
    private int deleteFlag;
    private String id;
    private int isDefault;
    private int pageSize;
    private String province;
    private String realName;
    private String sourceTerminal;
    private int state;
    private String userId;
    private long version;

    public String getCardNo()
    {
        return cardNo;
    }

    public void setCardNo(String cardNo)
    {
        this.cardNo = cardNo;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public long getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(long createdDate)
    {
        this.createdDate = createdDate;
    }

    public int getCurrentPage()
    {
        return currentPage;
    }

    public void setCurrentPage(int currentPage)
    {
        this.currentPage = currentPage;
    }

    public int getDeleteFlag()
    {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public int getIsDefault()
    {
        return isDefault;
    }

    public void setIsDefault(int isDefault)
    {
        this.isDefault = isDefault;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getRealName()
    {
        return realName;
    }

    public void setRealName(String realName)
    {
        this.realName = realName;
    }

    public String getSourceTerminal()
    {
        return sourceTerminal;
    }

    public void setSourceTerminal(String sourceTerminal)
    {
        this.sourceTerminal = sourceTerminal;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public long getVersion()
    {
        return version;
    }

    public void setVersion(long version)
    {
        this.version = version;
    }
}
