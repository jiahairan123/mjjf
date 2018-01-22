package com.mingjie.jf.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.BaseActivity;
import com.mingjie.jf.bean.AppUpdateResponse;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.widgets.QuitDialog;
import com.mingjie.jf.widgets.UpdateDialog;
import com.umeng.analytics.MobclickAgent;

import java.io.File;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.utils
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： app更新辅助类
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-09-28 19:19
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class AppUpdateUtil implements QuitDialog.OnCancelListener, QuitDialog.OnEnterListener
{
    private Context mContext;
    private int localVersionCode;//本地版本号
    private QuitDialog mQuitDialog;
    private AppUpdateResponse updateResponse;
    private UpdateDialog updateDialog;//下载中对话框
    private String savaPath;//apk保存地址

    public AppUpdateUtil(Context mContext)
    {
        this.mContext = mContext;
        try
        {
            PackageManager manager = mContext.getPackageManager();
            String packageName = mContext.getPackageName();
            PackageInfo info = manager.getPackageInfo(packageName, 0);
            localVersionCode = info.versionCode;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            CfLog.e(e.toString());
        }

    }

    /**
     * 更新app
     *
     * @param updateResponse 服务端返回的检查更新结果
     * @param isHome         是否在首页检查，首页检查无更新不要提示，其他页面用户主动检查需要提示
     */
    public void appUpdate(AppUpdateResponse updateResponse, boolean isHome)
    {
        this.updateResponse = updateResponse;
        CfLog.i("localVersionCode="+localVersionCode+";updateResponse.getVersionCode()="+updateResponse.getVersionCode());
        if (updateResponse.getVersionCode() > localVersionCode)//服务端版本号高于当前版本号，需要更新
        {
            String leftText;//升级框左边按钮文字
            if (updateResponse.getUpdating() == 1)
            {//强制更新
                leftText = "退出";
            }
            else
            {
                leftText = "取消";
            }
            mQuitDialog = new QuitDialog(mContext, "发现新版本", updateResponse.getRemark(), leftText, "更新");
            mQuitDialog.show();
            //如果不是强制升级，可让弹出框点击周边消失
            mQuitDialog.setCancelable(updateResponse.getUpdating() == 0);
            mQuitDialog.setCancelListener(this);
            mQuitDialog.setEnterListener(this);
        }
        else
        {
            if (!isHome)//不在首页无更新时需要提醒用户当前是最高版本
            {
                // updateResponse.getVersionName() // 后台返回的版本号
                mQuitDialog = new QuitDialog(mContext, "版本更新", "当前已是最新版本:V" + UIUtils.getAppVersionName(),
                        "返回", "确定");
                mQuitDialog.show();
                mQuitDialog.setCancelListener(new QuitDialog.OnCancelListener()
                {
                    @Override
                    public void onCancelListener()
                    {
                        mQuitDialog.dismiss();
                    }
                });
                mQuitDialog.setEnterListener(new QuitDialog.OnEnterListener()
                {
                    @Override
                    public void onEnterListener()
                    {
                        mQuitDialog.dismiss();
                    }
                });
            }
        }
    }

    @Override
    public void onCancelListener()
    {
        if (updateResponse.getUpdating() == 1)
        {//退出程序
            mQuitDialog.dismiss();
            //            BaseActivity.exitApp();
            //            System.exit(0);
            ((Activity) mContext).finish();
        }
        else
        {//取消
            mQuitDialog.dismiss();
        }
    }

    @Override
    public void onEnterListener()
    {
        mQuitDialog.dismiss();
        //下载APK
        downLoadAPK();
    }

    /**
     *
     */
    private void downLoadAPK()
    {
        if (!checkSDCard())//sd卡异常
        {
            UIUtils.showToast(mContext, "SD卡异常，请检查!");
            return;
        }
        savaPath = Environment.getExternalStorageDirectory().getPath() + File.separator + Constants.APP_NAME
                + File.separator + "download";
        File file = new File(savaPath);

        //如果文件目录不存在，就創建該目录
        if (!file.exists())
        {
            file.mkdirs();
        }

        if (!file.canWrite())
        {
            CfLog.i(savaPath + " cannot write");
            // /storage/emulated/0/Android/data/packageName/cache
            File cacheDir = mContext.getExternalCacheDir();
            if (!cacheDir.exists())
            {
                cacheDir.mkdirs();
            }
            savaPath = cacheDir.getAbsolutePath();
            CfLog.i(savaPath + ", exists:" + cacheDir.exists() + ", can write:" + cacheDir.canWrite());
        }

        CfLog.i("download dir: " + savaPath);

        updateDialog = new UpdateDialog(mContext);
        updateDialog.show();
        updateDialog.setMax(100);
        //        设置进度背景
        updateDialog.setProgressDrawable(mContext.getResources().getDrawable(R.mipmap.progress_change));
        updateDialog.setOnQuitClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //                退出程序
                updateDialog.dismiss();
                BaseActivity.exitApp();
                MobclickAgent.onKillProcess(mContext);
                System.exit(0);
            }
        });

        //开始下载
        HttpManager.getInstance().download(updateResponse.getDownloadUrl(), savaPath, updateResponse.getFileName(),
                new HttpManager.OnDowloadListener()
                {
                    @Override
                    public void onDownloadSucc()
                    {
                        UIUtils.showToast("下载成功");
                        updateDialog.setText("下载完成");
                        updateDialog.setBtnText("点击安装");
                        updateDialog.setOnQuitClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                installApp();
                            }
                        });
                    }

                    @Override
                    public void onDownloadProgress(int progress)
                    {
                        updateDialog.setProgress(progress);
                        updateDialog.setText(progress + "%");
                    }

                    @Override
                    public void onDownloadFail(Exception e)
                    {
                        CfLog.e(updateResponse.getDownloadUrl());
                        CfLog.e(savaPath);
                        CfLog.e(e);

                        UIUtils.showToast("下载失败");
                        // startUrl(updateResponse.getDownloadUrl());
                        if (1 == updateResponse.getUpdating())
                        {
                            MobclickAgent.onKillProcess(mContext);
                            System.exit(0);
                        }

                        updateDialog.dismiss();
                    }
                });
    }

    //    /**
    //     * 通过浏览器打开URL
    //     * @param url 网址/下载地址
    //     */
    //    public void startUrl(String url)
    //    {
    //        Intent intent = new Intent(Intent.ACTION_VIEW);
    //        Uri uri = Uri.parse(url);
    //        intent.setData(uri);
    //        mContext.startActivity(intent);
    //    }

    /**
     * 安装App
     */
    private void installApp()
    {
        File apkfile = new File(savaPath,updateResponse.getFileName());
        if (!apkfile.exists()) {
            CfLog.i("apkFile:not exitsts!");
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }

    /**
     * sd卡能否正常使用
     *
     * @return
     */
    private boolean checkSDCard()
    {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
