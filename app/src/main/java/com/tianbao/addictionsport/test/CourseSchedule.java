package com.tianbao.addictionsport.test;

import java.io.Serializable;

/**
 * 课程
 * Created by edianzu on 2017/9/8.
 */

public class CourseSchedule implements Serializable {

    private String code;

    private Data data;

    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
