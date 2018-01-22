package com.mingjie.jf.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingjie.jf.R;
import com.mingjie.jf.activity.BorrowAgreementActivity;
import com.mingjie.jf.activity.CollectPlanActivity;
import com.mingjie.jf.bean.CheBiaoVo;
import com.mingjie.jf.bean.MyInvestResponse;
import com.mingjie.jf.bean.ServerResponse;
import com.mingjie.jf.constant.ServiceName;
import com.mingjie.jf.service.HttpCallBack;
import com.mingjie.jf.service.HttpManager;
import com.mingjie.jf.utils.CacheUtils;
import com.mingjie.jf.utils.ProductStatusUtil;
import com.mingjie.jf.utils.StringUtils;
import com.mingjie.jf.utils.TimeFormatUtil;
import com.mingjie.jf.utils.UIUtils;
import com.mingjie.jf.utils.UrlUtil;
import com.mingjie.jf.widgets.JRecyclerView;
import com.mingjie.jf.widgets.QuitDialog;
import com.squareup.okhttp.Request;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.adapter
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 我的账户-我的投资
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-09-19 10:55
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class MyInvestAdapter extends RecyclerView.Adapter<MyInvestAdapter.MyViewHolder> implements QuitDialog
        .OnEnterListener, QuitDialog.OnCancelListener {
    private final Context mContext;
    private final int status;//用来区分是还款中0、招标中1，还是已完结2

    //传入JRecyclerView用来刷新列表，因为JRcyclerView真正绑定的不是该adapter，而是它内部的adapter
    private final JRecyclerView jRecyclerView;
    private List<MyInvestResponse.ItemMyInvest> mData;
    private QuitDialog quitDialog;
    private int chebiaoPosition;//点击撤标的位置

    public MyInvestAdapter(Context context, List<MyInvestResponse.ItemMyInvest> data, int status, JRecyclerView
            jRecyclerView) {
        this.mContext = context;
        this.mData = data;
        this.status = status;
        this.jRecyclerView = jRecyclerView;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_fragment_repay_list, parent,
                false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyInvestResponse.ItemMyInvest data = mData.get(position);
        holder.tvHuanKuanShiJian.setText(TimeFormatUtil.data2StrTime2(data.createdDate));
        holder.tvHuanKuanFangShi.setText(data.modeName);
        holder.tvDaiShouBenXi.setText(StringUtils.dou2Str(data.availableAmount));
        holder.tvYiShouBenXi.setText(StringUtils.dou2Str(data.favorableAmount) + "元");
        holder.tvQiXian.setText(String.valueOf(data.productDeadline));
        holder.tvUnit.setText(data.mode == 3 ? "日" : "个月");
        holder.tvIncom.setText(data.profit + "%");
        holder.tvInvestMoney.setText(StringUtils.dou2Str(data.bidAmount));
        holder.tvTitle.setText(data.title + "");

        if (status == 1) {
            //用来区分是还款中0、招标中1，还是已完结2
            if (data.status == 7) {
                holder.btCheBiao.setVisibility(View.GONE);
                holder.llShouKuanJiHua.setVisibility(View.GONE);
//                holder.btCheBiao.setVisibility(View.VISIBLE);
//                holder.llShouKuanJiHua.setVisibility(View.GONE);
//                holder.btCheBiao.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)//点击撤标
//                    {
//                        chebiaoPosition = position;
//                        quitDialog = new QuitDialog(mContext, "温馨提示", "是否确认撤标？", "取消", "确认");
//                        quitDialog.setEnterListener(MyInvestAdapter.this);
//                        quitDialog.setCancelListener(MyInvestAdapter.this);
//                        quitDialog.show();
//                    }
//                });
            } else {
                holder.btCheBiao.setVisibility(View.GONE);
                holder.llShouKuanJiHua.setVisibility(View.GONE);
            }
            //1-准备发布2-初审3-终审4-初审拒绝5-终审拒绝 6-招标失败 7-招标中8-已撤销9-流标10-还款中11-已还清12-逾期13-坏账
        } else {
            holder.btCheBiao.setVisibility(View.GONE);
            if (data.state == 4) {
                holder.tvShouKuanJiHua.setVisibility(View.INVISIBLE);
            } else {
                holder.tvShouKuanJiHua.setVisibility(View.VISIBLE);
                //收款計劃
                holder.tvShouKuanJiHua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent repayPlan = new Intent(mContext, CollectPlanActivity.class);
                        repayPlan.putExtra("id", data.id);
                        mContext.startActivity(repayPlan);
                    }
                });
            }
            //出借人咨询fuwuxieyi合同
            holder.tvChuJieXieYi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (status == 1){
                        UIUtils.showToast(mContext, "待银行处理中，尚未生成");
                    }

                    Intent intent1 = new Intent(mContext, BorrowAgreementActivity.class);
                    intent1.putExtra("productId",  UrlUtil.BASE_URL + "/mobile/contract/product/contract?oidTenderId=" + data.id + "&type=2");

                    intent1.putExtra("title","出借人借款服务协议");
                    mContext.startActivity(intent1);
                }
            });

            //1-准备发布2-初审3-终审4-初审拒绝5-终审拒绝 6-招标失败 7-招标中8-已撤销9-流标10-还款中11-已还清12-逾期13-坏账

            //借款协议 2
            holder.tvJieKuanXieYi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BorrowAgreementActivity.class);
                    //满标
                    if (data.state == 4) {
                        //借款协议
//                        intent.putExtra("productId", UrlUtil.getBorrowUrl() + data.productId + "&agreementType=3" +
//                                "&bidId=" + data.id);
                        intent.putExtra("productId",  UrlUtil.BASE_URL + "/mobile/contract/product/contract?oidTenderId=" + data.id + "&type=1");
                        intent.putExtra("title","借款协议");
                    } else if (data.status == 14) {
                        UIUtils.showToast(mContext, "满标待银行处理中，未生成借款协议");
                    } else {
                        if (data.productType == 1) {
                            //借款协议
//                            intent.putExtra("productId", UrlUtil.getBorrowUrl() + data.rootProductId +
//                                    "&agreementType=3" + "&newProductId=" + data.productId);
                            intent.putExtra("productId",  UrlUtil.BASE_URL + "/mobile/contract/product/contract?oidTenderId=" + data.id + "&type=1");
                            intent.putExtra("title","借款协议");

                        } else {
                            //借款协议
//                            intent.putExtra("productId", UrlUtil.getBorrowUrl() + data.productId + "&bidId=" + data.id + "&agreementType=1");
                            intent.putExtra("productId",  UrlUtil.BASE_URL + "/mobile/contract/product/contract?oidTenderId=" + data.id + "&type=1");
                            intent.putExtra("title","借款协议");
                        }
                    }
                    mContext.startActivity(intent);
                }
            });
        }
        if (data.productType == 1 || data.state == 4) {
            holder.ivObjType.setImageResource(R.mipmap.zhuan);
//          holder.tvJieKuanXieYi.setText("转让协议");
            holder.tvJieKuanXieYi.setText("借款协议");
        } else {
            holder.ivObjType.setImageResource(R.drawable.invest_type_level);
            holder.ivObjType.setImageLevel(data.assetType);
            holder.tvJieKuanXieYi.setText("借款协议");
        }

        //        switch (data.assetType)
        //        {
        //            case 1:
        //                break;
        //            case 2:
        //                break;
        //            case 3://但
        //                holder.ivObjType.setImageResource(R.mipmap.product_type_3);
        //                break;
        //            case 4://信
        //                holder.ivObjType.setImageResource(R.mipmap.product_type_4);
        //                break;
        //            case 5:
        //                holder.ivObjType.setImageResource(R.mipmap.product_type_5);
        //                break;
        //            case 6:
        //                holder.ivObjType.setImageResource(R.mipmap.product_type_6);
        //                break;
        //            case 7:
        //                holder.ivObjType.setImageResource(R.mipmap.product_type_7);
        //                break;
        //            case 8:
        //                CfLog.i("---"+data.assetType);
        //                holder.ivObjType.setImageResource(R.mipmap.product_type_8);
        //                break;
        //            case 9:
        //                holder.ivObjType.setImageResource(R.mipmap.product_type_9);
        //                break;
        //            default:
        //                break;
        //        }
        //用来区分是还款中0、招标中1，还是已完结2

        //1-准备发布2-初审3-终审4-初审拒绝5-终审拒绝 6-招标失败 7-招标中8-已撤销9-流标10-还款中11-已还清12-逾期13-坏账
        // 20- 银行放款处理中 21银行放款处理失败



        if (data.state == 4) {
            holder.ivStatus.setImageResource(R.mipmap.equ_9);
        } else {

            if (ProductStatusUtil.status2SmallImg(data.status) != 0) {
                holder.ivStatus.setImageResource(ProductStatusUtil.status2SmallImg(data.status));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onEnterListener()//确定撤标
    {
        quitDialog.dismiss();
        UIUtils.showLoading(mContext);
        CheBiaoVo vo = new CheBiaoVo(mData.get(chebiaoPosition).id, mData.get(chebiaoPosition).productId);

        HashMap<String, String> map = new HashMap<>();
        map.put("name", CacheUtils.getUserinfo(mContext).getName());
        map.put("HHmm", TimeFormatUtil.getSimpleTime());
        map.put("HH", TimeFormatUtil.getHour());
        map.put("id", vo.id);
        map.put("productId", vo.productId);
        MobclickAgent.onEvent(mContext, ServiceName.CGKX_WITHDRAWBID, map);

        HttpManager.getInstance().doPost(ServiceName.CGKX_WITHDRAWBID, vo, new HttpCallBack<ServerResponse>() {
            @Override
            public void getHttpCallNull(String str) {
                UIUtils.showToast(mContext, mContext.getResources().getString(R.string.service_err2) + ",撤标失败！");
                UIUtils.dimissLoading();
            }

            @Override
            public void getHttpCallBack(ServerResponse rsp) {
                UIUtils.dimissLoading();
                if (rsp != null) {
                    if (rsp.getCode().equals("000000")) {
                        UIUtils.showToast(mContext, "撤标成功！");
                        mData.remove(chebiaoPosition);
                        jRecyclerView.getAdapter().notifyDataSetChanged();
                    } else {
                        UIUtils.showToast(mContext, rsp.getMsg() + ",撤标失败！");
                    }
                } else {
                    UIUtils.showToast(mContext, mContext.getResources().getString(R.string.service_err2) + ",撤标失败！");
                }
            }
        });
    }

    @Override
    public void onCancelListener()//取消撤标
    {
        quitDialog.dismiss();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        Button btCheBiao;//撤标
        ImageView ivStatus;//标的状态
        TextView tvHuanKuanShiJian;//还款时间
        TextView tvHuanKuanFangShi;//还款方式
        TextView tvYiShouBenXi;//已收本息
        TextView tvDaiShouBenXi;//待收本息
        TextView tvQiXian;//期限

        TextView tvIncom;//年化收益
        TextView tvInvestMoney;//投资金额
        TextView tvTitle;//标的标题
        ImageView ivObjType;//标的类型图片
        TextView tvUnit;//单位

        View llShouKuanJiHua;
        TextView tvShouKuanJiHua;//收款计划
        TextView tvJieKuanXieYi;//借款协议
        TextView tvChuJieXieYi; //出借人借款服务协议

        public MyViewHolder(View itemView) {
            super(itemView);
            ivObjType = (ImageView) itemView.findViewById(R.id.iv_objtype);
            ivStatus = (ImageView) itemView.findViewById(R.id.iv_status);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvInvestMoney = (TextView) itemView.findViewById(R.id.tv_invest_money);
            tvIncom = (TextView) itemView.findViewById(R.id.tv_income);
            tvQiXian = (TextView) itemView.findViewById(R.id.tv_qixian);
            tvDaiShouBenXi = (TextView) itemView.findViewById(R.id.tv_daishoubenxi);
            tvYiShouBenXi = (TextView) itemView.findViewById(R.id.tv_yishoubenxi);
            tvHuanKuanFangShi = (TextView) itemView.findViewById(R.id.tv_huankuanfangshi);
            tvHuanKuanShiJian = (TextView) itemView.findViewById(R.id.tv_huankuanshijian);
            btCheBiao = (Button) itemView.findViewById(R.id.bt_chebiao);
            tvUnit = (TextView) itemView.findViewById(R.id.tv_unit);

            llShouKuanJiHua = itemView.findViewById(R.id.ll_shoukuanjihua);
            tvShouKuanJiHua = (TextView) itemView.findViewById(R.id.tv_shoukuanjihua);
            tvJieKuanXieYi = (TextView) itemView.findViewById(R.id.tv_jiekuanxieyi);
            tvChuJieXieYi = (TextView) itemView.findViewById(R.id.tv_chujiexiyi);
        }
    }
}
