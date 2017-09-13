package com.tianbao.addictionsport.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianbao.addictionsport.R;
import com.tianbao.addictionsport.constant.IntegerConstants;
import com.tianbao.addictionsport.constant.StringConstants;
import com.tianbao.addictionsport.mode.UserSelf;
import com.tianbao.addictionsport.net.ApiService;
import com.tianbao.addictionsport.net.NetApi;
import com.tianbao.addictionsport.utils.L;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 我的瘾
 */
public class MyFragment extends Fragment {

    @BindView(R.id.view_sport_data)
    LinearLayout viewSportData;
    @BindView(R.id.text_beyond)
    TextView textBeyond;
    @BindView(R.id.text_total)
    TextView textTotal;
    @BindView(R.id.text_more)
    TextView textMore;
    @BindView(R.id.image_cover)
    ImageView imageCover;
    @BindView(R.id.text_state)
    TextView textState;
    @BindView(R.id.text_courses_name)
    TextView textCoursesName;
    @BindView(R.id.text_courses_address)
    TextView textCoursesAddress;
    @BindView(R.id.text_courses_time)
    TextView textCoursesTime;
    @BindView(R.id.text_courses_)
    TextView textCourses;
    @BindView(R.id.text_courses_price)
    TextView textCoursesPrice;
    @BindView(R.id.view_courses)
    RelativeLayout viewCourses;
    @BindView(R.id.view_integral)
    RelativeLayout viewIntegral;
    @BindView(R.id.view_coupon)
    RelativeLayout viewCoupon;
    @BindView(R.id.view_problem)
    RelativeLayout viewProblem;
    @BindView(R.id.view_about)
    RelativeLayout viewAbout;
    @BindView(R.id.view_join)
    RelativeLayout viewJoin;
    Unbinder unbinder;

    private Context context;
    private View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_my, container, false);
            unbinder = ButterKnife.bind(this, rootView);

            request();
        }
        return rootView;
    }

    // 发送网络请求获取网络数据
    private void request() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetApi.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 请求需要的参数
        Map<String, String> param = new HashMap<>();
        param.put("openId", StringConstants.OPEN_ID);// openId

        ApiService service = retrofit.create(ApiService.class);
        Call<UserSelf> model = service.requestUserSelf(param, StringConstants.JWT_TOKEN);

        model.enqueue(new Callback<UserSelf>() {
            @Override
            public void onResponse(Response<UserSelf> response, Retrofit retrofit) {
                int code = response.body().getCode();
                if (code == IntegerConstants.RESULT_CODE) {
                    L.i("网络请求成功 个人中心信息获取成功");
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (rootView != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
            unbinder.unbind();
        }
    }
}
