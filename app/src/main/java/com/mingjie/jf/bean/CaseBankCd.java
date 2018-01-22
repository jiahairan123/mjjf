package com.mingjie.jf.bean;

/**
 * 返回的用户银行卡信息
 */
public class CaseBankCd
{

    /**
     * max_withdraw_amount : 1000
     * withdraw_number : 5
     * walletBankcard : {"bankNameByNo":"上海银行","bankNo":"12","cardNo":"4646","city":"市辖区",
     * "createdDate":1480063419000,"currentPage":1,"deleteFlag":0,"id":"0ad95f0968554215bb034957291a3ec2",
     * "isDefault":1,"pageSize":6,"province":"北京市","realName":"路**","sourceTerminal":"PC","state":1,
     * "userId":"ec0590646b1844c9bbac6b6834c8f898","version":1480063419000}
     * min_withdraw_amount : 10
     */


    private double max_withdraw_amount;
    private int withdraw_number;
    /**
     * bankNameByNo : 上海银行
     * bankNo : 12
     * cardNo : 4646
     * city : 市辖区
     * createdDate : 1480063419000
     * currentPage : 1
     * deleteFlag : 0
     * id : 0ad95f0968554215bb034957291a3ec2
     * isDefault : 1
     * pageSize : 6
     * province : 北京市
     * realName : 路**
     * sourceTerminal : PC
     * state : 1
     * userId : ec0590646b1844c9bbac6b6834c8f898
     * version : 1480063419000
     */

    private WalletBankcardBean walletBankcard;
    private double min_withdraw_amount;

    public double getMax_withdraw_amount()
    {
        return max_withdraw_amount;
    }

    public void setMax_withdraw_amount(double max_withdraw_amount)
    {
        this.max_withdraw_amount = max_withdraw_amount;
    }

    public int getWithdraw_number()
    {
        return withdraw_number;
    }

    public void setWithdraw_number(int withdraw_number)
    {
        this.withdraw_number = withdraw_number;
    }

    public WalletBankcardBean getWalletBankcard()
    {
        return walletBankcard;
    }

    public void setWalletBankcard(WalletBankcardBean walletBankcard)
    {
        this.walletBankcard = walletBankcard;
    }

    public double getMin_withdraw_amount()
    {
        return min_withdraw_amount;
    }

    public void setMin_withdraw_amount(double min_withdraw_amount)
    {
        this.min_withdraw_amount = min_withdraw_amount;
    }

    public static class WalletBankcardBean
    {
        private String bankNameByNo;
        private String bankNo;
        private String cardNo;
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

        public String getBankNameByNo()
        {
            return bankNameByNo;
        }

        public void setBankNameByNo(String bankNameByNo)
        {
            this.bankNameByNo = bankNameByNo;
        }

        public String getBankNo()
        {
            return bankNo;
        }

        public void setBankNo(String bankNo)
        {
            this.bankNo = bankNo;
        }

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
}
