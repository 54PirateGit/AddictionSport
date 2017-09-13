package com.tianbao.addictionsport.test;

import java.io.Serializable;

/**
 * coach
 * Created by edianzu on 2017/9/8.
 */

public class Coach implements Serializable {

    private String avatar;

    private String id;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
