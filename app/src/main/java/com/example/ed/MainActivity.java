package com.example.ed;

import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {



    // def variable
    FragmentTransaction ft = getFragmentManager().beginTransaction();

    // def method
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
//            LoginFragment loginFragment = new LoginFragment();

            switch (item.getItemId()) {
                case R.id.menu_home:
                    ft.replace(R.id.main_content, new HomeFragment());
                    ft.commit();
                    return true;
                case R.id.menu_search:
                    ft.replace(R.id.main_content, new SearchFragment());
                    ft.commit();
                    return true;
                case R.id.menu_login:
                    ft.replace(R.id.main_content, new LoginFragment());
                    ft.commit();
                    return true;

                case R.id.menu_search_api:
                    ft.replace(R.id.main_content, new SearchApiFragment());
                    ft.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.add(R.id.main_content, new RegisterFragment());
//        ft.commit();
        // test Fragment
//        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        ft.replace(R.id.main_content, new HomeFragment());
        ft.commit();
//
//        // navigation
        BottomNavigationView navView = findViewById(R.id.my_toolbar);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        findTypeRequest get_all_shop = new findTypeRequest("/get_all_shop","{\"type_id\":3}");
//        get_all_shop.execute();



    }
}



class findTypeRequest extends AsyncTask {
    private String url;
    private String bodyParams;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String ipAddress = "http://192.168.1.10:5000";

    public findTypeRequest(String pathUrl, String params) {
        this.url = this.ipAddress + pathUrl;
        this.bodyParams = params;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        OkHttpClient client = new OkHttpClient();

        final RequestBody body = RequestBody.create(JSON, bodyParams);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", e.getMessage());
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.e("type find", response.body().string());
            }
        });

        return null;
    }
}


//class GetAllShopRequest extends AsyncTask {
//    private String url;
//    private String bodyParams;
//    private ArrayList <Shop> shopList = new ArrayList<Shop>();
//
//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//
//    private String ipAddress = "http://192.168.1.10:5000";
//
//    public GetAllShopRequest(String pathUrl, String params) {
//        this.url = this.ipAddress + pathUrl;
//        this.bodyParams = params;
//    }
//
//    @Override
//    protected Object doInBackground(Object[] params) {
//        OkHttpClient client = new OkHttpClient();
//
//        final RequestBody body = RequestBody.create(JSON, bodyParams);
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("TAG", e.getMessage());
//                call.cancel();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                try {
//                    JSONObject data = new JSONObject(response.body().string());
//                    JSONArray shopArray = data.getJSONArray("shops");
//
//                    for (int i = 0; i< shopArray.length(); i++) {
//                        JSONObject object = shopArray.getJSONObject(i);
//                        String localtion = object.getString("location");
//                        String name = object.getString("name");
//                        String shop_id = object.getString("shop_id");
//                        String url_image = object.getString("url_image");
//
//                        shopList.add(new Shop(shop_id, name, localtion, url_image));
//                    }
//
//                    Log.e("shop List:", shopList.size() + "");
////
//
//                } catch (Exception e) {
//                    Log.e("err",e.getMessage());
//                }
//
////                System.out.println("get all shop: " + response.body().string());
//            }
//        });
//
//        return null;
//    }
//}
