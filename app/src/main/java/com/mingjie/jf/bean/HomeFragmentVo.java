package com.mingjie.jf.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class HomeFragmentVo
{

    public StatisticsBean statistics;
    public List<BannerListBean> bannerList;
    public List<AnnounceListBean> announceList;

    public StatisticsBean getStatistics()
    {
        return statistics;
    }

    public void setStatistics(StatisticsBean statistics)
    {
        this.statistics = statistics;
    }

    public List<BannerListBean> getBannerList()
    {
        return bannerList;
    }

    public void setBannerList(List<BannerListBean> bannerList)
    {
        this.bannerList = bannerList;
    }

    public List<AnnounceListBean> getAnnounceList()
    {
        return announceList;
    }

    public void setAnnounceList(List<AnnounceListBean> announceList)
    {
        this.announceList = announceList;
    }

    public static class StatisticsBean
    {
        public String getTotalBid()
        {
            return totalBid;
        }

        public void setTotalBid(String totalBid)
        {
            this.totalBid = totalBid;
        }

        public String totalBid;//总投资金额
        public String sumUser;//总投资人数


        public String getSumUser()
        {
            return sumUser;
        }

        public void setSumUser(String sumUser)
        {
            this.sumUser = sumUser;
        }
    }

    public static class BannerListBean
    {
        public String filePath;//文件路径

        public String url;//跳转链接

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public String title;//图片标题

        public String getFilePath()
        {
            return filePath;
        }

        public void setFilePath(String filePath)
        {
            this.filePath = filePath;
        }

        public String getUrl()
        {
            return url;
        }

        public void setUrl(String url)
        {
            this.url = url;
        }
    }

    public static class AnnounceListBean
    {
        public String plateId;
        public String id;//图片id
        public String title;//通知标题
        public String type;//类型

        public String getPlateId()
        {
            return plateId;
        }

        public void setPlateId(String plateId)
        {
            this.plateId = plateId;
        }

        public String getId()
        {
            return id;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }
    }

    @Override
    public String toString()
    {
        return "HomeFragmentVo{" +
                "statistics=" + statistics +
                ", bannerList=" + bannerList +
                ", announceList=" + announceList +
                '}';
    }
}
