package com.tianbao.addictionsport.test;

import java.io.Serializable;
import java.util.List;

/**
 * course4Days
 * Created by edianzu on 2017/9/8.
 */

public class Course4Days implements Serializable {

    private List<Course> course;

    private String yearMonthDayFormat;

    public List<Course> getCourse() {
        return course;
    }

    public void setCourse(List<Course> course) {
        this.course = course;
    }

    public String getYearMonthDayFormat() {
        return yearMonthDayFormat;
    }

    public void setYearMonthDayFormat(String yearMonthDayFormat) {
        this.yearMonthDayFormat = yearMonthDayFormat;
    }
}
