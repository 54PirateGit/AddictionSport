package com.tianbao.addictionsport.net;

/**
 * 网络数据接口
 * Created by edianzu on 2017/9/7.
 */

public class NetApi {

    public final static String baseUrl = "http://106.15.91.17/api/";

    // Order
    public final static String orderList = "order/list";// 课程订单列表

    public final static String orderCreate = "order/create";// 课程单次购买界面，下单，生成订单，但还没支付

    public final static String orderBuild = "order/build";// 课程单次购买界面，用户点击“预约”按钮时调用。

    public final static String wxCancel = "wx/cancel";// 真正取消订单。这个现在只是用来测试用，上线后，会隐藏

    public final static String orderDetail = "order/detail";// 查看订单详情

    public final static String orderCancel = "order/cancel";// 订单取消

    public final static String orderAdjust = "order/adjust";// 课程单次购买界面，用户点调整购买参数时实时调用。

    // course
    public final static String courseSchedule = "course/schedule";// 课程列表

    public final static String courseDetail = "course/detail";// 课程详情

    // coupon
    public final static String couponList = "coupon/list";// 礼券列表

    public final static String couponObtain = "coupon/obtain";// 领取礼券

    // card
    public final static String cardAdjust = "yen/card/adjust";// 瘾卡充值界面

    public final static String wxArrival = "wx/arrival";// 测试接口 上线会关闭 需到微信支付才能成功

    public final static String cardList = "yen/card/list";// 瘾卡列表

    public final static String cardCreate = "yen/card/create";// 瘾卡充值界面点击支付时调用

    public final static String cardBuild = "yen/card/build";// 瘾卡信息界面 点击充值跳转到瘾卡充值界面

    // user
    public final static String userSelf = "user/self";// 我的瘾

    public final static String userRecommend = "user/recommend";// 接受邀请

    public final static String  userPin = "user/pin";// 获取验证码

    public final static String userInvitation = "user/invitation";// 邀请好友

    public final static String userValidatePhone = "user/validate/phone";// 验证手机

    // 微信
    public final static String portalOpenid = "wechat/portal/openid";// 用微信 code 换取 openId

    public final static String portalUser = "wechat/portal/user";// 通过 openId 获得基本用户信息

}
