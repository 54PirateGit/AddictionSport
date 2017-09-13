package com.tianbao.addictionsport.test;

import java.io.Serializable;
import java.util.List;

/**
 * Course
 * Created by edianzu on 2017/9/8.
 */

public class Course implements Serializable {

    private Button button;

    private Coach coach;

    private String id;

    private String price;

    private String shortTime;

    private String stock;

    private List<String> tags;

    private String time;

    private String title;

    private String yenPrice;

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShortTime() {
        return shortTime;
    }

    public void setShortTime(String shortTime) {
        this.shortTime = shortTime;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYenPrice() {
        return yenPrice;
    }

    public void setYenPrice(String yenPrice) {
        this.yenPrice = yenPrice;
    }
}
