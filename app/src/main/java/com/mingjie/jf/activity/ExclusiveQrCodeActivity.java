package com.mingjie.jf.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.bean.MyRecommendVo;
import com.mingjie.jf.bean.RecommendParams;
import com.mingjie.jf.bean.UserVoValible;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.CfLog;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.UrlUtil;
import com.mingjie.jf.utils.Utilities;
import com.mingjie.jf.widgets.CaffGridView;
import com.mingjie.jf.widgets.PictureDialog;
import com.mingjie.jf.widgets.QRCodeUtil;
import com.mingjie.jf.widgets.RefreshLayout;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.social.UMPlatformData;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.sharesdk.wechat.utils.WechatHelper;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 我的推荐
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-09-09 19:22
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class ExclusiveQrCodeActivity extends BaseActivity implements View.OnClickListener, AdapterView
        .OnItemClickListener, PlatformActionListener, SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener
{

    CaffGridView gv_share;
    @BindView(R.id.iv_Left_public)
    ImageView ivLeftPublic;
    @BindView(R.id.tv_content_public)
    TextView tvContentPublic;
    ImageView iv_qrcode;//二维码
    private SimpleAdapter saImageItems;
    @BindView(R.id.listview_mark_history)
    ListView listview_mark_history;
    @BindView(R.id.swipe_container)
    RefreshLayout mRefreshView;
    TextView recommend_name;        //用户名
    @BindView(R.id.ll_head)
    LinearLayout ll_head;

    private String[] platformTitle = {"QQ", "QQ空间", "微信", "微信朋友圈", "复制链接"};

    private int[] image = {R.mipmap.qq, R.mipmap.qq_zone, R.mipmap.weixin, R.mipmap.friend, R
            .mipmap.copy_link};

    private RecommendParams params;
    private UserVoValible userinfo; // 用户id
    private SimpleDateFormat dateFormater;

    private List<MyRecommendVo.DataBean.ListBean> mData = new ArrayList<>(); // 存放listview数据的容器

    private boolean isRefresh;                 // 是否正在刷新
    private boolean isLoad;                    // 是否正在加载

    private int currentPage = 1;                   //参数
    private int pageTotal;                     //总数据条数
    private MyAdapter adapter;
    private String userId;
    private String phone;       // 用户手机号
    private String refcode; //邀请码

    private static final int ONCOMPLETE = 1;   // 成功
    private static final int ONERROR = 2;      // 失败
    private static final int ONCANCEL = 3;   // 取消

    /**
     * 接收分享回调信息
     */
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case ONCOMPLETE:
                    UIUtils.showToast("分享成功！");
                    break;
                case ONERROR:
                    UIUtils.showToast("分享失败！");
                    break;
                case ONCANCEL:
                    UIUtils.showToast("取消分享！");
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 注册连接（官网）
     */
    private String mReferrerLink = UrlUtil.getUrl() + "/register?rcode=";
    /**
     * 微信分享地址
     */
//    private String mWX_share_url = UrlUtil.getUrl() + "/wx/register/toRegister?phone=";
    private String mWX_share_url = UrlUtil.getUrl() + "/wx/register/toRegister?refcode=";
    /**
     * 分享标题
     */
    private String mShareTitle;

    /**
     * 分享说明
     */
    private String mShareText;

    private static final String FILE_NAME = "/app_share_logo.png";
    private String testImage;

    private ClipboardManager mClipboard;
    private ClipData mClip;
    private PictureDialog pcDialog;
    private Bitmap btmp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // ButterKnife.bind(this);
    }

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_my_recommend);
        ButterKnife.bind(this);
        phone = getIntent().getStringExtra("phone");
        userId = CacheUtils.getUserinfo(this).getId();
        refcode = CacheUtils.getUserinfo(this).getRefcode();
        CfLog.d("userId = " + userId);

        ivLeftPublic.setOnClickListener(this);
        mRefreshView.setOnLoadListener(this);
        mRefreshView.setOnRefreshListener(this);

        initHead01();
        initHead02();
        adapter = new MyAdapter();
        listview_mark_history.setAdapter(adapter);
        initEvent();
    }

    private void initHead01()
    {
        View view = View.inflate(this, R.layout.head_item_recommend_01, null);
        recommend_name = (TextView) view.findViewById(R.id.recommend_name);
        iv_qrcode = (ImageView) view.findViewById(R.id.iv_qrcode);
        gv_share = (CaffGridView) view.findViewById(R.id.gv_share);
        gv_share.setOnItemClickListener(this);

        if (CacheUtils.isLogin())
        {
            recommend_name.setText("推荐好友注册时请输入您的邀请码：" + refcode);
        }
        else
        {
            recommend_name.setText("推荐好友注册时请输入您的手机号码：" + "");
        }
        listview_mark_history.addHeaderView(view);
    }

    private void initHead02()
    {
        View view = View.inflate(this, R.layout.head_item_recommend_02, null);
        listview_mark_history.addHeaderView(view);
    }

    protected void initData()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(this, "当前网络不可用，请重试！");
            return;
        }
        mShareTitle = getResources().getString(R.string.share_title);
        mShareText = getResources().getString(R.string.share_text);
        UIUtils.showLoading(this);
        initImagePath();
        ShareSDK.initSDK(this);
        dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        tvContentPublic.setText("我的推荐");
        userinfo = CacheUtils.getUserinfo(this);
        List<HashMap<String, Object>> shareList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < image.length; i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", image[i]);//添加图像资源的ID
            map.put("ItemText", platformTitle[i]);//按序号做ItemText
            shareList.add(map);
            CfLog.i("---" + map);
        }
        saImageItems = new SimpleAdapter(this, shareList, R.layout.share_item, new String[]{"ItemImage", "ItemText"},
                new int[]{R.id.iv_share, R.id.tv_platform});
        gv_share.setAdapter(saImageItems);

