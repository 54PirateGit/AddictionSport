package com.tianbao.addictionsport.activity;

import android.content.Intent;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tianbao.addictionsport.R;
import com.tianbao.addictionsport.base.BaseActivity;
import com.tianbao.addictionsport.constant.StringConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 加载 WebView
 */
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView webView;

    @Override
    public void initViews() {
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            Log.e("TAG", "intent is null");
            return;
        }
        String url = intent.getStringExtra(StringConstants.BANNER_REDIRECT_URL);// 获取 url
        if (url == null || url.equals("")) {
            Log.e("TAG", "Url is null");
            return;
        }

        Log.i("TAG", "url == " + url);
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);// 支持 js
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);// 解决缓存问题
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);// 使用当前的 WebView 加载页面
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
    }

    @Override
    public int setView() {
        return R.layout.activity_web_view;
    }
}
