package com.mingjie.jf.bean;

import java.io.Serializable;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： xxxx
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-09-27 14:51
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class UserInfo implements Serializable
{

    private static final long serialVersionUID = -352823489429210982L;
    private UserStatesBean userStates;
    private String createTime;

    private String isBindBankcard;//是否绑卡

    private UserBean user;

    @Override
    public String toString()
    {
        return "UserInfo{" +
                "createTime='" + createTime + '\'' +
                ", isBindBankcard='" + isBindBankcard + '\'' +
                ", userStates=" + userStates +
                ", user=" + user +
                '}';
    }

    public String getIsBindBankcard()
    {
        return isBindBankcard;
    }

    public void setIsBindBankcard(String isBindBankcard)
    {
        this.isBindBankcard = isBindBankcard;
    }

    public UserStatesBean getUserStates()
    {
        return userStates;
    }

    public void setUserStates(UserStatesBean userStates)
    {
        this.userStates = userStates;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public UserBean getUser()
    {
        return user;
    }

    public void setUser(UserBean user)
    {
        this.user = user;
    }

}
