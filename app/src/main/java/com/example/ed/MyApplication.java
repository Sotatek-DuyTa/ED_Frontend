package com.example.ed;

import android.app.Application;

public class MyApplication extends Application {
    private int user_id = -1;

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public int getCurrentUserId() {
        return this.user_id;
    }

    public Boolean isAuth () {
        return this.user_id != -1;
    }
}
