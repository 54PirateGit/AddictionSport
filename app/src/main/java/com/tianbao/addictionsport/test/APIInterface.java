package com.tianbao.addictionsport.test;

import retrofit.Call;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * API
 * Created by edianzu on 2017/9/8.
 */

public interface APIInterface {

    @POST("/api/course/{courseSchedule}")
    Call<CourseSchedule> repo(@Path("courseSchedule") String courseSchedule);
}
