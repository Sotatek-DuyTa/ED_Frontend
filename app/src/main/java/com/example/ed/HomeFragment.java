package com.example.ed;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

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

public class HomeFragment extends Fragment {

    // define variable
    GridView listShop;
//    FragmentActivity myContext;

    // define method
    public void initListType(View v) {
        final Spinner spinner = (Spinner) v.findViewById(R.id.filter_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(), R.array.types_resource, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
//        spinner.setSelection(0);
        listShop = v.findViewById(R.id.list_shop);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, final View view, int pos, long id) {
                String pathUrl;
                String params;

                if (pos == 0) {
                    pathUrl = "/get_all_shop";
                    params = "{\"type_id\":" + pos + "}";
                } else {
                    pathUrl = "/find_type";
                    params = "{\"type_id\":" + pos + "}";
                }

                GetAllShopRequest get_all_shop = new GetAllShopRequest(pathUrl,params, view.getContext(), listShop);
                get_all_shop.execute();

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void changeFragment() {
        Log.e("Test","change");
    }

    public void initListCuisine(View v) {
        Spinner spinner2 = (Spinner) v.findViewById(R.id.filter_cuisine);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(), R.array.cuisines_resource, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        listShop = v.findViewById(R.id.list_shop);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, final View view, int pos, long id) {
                String pathUrl;
                String params;

                if (pos == 0) {
                    pathUrl = "/get_all_shop";
                    params = "{\"cuisine_id\":" + pos + "}";
                } else {
                    pathUrl = "/find_cuisine";
                    params = "{\"cuisine_id\":" + pos + "}";
                }

                GetAllShopRequest get_all_shop = new GetAllShopRequest(pathUrl,params, view.getContext(), listShop);
                get_all_shop.execute();

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.home, container, false);

        initListType(view);
        initListCuisine(view);

        listShop = view.findViewById(R.id.list_shop);

        listShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int user_id = ((MyApplication) ((Activity) v.getContext()).getApplication()).getCurrentUserId();

                Intent intent = new Intent(HomeFragment.this.getActivity(), ShopDetailFragment.class);
                intent.putExtra("shop_id", Integer.parseInt(GetAllShopRequest.shopList.get(position).getShop_id()) );
                intent.putExtra("user_id", user_id);
                startActivity(intent);
//                Log.e("errrr", "asdasdsadasda");
            }
        });

        return view;
    }
}


class GetAllShopRequest extends AsyncTask {
    private String url;
    private String bodyParams;
    public static ArrayList<Shop> shopList = new ArrayList<Shop>();
    private Context ctx;
    private GridView listShop;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String ipAddress = "http://35.198.228.3:5000";

    public GetAllShopRequest(String pathUrl, String params, Context ctx, GridView listShop) {
        this.url = this.ipAddress + pathUrl;
        this.bodyParams = params;
        this.ctx = ctx;
        this.listShop = listShop;

        System.out.println(params);
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
                    shopList = new ArrayList<Shop>();
                    System.out.println("runing");
                    JSONObject data = new JSONObject(response.body().string());
                    JSONArray shopArray;
                    try {
                        shopArray = data.getJSONArray("shops");
                    } catch(Exception e) {
                        shopArray = data.getJSONArray("shop");
                    }

                    for (int i = 0; i< shopArray.length(); i++) {
                        JSONObject object = shopArray.getJSONObject(i);
                        String localtion = object.getString("location");
                        String name = object.getString("name");
                        String shop_id = object.getString("shop_id");
                        String url_image = object.getString("url_image");

                        shopList.add(new Shop(shop_id, name, localtion, url_image));
                    }

                    ShopAdapter shopAdapter = new ShopAdapter(ctx, shopList);
                    changeListShop c = new changeListShop(ctx, listShop, shopAdapter);
                    c.updateShop();


                } catch (Exception e) {
                    Log.e("err",e.getMessage());
                    Log.e("err",e.getMessage() + "hello");
                }
            }
        });

        return null;
    }

    class changeListShop {
        private final Handler handler;
        private GridView listShop;
        private  ShopAdapter shopAdapter;
        private FragmentActivity myContext;

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