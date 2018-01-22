package com.mingjie.jf.bean;

/**
 * Created by jiahairan on 2017/8/23 14.
 * <p>
 * "createDate":1503045632000,
 * "currentPage":1,
 * "dayUserRange":"1-365",
 * "deleteFlag":0,
 * "effectiveTime":"10:00:00",
 * "expireTime":"23:59:59",
 * "iDisplayLength":10,
 * "iDisplayStart":0,
 * "id":"792fe04a9f874e19a7261811942d59c5",
 * "maxInvestmentAmount":100000.00,
 * "minInvestmentAmount":100.00,
 * "monthUserRange":"1-12",
 * "name":"抢券活动",
 * "number":32,
 * "pageSize":6,
 * "quantity":36,
 * "rewardName":"现金券测试003",
 * "rewardType":"51be1766f7aa45a1bfba7926ba724b17",
 * "sourceTerminal":"PC",
 * "type":"1",
 * "version":1503045640000
 */

public class GetCouponBean {

    String assetName;

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

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

    public GetCouponBean(String createDate, String currentPage, String dayUserRange, String stringdeleteFlag, String effectiveTime, String expireTime, String iDisplayLength, String iDisplayStart, String id, String maxInvestmentAmountStr, String minInvestmentAmountStr, String monthUserRange, String name, String numberStr, String pageSize, String quantityStr, String rewardName, String rewardType, String sourceTerminal, String type, String version, String fixedAmountStr, String investmentPercentStr) {
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

    @Override
    public String toString() {
        return "GetCouponBean{" +
                "createDate='" + createDate + '\'' +
                ", currentPage='" + currentPage + '\'' +
                ", dayUserRange='" + dayUserRange + '\'' +
                ", StringdeleteFlag='" + StringdeleteFlag + '\'' +
                ", effectiveTime='" + effectiveTime + '\'' +
                ", expireTime='" + expireTime + '\'' +
                ", iDisplayLength='" + iDisplayLength + '\'' +
                ", iDisplayStart='" + iDisplayStart + '\'' +
                ", id='" + id + '\'' +
                ", maxInvestmentAmountStr='" + maxInvestmentAmountStr + '\'' +
                ", minInvestmentAmountStr='" + minInvestmentAmountStr + '\'' +
                ", monthUserRange='" + monthUserRange + '\'' +
                ", name='" + name + '\'' +
                ", numberStr='" + numberStr + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", quantityStr='" + quantityStr + '\'' +
                ", rewardName='" + rewardName + '\'' +
                ", rewardType='" + rewardType + '\'' +
                ", sourceTerminal='" + sourceTerminal + '\'' +
                ", type='" + type + '\'' +
                ", version='" + version + '\'' +
                ", fixedAmountStr='" + fixedAmountStr + '\'' +
                ", investmentPercentStr='" + investmentPercentStr + '\'' +
                '}';
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
