package com.mingjie.jf.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mingjie.jf.R;

/**
 * <p>项目名称：CunGuan
 * <p>包   名：  com.minghao.cgkx.Fragment
 * <p>版   权：  深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述：  用户材料 Fragment
 * <p>创 建 人： wuxianai
 * <p>邮箱地址： wuxianai@ming-hao.cn
 * <p>创建时间： 2016/6/29 20:22
 * <p>当前版本： V3.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class UserMaterialFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View userMaterialView = inflater.inflate(R.layout.fragment_loan_info_about_user_material, container, false);
        return userMaterialView;
    }
}
