package com.tianbao.addictionsport.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.tianbao.addictionsport.R;

/**
 * base
 * Created by edianzu on 2017/9/9.
 */
public abstract class BaseActivity extends Activity {
    private TextView textTitle;// 标题
    private TextView textBack;// 返回

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setView());
        init();
        initViews();
    }

    //  初始化
    private void init() {
        textBack = findViewById(R.id.text_back);// 返回
        textBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textTitle = findViewById(R.id.text_title);// 标题
        textTitle.setText("冲瘾健身");
    }

    /**
     * 设置标题
     */
    protected void setTextTitle(String title) {
        if (title != null && !title.equals("")) {
            textTitle.setText(title);
        } else {
            textTitle.setText("冲瘾健身");
        }
    }

    /**
     * 设置没有返回键
     */
    protected void setNoBack() {
        textBack.setVisibility(View.GONE);
    }

    /**
     * 初始化视图
     */
    public abstract void initViews();

    /**
     * 设置视图
     */
    public abstract int setView();
}
