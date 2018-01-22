package com.mingjie.jf.bean;

/**
 * <p>项目名称：CunGuan
 * <p>包   名： com.minghao.cgkx.Activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 开户结果查询
 * <p>创 建 人： qinsiyi
 * <p>创建时间： 2016-09-24 15:33
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */

public class OpenCtResult
{
    /**
     * 原交易流水号
     */
    public String oldreqseqno;

    /**
     * 交易状态 S： 成功 F： 失败 R：处理中（客户仍停留在页面操作） N：未知（已提交后台，需再次发查询接口。）
     */
    public String return_status;

    /**
     * 失败原因 （失败才返回）
     */
    public String errormsg;

    /**
     * 银行交易流水号 （成功才返回）
     */
    public String resjnlno;

    /**
     * 交易日期
     */
    public String transdt;

    /**
     * 交易时间
     */
    public String transtm;

    /**
     * 银行账号 （RETURN_STATUS=S 时才有）
     */
    public String acno;

    /**
     * 客户姓名
     */
    public String acname;

    /**
     * 证件类型 （身份证：1010）
     */
    public String idtype;

    /**
     * 证件号码
     */
    public String idno;

    /**
     * 手机号码
     */
    public String mobile;

    /**
     * 备用字段1
     */
    public String ext_filed1;



}
