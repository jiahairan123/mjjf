package com.mingjie.jf.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/8.
 */
public class UserVo {
    /**
     * msg : 成功
     * code : 000000
     * data : {"user":{"createdDate":1471861226000,"currentPage":1,"deleteFlag":0,"exkey":0,"id":"587dc86bb76f4e48b38a382d86008040","isForbitBid":0,"isForbitReceiptTransfer":0,"isForbitWithdraw":0,"isFundDepository":0,"jobCount":7,"name":"guyang","pageSize":6,"password":"3dc181885ba113d997c21ec5a6e4ace94bb385fc","phone":"13242038231","pollingTimes":0,"source":1,"sourceTerminal":"PC"},"token":"44a4fa7dc63944c991b5122d424fadb6"}
     * success : true
     */

    private String msg;
    private String code;
    /**
     * user : {"createdDate":1471861226000,"currentPage":1,"deleteFlag":0,"exkey":0,"id":"587dc86bb76f4e48b38a382d86008040","isForbitBid":0,"isForbitReceiptTransfer":0,"isForbitWithdraw":0,"isFundDepository":0,"jobCount":7,"name":"guyang","pageSize":6,"password":"3dc181885ba113d997c21ec5a6e4ace94bb385fc","phone":"13242038231","pollingTimes":0,"source":1,"sourceTerminal":"PC"}
     * token : 44a4fa7dc63944c991b5122d424fadb6
     */

    private DataBean data;
    private boolean success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean implements Serializable{
        /**
         * createdDate : 1471861226000
         * currentPage : 1
         * deleteFlag : 0
         * exkey : 0
         * id : 587dc86bb76f4e48b38a382d86008040
         * isForbitBid : 0
         * isForbitReceiptTransfer : 0
         * isForbitWithdraw : 0
         * isFundDepository : 0
         * jobCount : 7
         * name : guyang
         * pageSize : 6
         * password : 3dc181885ba113d997c21ec5a6e4ace94bb385fc
         * phone : 13242038231
         * pollingTimes : 0
         * source : 1
         * sourceTerminal : PC
         */

        private static final long serialVersionUID = -352823489429210980L;
        private UserBean user;
        // private String token;

        private String sessionId;

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getIsNeedVcode()
        {
            return isNeedVcode;
        }

        public void setIsNeedVcode(String isNeedVcode)
        {
            this.isNeedVcode = isNeedVcode;
        }

        private String isNeedVcode;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean implements Serializable {
            private static final long serialVersionUID = -352823489429213985L;
            private long createdDate;
            //private int currentPage;
            //private int deleteFlag;
            //private int pageSize;
            // private int exkey;
            private String id;
            private String  wealthManagerFlg;

            public static long getSerialVersionUID() {
                return serialVersionUID;
            }

            public String getWealthManagerFlg() {
                return wealthManagerFlg;
            }

            public void setWealthManagerFlg(String wealthManagerFlg) {
                this.wealthManagerFlg = wealthManagerFlg;
            }

            /**
             * 是否开通存管0-未禁止,1-禁止
             */
            private int isFundDepository;

            /**
             * 是否禁止投标0-未禁止,1-禁止
             */
            private int isForbitBid;

            /**
             * 是否禁止提现0-未禁止,1-禁止
             */
            private int isForbitWithdraw;

            /**
             * 是否禁止债权转让0-未禁止,1-禁止
             */
            private int isForbitReceiptTransfer;
            private int jobCount;
            private String name;
            private String password;
            private String phone;
            private int pollingTimes;
            private int source;
            private String sourceTerminal;
            private String email;
            private String refcode;

            public String getRefcode() {
                return refcode;
            }

            public void setRefcode(String refcode) {
                this.refcode = refcode;
            }

            public String getEmail()
            {
                return email;
            }

            public void setEmail(String email)
            {
                this.email = email;
            }

            public long getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(long createdDate) {
                this.createdDate = createdDate;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public int getJobCount() {
                return jobCount;
            }

            public void setJobCount(int jobCount) {
                this.jobCount = jobCount;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
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
        }
    }

}
