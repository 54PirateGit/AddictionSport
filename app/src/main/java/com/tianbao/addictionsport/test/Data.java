package com.tianbao.addictionsport.test;

import java.io.Serializable;
import java.util.List;

/**
 * data
 * Created by edianzu on 2017/9/8.
 */

public class Data implements Serializable {

    private Address address;

    private List<Banner> banner;

    private List<Calendar> calendar;

    private List<Course4Day> course4Day;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Banner> getBanner() {
        return banner;
    }

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }

    public List<Calendar> getCalendar() {
        return calendar;
    }

    public void setCalendar(List<Calendar> calendar) {
        this.calendar = calendar;
    }

    public List<Course4Day> getCourse4Day() {
        return course4Day;
    }

    public void setCourse4Day(List<Course4Day> course4Day) {
        this.course4Day = course4Day;
    }

    @Override
    public String toString() {
        return "Data{" +
                "address=" + address +
                ", banner=" + banner +
                ", calendar=" + calendar +
                ", course4Day=" + course4Day +
                '}';
    }
}
