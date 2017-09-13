package com.tianbao.addictionsport.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tianbao.addictionsport.R;
import com.tianbao.addictionsport.activity.CoursesDetailActivity;
import com.tianbao.addictionsport.activity.MainActivity;
import com.tianbao.addictionsport.activity.WebViewActivity;
import com.tianbao.addictionsport.adapter.BannerAdapter;
import com.tianbao.addictionsport.adapter.CoursesListAdapter;
import com.tianbao.addictionsport.constant.IntegerConstants;
import com.tianbao.addictionsport.constant.StringConstants;
import com.tianbao.addictionsport.mode.CourseSchedule;
import com.tianbao.addictionsport.mode.CourseSchedule.DataBean.CalendarBean;
import com.tianbao.addictionsport.mode.CourseSchedule.DataBean.Course4DayBean.CourseBean;
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
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 课程
 */
public class CoursesFragment extends Fragment {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.text_address)
    TextView textAddress;
    @BindView(R.id.text_map)
    TextView textMap;
    Unbinder unbinder;
    @BindView(R.id.text_day_1)
    TextView textDay1;
    @BindView(R.id.text_day_2)
    TextView textDay2;
    @BindView(R.id.text_day_3)
    TextView textDay3;
    @BindView(R.id.text_day_4)
    TextView textDay4;
    @BindView(R.id.text_day_5)
    TextView textDay5;
    @BindView(R.id.text_day_6)
    TextView textDay6;
    @BindView(R.id.text_day_7)
    TextView textDay7;

    private Context context;

    private CourseSchedule courseSchedule;// 数据对象
    private CoursesListAdapter clAdapter;
    private List<CourseBean> courseBeanList;// 课程信息数据

    private View rootView;
    private ListView coursesListView;// list_courses  课程列表

    private int position = 0;// 课程日期位置
