package com.mingjie.jf.utils;

import com.mingjie.jf.R;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.utils
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 标的状态转换成对应图片
 * <p>创 建 人： hexiangjun
 * <p>创建时间： 2016-09-22 10:08
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ProductStatusUtil
{
    /**
     *  readyPublish("准备发布", 1),
     firstAudit("初审",2),
     finalAudit("终审",3),
     firstAuditReject("初审拒绝",4),
     finalAuditReject("终审拒绝",5),
     failBid("招标失败",6),
     biding("招标中",7),
     canceled("已撤销",8),
     flowProduct("流标",9),
     repaymentIng("还款中",10),
     payOff("已还清",11),
     overdue("逾期",12),
     badDebt("坏账",13),
     fullBid("满标",14),
     bankLoanIng("放款中",20),
     bankLoanFail("放款失败",21);
     */

    /**
     * @param status 标的状态<br></>
     * @return (小图标)标的状态对应的图片id, 未找到对应状态时 返回0，使用时须对0进行判断
     */
    public static int status2SmallImg(int status)
    {
        switch (status)
        {
            case 1:
                return R.mipmap.obj_status_s1;
            case 2:
                return R.mipmap.obj_status_s2;
            case 3:
                return R.mipmap.obj_status_s3;
            case 4:
                return R.mipmap.obj_status_s4;
            case 5:
                return R.mipmap.obj_status_s5;
            case 6:
                return R.mipmap.obj_status_s6;
            case 7:
                return R.mipmap.obj_status_s7;
            case 8:
                return R.mipmap.obj_status_s8;
            case 9:
                return R.mipmap.obj_status_s9;
            case 10:
                return R.mipmap.obj_status_s10;
            case 11:
                return R.mipmap.obj_status_s11;
            case 12:
                return R.mipmap.obj_status_s12;
            case 13:
                return R.mipmap.obj_status_s13;
            case 14:
                return R.mipmap.obj_status_s14;
            case 20:
                return R.mipmap.obj_status_s20;
            case 21:
                return R.mipmap.obj_status_s21;
            default:
                return 0;
        }
    }

    /**
     * @param status 标的状态<br></>
     * @return (大图标)标的状态对应的图片id, 未找到对应状态时 返回0，使用时须对0进行判断
     */
    public static int status2BigImg(int status)
    {
        switch (status)
        {
            case 1:
                return R.mipmap.obj_status_b1;
            case 2:
                return R.mipmap.obj_status_b2;
            case 3:
                return R.mipmap.obj_status_b3;
            case 4:
                return R.mipmap.obj_status_b4;
            case 5:
                return R.mipmap.obj_status_b5;
            case 6:
                return R.mipmap.obj_status_b6;
            case 7:
                return R.mipmap.obj_status_b7;
            case 8:
                return R.mipmap.obj_status_b8;
            case 9:
                return R.mipmap.obj_status_b9;
            case 10:
                return R.mipmap.obj_status_b10;
            case 11:
                return R.mipmap.obj_status_b11;
            case 12:
                return R.mipmap.obj_status_b12;
            case 13:
                return R.mipmap.obj_status_b13;
            case 14:
                return R.mipmap.obj_status_b14;
            case 20:
                return R.mipmap.obj_status_b20;
            case 21:
                return R.mipmap.obj_status_b21;
            default:
                return 0;
        }
    }

    /**
     * @param status 标的状态<br></>
     * @return 标的状态对应的Sting描述, 未找到对应状态时 返回“”，使用时须对“”进行判断
     */
    public static String status2String(int status)
    {
        switch (status)
        {
            case 1:
                return "准备发布";
            case 2:
                return "初审";
            case 3:
                return "终审";
            case 4://初审拒绝
                return "初审拒绝";
            case 5://终审拒绝
                return "终审拒绝";
            case 6:
                return "招标失败";
            case 7://招标中
                return "招标中";
            case 8://已撤销
                return "已撤销";
            case 9://流标
                return "流标";
            case 10://还款中
                return "还款中";
            case 11:
                return "已还清";
            case 12://逾期
                return "逾期";
            case 13://坏账
                return "坏账";
            case 14://满标
                return "满标";

            case 20://放款中
                return "放款中";
            case 21://放款失败
                return "放款失败";
            default:
                return "";
        }
    }

    /**
     * @param status 标的状态<br></>
     * @return 标的状态对应是否可投，只有当标的状态为招标中时（status=7）才可投
     */
    public static boolean isCanInvest(int status)
    {
        return status == 7;
    }
}
