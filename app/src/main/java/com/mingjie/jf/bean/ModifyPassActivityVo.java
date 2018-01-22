package com.mingjie.jf.bean;

/**
 * Created by QinSiYi on 2016-8-29.
 */
public class ModifyPassActivityVo {
//    "newPassword": "aaaaaa",
//            "oldPassword": "123456",
//            "reNewPassword": "aaaaaa",
//            " pcode ": "7b97f036e36945b7bb08e6a9c01eb39f"

    String newPassword;//新密码
    String oldPassword;//登录密码
    String reNewPassword;//新的确认密码

    public String getPcode()
    {
        return pcode;
    }

    public void setPcode(String pcode)
    {
        this.pcode = pcode;
    }

    String pcode;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getReNewPassword() {
        return reNewPassword;
    }

    public void setReNewPassword(String reNewPassword) {
        this.reNewPassword = reNewPassword;
    }

}
