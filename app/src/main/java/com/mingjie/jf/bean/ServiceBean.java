package com.mingjie.jf.bean;

import java.util.List;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： xxxx
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-09-08 14:32
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class ServiceBean
{

    /**
     * adminUserId : 88FC031DDEB341039CB980698D082736
     * attachmentId : FA483A93C90A44AA8065291CE23978E0
     * createdDate : 1428459679000
     * currentPage : 1
     * deleteFlag : 0
     * id : 67
     * motto : 乱步之舞，舞在存管，反反复复，逍遥自如。只要我们迈出双足，前方就是存管快线，相信存管是您正确的选择。
     * name : 存管小惠
     * pageSize : 6
     * path : http://192.168.99.122:8080/images/customer/FA483A93C90A44AA8065291CE23978E0.jpg
     * phone : 13692273552
     * qq : 1809989462
     * sourceTerminal : PC
     * state : 1
     * version : 1473493943000
     */

    private List<ListBean> list;

    public List<ListBean> getList()
    {
        return list;
    }

    public void setList(List<ListBean> list)
    {
        this.list = list;
    }

    public static class ListBean
    {
        private String adminUserId;
        private String attachmentId;
        private long createdDate;
        private int currentPage;
        private int deleteFlag;
        private String id;
        private String motto;
        private String name;
        private int pageSize;
        private String path;
        private String phone;
        private String qq;
        private String sourceTerminal;
        private String state;
        private long version;

        public String getAdminUserId()
        {
            return adminUserId;
        }

        public void setAdminUserId(String adminUserId)
        {
            this.adminUserId = adminUserId;
        }

        public String getAttachmentId()
        {
            return attachmentId;
        }

        public void setAttachmentId(String attachmentId)
        {
            this.attachmentId = attachmentId;
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

        public String getMotto()
        {
            return motto;
        }

        public void setMotto(String motto)
        {
            this.motto = motto;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public int getPageSize()
        {
            return pageSize;
        }

        public void setPageSize(int pageSize)
        {
            this.pageSize = pageSize;
        }

        public String getPath()
        {
            return path;
        }

        public void setPath(String path)
        {
            this.path = path;
        }

        public String getPhone()
        {
            return phone;
        }

        public void setPhone(String phone)
        {
            this.phone = phone;
        }

        public String getQq()
        {
            return qq;
        }

        public void setQq(String qq)
        {
            this.qq = qq;
        }

        public String getSourceTerminal()
        {
            return sourceTerminal;
        }

        public void setSourceTerminal(String sourceTerminal)
        {
            this.sourceTerminal = sourceTerminal;
        }

        public String getState()
        {
            return state;
        }

        public void setState(String state)
        {
            this.state = state;
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
