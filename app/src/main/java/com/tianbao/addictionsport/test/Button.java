package com.tianbao.addictionsport.test;

import java.io.Serializable;

/**
 * button
 * Created by edianzu on 2017/9/8.
 */

public class Button implements Serializable {

    private String disable;

    private String title;

    public String getDisable() {
        return disable;
    }

    public void setDisable(String disable) {
        this.disable = disable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
