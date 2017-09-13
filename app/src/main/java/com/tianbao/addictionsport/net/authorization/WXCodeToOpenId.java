package com.tianbao.addictionsport.net.authorization;

import com.tianbao.addictionsport.constant.IntegerConstants;
import com.tianbao.addictionsport.constant.StringConstants;
import com.tianbao.addictionsport.mode.OpenId;
import com.tianbao.addictionsport.net.ApiService;
import com.tianbao.addictionsport.net.NetApi;
import com.tianbao.addictionsport.utils.L;

import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 微信Code 换取 openId
 * Created by edianzu on 2017/9/11.
 */

public class WXCodeToOpenId {
    private String openId;
    private String jwtToken;

    /**
     * 发送请求
     */
    public void request() {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(NetApi.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Map<String, String> param = new HashMap<>();
        param.put("code", StringConstants.WE_CHAT_CODE);

        ApiService service = retrofit.create(ApiService.class);
        Call<OpenId> model = service.wxCodeToOpenId(param);
        model.enqueue(new Callback<OpenId>() {
            @Override
            public void onResponse(Response<OpenId> response, Retrofit retrofit) {
                int code = response.body().getCode();
                if (code == IntegerConstants.RESULT_CODE) {
                    String message = response.body().getMessage();
                    L.e("openId == " + message);

                    if (message != null && !message.equals("")) {
                        setOpenId(message);
                    }

                    jwtToken = response.headers().get("jwtToken");
                    if (jwtToken == null || jwtToken.equals("")) {
                        jwtToken = "-1";
                    }
                    L.e("jwtToken == " + jwtToken);
                    setJwtToken(jwtToken);
                } else {
                    L.i("code == " + code);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                L.e("数据请求错误");
            }
        });
    }

    /**
     * 数据请求成功则设置 openId
     * @param openId openId
     */
    private void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * 将 openId 返回
     * @return openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置 jwtToken
     */
    private void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    /**
     * 获取 jwtToken
     */
    public String getJwtToken() {
        return jwtToken;
    }
}
