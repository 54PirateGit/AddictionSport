package com.tianbao.addictionsport.test;

import java.io.Serializable;

/**
 * calendar
 * Created by edianzu on 2017/9/8.
 */

public class Calendar implements Serializable {

    private String dayOfWeek;

    private String dayOfWeekDetail;

    private String monthDayFormat;

    private String yearMonthDayFormat;

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDayOfWeekDetail() {
        return dayOfWeekDetail;
    }

    public void setDayOfWeekDetail(String dayOfWeekDetail) {
        this.dayOfWeekDetail = dayOfWeekDetail;
    }

    public String getMonthDayFormat() {
        return monthDayFormat;
    }

    public void setMonthDayFormat(String monthDayFormat) {
        this.monthDayFormat = monthDayFormat;
    }

    public String getYearMonthDayFormat() {
        return yearMonthDayFormat;
    }

    public void setYearMonthDayFormat(String yearMonthDayFormat) {
        this.yearMonthDayFormat = yearMonthDayFormat;
    }
}
