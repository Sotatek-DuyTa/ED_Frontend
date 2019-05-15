package com.example.ed;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShopDetailRequest extends AsyncTask {
    private String url;
    private String bodyParams;
    private ArrayList<Category> categoryList = new ArrayList<Category>();
    private ArrayList<Shipping> shippingList = new ArrayList<Shipping>();
    private ArrayList<Time> timeList = new ArrayList<Time>();
    private ArrayList<Product> productList = new ArrayList<Product>();
    private Context ctx;
    private GridView listShopDetail;
    private ArrayList<ShopDetail> shopDetailList = new ArrayList<ShopDetail>();

    private TextView shop_name;
    private TextView sort_title ;
    private TextView type;
    private TextView time;
    private TextView ship;
    private ImageView image;
    private TextView location;
    private Button likes;
    private WebView webView;
    private  String params;
    private  int shop_id;
    private  int user_id;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String ipAddress = "http://35.198.228.3:5000";

    public ShopDetailRequest(String pathUrl, String params, Context ctx, TextView shop_name, TextView sort_title, TextView type, ImageView image, Button likes, TextView location, WebView webView, int shop_id, int user_id, TextView time, TextView ship) {
        this.url = this.ipAddress + pathUrl;
        this.bodyParams = params;
        this.ctx = ctx;

        this.shop_name = shop_name;
        this.sort_title = sort_title;
        this.type = type;
        this.image = image;
        this.location = location;
        this.likes = likes;
        this.webView = webView;
        this.shop_id = shop_id;
        this.user_id = user_id;
        this.time = time;
        this.ship = ship;
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
                    updateUI c = new ShopDetailRequest.updateUI(ctx, shop_name, sort_title,  type, image, likes, location, response.body().string(), webView, bodyParams, shop_id, user_id, time, ship);
                    c.getCategory_Comment();
                    c.updateShop();

                } catch (Exception e) {
                    Log.e("err",e.getMessage());
                }
            }
        });

        return null;
    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
//            Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
            imageView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    class updateUI {
        private final Handler handler;
        private TextView shop_name;
        private TextView sort_title ;
        private TextView type;
        private ImageView image;
//        private TextView cuisine ;
        private TextView location;
        private TextView time;
        private TextView ship;
        private TextView like_text;
        private Button likes;
        private String JsonList;
        private WebView webView;
        private String params;
        private Integer shop_id;
        private Integer user_id;
        private Context ctx;

        public updateUI(Context context, TextView shop_name, TextView sort_title, TextView type, ImageView image, Button likes, TextView location, String JsonList, WebView webView, String params, int shop_id, int user_id, TextView time, TextView ship){
            this.shop_name = shop_name;
            this.sort_title = sort_title;
            this.type = type;
            this.time = time;
            this.ship = ship;
            this.image = image;
            this.location = location;
            this.likes = likes;
            this.JsonList = JsonList;
            this.webView = webView;
            this.params = params;
            this.shop_id = shop_id;
            this.user_id = user_id;
            this.ctx = context;

            handler = new Handler(context.getMainLooper());
        }

        public void updateShop() {
            // Do work
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = null;
                    try {
                        data = new JSONObject(JsonList);
                        ShopDetailRequest.DownloadImageFromInternet difi = new ShopDetailRequest.DownloadImageFromInternet(image);
                        JSONObject shop = (JSONObject) data.get("shops");

                        difi.execute(shop.getString("url_image"));

//                        cuisine.setText(shop.getString("cuisine"));
                        likes.setText(shop.getString("likes"));
                        String start = ((shop.getJSONArray("time")).getJSONObject(0)).getString("start");
                        String end = ((shop.getJSONArray("time")).getJSONObject(1)).getString("end");

                        time.setText("Giờ mở cửa: " + start +" -> " + end);

                        JSONArray shipList = shop.getJSONArray("shipping");
                        String ship_text = "";
                        for (int i = 0; i< shipList.length() ; ++i) {
                            ship_text += shipList.getJSONObject(i).getString("ship_brand");
                            if( i < shipList.length() - 1) {
                                ship_text += ", ";
                            }
                        }

                        ship.setText(ship_text);


//                        String s = ((MyApplication) ((Activity) ctx).getApplication()).getUserId();
//                        String user_id = ((MyApplication) ((Activity) ctx).getApplication()).getUserId();
                        likes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (user_id != -1) {
                                    System.out.println("clicked");
                                    like_shop ls = new like_shop(user_id, shop_id, likes);
                                    ls.execute();
                                } else {
                                    new AlertDialog.Builder(v.getContext())
                                    .setTitle("Bạn hãy đăng nhập để dùng chức năng này")
                                        .setNegativeButton("Ok", null).show();
                                }
                            }
                        });


                        location.setText(shop.getString("location"));
                        shop_name.setText(shop.getString("name"));
                        if (shop.getString("sort_title") != "") {
                            sort_title.setText(shop.getString("sort_title"));
                        } else {
                            sort_title.setVisibility(View.INVISIBLE);
                        }
                        type.setText(shop.getString("type"));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

//        public void likeHandle () {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            });
//        }

        public void getCategory_Comment () {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.setWebViewClient(new WebViewClient());
                    WebSettings webSettings = webView.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    Log.e("user_id", "user_id" +user_id);
                    if (user_id != -1) {
                        webView.loadUrl("http://192.168.1.10:8080/product/" + shop_id + "/" + user_id);
                    } else {
                        webView.loadUrl("http://192.168.1.10:8080/product/" + shop_id );
                    }
//                    System.out.println("http://192.168.1.10:8080/product/" + shop_id);

                }
            });
        }

        private void runOnUiThread(Runnable r) {
            handler.post(r);
        }
    }
}


class like_shop extends AsyncTask {
    private Integer user_id;
    private Integer shop_id;
    private Button likes;
    private String url = "http://35.198.228.3:5000/users_likes_shop";

    public like_shop(Integer user_id, Integer shop_id, Button likes) {
        super();
        this.user_id = user_id;
        this.shop_id = shop_id;
        this.likes = likes;
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected Object doInBackground(Object[] params) {
        OkHttpClient client = new OkHttpClient();

        final RequestBody body = RequestBody.create(JSON, "{\"users_id\":" + user_id + ", \"shop_id\": " + shop_id + "}");

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
                    JSONObject res = new JSONObject(response.body().string());
                    Integer count_like = res.getInt("count_like");
                    System.out.println("hello userid" + user_id);
                    likes.setText(count_like + "");

                } catch (Exception e) {
                    Log.e("err", e.getMessage());
                }
            }
        });

        return null;
    }
}