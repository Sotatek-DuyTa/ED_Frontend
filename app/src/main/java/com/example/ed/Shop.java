package com.example.ed;

import java.io.Serializable;

public class Shop implements Serializable {
    private String shop_id;
    private String name;
    private String location;
    private String url_image;

    public Shop(String shop_id, String name, String location, String url_image) {
        this.shop_id = shop_id;
        this.name = name;
        this.location = location;
        this.url_image = url_image;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }
}
