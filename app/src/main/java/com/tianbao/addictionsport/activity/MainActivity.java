package com.tianbao.addictionsport.activity;

import android.app.FragmentTransaction;
import android.widget.TextView;

import com.tianbao.addictionsport.R;
import com.tianbao.addictionsport.base.BaseActivity;
import com.tianbao.addictionsport.constant.StringConstants;
import com.tianbao.addictionsport.fragment.CoursesFragment;
import com.tianbao.addictionsport.fragment.MyFragment;
import com.tianbao.addictionsport.net.authorization.WXCodeToOpenId;
import com.tianbao.addictionsport.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.txt_courses)
    TextView txtCourses;// 课程
    @BindView(R.id.txt_my)
    TextView txtMy;// 我的瘾

    private FragmentTransaction transaction;
    private CoursesFragment cFragment;
    private MyFragment mFragment;

    @Override
    public void initViews() {
        setNoBack();

        ButterKnife.bind(this);

        transaction = getFragmentManager().beginTransaction();
        if (mFragment != null) {
            transaction.hide(mFragment);
        }
        if (cFragment == null) {
            cFragment = new CoursesFragment();
            transaction.add(R.id.fragment_container, cFragment);
        } else {
            transaction.show(cFragment);
        }
        transaction.commit();

        initOpenId();
    }

    @Override
    public int setView() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.txt_courses)
    public void courses() {
        txtCourses.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.mipmap.courses), null, null);
        txtCourses.setTextColor(getColor(R.color.blue));

        txtMy.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.mipmap.my), null, null);
        txtMy.setTextColor(getColor(R.color.gray));

        transaction = getFragmentManager().beginTransaction();
        if (mFragment != null) {
            transaction.hide(mFragment);
        }
        if (cFragment == null) {
            cFragment = new CoursesFragment();
            transaction.add(R.id.fragment_container, cFragment);
        } else {
            transaction.show(cFragment);
        }
        transaction.commit();
    }

    @OnClick(R.id.txt_my)
    public void my() {
        txtMy.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.mipmap.my), null, null);
        txtMy.setTextColor(getColor(R.color.blue));

        txtCourses.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.mipmap.courses), null, null);
        txtCourses.setTextColor(getColor(R.color.gray));

        transaction = getFragmentManager().beginTransaction();
        if (cFragment != null) {
            transaction.hide(cFragment);
        }
        if (mFragment == null) {
            mFragment = new MyFragment();
            transaction.add(R.id.fragment_container, mFragment);
        } else {
            transaction.show(mFragment);
        }
        transaction.commit();
    }

    private boolean isRun = true;
    private int index = 0;
    private static String openId = "-1";// 需要的数据 最重要就是获取这个数据
    private static String jwtToken = "-1";// 需要的数据

    private WXCodeToOpenId codeToOpenId;

    // 初始化网络请求需要的 openId 参数
    private void initOpenId() {
        codeToOpenId = new WXCodeToOpenId();
        codeToOpenId.request();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRun) {
                    openId = codeToOpenId.getOpenId();
                    jwtToken = codeToOpenId.getJwtToken();

                    StringConstants.JWT_TOKEN = jwtToken;// 数据传递
                    StringConstants.OPEN_ID = openId;// 数据传递

                    if (index == 10) {
                        isRun = false;
                    }
                    if ((openId !=null && !openId.equals("-1")) && (jwtToken != null && !jwtToken.equals("-1"))) {
                        isRun = false;
                    }
                    try {
                        Thread.sleep(500);
                        index++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * 将 openId 返回
     */
    public static String getOpenId() {
        return openId;
    }

    /**
     * 将 jwtToken 返回
     */
    public static String getJwtToken() {
        return jwtToken;
    }

    // 点击两次返回提示退出
    private long time;

    @Override
    public void onBackPressed() {
        long nowTime = System.currentTimeMillis();
        if (nowTime - time >= 2000) {
            time = nowTime;
            T.alwaysLong(MainActivity.this, "再按一次返回键退出程序");
        } else {
            super.onBackPressed();
        }
    }
}
