package com.mingjie.jf.bean;

import java.util.List;

/**
 * Created by QinSiYi on 2016-8-22.
 */
public class AnnounceMent
{

    private String msg;
    private String code;
    private boolean success;

    private DataBean data;

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        public int pageCurrent;
        public int pageSize;
        public int pageTotal;
        public int pages;
        public List<ListBean> list;
        public int getPageCurrent()
        {
            return pageCurrent;
        }

        public void setPageCurrent(int pageCurrent)
        {
            this.pageCurrent = pageCurrent;
        }

        public int getPageSize()
        {
            return pageSize;
        }

        public void setPageSize(int pageSize)
        {
            this.pageSize = pageSize;
        }

        public int getPageTotal()
        {
            return pageTotal;
        }

        public void setPageTotal(int pageTotal)
        {
            this.pageTotal = pageTotal;
        }

        public int getPages()
        {
            return pages;
        }

        public void setPages(int pages)
        {
            this.pages = pages;
        }

        public List<ListBean> getList()
        {
            return list;
        }

        public void setList(List<ListBean> list)
        {
            this.list = list;
        }
//        private String id;
//        private  String url;
//        private long createdDate;
//        private String title;
//        public long getCreatedDate()
//        {
//            return createdDate;
//        }
//
//        public void setCreatedDate(long createdDate)
//        {
//            this.createdDate = createdDate;
//        }
//
//
//        public String getUrl()
//        {
//            return url;
//        }
//
//        public void setUrl(String url)
//        {
//            this.url = url;
//        }
//
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }

        public static class ListBean {
            private String id;
            private String url;
            private long createdDate;

            public String getTitle()
            {
                return title;
            }

            public void setTitle(String title)
            {
                this.title = title;
            }

            public String getId()
            {
                return id;
            }

            public void setId(String id)
            {
                this.id = id;
            }

            public String getUrl()
            {
                return url;
            }

            public void setUrl(String url)
            {
                this.url = url;
            }

            public long getCreatedDate()
            {
                return createdDate;
            }

            public void setCreatedDate(long createdDate)
            {
                this.createdDate = createdDate;
            }

            private String title;
        }
    }
}
