package com.tianbao.addictionsport.test;

import java.io.Serializable;

/**
 * banner
 * Created by edianzu on 2017/9/8.
 */

public class Banner implements Serializable {

    private String disable;

    private Event event;

    private String icon;

    public String getDisable() {
        return disable;
    }

    public void setDisable(String disable) {
        this.disable = disable;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
