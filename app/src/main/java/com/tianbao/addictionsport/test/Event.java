package com.tianbao.addictionsport.test;

import java.io.Serializable;

/**
 * event
 * Created by edianzu on 2017/9/8.
 */

public class Event implements Serializable {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
