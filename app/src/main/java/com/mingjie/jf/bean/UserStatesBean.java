package com.mingjie.jf.bean;

import java.io.Serializable;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： xxxx
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-09-27 19:53
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class UserStatesBean implements Serializable
{
    private static final long serialVersionUID = -352823489429210992L;
    private long createDate;
    private String id;
    private int isForbitBid;
    private int isForbitReceiptTransfer;
    private int isForbitWithdraw;
    private int isFundDepository;
    private int isOpenAutoBid;
    private int isOpenAutoRepayment;
    private String userId;
    private long version;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getWealthManagerFlg() {
        return wealthManagerFlg;
    }

    public void setWealthManagerFlg(String wealthManagerFlg) {
        this.wealthManagerFlg = wealthManagerFlg;
    }

    private String wealthManagerFlg; //是否是集团员工 1是 0否

    @Override
    public String toString()
    {
        return "UserStatesBean{" +
                "id='" + id + '\'' +
                ", createDate=" + createDate +
                ", isForbitBid=" + isForbitBid +
                ", isForbitReceiptTransfer=" + isForbitReceiptTransfer +
                ", isForbitWithdraw=" + isForbitWithdraw +
                ", isFundDepository=" + isFundDepository +
                ", isOpenAutoBid=" + isOpenAutoBid +
                ", isOpenAutoRepayment=" + isOpenAutoRepayment +
                ", userId='" + userId + '\'' +
                ", version=" + version +
                '}';
    }

    public long getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(long createDate)
    {
        this.createDate = createDate;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public int getIsForbitBid()
    {
        return isForbitBid;
    }

    public void setIsForbitBid(int isForbitBid)
    {
        this.isForbitBid = isForbitBid;
    }

    public int getIsForbitReceiptTransfer()
    {
        return isForbitReceiptTransfer;
    }

    public void setIsForbitReceiptTransfer(int isForbitReceiptTransfer)
    {
        this.isForbitReceiptTransfer = isForbitReceiptTransfer;
    }

    public int getIsForbitWithdraw()
    {
        return isForbitWithdraw;
    }

    public void setIsForbitWithdraw(int isForbitWithdraw)
    {
        this.isForbitWithdraw = isForbitWithdraw;
    }

    public int getIsFundDepository()
    {
        return isFundDepository;
    }

    public void setIsFundDepository(int isFundDepository)
    {
        this.isFundDepository = isFundDepository;
    }

    public int getIsOpenAutoBid()
    {
        return isOpenAutoBid;
    }

    public void setIsOpenAutoBid(int isOpenAutoBid)
    {
        this.isOpenAutoBid = isOpenAutoBid;
    }

    public int getIsOpenAutoRepayment()
    {
        return isOpenAutoRepayment;
    }

    public void setIsOpenAutoRepayment(int isOpenAutoRepayment)
    {
        this.isOpenAutoRepayment = isOpenAutoRepayment;
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
