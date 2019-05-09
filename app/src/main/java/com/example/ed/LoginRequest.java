package com.example.ed;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class LoginRequest extends AsyncTask {
    private String url;
    private String bodyParams;
    private ArrayList<Shop> shopList = new ArrayList<Shop>();
    private Context ctx;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String ipAddress = "http://35.247.173.72:5000";

    public LoginRequest(String pathUrl, String params, Context ctx) {
        this.url = this.ipAddress + pathUrl;
        this.bodyParams = params;
        this.ctx = ctx;
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

                try {
                    Log.e("login", response.body().string());

                } catch (Exception e) {
                    Log.e("err",e.getMessage());
                }
            }
        });

        return null;
    }

    class changeListShop {
        private final Handler handler;
        private GridView listShop;
        private  ShopAdapter shopAdapter;

        public changeListShop(Context context, GridView listShop, ShopAdapter shopAdapter){
            this.listShop = listShop;
            this.shopAdapter = shopAdapter;
            handler = new Handler(context.getMainLooper());
        }

        public void updateShop() {
            // Do work
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listShop.setAdapter(shopAdapter);
                }
            });
        }

        private void runOnUiThread(Runnable r) {
            handler.post(r);
        }
    }
}

