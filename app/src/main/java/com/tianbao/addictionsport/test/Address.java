package com.tianbao.addictionsport.test;

import java.io.Serializable;

/**
 * 地址
 * Created by edianzu on 2017/9/8.
 */

public class Address implements Serializable {

    private String detailAddress;

    private String latitude;

    private String longitude;

    private String name;

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
