package com.tianbao.addictionsport.net;


import com.tianbao.addictionsport.mode.CourseDetail;
import com.tianbao.addictionsport.mode.CourseSchedule;
import com.tianbao.addictionsport.mode.OpenId;
import com.tianbao.addictionsport.mode.UserSelf;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.QueryMap;

/**
 * API
 * Created by edianzu on 2017/9/8.
 */
public interface ApiService {

    // 用微信 code 换取 openId
    @POST(NetApi.portalOpenid)
    Call<OpenId> wxCodeToOpenId(@QueryMap Map<String, String> param);

    // 课程界面所有信息
    @POST(NetApi.courseSchedule)
    Call<CourseSchedule> requestCoursesSchedule(@QueryMap Map<String, String> param, @Header("jwtToken") String jwtToken);

    // 课程详情
    @POST(NetApi.courseDetail)
    Call<CourseDetail> requestCoursesDetail(@QueryMap Map<String, String> param, @Header("jwtToken") String jwtToken);

    // 我的瘾
    @POST(NetApi.userSelf)
    Call<UserSelf> requestUserSelf(@QueryMap Map<String, String> param, @Header("jwtToken") String jwtToken);
}
