package com.tianbao.addictionsport.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tianbao.addictionsport.R;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 测试 Retrofit 的使用
 * Created by edianzu on 2017/9/8.
 */

public class RetrofitRequestActivity  extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                request();
            }
        }, 2000);
    }

    // 发送网络请求
    private void request() {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://106.15.91.17")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface service = retrofit.create(APIInterface.class);
        Call<CourseSchedule> model = service.repo("schedule");

        model.enqueue(new Callback<CourseSchedule>() {
            @Override
            public void onResponse(Response<CourseSchedule> response, Retrofit retrofit) {
                System.out.print(response.body().getMessage());

                Log.i("TAG", "网络请求成功");

                Log.v("TAG", response.body().getCode());

                Log.v("TAG", response.body().getMessage());

                response.headers().values("");// 获取头部信息

                Log.v("TAG", response.body().getData().getAddress() + "+  ====");
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.print(t.getMessage());

                Log.e("TAG", "网络请求失败");
            }
        });
    }
}
