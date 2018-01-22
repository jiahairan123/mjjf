package com.mingjie.jf.bean;

/**
 * Created by QinSiYi on 2016-8-24.
 */
public class Register {

    /**
     * msg : 成功
     * code : 000000
     * data : {"createdDate":1471999363679,"currentPage":1,"deleteFlag":0,"exkey":0,"id":"39d6d1def6c04c4791482f39cfe118e3","name":"qsy","pageSize":6,"password":"3dc181885ba113d997c21ec5a6e4ace94bb385fc","phone":"15111111111","pollingTimes":0,"sourceTerminal":"PC"}
     * success : true
     */

    private String msg;
    private String code;
    /**
     * createdDate : 1471999363679
     * currentPage : 1
     * deleteFlag : 0
     * exkey : 0
     * id : 39d6d1def6c04c4791482f39cfe118e3
     * name : qsy
     * pageSize : 6
     * password : 3dc181885ba113d997c21ec5a6e4ace94bb385fc
     * phone : 15111111111
     * pollingTimes : 0
     * sourceTerminal : PC
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

    public static class DataBean {
        private long createdDate;
        private int currentPage;
        private int deleteFlag;
        // private int exkey;
        private String id;
        private String name;//用户名
        private int pageSize;
        private String password;//密码
        private String phone;//电话
        private int pollingTimes;
        private String sourceTerminal;

        public long getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(long createdDate) {
            this.createdDate = createdDate;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getDeleteFlag() {
            return deleteFlag;
        }

        public void setDeleteFlag(int deleteFlag) {
            this.deleteFlag = deleteFlag;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
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

        public String getSourceTerminal() {
            return sourceTerminal;
        }

        public void setSourceTerminal(String sourceTerminal) {
            this.sourceTerminal = sourceTerminal;
        }
    }
}
