package com.example.ed;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
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
    private ImageView image;
    private TextView location;
    private Button likes;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String ipAddress = "http://192.168.1.10:5000";

//    public ShopDetailRequest(String pathUrl, String params, Context ctx, TextView shop_name, TextView sort_title, TextView type, ImageView image, Button likes, TextView cuisine, TextView location, TextView like_text) {
//        this.url = this.ipAddress + pathUrl;
//        this.bodyParams = params;
//        this.ctx = ctx;
//
//        this.shop_name = shop_name;
//        this.sort_title = sort_title;
//        this.type = type;
//        this.image = image;
//        this.cuisine = cuisine;
//        this.location = location;
//        this.likes = likes;
//        this.like_text = like_text;
//
//    }

    public ShopDetailRequest(String pathUrl, String params, Context ctx, TextView shop_name, TextView sort_title, TextView type, ImageView image, Button likes, TextView location) {
        this.url = this.ipAddress + pathUrl;
        this.bodyParams = params;
        this.ctx = ctx;

        this.shop_name = shop_name;
        this.sort_title = sort_title;
        this.type = type;
        this.image = image;
        this.location = location;
        this.likes = likes;

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
                    System.out.println("shop_name : " + shop_name);
                    System.out.println("location : " +  location);
//                    updateUI c = new ShopDetailRequest.updateUI(ctx, shop_name, sort_title,  type, image, likes, cuisine, location, response.body().string(), like_text);
                    updateUI c = new ShopDetailRequest.updateUI(ctx, shop_name, sort_title,  type, image, likes, location, response.body().string());
                    c.updateShop();
//                    JSONObject data = new JSONObject(response.body().string());
//                    JSONObject shop = (JSONObject) data.get("shops");

//                    String cuisine = shop.getString("cuisine");
//                    String likes = shop.getString("likes");
//                    String location = shop.getString("location");
//                    String name= shop.getString("name");
//                    String sort_title = shop.getString("sort_title");
//                    String type = shop.getString("type");

//                    cuisine.setText(shop.getString("cuisine"));
//                    likes.setText(shop.getString("likes"));
//                    location.setText(shop.getString("location"));
//                    shop_name.setText(shop.getString("name"));
//                    sort_title.setText(shop.getString("sort_title"));
//                    type.setText(shop.getString("type"));

//                    System.out.println("shops: " + shop);
//                    JSONObject json= (JSONObject) new JSONTokener(response.body().string()).nextValue();
//                    JSONObject data = json.getJSONObject("shops");
//                    test = (String) json2.get("name");


//                    JSONObject data= (JSONObject) new JSONTokener("shops").nextValue();
//                    JSONObject json2 = data.getJSONObject("results")

//                    JSONObject shop= (JSONObject) data.get("shops");
//                    JSONArray shopArray = data.getJSONArray("shops");
//                    System.out.println(shopArray);
//                    if (shopArray == null) {
//                        return;
//                    }

//                    this.shop_name = shop_name;
//                    this.sort_title = sort_title;
//                    this.type = type;
//                    this.image = image;
//                    this.cuisine = cuisine;
//                    this.location = location;
//                    this.likes = likes;\


//                    ShopDetailRequest.DownloadImageFromInternet difi = new ShopDetailRequest.DownloadImageFromInternet(image);
//                    difi.execute(data.getString("url_image"));

//                    String url_image = data.getString("url_image");
//                    JSONArray categoryArray = data.getJSONArray("categories");
//                    JSONArray shipping = data.getJSONArray("shipping");
//                    JSONArray time = data.getJSONArray("time");



//                    for (int i = 0; i< categoryArray.length(); i++) {
//                        productList = new ArrayList<Product>();
//                        JSONObject object = categoryArray.getJSONObject(i);
//                        String categories_id = object.getString("categories_id");
//                        String category_name = object.getString("name");
//                        JSONArray productArray = data.getJSONArray("products");
//
//                        for (int u = 0; u< productArray.length(); u++) {
//                            JSONObject product = categoryArray.getJSONObject(i);
////                            String product_category_id = product.getString("category_id");
//                            String product_name = product.getString("name");
//                            String product_price = product.getString("price");
//                            String products_id = product.getString("products_id");
//                            String product_url_image = product.getString("url_image");
//
//                            Product p = new Product(product_name, product_price, products_id, product_url_image);
//
//                            productList.add(p);
//                        }
//
//                        Category c = new Category(category_name, productList);
//                        categoryList.add(c);
//                    }
//
//                    for (int i = 0; i< shipping.length(); i++) {
//                        JSONObject product = shipping.getJSONObject(i);
//                        String ship_brand = product.getString("ship_brand");
//                        String shipping_id = product.getString("shipping_id");
//                        String shop_id = product.getString("shop_id");
//
//                        Shipping p = new Shipping(ship_brand, shipping_id, shop_id);
//
//                        shippingList.add(p);
//                    }
//
//                    for (int i = 0; i< time.length(); i++) {
//                        JSONObject product = time.getJSONObject(i);
//                        String end = product.getString("end");
//                        String name = product.getString("name");
//                        String shop_id = product.getString("shop_id");
//                        String start = product.getString("start");
//                        String time_id = product.getString("time_id");
//
//                        Time t = new Time(end, name, shop_id, start, time_id);
//
//                        timeList.add(t);
//                    }

//                    ShopDetail shopDetail = new ShopDetail(categoryList, cuisine, likes, location, shop_name, sort_title, type, url_image, shippingList, timeList);
//                    shopDetailList.add(shopDetail);

//                    ShopDetailAdapter shopDetailAdapter = new ShopDetailAdapter(ctx, shopDetailList);

//

//                    shopAdapter.notifyDataSetChanged();
//

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
        private TextView like_text;
        private Button likes;
        private String JsonList;

        public updateUI(Context context, TextView shop_name, TextView sort_title, TextView type, ImageView image, Button likes, TextView location, String JsonList){
            this.shop_name = shop_name;
            this.sort_title = sort_title;
            this.type = type;
            this.image = image;
            this.location = location;
            this.likes = likes;
            this.JsonList = JsonList;

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
                        JSONObject shop = (JSONObject) data.get("shops");
                        System.out.println(location);

//                        cuisine.setText(shop.getString("cuisine"));
                        likes.setText(shop.getString("likes"));
                        location.setText(shop.getString("location"));
                        shop_name.setText(shop.getString("name"));
                        sort_title.setText(shop.getString("sort_title"));
                        type.setText(shop.getString("type"));
//                        String url_image = shop.getString("url_image");

                        ShopDetailRequest.DownloadImageFromInternet difi = new ShopDetailRequest.DownloadImageFromInternet(image);
                        difi.execute(shop.getString("url_image"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        private void runOnUiThread(Runnable r) {
            handler.post(r);
        }
    }
}
