package com.example.ed;

public class Shipping {
    private String ship_brand;
    private String shipping_id;
    private String shop_id;

    public Shipping(String ship_brand, String shipping_id, String shop_id) {
        this.ship_brand = ship_brand;
        this.shipping_id = shipping_id;
        this.shop_id = shop_id;
    }

    public String getShip_brand() {
        return ship_brand;
    }

    public void setShip_brand(String ship_brand) {
        this.ship_brand = ship_brand;
    }

    public String getShipping_id() {
        return shipping_id;
    }

    public void setShipping_id(String shipping_id) {
        this.shipping_id = shipping_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }
}
