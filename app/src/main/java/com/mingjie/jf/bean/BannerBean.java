package com.mingjie.jf.bean;

import java.util.List;

/**
 * @项目名: CaffcooP2P
 * @包名: caffp2p.android.bean
 * @类名: BannerBean
 * @创建者: chenxiaoping
 * @创建时间 2015/10/10 15:37
 * @描述 TODO
 */
public class BannerBean {

    /**
     * error :
     * borrowSize : 1
     * items : [{"imagesUrl":"http://www.***.com/user/5957/214.jpg?c=6240447082679468475","bindex":0,
     * "address":"http://www.***.com/bbs/thread/2015/20150923/51991.html"}]
     * success : true
     */

    public String error;
    public int borrowSize;
    public boolean success;
    public List<ItemsImage> items;

    public static class ItemsImage {
        public String imagesUrl;
        public int bindex;
        public String address;
    }
}
