package com.example.ed;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopDetailFragment extends Activity {

    // define variable
    private int shop_id;
    // define method
    public void initPage(View v) {

    }

    public void initShop() {
//        TextView shop_name, TextView sort_title, TextView type, TextView url_image, Button likes, TextView cuisine, TextView location
        TextView shop_name = findViewById(R.id.shop_name);
        TextView sort_title = findViewById(R.id.sort_title);
        TextView type = findViewById(R.id.shop_type);
        ImageView image = findViewById(R.id.shop_image);
//        TextView cuisine = findViewById(R.id.shop_cuisine);
        TextView location = findViewById(R.id.location);
//        TextView like_text = findViewById(R.id.like_text);
        Button likes = findViewById(R.id.like);

        String pathUrl = "/get_shop_detail";
        String params = "{\"shop_id\":" + shop_id + "}";
//        ShopDetailRequest get = new ShopDetailRequest(pathUrl, params, getApplicationContext(), shop_name, sort_title, type, image, likes, cuisine, location);
//        ShopDetailRequest get = new ShopDetailRequest(pathUrl, params, getApplicationContext(), shop_name, sort_title, type, image, likes, cuisine, location, like_text);
        ShopDetailRequest get = new ShopDetailRequest(pathUrl, params, getApplicationContext(), shop_name, sort_title, type, image, likes, location);
        get.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_detail);

        shop_id = getIntent().getIntExtra("shop_id", 0);
        System.out.println(shop_id);
        initShop();
    }
}

