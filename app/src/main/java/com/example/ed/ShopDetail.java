package com.example.ed;

import java.util.ArrayList;

public class ShopDetail {
    private ArrayList <Category> categoryArray;
    private String cuisine;
    private String likes;
    private String location;
    private String name;
    private String sort_title;
    private String type;
    private String url_image;
    private ArrayList <Shipping> shipping;
    private ArrayList <Time> time;

    public ShopDetail(ArrayList<Category> categoryArray, String cuisine, String likes, String location, String name, String sort_title, String type, String url_image, ArrayList<Shipping> shipping, ArrayList<Time> time) {
        this.categoryArray = categoryArray;
        this.cuisine = cuisine;
        this.likes = likes;
        this.location = location;
        this.name = name;
        this.sort_title = sort_title;
        this.type = type;
        this.url_image = url_image;
        this.shipping = shipping;
        this.time = time;
    }

    public ArrayList<Category> getCategoryArray() {
        return categoryArray;
    }

    public void setCategoryArray(ArrayList<Category> categoryArray) {
        this.categoryArray = categoryArray;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort_title() {
        return sort_title;
    }

    public void setSort_title(String sort_title) {
        this.sort_title = sort_title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public ArrayList<Shipping> getShipping() {
        return shipping;
    }

    public void setShipping(ArrayList<Shipping> shipping) {
        this.shipping = shipping;
    }

    public ArrayList<Time> getTime() {
        return time;
    }

    public void setTime(ArrayList<Time> time) {
        this.time = time;
    }
}