//    private int coursesId = -1;// 课程 Id

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_courses, container, false);

            initViews();
        }
        return rootView;
    }

    // 初始化视图
    private void initViews() {
        View headView = LayoutInflater.from(context).inflate(R.layout.head_courses, null, false);// 头部 View
        unbinder = ButterKnife.bind(this, headView);

        coursesListView = rootView.findViewById(R.id.list_courses);// 课程列表
        coursesListView.addHeaderView(headView);
        coursesListView.setOnItemClickListener(new OnItemClickHandler());

        initRequest();// 数据请求开始
    }

    private boolean isRun = true;
    private int index = 0;

    private String openId = "-1";
    private String jwtToken = "-1";

    /**
     * 初始化网络请求 主要是需要的参数需要在别的接口中请求获取
     */
    public void initRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRun) {
                    openId = MainActivity.getOpenId();
                    jwtToken = MainActivity.getJwtToken();
                    index++;
                    if (index == 10) {
                        isRun = false;// 停止线程

                        if ((openId == null || openId.equals("-1")) || (jwtToken == null || jwtToken.equals("-1"))) {

                            try {
                                request(openId, jwtToken);// 获取课程页面信息网络请求
                            } catch (Exception e) {
                                e.printStackTrace();
                                L.e("Run Exception");
                            }
                        }
                    }
                    if ((openId != null && !openId.equals("-1")) && (jwtToken != null && !jwtToken.equals("-1"))) {
                        isRun = false;// 数据获取之后停止线程

                        try {
                            request(openId, jwtToken);// 获取课程页面信息网络请求
                        } catch (Exception e) {
                            e.printStackTrace();
                            L.e("Run Exception");
                        }
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    // 发送网络请求
    private void request(String openId, String jwtToken) throws Exception {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(NetApi.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Map<String, String> param = new HashMap<>();
        param.put("openId", openId);

        ApiService service = retrofit.create(ApiService.class);
        Call<CourseSchedule> model = service.requestCoursesSchedule(param, jwtToken);

        model.enqueue(new Callback<CourseSchedule>() {
            @Override
            public void onResponse(Response<CourseSchedule> response, Retrofit retrofit) {
                int code = response.body().getCode();
                if (code == IntegerConstants.RESULT_CODE) {
                    L.i("网络请求成功");

                    courseSchedule = response.body();
                    if (courseSchedule != null) {
                        // 轮播图
                        final List<CourseSchedule.DataBean.BannerBean> bannerBeanList = courseSchedule.getData().getBanner();
                        if (bannerBeanList != null && bannerBeanList.size() > 0) {
                            List<String> bannerData = new ArrayList<>();
                            String url;
                            for (int i=0; i<bannerBeanList.size(); i++) {
                                url = bannerBeanList.get(i).getIcon();
                                bannerData.add(url);
                            }

                            BannerAdapter bannerAdapter = new BannerAdapter(context, code);
                            bannerAdapter.setData(bannerData);
                            banner.setDotGravity(Banner.CENTER).
                                    setDot(R.drawable.no_selected_dot, R.drawable.selected_dot).
                                    setAdapter(bannerAdapter).
                                    setOnItemClickListener(new BannerPagerAdapter.onItemClickListener() {
                                        @Override
                                        public void onClick(int position) {
                                            String url = bannerBeanList.get(position).getEvent().getUrl();
                                            Intent intent = new Intent(context, WebViewActivity.class);
                                            intent.putExtra(StringConstants.BANNER_REDIRECT_URL, url);
                                            context.startActivity(intent);
                                        }
                                    }).
                                    startAutoPlay();
                        }

                        // 课程时间
                        List<CalendarBean> days = courseSchedule.getData().getCalendar();
                        setDays(days);

                        // 地址
                        CourseSchedule.DataBean.AddressBean addressBean = courseSchedule.getData().getAddress();
                        String address = addressBean.getName();
                        if (address != null && !address.equals("")) {
                            textAddress.setText(address);
                        }

                        // 默认加载课程第一天的数据
                        courseBeanList = courseSchedule.getData().getCourse4Day().get(position).getCourse();
                        if (courseBeanList == null) courseBeanList = new ArrayList<>();
                        clAdapter = new CoursesListAdapter(context, courseBeanList, onClickListener);
                        coursesListView.setAdapter(clAdapter);
                        updateDayView(position);
                    } else {
                        L.i("Data is error");
                        return ;
                    }
                } else {
                    L.v(response.body().getCode());

                    setTestData(code);
                }
                L.v(response.body().getMessage());
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.print(t.getMessage());
                L.e("网络请求失败");

                setTestData(400);
            }
        });
    }

    // 课程列表 Item
    private class OnItemClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            L.i("点击课程 position == " + i);
            if (courseSchedule == null) return ;

            i = i - 1;
            if (i < 0) return ;

            // 课程 Id
            int coursesId = courseSchedule.getData().getCourse4Day().get(position).getCourse().get(i).getId();

            // 课程标题
            String title = courseSchedule.getData().getCourse4Day().get(position).getCourse().get(i).getTitle();
            if (title == null || title.equals("")) {
                title = "冲瘾健身";
            }
            if (coursesId == -1) {
                L.d("courses id is error or null");
                return ;
            }

            // 课程封面图片 URL
            String coursesCoverUrl = courseSchedule.getData().getCourse4Day().get(position).getCourse().get(i).getCoach().getAvatar();
            if (coursesCoverUrl == null || coursesCoverUrl.equals("")) {
                coursesCoverUrl = "-1";
            }

            // 跳转到课程详情界面
            Intent intent = new Intent(context, CoursesDetailActivity.class);
            intent.putExtra(StringConstants.COURSES_ID, coursesId);
            intent.putExtra(StringConstants.COURSES_TITLE, title);
            intent.putExtra(StringConstants.COURSES_COVER_URL, coursesCoverUrl);
            context.startActivity(intent);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (rootView != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        unbinder.unbind();
    }

    // 设置测试数据 仅供测试时使用
    private void setTestData(int code) {
        String jsonString = sourceData();// 获取数据
        courseSchedule = new Gson().fromJson(jsonString, new TypeToken<CourseSchedule>() {}.getType());
        if (courseSchedule == null) {
            L.d("Data is error");
            return ;
        }

        // 轮播图数据  测试
        List<Integer> bannerData = new ArrayList<>();
        bannerData.add(R.mipmap.a1);
        bannerData.add(R.mipmap.a2);
        bannerData.add(R.mipmap.a3);
        bannerData.add(R.mipmap.a4);

        // 轮播图
        BannerAdapter bannerAdapter = new BannerAdapter(context, code);
        bannerAdapter.setData(bannerData);
        banner.setDotGravity(Banner.CENTER).
                setDot(R.drawable.no_selected_dot, R.drawable.selected_dot).
                setAdapter(bannerAdapter).
                setOnItemClickListener(new BannerPagerAdapter.onItemClickListener() {
                    @Override
                    public void onClick(final int position) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                T.showLong(context, "position == " + position);
                            }
                        });
                    }
                }).// 轮播图的点击事件
                startAutoPlay();//  自动播放轮播图

        // 课程时间
        List<CalendarBean> days = courseSchedule.getData().getCalendar();
        setDays(days);

        // 地址
        CourseSchedule.DataBean.AddressBean addressBean = courseSchedule.getData().getAddress();
        String address = addressBean.getName();
        if (address != null && !address.equals("")) {
            textAddress.setText(address);
        }

        // 默认加载课程第一天的数据
        courseBeanList = courseSchedule.getData().getCourse4Day().get(position).getCourse();
        if (courseBeanList == null) courseBeanList = new ArrayList<>();
        clAdapter = new CoursesListAdapter(context, courseBeanList, onClickListener);
        coursesListView.setAdapter(clAdapter);
    }

    @OnClick(R.id.text_day_1)
    public void coursesDay1() {
        updateCourses(0);
    }

    @OnClick(R.id.text_day_2)
    public void coursesDay2() {
        updateCourses(1);
    }

    @OnClick(R.id.text_day_3)
    public void coursesDay3() {
        updateCourses(2);
    }

    @OnClick(R.id.text_day_4)
    public void coursesDay4() {
        updateCourses(3);
    }

    @OnClick(R.id.text_day_5)
    public void coursesDay5() {
        updateCourses(4);
    }

    @OnClick(R.id.text_day_6)
    public void coursesDay6() {
        updateCourses(5);
    }

    @OnClick(R.id.text_day_7)
    public void coursesDay7() {
        updateCourses(6);
    }

    // 更新课程信息
    private void updateCourses(int index) {
        if (courseSchedule == null) {
            L.e("Data is error");
            return ;
        }
        try {
            if (position == index) return ;
            position = index;
            courseBeanList = courseSchedule.getData().getCourse4Day().get(position).getCourse();
            if (courseBeanList == null) courseBeanList = new ArrayList<>();
            if (clAdapter == null) clAdapter = new CoursesListAdapter(context, courseBeanList, onClickListener);
            else clAdapter.setList(courseBeanList);

            updateDayView(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 预约 btn 的点击事件
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            TextView btn = (TextView) view;

            int pos = (Integer) btn.getTag();

            System.out.println(" position :" + pos);
        }
    };

    // 设置时间
    private void setDays(List<CalendarBean> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        String day = "一";
        for (int i = 0; i < list.size(); i++) {
            switch (i) {
                case 0:
                    day = list.get(0).getDayOfWeek();
                    if (day == null || day.equals("")) {
                        day = "一";
                    }
                    textDay1.setText(day);
                    break;
                case 1:
                    day = list.get(1).getDayOfWeek();
                    if (day == null || day.equals("")) {
                        day = "二";
                    }
                    textDay2.setText(day);
                    break;
                case 2:
                    day = list.get(2).getDayOfWeek();
                    if (day == null || day.equals("")) {
                        day = "三";
                    }
                    textDay3.setText(day);
                    break;
                case 3:
                    day = list.get(3).getDayOfWeek();
                    if (day == null || day.equals("")) {
                        day = "四";
                    }
                    textDay4.setText(day);
                    break;
                case 4:
                    day = list.get(4).getDayOfWeek();
                    if (day == null || day.equals("")) {
                        day = "五";
                    }
                    textDay5.setText(day);
                    break;
                case 5:
                    day = list.get(5).getDayOfWeek();
                    if (day == null || day.equals("")) {
                        day = "六";
                    }
                    textDay6.setText(day);
                    break;
                case 6:
                    day = list.get(6).getDayOfWeek();
                    if (day == null || day.equals("")) {
                        day = "日";
                    }
                    textDay7.setText(day);
                    break;
            }
            L.i("i == " + i + " -> " + "星期" + day);
        }
    }

    // 更新课程时间选中与非选中的显示效果
    private void updateDayView(int index) {
        if (index < 0 || index > 6) return ;// 超出范围
        switch (index) {
            case 0:
                textDay1.setBackground(context.getDrawable(R.drawable.tab_day_select_background));

                textDay2.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay3.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay4.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay5.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay6.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay7.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                break;
            case 1:
                textDay2.setBackground(context.getDrawable(R.drawable.tab_day_select_background));

                textDay1.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay3.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay4.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay5.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay6.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay7.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                break;
            case 2:
                textDay3.setBackground(context.getDrawable(R.drawable.tab_day_select_background));

                textDay1.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay2.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay4.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay5.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay6.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay7.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                break;
            case 3:
                textDay4.setBackground(context.getDrawable(R.drawable.tab_day_select_background));

                textDay1.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay2.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay3.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay5.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay6.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay7.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                break;
            case 4:
                textDay5.setBackground(context.getDrawable(R.drawable.tab_day_select_background));

                textDay1.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay2.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay3.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay4.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay6.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay7.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                break;
            case 5:
                textDay6.setBackground(context.getDrawable(R.drawable.tab_day_select_background));

                textDay1.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay2.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay3.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay4.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay5.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay7.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                break;
            case 6:
                textDay7.setBackground(context.getDrawable(R.drawable.tab_day_select_background));

                textDay1.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay2.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay3.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay4.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay5.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                textDay6.setBackground(context.getDrawable(R.drawable.tab_day_no_select_background));
                break;
        }
    }

    // 源数据  测试用
    private String sourceData() {
        String sourceData = "{\n" +
                "    \"code\": 200,\n" +
                "    \"data\": {\n" +
                "        \"address\": {\n" +
                "            \"detailAddress\": \"湖南长沙市岳麓区麓山南路店299号渔湾码头时尚商业广场1层\",\n" +
                "            \"latitude\": \"28.1692535039\",\n" +
                "            \"longitude\": \"112.9443454742\",\n" +
                "            \"name\": \"天马冲瘾店\"\n" +
                "        },\n" +
                "        \"banner\": [\n" +
                "            {\n" +
                "                \"disable\": true,\n" +
                "                \"event\": {\n" +
                "                    \"url\": \"https://ju.taobao.com/m/jusp/o/dw/mtp.htm\"\n" +
                "                },\n" +
                "                \"icon\": \"http://img.alicdn.com/imgextra/i3/56/TB2FNdkXaKIJuJjSZFxXXavHVXa_!!56-0-yamato.jpg_q50.jpg\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"disable\": true,\n" +
                "                \"event\": {\n" +
                "                    \"url\": \"http://h5.taobao.com\"\n" +
                "                },\n" +
                "                \"icon\": \"http://img.alicdn.com/simba/img/TB1GrxMSVXXXXXPXpXXSutbFXXX.jpg_q50.jpg\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"calendar\": [\n" +
                "            {\n" +
                "                \"dayOfWeek\": \"一\",\n" +
                "                \"dayOfWeekDetail\": \"今天\",\n" +
                "                \"monthDayFormat\": \"09.11\",\n" +
                "                \"yearMonthDayFormat\": \"2017-09-11\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"dayOfWeek\": \"二\",\n" +
                "                \"dayOfWeekDetail\": \"明天\",\n" +
                "                \"monthDayFormat\": \"09.12\",\n" +
                "                \"yearMonthDayFormat\": \"2017-09-12\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"dayOfWeek\": \"三\",\n" +
                "                \"dayOfWeekDetail\": \"周三\",\n" +
                "                \"monthDayFormat\": \"09.13\",\n" +
                "                \"yearMonthDayFormat\": \"2017-09-13\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"dayOfWeek\": \"四\",\n" +
                "                \"dayOfWeekDetail\": \"周四\",\n" +
                "                \"monthDayFormat\": \"09.14\",\n" +
                "                \"yearMonthDayFormat\": \"2017-09-14\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"dayOfWeek\": \"五\",\n" +
                "                \"dayOfWeekDetail\": \"周五\",\n" +
                "                \"monthDayFormat\": \"09.15\",\n" +
                "                \"yearMonthDayFormat\": \"2017-09-15\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"dayOfWeek\": \"六\",\n" +
                "                \"dayOfWeekDetail\": \"周六\",\n" +
                "                \"monthDayFormat\": \"09.16\",\n" +
                "                \"yearMonthDayFormat\": \"2017-09-16\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"dayOfWeek\": \"日\",\n" +
                "                \"dayOfWeekDetail\": \"周日\",\n" +
                "                \"monthDayFormat\": \"09.17\",\n" +
                "                \"yearMonthDayFormat\": \"2017-09-17\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"course4Day\": [\n" +
                "            {\n" +
                "                \"course\": [\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": false,\n" +
                "                            \"title\": \"结束\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 89,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"08.00-08.50\",\n" +
                "                        \"stock\": 30,\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-11 08.00-08.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": true,\n" +
                "                            \"title\": \"预约\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 90,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"19.00-19.50\",\n" +
                "                        \"stock\": 9,\n" +
                "                        \"stockIcon\": \"http://gw.alicdn.com/tps/i2/TB19BluIVXXXXX6XpXXN4ls0XXX-183-129.png\",\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-11 19.00-19.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": false,\n" +
                "                            \"title\": \"满员\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 91,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"20.00-20.50\",\n" +
                "                        \"stock\": 0,\n" +
                "                        \"stockIcon\": \"http://img.alicdn.com/tfs/TB1Kxe8QFXXXXbSXVXXXXXXXXXX-183-129.png\",\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-11 20.00-20.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"yearMonthDayFormat\": \"2017-09-11\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"course\": [\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": true,\n" +
                "                            \"title\": \"预约\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 86,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"18.00-18.50\",\n" +
                "                        \"stock\": 30,\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-12 18.00-18.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": true,\n" +
                "                            \"title\": \"预约\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 87,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"19.00-19.50\",\n" +
                "                        \"stock\": 30,\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-12 19.00-19.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": true,\n" +
                "                            \"title\": \"预约\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 88,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"20.00-20.50\",\n" +
                "                        \"stock\": 30,\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-12 20.00-20.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"yearMonthDayFormat\": \"2017-09-12\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"course\": [\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": true,\n" +
                "                            \"title\": \"预约\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 83,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"18.00-18.50\",\n" +
                "                        \"stock\": 30,\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-13 18.00-18.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": true,\n" +
                "                            \"title\": \"预约\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 84,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"19.00-19.50\",\n" +
                "                        \"stock\": 30,\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-13 19.00-19.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": true,\n" +
                "                            \"title\": \"预约\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 85,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"20.00-20.50\",\n" +
                "                        \"stock\": 30,\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-13 20.00-20.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"yearMonthDayFormat\": \"2017-09-13\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"course\": [\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": true,\n" +
                "                            \"title\": \"预约\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 80,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"18.00-18.50\",\n" +
                "                        \"stock\": 30,\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-14 18.00-18.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": true,\n" +
                "                            \"title\": \"预约\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 81,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"19.00-19.50\",\n" +
                "                        \"stock\": 30,\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-14 19.00-19.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": true,\n" +
                "                            \"title\": \"预约\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 82,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"20.00-20.50\",\n" +
                "                        \"stock\": 30,\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-14 20.00-20.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"yearMonthDayFormat\": \"2017-09-14\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"course\": [\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": true,\n" +
                "                            \"title\": \"预约\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 74,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"18.00-18.50\",\n" +
                "                        \"stock\": 30,\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-15 18.00-18.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": true,\n" +
                "                            \"title\": \"预约\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 77,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"18.00-18.50\",\n" +
                "                        \"stock\": 30,\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-15 18.00-18.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": true,\n" +
                "                            \"title\": \"预约\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 75,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"19.00-19.50\",\n" +
                "                        \"stock\": 30,\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-15 19.00-19.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": true,\n" +
                "                            \"title\": \"预约\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 78,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"19.00-19.50\",\n" +
                "                        \"stock\": 30,\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-15 19.00-19.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": true,\n" +
                "                            \"title\": \"预约\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 76,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"20.00-20.50\",\n" +
                "                        \"stock\": 30,\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-15 20.00-20.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": true,\n" +
                "                            \"title\": \"预约\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 79,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"20.00-20.50\",\n" +
                "                        \"stock\": 30,\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-15 20.00-20.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"yearMonthDayFormat\": \"2017-09-15\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"course\": [\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": true,\n" +
                "                            \"title\": \"预约\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 71,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"18.00-18.50\",\n" +
                "                        \"stock\": 30,\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-16 18.00-18.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": true,\n" +
                "                            \"title\": \"预约\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 72,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"19.00-19.50\",\n" +
                "                        \"stock\": 30,\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-16 19.00-19.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"button\": {\n" +
                "                            \"disable\": true,\n" +
                "                            \"title\": \"预约\"\n" +
                "                        },\n" +
                "                        \"coach\": {\n" +
                "                            \"avatar\": \"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74372047,3915263719&fm=117&gp=0.jpg\",\n" +
                "                            \"id\": 1\n" +
                "                        },\n" +
                "                        \"id\": 73,\n" +
                "                        \"price\": \"¥20\",\n" +
                "                        \"shortTime\": \"20.00-20.50\",\n" +
                "                        \"stock\": 30,\n" +
                "                        \"tags\": [\n" +
                "                            \"瑜珈\",\n" +
                "                            \"拉升\"\n" +
                "                        ],\n" +
                "                        \"time\": \"2017-09-16 20.00-20.50\",\n" +
                "                        \"title\": \"Yin-Yang瑜伽1\",\n" +
                "                        \"yenPrice\": \"¥17\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"yearMonthDayFormat\": \"2017-09-16\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"course\": [],\n" +
                "                \"yearMonthDayFormat\": \"2017-09-17\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"message\": \"SUCCESS\"\n" +
                "}";

        return sourceData;
    }
}
