package com.example.ed;

public class Time {
    private String end;
    private String name;
    private String shop_id;
    private String start;
    private String time_id;

    public Time(String end, String name, String shop_id, String start, String time_id) {
        this.end = end;
        this.name = name;
        this.shop_id = shop_id;
        this.start = start;
        this.time_id = time_id;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getTime_id() {
        return time_id;
    }

    public void setTime_id(String time_id) {
        this.time_id = time_id;
    }
}
