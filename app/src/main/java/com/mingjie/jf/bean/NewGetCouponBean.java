package com.mingjie.jf.bean;

import java.util.List;

/**
 * Created by jiahairan on 2017/12/11 12.
 */

public class NewGetCouponBean {

    private String isTime;

    public String getIsTime() {
        return isTime;
    }

    public void setIsTime(String isTime) {
        this.isTime = isTime;
    }

    private List<GetCouponBean1> list;

    public List<GetCouponBean1> getList() {
        return list;
    }

    public void setList(List<GetCouponBean1> list) {
        this.list = list;
    }

    public static class GetCouponBean1 {

        public GetCouponBean1(String assetName, String createDate, String currentPage, String dayUserRange, String stringdeleteFlag, String effectiveTime, String expireTime, String iDisplayLength, String iDisplayStart, String id, String maxInvestmentAmountStr, String minInvestmentAmountStr, String monthUserRange, String name, String numberStr, String pageSize, String quantityStr, String rewardName, String rewardType, String sourceTerminal, String type, String version, String fixedAmountStr, String investmentPercentStr) {
            this.assetName = assetName;
            this.createDate = createDate;
            this.currentPage = currentPage;
            this.dayUserRange = dayUserRange;
            StringdeleteFlag = stringdeleteFlag;
            this.effectiveTime = effectiveTime;
            this.expireTime = expireTime;
            this.iDisplayLength = iDisplayLength;
            this.iDisplayStart = iDisplayStart;
            this.id = id;
            this.maxInvestmentAmountStr = maxInvestmentAmountStr;
            this.minInvestmentAmountStr = minInvestmentAmountStr;
            this.monthUserRange = monthUserRange;
            this.name = name;
            this.numberStr = numberStr;
            this.pageSize = pageSize;
            this.quantityStr = quantityStr;
            this.rewardName = rewardName;
            this.rewardType = rewardType;
            this.sourceTerminal = sourceTerminal;
            this.type = type;
            this.version = version;
            this.fixedAmountStr = fixedAmountStr;
            this.investmentPercentStr = investmentPercentStr;
        }

        String assetName;
        String createDate;
        String currentPage;
        String dayUserRange;
        String StringdeleteFlag;
        String effectiveTime;
        String expireTime;
        String iDisplayLength;
        String iDisplayStart;
        String id;
        String maxInvestmentAmountStr;
        String minInvestmentAmountStr;
        String monthUserRange;
        String name;
        String numberStr;
        String pageSize;
        String quantityStr;
        String rewardName;
        String rewardType;
        String sourceTerminal;
        String type; //1 现金券 2 加息券
        String version;
        String fixedAmountStr;
        String investmentPercentStr;

        public String getAssetName() {
            return assetName;
        }

        public void setAssetName(String assetName) {
            this.assetName = assetName;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(String currentPage) {
            this.currentPage = currentPage;
        }

        public String getDayUserRange() {
            return dayUserRange;
        }

        public void setDayUserRange(String dayUserRange) {
            this.dayUserRange = dayUserRange;
        }

        public String getStringdeleteFlag() {
            return StringdeleteFlag;
        }

        public void setStringdeleteFlag(String stringdeleteFlag) {
            StringdeleteFlag = stringdeleteFlag;
        }

        public String getEffectiveTime() {
            return effectiveTime;
        }

        public void setEffectiveTime(String effectiveTime) {
            this.effectiveTime = effectiveTime;
        }

        public String getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(String expireTime) {
            this.expireTime = expireTime;
        }

        public String getiDisplayLength() {
            return iDisplayLength;
        }

        public void setiDisplayLength(String iDisplayLength) {
            this.iDisplayLength = iDisplayLength;
        }

        public String getiDisplayStart() {
            return iDisplayStart;
        }

        public void setiDisplayStart(String iDisplayStart) {
            this.iDisplayStart = iDisplayStart;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMaxInvestmentAmountStr() {
            return maxInvestmentAmountStr;
        }

        public void setMaxInvestmentAmountStr(String maxInvestmentAmountStr) {
            this.maxInvestmentAmountStr = maxInvestmentAmountStr;
        }

        public String getMinInvestmentAmountStr() {
            return minInvestmentAmountStr;
        }

        public void setMinInvestmentAmountStr(String minInvestmentAmountStr) {
            this.minInvestmentAmountStr = minInvestmentAmountStr;
        }

        public String getMonthUserRange() {
            return monthUserRange;
        }

        public void setMonthUserRange(String monthUserRange) {
            this.monthUserRange = monthUserRange;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumberStr() {
            return numberStr;
        }

        public void setNumberStr(String numberStr) {
            this.numberStr = numberStr;
        }

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }

        public String getQuantityStr() {
            return quantityStr;
        }

        public void setQuantityStr(String quantityStr) {
            this.quantityStr = quantityStr;
        }

        public String getRewardName() {
            return rewardName;
        }

        public void setRewardName(String rewardName) {
            this.rewardName = rewardName;
        }

        public String getRewardType() {
            return rewardType;
        }

        public void setRewardType(String rewardType) {
            this.rewardType = rewardType;
        }

        public String getSourceTerminal() {
            return sourceTerminal;
        }

        public void setSourceTerminal(String sourceTerminal) {
            this.sourceTerminal = sourceTerminal;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getFixedAmountStr() {
            return fixedAmountStr;
        }

        public void setFixedAmountStr(String fixedAmountStr) {
            this.fixedAmountStr = fixedAmountStr;
        }

        public String getInvestmentPercentStr() {
            return investmentPercentStr;
        }

        public void setInvestmentPercentStr(String investmentPercentStr) {
            this.investmentPercentStr = investmentPercentStr;
        }
    }

}
