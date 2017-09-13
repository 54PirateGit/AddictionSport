package com.tianbao.addictionsport.activity;

import android.content.Intent;

import com.tianbao.addictionsport.R;
import com.tianbao.addictionsport.adapter.BannerAdapter;
import com.tianbao.addictionsport.base.BaseActivity;
import com.tianbao.addictionsport.constant.IntegerConstants;
import com.tianbao.addictionsport.constant.StringConstants;
import com.tianbao.addictionsport.mode.CourseDetail;
import com.tianbao.addictionsport.net.ApiService;
import com.tianbao.addictionsport.net.NetApi;
import com.tianbao.addictionsport.utils.L;
import com.tianbao.addictionsport.utils.T;
import com.tianbao.addictionsport.widget.banner.Banner;
import com.tianbao.addictionsport.widget.banner.BannerPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 课程详情
 */
public class CoursesDetailActivity extends BaseActivity {

    @BindView(R.id.banner)
    Banner banner;

    @Override
    public void initViews() {
        ButterKnife.bind(this);

        // 获取数据
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        // 课程 Id
        int coursesId = intent.getIntExtra(StringConstants.COURSES_ID, -1);

        // 课程标题
        String coursesTitle = intent.getStringExtra(StringConstants.COURSES_TITLE);
        if (coursesTitle == null) coursesTitle = "冲瘾健身";
        setTextTitle(coursesTitle);

        // 课程封面图片地址
        String coursesCoverUrl = intent.getStringExtra(StringConstants.COURSES_COVER_URL);

        // 轮播图
        banner();

        // 请求网络获取网络数据
        request(coursesId);
    }

    @Override
    public int setView() {
        return R.layout.activity_courses_details;
    }

    // 执行网络请求获取网络数据
    private void request(int coursesId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetApi.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Map<String, String> param = new HashMap<>();
        param.put("openId", StringConstants.OPEN_ID);
        param.put("id", String.valueOf(coursesId));

        ApiService service = retrofit.create(ApiService.class);
        Call<CourseDetail> model = service.requestCoursesDetail(param, StringConstants.JWT_TOKEN);

        model.enqueue(new Callback<CourseDetail>() {
            @Override
            public void onResponse(Response<CourseDetail> response, Retrofit retrofit) {
                int code = response.body().getCode();
                if (code == IntegerConstants.RESULT_CODE) {
                    L.i("网络请求成功 课程详情获取成功");
                } else {
                    L.v(response.body().getCode());
                }
                L.v(response.body().getMessage());
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.print(t.getMessage());
                L.e("网络请求失败");
            }
        });
    }

    // 轮播图
    private void banner() {
        // 轮播图数据  测试
        List<Integer> bannerData = new ArrayList<>();
        bannerData.add(R.mipmap.a1);
        bannerData.add(R.mipmap.a2);
        bannerData.add(R.mipmap.a3);
        bannerData.add(R.mipmap.a4);

        // 轮播图
        BannerAdapter bannerAdapter = new BannerAdapter(this, 400);
        bannerAdapter.setData(bannerData);
        banner.setDotGravity(Banner.CENTER).
                setDot(R.drawable.no_selected_dot, R.drawable.selected_dot).
                setAdapter(bannerAdapter).
                setOnItemClickListener(new BannerPagerAdapter.onItemClickListener() {
                    @Override
                    public void onClick(final int position) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                T.showLong(CoursesDetailActivity.this, "position == " + position);
                            }
                        });
                    }
                }).// 轮播图的点击事件
                startAutoPlay();//  自动播放轮播图
    }
}
