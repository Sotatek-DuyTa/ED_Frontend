package com.example.ed;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.GridView;

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

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String ipAddress = "http://35.247.173.72:5000";

    public ShopDetailRequest(String pathUrl, String params, Context ctx, GridView listShopDetail) {
        this.url = this.ipAddress + pathUrl;
        this.bodyParams = params;
        this.ctx = ctx;
        this.listShopDetail = listShopDetail;
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
                    JSONObject data = new JSONObject(response.body().string());
                    JSONArray shopArray = data.getJSONArray("shops");
                    if (shopArray == null) {
                        return;
                    }

                    JSONArray categoryArray = data.getJSONArray("categories");
                    String cuisine = data.getString("cuisine");
                    String likes = data.getString("likes");
                    String location = data.getString("location");
                    String shop_name = data.getString("name");
                    String sort_title = data.getString("sort_title");
                    String type = data.getString("type");
                    String url_image = data.getString("url_image");
                    JSONArray shipping = data.getJSONArray("shipping");
                    JSONArray time = data.getJSONArray("time");



                    for (int i = 0; i< categoryArray.length(); i++) {
                        productList = new ArrayList<Product>();
                        JSONObject object = categoryArray.getJSONObject(i);
                        String categories_id = object.getString("categories_id");
                        String category_name = object.getString("name");
                        JSONArray productArray = data.getJSONArray("products");

                        for (int u = 0; u< productArray.length(); u++) {
                            JSONObject product = categoryArray.getJSONObject(i);
//                            String product_category_id = product.getString("category_id");
                            String product_name = product.getString("name");
                            String product_price = product.getString("price");
                            String products_id = product.getString("products_id");
                            String product_url_image = product.getString("url_image");

                            Product p = new Product(product_name, product_price, products_id, product_url_image);

                            productList.add(p);
                        }

                        Category c = new Category(category_name, productList);
                        categoryList.add(c);
                    }

                    for (int i = 0; i< shipping.length(); i++) {
                        JSONObject product = shipping.getJSONObject(i);
                        String ship_brand = product.getString("ship_brand");
                        String shipping_id = product.getString("shipping_id");
                        String shop_id = product.getString("shop_id");

                        Shipping p = new Shipping(ship_brand, shipping_id, shop_id);

                        shippingList.add(p);
                    }

                    for (int i = 0; i< time.length(); i++) {
                        JSONObject product = time.getJSONObject(i);
                        String end = product.getString("end");
                        String name = product.getString("name");
                        String shop_id = product.getString("shop_id");
                        String start = product.getString("start");
                        String time_id = product.getString("time_id");

                        Time t = new Time(end, name, shop_id, start, time_id);

                        timeList.add(t);
                    }

                    ShopDetail shopDetail = new ShopDetail(categoryList, cuisine, likes, location, shop_name, sort_title, type, url_image, shippingList, timeList);
                    shopDetailList.add(shopDetail);

                    ShopDetailAdapter shopDetailAdapter = new ShopDetailAdapter(ctx, shopDetailList);

//
                    updateUI c = new ShopDetailRequest.updateUI(ctx, listShopDetail, shopDetailAdapter);
                    c.updateShop();
//                    shopAdapter.notifyDataSetChanged();
//

                } catch (Exception e) {
                    Log.e("err",e.getMessage());
                }
            }
        });

        return null;
    }

    class updateUI {
        private final Handler handler;
        private GridView listShopDetail;
        private  ShopDetailAdapter shopDetailAdapter;

        public updateUI(Context context, GridView listShopDetail, ShopDetailAdapter shopDetailAdapter){
            this.listShopDetail = listShopDetail;
            this.shopDetailAdapter = shopDetailAdapter;
            handler = new Handler(context.getMainLooper());
        }

        public void updateShop() {
            // Do work
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listShopDetail.setAdapter(shopDetailAdapter);
                }
            });
        }

        private void runOnUiThread(Runnable r) {
            handler.post(r);
        }
    }
}
