package com.example.myapplication;

public class HomeItem {
    private int id;
    private String name, desc, URL;

    public HomeItem(int id, String name, String desc, String  URL) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
