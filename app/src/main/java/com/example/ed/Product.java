package com.example.ed;

public class Product {
    private String name;
    private String price;
    private String products_id;
    private String url_image;

    public Product(String name, String price, String products_id, String url_image) {
        this.name = name;
        this.price = price;
        this.products_id = products_id;
        this.url_image = url_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProducts_id() {
        return products_id;
    }

    public void setProducts_id(String products_id) {
        this.products_id = products_id;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }
}
