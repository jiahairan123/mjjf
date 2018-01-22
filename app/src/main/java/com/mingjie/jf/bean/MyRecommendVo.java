package com.mingjie.jf.bean;

import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 我的推荐返回数据bean
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-24 15:01
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class MyRecommendVo
{

    /**
     * code : 000000
     * data : {"list":[{"bidAmount":0,"currentPage":1,"deleteFlag":0,"name":"wenzhenmei","pageSize":6,"regisDate":1472646691000,"relateDesc":"直接推荐","userId":"74db73ca772b11e686dc6c92bf20d033"},{"bidAmount":0,"currentPage":1,"deleteFlag":0,"name":"yangguifang","pageSize":6,"regisDate":1472041252000,"relateDesc":"间接推荐","userId":"74da72bd772b11e686dc6c92bf20d033"},{"bidAmount":0,"currentPage":1,"deleteFlag":0,"name":"chengao","pageSize":6,"regisDate":1471916580000,"relateDesc":"直接推荐","userId":"74da39e3772b11e686dc6c92bf20d033"},{"bidAmount":0,"currentPage":1,"deleteFlag":0,"name":"这里有个坑","pageSize":6,"regisDate":1467192577000,"relateDesc":"直接推荐","userId":"74bb2b8d772b11e686dc6c92bf20d033"},{"bidAmount":0,"currentPage":1,"deleteFlag":0,"name":"爱相随","pageSize":6,"regisDate":1462436762000,"relateDesc":"直接推荐","userId":"744fb3d5772b11e686dc6c92bf20d033"},{"bidAmount":0,"currentPage":1,"deleteFlag":0,"name":"cammyzheng","pageSize":6,"regisDate":1462418496000,"relateDesc":"直接推荐","userId":"744fa8e4772b11e686dc6c92bf20d033"}],"pageCurrent":1,"pageSize":10,"pageTotal":6,"pages":1}
     * msg : 成功
     * success : true
     */

    public String code;
    /**
     * list : [{"bidAmount":0,"currentPage":1,"deleteFlag":0,"name":"wenzhenmei","pageSize":6,"regisDate":1472646691000,"relateDesc":"直接推荐","userId":"74db73ca772b11e686dc6c92bf20d033"},{"bidAmount":0,"currentPage":1,"deleteFlag":0,"name":"yangguifang","pageSize":6,"regisDate":1472041252000,"relateDesc":"间接推荐","userId":"74da72bd772b11e686dc6c92bf20d033"},{"bidAmount":0,"currentPage":1,"deleteFlag":0,"name":"chengao","pageSize":6,"regisDate":1471916580000,"relateDesc":"直接推荐","userId":"74da39e3772b11e686dc6c92bf20d033"},{"bidAmount":0,"currentPage":1,"deleteFlag":0,"name":"这里有个坑","pageSize":6,"regisDate":1467192577000,"relateDesc":"直接推荐","userId":"74bb2b8d772b11e686dc6c92bf20d033"},{"bidAmount":0,"currentPage":1,"deleteFlag":0,"name":"爱相随","pageSize":6,"regisDate":1462436762000,"relateDesc":"直接推荐","userId":"744fb3d5772b11e686dc6c92bf20d033"},{"bidAmount":0,"currentPage":1,"deleteFlag":0,"name":"cammyzheng","pageSize":6,"regisDate":1462418496000,"relateDesc":"直接推荐","userId":"744fa8e4772b11e686dc6c92bf20d033"}]
     * pageCurrent : 1
     * pageSize : 10
     * pageTotal : 6
     * pages : 1
     */

    public DataBean data;
    public String msg;
    public boolean success;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public DataBean getData()
    {
        return data;
    }

    public void setData(DataBean data)
    {
        this.data = data;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public static class DataBean
    {
        public int pageCurrent;
        public int pageSize;
        public int pageTotal;
        public int pages;
        /**
         * bidAmount : 0
         * currentPage : 1
         * deleteFlag : 0
         * name : wenzhenmei
         * pageSize : 6
         * regisDate : 1472646691000
         * relateDesc : 直接推荐
         * userId : 74db73ca772b11e686dc6c92bf20d033
         */

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

        public static class ListBean
        {
            public double bidAmount;
            public int currentPage;
            public int deleteFlag;
            public String name;
            public int pageSize;
            public long regisDate;
            public String relateDesc;
            public String userId;

            public double getBidAmount()
            {
                return bidAmount;
            }

            public void setBidAmount(double bidAmount)
            {
                this.bidAmount = bidAmount;
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

            public long getRegisDate()
            {
                return regisDate;
            }

            public void setRegisDate(long regisDate)
            {
                this.regisDate = regisDate;
            }

            public String getRelateDesc()
            {
                return relateDesc;
            }

            public void setRelateDesc(String relateDesc)
            {
                this.relateDesc = relateDesc;
            }

            public String getUserId()
            {
                return userId;
            }

            public void setUserId(String userId)
            {
                this.userId = userId;
            }
        }
    }
}
