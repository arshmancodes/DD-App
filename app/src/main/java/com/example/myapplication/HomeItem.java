package com.example.myapplication;

public class HomeItem {
    private int id;
    private String name, desc;
    private byte[] image;

    public HomeItem(int id, String name, String desc, byte[] image) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.image = image;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
