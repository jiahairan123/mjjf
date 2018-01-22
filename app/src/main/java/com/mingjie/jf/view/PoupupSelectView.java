package com.mingjie.jf.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.adapter.PopupAdapter;
import com.mingjie.jf.utils.UIUtils;

import java.util.List;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.View
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 底部划出的选择窗口（ppwindow）
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-09-06 14:09
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class PoupupSelectView implements PopupWindow.OnDismissListener, AdapterView.OnItemClickListener
{

    private List<String> mDatas;
    private ListView mListView;
    private Activity mContext;
    private PopupWindow mWindow;
    private View mParent;
    private OnListItemListener mListener;
    private OnDisMissListener mDisListener;
    private View mView;
    private TextView mTitle;

    public PoupupSelectView(Context mContext, List<String> mDatas, View parent)
    {
        this.mDatas = mDatas;
        this.mParent = parent;
        this.mContext = (Activity) mContext;
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.popupwindow_spinner, null);

    }

    public void setDatas(List<String> mDatas, String title, View mParent)
    {
        mTitle = (TextView) mView.findViewById(R.id.tv_popupwindow);
        mTitle.setText(title);
        this.mParent = mParent;
        this.mDatas = mDatas;
    }

    public void setmTitle(String title)
    {
        mTitle = (TextView) mView.findViewById(R.id.tv_popupwindow);
        mTitle.setText(title);
    }

    public void showPopupWindow()
    {

        mListView = (ListView) mView.findViewById(R.id.listview_popup);
        mListView.setAdapter(new PopupAdapter(mContext, mDatas, mListView));
        mListView.setOnItemClickListener(this);
        int scaleHeigth = (int) (UIUtils.getScreenY(mContext) * 0.30);
        mWindow = new PopupWindow(mView,
                WindowManager.LayoutParams.MATCH_PARENT,
                scaleHeigth);

        ColorDrawable cd = new ColorDrawable(0x000000);
        mWindow.setBackgroundDrawable(cd);
        mWindow.setOutsideTouchable(true);
        mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        WindowManager.LayoutParams layoutParams = mContext.getWindow().getAttributes();
        layoutParams.alpha = 0.5f;
        mContext.getWindow().setAttributes(layoutParams);
        mWindow.setOnDismissListener(this);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        mWindow.setFocusable(true);

        // 设置popWindow的显示和消失动画
        mWindow.setAnimationStyle(R.style.popwindow_avatar_set_anim_style);
        // 在底部显示
        mWindow.showAtLocation(mParent, Gravity.BOTTOM, 0, 0);
    }

    public void setOnListItemClickListener(OnListItemListener mListener)
    {
        this.mListener = mListener;
    }

    public void setOnDisMissListener(OnDisMissListener mDisListener)
    {
        this.mDisListener = mDisListener;
    }

    @Override
    public void onDismiss()
    {

        if (mWindow.isShowing())
        {
            mWindow.dismiss();
        }
        if (mDisListener != null)
        {
            mDisListener.onDisMissListener();
        }
        WindowManager.LayoutParams layoutParams = mContext.getWindow().getAttributes();
        layoutParams.alpha = 1f;
        mContext.getWindow().setAttributes(layoutParams);
        mWindow = null;
    }

    public interface OnListItemListener
    {
        void onListItemClick(AdapterView<?> parent, TextView view, int position, long id);
    }

    public interface OnDisMissListener
    {
        void onDisMissListener();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if (mListener != null)
        {
            mListener.onListItemClick(parent, (TextView) view.findViewById(R.id.tv_holder_popup), position, id);
        }
        mWindow.dismiss();
    }
}
