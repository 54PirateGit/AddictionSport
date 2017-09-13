package com.tianbao.addictionsport.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianbao.addictionsport.R;
import com.tianbao.addictionsport.constant.IntegerConstants;
import com.tianbao.addictionsport.constant.StringConstants;
import com.tianbao.addictionsport.mode.UserSelf;
import com.tianbao.addictionsport.net.ApiService;
import com.tianbao.addictionsport.net.NetApi;
import com.tianbao.addictionsport.utils.L;
import com.tianbao.addictionsport.utils.T;

import java.util.HashMap;
import java.util.List;
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

    Unbinder unbinder;
    @BindView(R.id.text_beyond)
    TextView textBeyond;// 超过
    @BindView(R.id.text_total)
    TextView textTotal;// 瘾卡总额
    @BindView(R.id.view_purchase)
    View viewPurchase;// 购买记录 如果没有记录则不显示
    @BindView(R.id.text_more)
    TextView textMore;// 查看更多记录
    @BindView(R.id.image_cover)
    ImageView imageCover;// 购买课程信息中的图片
    @BindView(R.id.text_state)
    TextView textState;// 订单状态
    @BindView(R.id.text_courses_name)
    TextView textCoursesName;// 课程
    @BindView(R.id.text_courses_address)
    TextView textCoursesAddress;// 课程地址
    @BindView(R.id.text_courses_time)
    TextView textCoursesTime;// 课程时间
    @BindView(R.id.text_courses_price)
    TextView textCoursesPrice;// 课程价格
    @BindView(R.id.text_courses_num)
    TextView textCoursesNum;// 课程人数
    @BindView(R.id.view_courses)
    RelativeLayout viewCourses;// 课程信息 点击可查看详情
    @BindView(R.id.view_integral)
    RelativeLayout viewIntegral;// 我的积分
    @BindView(R.id.view_coupon)
    RelativeLayout viewCoupon;// 我的礼券
    @BindView(R.id.view_problem)
    RelativeLayout viewProblem;// 常见问题
    @BindView(R.id.view_about)
    RelativeLayout viewAbout;// 关于
    @BindView(R.id.view_join)
    RelativeLayout viewJoin;// 加入
    @BindView(R.id.text_calorie)
    TextView textCalorie;// 总消耗
    @BindView(R.id.text_duration_week)
    TextView textDurationWeek;// 周运动量
    @BindView(R.id.text_duration_int)
    TextView textDurationInt;// 总运动时长 整数部分
    @BindView(R.id.text_duration_fractional)
    TextView textDurationFractional;// 总运动时长 小数部分

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

            try {
                request();
            } catch (Exception e) {
                e.printStackTrace();
                T.showLong(context, "run exception");
            }
        }
        return rootView;
    }

    // 发送网络请求获取网络数据
    private void request() throws Exception {
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
                // 数据对象
                UserSelf self = response.body();
                if (self == null) return ;

                int code = self.getCode();
                if (code == IntegerConstants.RESULT_CODE) {
                    L.i("网络请求成功 个人中心信息获取成功");

                    // 数据
                    UserSelf.DataBean data = self.getData();
                    if (data == null) return ;

                    // 总消耗
                    String calorie = data.getCalorieTotal();
                    if (calorie == null || calorie.equals("")) calorie = "0";
                    textCalorie.setText(calorie);

                    // 本周运动时长
                    String durationWeek = data.getDurationWeek().getIntPart();
                    if (durationWeek == null || durationWeek.equals("")) durationWeek = "0";
                    textDurationWeek.setText(durationWeek);

                    // 总运动时长 整数部分
                    String intPart = data.getDurationTotal().getIntPart();
                    if (intPart == null || intPart.equals("") || intPart.equals("0")) {
                        textDurationInt.setVisibility(View.GONE);
                    } else {
                        textDurationInt.setVisibility(View.VISIBLE);
                        textDurationInt.setText(intPart);
                    }

                    // 总运动时长 小数部分
                    String fractionalPart = data.getDurationTotal().getFractionalPart();
                    if (fractionalPart == null || fractionalPart.equals("")) fractionalPart = "0";
                    textDurationFractional.setText(fractionalPart);

                    // 我的瘾卡总额
                    List<UserSelf.DataBean.CardsBean> cards = data.getCards();
                    if (cards == null || cards.size() < 0) {
                        textTotal.setText("0");
                    } else {
                        float total = 0;
                        for (int i=0; i<cards.size(); i++) {
                            String t = cards.get(i).getTotal();
                            if (t != null) {
                                total = total + Float.valueOf(t);// 计算出总额
                            }
                        }
                        textTotal.setText(String.valueOf(total));// 显示在界面上
                    }

                    // 购买记录 显示第一条数据
                    List<UserSelf.DataBean.OrdersBean> order = data.getOrders();
                    if (order == null || order.size() < 0) {
                        viewPurchase.setVisibility(View.GONE);// 查询没有购买记录则不显示在界面上
                    } else {// 有购买记录
                        viewPurchase.setVisibility(View.VISIBLE);
                        order.get(0);// 首页只显示第一条记录
                    }

                } else {
                    L.v(code);
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
