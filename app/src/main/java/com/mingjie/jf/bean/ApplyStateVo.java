package com.mingjie.jf.bean;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.bean
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 创业小伙伴 参数
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-09-24 19:23
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ApplyStateVo
{

    /**
     * code : 000000
     * data : {"isFundDepository":1,"stateInt":2}
     * msg : 成功
     * success : true
     */

    public String code;
    /**
     * isFundDepository : 1
     * stateInt : 2
     */

    public DataBean data;
    public String msg;
    public boolean success;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public DataBean getData()
    {
        return data;
    }

    public void setData(DataBean data)
    {
        this.data = data;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public static class DataBean
    {
        public int isFundDepository;
        public int stateInt;
        public double directCommissionRate;     // 直接推荐提成
        public double indirectCommissionRate;   // 间接推荐提成
        public String note;   // 拒绝备注

        public String getNote()
        {
            return note;
        }

        public void setNote(String note)
        {
            this.note = note;
        }

        public double getIndirectCommissionRate()
        {
            return indirectCommissionRate;
        }

        public void setIndirectCommissionRate(double indirectCommissionRate)
        {
            this.indirectCommissionRate = indirectCommissionRate;
        }

        public double getDirectCommissionRate()
        {
            return directCommissionRate;
        }

        public void setDirectCommissionRate(double directCommissionRate)
        {
            this.directCommissionRate = directCommissionRate;
        }

        public int getIsFundDepository()
        {
            return isFundDepository;
        }

        public void setIsFundDepository(int isFundDepository)
        {
            this.isFundDepository = isFundDepository;
        }

        public int getStateInt()
        {
            return stateInt;
        }

        public void setStateInt(int stateInt)
        {
            this.stateInt = stateInt;
        }
    }
}
