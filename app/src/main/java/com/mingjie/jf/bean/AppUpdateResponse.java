package com.mingjie.jf.bean;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： APP升级返回
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-09-29 09:58
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class AppUpdateResponse
{
    private String creatTime;//保存时间

    private String downloadUrl;//下载连接

    private String fileName;//文件名

    private String remark;//描述

    private int state;//状态 0:不可用1:可用

    private String updateTime;//更新时间

    private int updating;//是否强制更新 0:否1:是

    private String versionName;//版本名

    private int versionCode;//版本号

    public AppUpdateResponse()
    {
    }

    public AppUpdateResponse(String creatTime, String downloadUrl, String fileName, String remark, int state,
                             String updateTime, int updating, String versionName, int versionCode)
    {
        this.creatTime = creatTime;
        this.downloadUrl = downloadUrl;
        this.fileName = fileName;
        this.remark = remark;
        this.state = state;
        this.updateTime = updateTime;
        this.updating = updating;
        this.versionName = versionName;
        this.versionCode = versionCode;
    }

    public String getCreatTime()
    {
        return creatTime;
    }

    public void setCreatTime(String creatTime)
    {
        this.creatTime = creatTime;
    }

    public String getDownloadUrl()
    {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl)
    {
        this.downloadUrl = downloadUrl;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public String getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }

    public int getUpdating()
    {
        return updating;
    }

    public void setUpdating(int updating)
    {
        this.updating = updating;
    }

    public String getVersionName()
    {
        return versionName;
    }

    public void setVersionName(String versionName)
    {
        this.versionName = versionName;
    }

    public int getVersionCode()
    {
        return versionCode;
    }

    public void setVersionCode(int versionCode)
    {
        this.versionCode = versionCode;
    }

    @Override
    public String toString()
    {
        return "AppUpdateResponse{" +
                "creatTime='" + creatTime + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", fileName='" + fileName + '\'' +
                ", remark='" + remark + '\'' +
                ", state=" + state +
                ", updateTime='" + updateTime + '\'' +
                ", updating=" + updating +
                ", versionName='" + versionName + '\'' +
                ", versionCode=" + versionCode +
                '}';
    }
}
