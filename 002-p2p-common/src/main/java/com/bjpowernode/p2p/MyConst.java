package com.bjpowernode.p2p;

public class MyConst {
    private MyConst(){};

    //平均历史年化收益率
    public static final String AVG_RATE = "avgRate";
    //平台总用户数
    public static final String TOTAL_USERS = "totalUsers";
    //累计交易总额
    public static final String TOTAL_BID_MONEY = "totalBidMoney";

    //产品类型代号：0表示新手宝，1表示优选产品，2表示散标
    public static final Integer PRODUCT_TYPE_SXB = 0;
    public static final Integer PRODUCT_TYPE_YX = 1;
    public static final Integer PRODUCT_TYPE_SB = 2;

    //登陆成功的用户在会话作用域的名称
    public static final String SESSION_USER = "loginUser";
}