//        showQrCode(mWX_share_url + CacheUtils.getUserinfo(ExclusiveQrCodeActivity.this).getPhone());
        showQrCode(mWX_share_url + CacheUtils.getUserinfo(ExclusiveQrCodeActivity.this).getRefcode());
        iv_qrcode.setOnClickListener(this);

        params = getParams();
        HttpManager.getInstance().doPost(ServiceName.CGKX_RECOMMEND, params, new HttpCallBack<MyRecommendVo>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                showToast(R.string.dataError);
                UIUtils.dimissLoading();
            }

            @Override
            public void getHttpCallBack(MyRecommendVo recomment)
            {
                UIUtils.dimissLoading();
                if (null != recomment)
                {
                    if ("000000".equals(recomment.code))
                    {
                        pageTotal = recomment.data.pageTotal;
                        mData = recomment.data.list;
                        adapter.notifyDataSetChanged();
                    }
                    else
                    {
                        showToast(recomment.msg);
                    }
                }
                else
                {
                    UIUtils.showToast(getBaseContext(), R.string.service_err);
                }
            }
        });
    }

    protected void closeRefresh()
    {
        if (mRefreshView == null)
        {
            return;
        }
        if (mRefreshView.isRefreshing())
        {
            mRefreshView.setRefreshing(false);
        }
        else if (mRefreshView.isLoading())
        {
            mRefreshView.setLoading(false);
        }
    }

    @Override
    public void onLoad()
    {

    }

    @Override
    public void onRefresh()
    {
        // 下拉刷新
        currentPage = 1;
        mRefreshView.setColorSchemeResources(R.color.color_public_red, R.color.green, R.color.blue, R.color.red);
        mRefreshView.setRefreshing(true);
        isRefresh = true;
        getData();
    }

    public void getData()
    {
        if (!Utilities.canNetworkUseful(this))//如果当前网络不可用
        {
            UIUtils.showToast(this, R.string.network_unused_retry_pls);
            return;
        }
        params = getParams();
        HttpManager.getInstance().doPost(ServiceName.CGKX_RECOMMEND, params, new HttpCallBack<MyRecommendVo>()
        {
            @Override
            public void getHttpCallNull(String str)
            {
                UIUtils.showToast(getBaseContext(), R.string.dataError);
                closeRefresh();
            }

            @Override
            public void getHttpCallBack(MyRecommendVo recomment)
            {
                closeRefresh();
                if (null != recomment)
                {
                    if ("000000".equals(recomment.code))
                    {
                        pageTotal = recomment.data.pageTotal;
                        if (isLoad)
                        {
                            isLoad = false;
                            mData.addAll(recomment.data.list);
                        }
                        else if (isRefresh)
                        {
                            isRefresh = false;
                            mData.clear();
                            mData.addAll(recomment.data.list);
                            showToast("刷新完成");
                        }
                        else
                        {
                            mData.clear();
                            mData.addAll(recomment.data.list);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    else
                    {
                        showToast(recomment.msg);
                    }
                }
                else
                {
                    UIUtils.showToast(getBaseContext(), R.string.service_err);
                }
            }
        });
    }

    public class MyAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            if (mData.size() != 0)
            {
                return mData.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position)
        {
            if (null != mData)
            {
                return mData.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder holder = null;
            if (convertView == null)
            {
                convertView = View.inflate(ExclusiveQrCodeActivity.this, R.layout.activity_my_recom, null);
                holder = new ViewHolder();
                holder.tv_recommend_user = (TextView) convertView.findViewById(R.id.tv_recommend_user);
//                holder.tv_recommend_guanxi = (TextView) convertView.findViewById(R.id.tv_recommend_guanxi);
                holder.tv_invest_money = (TextView) convertView.findViewById(R.id.tv_invest_money);
                holder.tv_register_time = (TextView) convertView.findViewById(R.id.tv_register_time);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_recommend_user.setText(mData.get(position).name);
//            holder.tv_recommend_guanxi.setText(mData.get(position).relateDesc);
            holder.tv_invest_money.setText(String.valueOf(mData.get(position).bidAmount));
            holder.tv_register_time.setText(dateFormater.format(mData.get(position).regisDate));
            return convertView;
        }
    }

    static class ViewHolder
    {
        TextView tv_recommend_user;   // 被推荐用户
        // TextView tv_recommend_guanxi;  // 推荐关系
        TextView tv_invest_money;      // 投资金额
        TextView tv_register_time;    // 注册时间
    }

    /**
     * @return 返回我的推荐参数
     */
    public RecommendParams getParams()
    {
        RecommendParams params = new RecommendParams();
        params.setPageSize(0);              // 传0获取所有数据
        params.setCurrentPage(0);           // 传0获取所有数据
        params.setUserId(userinfo.getId());
        return params;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        UMPlatformData.UMedia uMedia = null;
        switch (position)
        {
            case 0:  // QQ
                uMedia = UMPlatformData.UMedia.TENCENT_QQ;
                QQ.ShareParams qqSp = new QQ.ShareParams();
                qqSp.setTitle(mShareTitle);
                qqSp.setTitleUrl(mWX_share_url + CacheUtils.getUserinfo(this).getRefcode()); // 标题的超链接
                qqSp.setText(mShareText);
                qqSp.setImagePath(testImage);

                Platform qqPlatform = ShareSDK.getPlatform(QQ.NAME);

                //关闭sso授权
                qqPlatform.SSOSetting(true);
                qqPlatform.setPlatformActionListener(this); // 设置分享事件回调
                // 执行图文分享
                qqPlatform.share(qqSp);
                break;
            case 1: // QQ空间
                uMedia = UMPlatformData.UMedia.TENCENT_QZONE;
                QZone.ShareParams qzSp = new QZone.ShareParams();
                qzSp.setTitle(mShareTitle);
                qzSp.setTitleUrl(mWX_share_url + CacheUtils.getUserinfo(this).getRefcode()); // 标题的超链接
                qzSp.setText(mShareText);
                qzSp.setImagePath(testImage);

                Platform qzPlatform = ShareSDK.getPlatform(QZone.NAME);
                //关闭sso授权
                qzPlatform.SSOSetting(true);
                qzPlatform.setPlatformActionListener(this); // 设置分享事件回调
                // 执行图文分享
                qzPlatform.share(qzSp);
                break;
            case 2: //微信分享
                uMedia = UMPlatformData.UMedia.WEIXIN_FRIENDS;
                WechatHelper.ShareParams wcSp = new WechatHelper.ShareParams();
                wcSp.setShareType(Platform.SHARE_WEBPAGE);
                wcSp.setTitle(mShareTitle);
                wcSp.setText(mShareText);
                wcSp.setImagePath(testImage);
                wcSp.setUrl(mWX_share_url + CacheUtils.getUserinfo(this).getRefcode());
                Platform wcPlatform = ShareSDK.getPlatform(Wechat.NAME);
                //关闭sso授权
                wcPlatform.SSOSetting(true);
                wcPlatform.setPlatformActionListener(this); // 设置分享事件回调
                wcPlatform.SSOSetting(true);
                // 执行图文分享
                wcPlatform.share(wcSp);
                break;
            case 3: // 微信朋友圈
                uMedia = UMPlatformData.UMedia.WEIXIN_CIRCLE;
                WechatHelper.ShareParams wmSp = new WechatHelper.ShareParams();
                wmSp.setShareType(Platform.SHARE_WEBPAGE);
                wmSp.setTitle(mShareTitle); //Tiltle,设置为分享说明
                // wmSp.setText(mShareText);   //text(朋友圈不显示此字段)
                wmSp.setImagePath(testImage);
                CfLog.i("mReferrerLink + userId = " + mReferrerLink + userId);
                wmSp.setUrl(mWX_share_url + CacheUtils.getUserinfo(this).getRefcode());  //加证书的链接不可用
                Platform wmPlatform = ShareSDK.getPlatform(WechatMoments.NAME);
                wmPlatform.setPlatformActionListener(this); // 设置分享事件回调
                wmPlatform.SSOSetting(true);
                // 执行图文分享
                wmPlatform.share(wmSp);
                break;
//            case 4://新浪微博
//                uMedia = UMPlatformData.UMedia.SINA_WEIBO;
//                SinaWeibo.ShareParams swSp = new SinaWeibo.ShareParams();
//                swSp.setText(mShareText + mReferrerLink + userId);
//                swSp.setImagePath(testImage);
//                swSp.setUrl(mWX_share_url + CacheUtils.getUserinfo(this).getPhone());
//                Platform swPlatform = ShareSDK.getPlatform(SinaWeibo.NAME);
//                //关闭sso授权
//                swPlatform.SSOSetting(true);
//                swPlatform.setPlatformActionListener(this); // 设置分享事件回调
//                // 执行图文分享
//                swPlatform.share(swSp);
//                break;
            case 4://复制链接
                /**
                 * 设置复制/粘贴板的内容为我的推荐链接。
                 */
                mClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                mClip = ClipData.newPlainText("text", mWX_share_url + CacheUtils.getUserinfo(this).getRefcode());
                mClipboard.setPrimaryClip(mClip);
                showToast("已复制您的推荐链接！");
                break;
            default:
                break;
        }

        if (null != uMedia)
        {
            UMPlatformData platform = new UMPlatformData(uMedia, userId);
            MobclickAgent.onSocialEvent(this, platform);
        }

    }

    public void initEvent()
    {
        // 给listview加滚动事件
        listview_mark_history.setOnScrollListener(new AbsListView.OnScrollListener()
        {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {

            }

            /**
             * 按住滑动
             */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                // 如果显示用户进程 标签要显示用户进程的tag
                CfLog.i("firstVisibleItem = " + firstVisibleItem);
                if (firstVisibleItem >= 1)
                {
                    ll_head.setVisibility(View.VISIBLE);
                }
                else
                {
                    ll_head.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap)
    {
        CfLog.d("分享成功");
        Message msg = handler.obtainMessage();
        msg.what = ONCOMPLETE;
        handler.sendMessage(msg);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable)
    {
        CfLog.d("分享失败");
        Message msg = handler.obtainMessage();
        msg.what = ONERROR;
        handler.sendMessage(msg);
    }

    @Override
    public void onCancel(Platform platform, int i)
    {
        CfLog.d("取消分享");
        Message msg = handler.obtainMessage();
        msg.what = ONCANCEL;
        handler.sendMessage(msg);
    }

    private void initImagePath()
    {
        try
        {
            String cachePath = com.mob.tools.utils.R.getCachePath(this, null);
            testImage = cachePath + FILE_NAME;
            File file = new File(testImage);
            if (!file.exists())
            {
                file.createNewFile();
                Bitmap pic = BitmapFactory.decodeResource(getResources(), R.mipmap.ic2);
                FileOutputStream fos = new FileOutputStream(file);
                pic.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            }
        }
        catch (Throwable t)
        {
            t.printStackTrace();
            testImage = null;
        }
    }

    //    private void showShare()
    //    {
    //        ShareSDK.initSDK(this);
    //        OnekeyShare oks = new OnekeyShare();
    //        //关闭sso授权
    //        oks.disableSSOWhenAuthorize();
    //
    //        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
    //        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
    //        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
    //        oks.setTitle("licaila");
    //        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
    //        oks.setTitleUrl("http://sharesdk.cn");
    //        // text是分享文本，所有平台都需要这个字段
    //        oks.setText("我是分享文本");
    //        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
    //        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
    //        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
    //        oks.setUrl("http://sharesdk.cn");
    //        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
    //        oks.setComment("我是测试评论文本");
    //        // site是分享此内容的网站名称，仅在QQ空间使用
    //        oks.setSite(getString(R.string.app_name));
    //        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
    //        oks.setSiteUrl("http://sharesdk.cn");
    //
    //        // 启动分享GUI
    //        oks.show(this);
    //    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;
            //放大二维码
            case R.id.iv_qrcode:
                //给dialog传bitmap
                pcDialog = new PictureDialog(this, btmp);
                pcDialog.show();
                break;
            default:
                break;
        }

    }

    public void showQrCode(final String referrerLink)
    {
        //二维码图片较大时，生成图片因此放在新线程中
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        btmp = QRCodeUtil.getQRImage(referrerLink, 150, 150, BitmapFactory.decodeResource
                                (getResources(), R.mipmap.ic));
                        iv_qrcode.setImageBitmap(btmp);
                    }
                });
            }

        }).start();

    }
}

//        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//        // url仅在微信（包括好友和朋友圈）中使用