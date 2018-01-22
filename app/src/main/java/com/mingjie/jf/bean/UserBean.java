package com.mingjie.jf.bean;

import java.io.Serializable;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： xxxx
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-09-27 19:50
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class UserBean implements Serializable {

    private static final long serialVersionUID = -352823489429210993L;
    private String acno;//存管账号
    private long createdDate;
    //private int currentPage;
    //private int deleteFlag;
    private String email;//邮箱
    // private int exkey;
    private String id;
    private String idnumber;//身份证号码
    private int isForbitBid;//
    private int isForbitReceiptTransfer;
    private int isForbitWithdraw;
    private int isFundDepository;//是否开通存管（1--开通，0--未开通）
    private int isOpenAutoBid;
    private int isOpenAutoRepayment;
    private int loginErrorCount;
    private String name;
    private int oriReferrer;
    private int oriUserid;
    //private int pageSize;
    //private String password;
    private String phone;
    private int pollingTimes;
    private String realname;//真实姓名
    private String reqseqno;
    private int source;
    private String sourceTerminal;
    private int userType;
    private long version;
    private String serviceId;//客服id
    private String serviceName;//客服姓名
    private String wealthManagerFlg; //是否是集团员工 1是 0否

    public UserBean(String acno, long createdDate, String email, String id, String idnumber, int isForbitBid, int isForbitReceiptTransfer, int isForbitWithdraw, int isFundDepository, int isOpenAutoBid, int isOpenAutoRepayment, int loginErrorCount, String name, int oriReferrer, int oriUserid, String phone, int pollingTimes, String realname, String reqseqno, int source, String sourceTerminal, int userType, long version, String serviceId, String serviceName, String wealthManagerFlg) {
        this.acno = acno;
        this.createdDate = createdDate;
        this.email = email;
        this.id = id;
        this.idnumber = idnumber;
        this.isForbitBid = isForbitBid;
        this.isForbitReceiptTransfer = isForbitReceiptTransfer;
        this.isForbitWithdraw = isForbitWithdraw;
        this.isFundDepository = isFundDepository;
        this.isOpenAutoBid = isOpenAutoBid;
        this.isOpenAutoRepayment = isOpenAutoRepayment;
        this.loginErrorCount = loginErrorCount;
        this.name = name;
        this.oriReferrer = oriReferrer;
        this.oriUserid = oriUserid;
        this.phone = phone;
        this.pollingTimes = pollingTimes;
        this.realname = realname;
        this.reqseqno = reqseqno;
        this.source = source;
        this.sourceTerminal = sourceTerminal;
        this.userType = userType;
        this.version = version;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.wealthManagerFlg = wealthManagerFlg;
    }

    public String getWealthManagerFlg() {
        return wealthManagerFlg;
    }

    public void setWealthManagerFlg(String wealthManagerFlg) {
        this.wealthManagerFlg = wealthManagerFlg;
    }

    @Override

    public String toString() {
        return "UserBean{" +
                "id='" + id + '\'' +
                ", acno='" + acno + '\'' +
                ", createdDate=" + createdDate +
                ", email='" + email + '\'' +
                ", idnumber='" + idnumber + '\'' +
                ", isForbitBid=" + isForbitBid +
                ", isForbitReceiptTransfer=" + isForbitReceiptTransfer +
                ", isForbitWithdraw=" + isForbitWithdraw +
                ", isFundDepository=" + isFundDepository +
                ", isOpenAutoBid=" + isOpenAutoBid +
                ", isOpenAutoRepayment=" + isOpenAutoRepayment +
                ", loginErrorCount=" + loginErrorCount +
                ", name='" + name + '\'' +
                ", oriReferrer=" + oriReferrer +
                ", oriUserid=" + oriUserid +
                ", phone='" + phone + '\'' +
                ", pollingTimes=" + pollingTimes +
                ", realname='" + realname + '\'' +
                ", reqseqno='" + reqseqno + '\'' +
                ", source=" + source +
                ", sourceTerminal='" + sourceTerminal + '\'' +
                ", userType=" + userType +
                ", version=" + version +
                ", serviceId='" + serviceId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                '}';
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public int getIsForbitBid() {
        return isForbitBid;
    }

    public void setIsForbitBid(int isForbitBid) {
        this.isForbitBid = isForbitBid;
    }

    public int getIsForbitReceiptTransfer() {
        return isForbitReceiptTransfer;
    }

    public void setIsForbitReceiptTransfer(int isForbitReceiptTransfer) {
        this.isForbitReceiptTransfer = isForbitReceiptTransfer;
    }

    public int getIsForbitWithdraw() {
        return isForbitWithdraw;
    }

    public void setIsForbitWithdraw(int isForbitWithdraw) {
        this.isForbitWithdraw = isForbitWithdraw;
    }

    public int getIsFundDepository() {
        return isFundDepository;
    }

    public void setIsFundDepository(int isFundDepository) {
        this.isFundDepository = isFundDepository;
    }

    public int getIsOpenAutoBid() {
        return isOpenAutoBid;
    }

    public void setIsOpenAutoBid(int isOpenAutoBid) {
        this.isOpenAutoBid = isOpenAutoBid;
    }

    public int getIsOpenAutoRepayment() {
        return isOpenAutoRepayment;
    }

    public void setIsOpenAutoRepayment(int isOpenAutoRepayment) {
        this.isOpenAutoRepayment = isOpenAutoRepayment;
    }

    public int getLoginErrorCount() {
        return loginErrorCount;
    }

    public void setLoginErrorCount(int loginErrorCount) {
        this.loginErrorCount = loginErrorCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOriReferrer() {
        return oriReferrer;
    }

    public void setOriReferrer(int oriReferrer) {
        this.oriReferrer = oriReferrer;
    }

    public int getOriUserid() {
        return oriUserid;
    }

    public void setOriUserid(int oriUserid) {
        this.oriUserid = oriUserid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPollingTimes() {
        return pollingTimes;
    }

    public void setPollingTimes(int pollingTimes) {
        this.pollingTimes = pollingTimes;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getReqseqno() {
        return reqseqno;
    }

    public void setReqseqno(String reqseqno) {
        this.reqseqno = reqseqno;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getSourceTerminal() {
        return sourceTerminal;
    }

    public void setSourceTerminal(String sourceTerminal) {
        this.sourceTerminal = sourceTerminal;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
