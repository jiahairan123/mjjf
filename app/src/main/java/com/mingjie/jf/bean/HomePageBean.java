package com.mingjie.jf.bean;

import java.util.List;

/*
 *  @项目名：  CaffcooP2P 
 *  @包名：    caffp2p.android.bean
 *  @类名:    HomePageBean
 *  @创建者:   陈小平
 *  @创建时间:  2015/12/01 15:25
 *  @描述：    TODO
 */
public class HomePageBean {

    public DataEntity data;
    public int returnCode;
    public String returnMsg;

    public class DataEntity {
        public AppVersionEntity appVersion;//app版本
        public PlatformDataEntity platformData;//平台数据
        public List<BannerListEntity> bannerList;//banner列表
        public List<SysNoticeListEntity> sysNoticeList;//系统公告

        public class AppVersionEntity {
            public String androidDesc;
            public String androidUpgradeLink;
            public int androidUpgradeType;
            public String androidVersionNo;
        }

        public class PlatformDataEntity {
            public String earnedAmount;
            public int safeOperatingMonth;
            public int safeOperatingYear;
            public double successAmount;
            public String tradingRecord;
            public String userAmount;
            public String waitingAmount;
        }

        public class BannerListEntity {
            public String address;
            public String bannerTitle;
            public String imgUrl;
        }

        public class SysNoticeListEntity {
            public int contentId;
            public String contentTitle;
            public String createdDate;
        }
    }
}
